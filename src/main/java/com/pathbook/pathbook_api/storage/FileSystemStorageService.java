package com.pathbook.pathbook_api.storage;

import com.pathbook.pathbook_api.dto.FileDto;
import com.pathbook.pathbook_api.dto.FileMetaDto;
import com.pathbook.pathbook_api.entity.File;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.exception.StorageException;
import com.pathbook.pathbook_api.exception.StorageFileNotFoundException;
import com.pathbook.pathbook_api.exception.UserNotFoundException;
import com.pathbook.pathbook_api.repository.FileRepository;
import com.pathbook.pathbook_api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation;

    @Autowired private FileRepository fileRepository;

    @Autowired private UserRepository userRepository;

    public FileSystemStorageService(@Autowired StorageProperties properties) {
        if (properties.getLocation().trim().length() == 0) {
            throw new StorageException("File upload location cannot be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException ex) {
            throw new StorageException("Could not initialize storage", ex);
        }
    }

    @Override
    public FileMetaDto store(MultipartFile file, String ownerId) {
        // 소유자 검증, owner == null 이면 무시
        User owner = null;
        if (ownerId != null) {
            owner =
                    userRepository
                            .findById(ownerId)
                            .orElseThrow(() -> UserNotFoundException.withUserId(ownerId));
        }

        return store(file, owner);
    }

    @Override
    public FileMetaDto store(MultipartFile file, User owner) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }

        // 파일 이름 해시
        String originalFilename = file.getOriginalFilename();
        String hashedFilename = hashFileNameMd5(originalFilename);

        // 스토리지에 파일 저장 시도
        Path destinationFile;
        try {
            destinationFile =
                    this.rootLocation
                            .resolve(Paths.get(hashedFilename))
                            .normalize()
                            .toAbsolutePath();

            // 보안적 이슈 체크
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            throw new StorageException("Failed to store file.", ex);
        }

        // 저장한 파일을 데이터베이스에 인덱싱
        try {
            File fileEntity =
                    new File(
                            hashedFilename,
                            originalFilename,
                            file.getContentType(),
                            file.getSize(),
                            owner);

            return new FileMetaDto(fileRepository.save(fileEntity));
        } catch (Exception e) {
            throw new StorageException("File saved but failed to index in database.", e);
        }
    }

    @Override
    public List<FileMetaDto> storeAll(MultipartFile[] files, String ownerId) {
        // 소유자 검증, owner == null 이면 무시
        User owner = null;
        if (ownerId != null) {
            owner =
                    userRepository
                            .findById(ownerId)
                            .orElseThrow(() -> UserNotFoundException.withUserId(ownerId));
        }

        return storeAll(files, owner);
    }

    @Override
    public List<FileMetaDto> storeAll(MultipartFile[] files, User owner) {
        if (files == null || files.length == 0) {
            throw new StorageException("Failed to store empty file.");
        }

        List<File> fileEntities = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String hashedFilename = hashFileNameMd5(originalFilename);

            // 스토리지에 파일 저장 시도
            Path destinationFile;
            try {
                destinationFile =
                        this.rootLocation
                                .resolve(Paths.get(hashedFilename))
                                .normalize()
                                .toAbsolutePath();

                // 보안적 이슈 체크
                if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                    throw new StorageException("Cannot store file outside current directory.");
                }

                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException ex) {
                throw new StorageException("Failed to store file.", ex);
            }

            // 저장한 파일을 데이터베이스에 인덱싱 (엔티티만 생성)
            try {
                File fileEntity =
                        new File(
                                hashedFilename,
                                originalFilename,
                                file.getContentType(),
                                file.getSize(),
                                owner);

                fileEntities.add(fileEntity);

            } catch (Exception e) {
                throw new StorageException("File saved but failed to index in database.", e);
            }
        }

        return fileRepository.saveAll(fileEntities).stream()
                .map(FileMetaDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException ex) {
            throw new StorageException("Failed to read stored files", ex);
        }
    }

    @Override
    public FileMetaDto load(String filename) {
        File file =
                fileRepository
                        .findById(filename)
                        .orElseThrow(() -> new StorageFileNotFoundException(filename));

        return new FileMetaDto(file);
    }

    @Override
    public Path loadAsPath(String filename) {
        FileMetaDto fileMeta = load(filename);

        return rootLocation.resolve(fileMeta.getFilename());
    }

    @Override
    public FileDto loadFull(String filename) {
        try {
            FileMetaDto fileMeta = load(filename);
            Path filePath = rootLocation.resolve(fileMeta.getFilename());
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() && !resource.isReadable()) {
                throw new StorageFileNotFoundException(filename);
            }

            return new FileDto(fileMeta, filePath, resource);
        } catch (MalformedURLException ex) {
            throw new StorageFileNotFoundException(filename, ex);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void delete(String filename) {
        Path filePath = null;
        try {
            filePath = loadAsPath(filename);
        } catch (StorageFileNotFoundException ex) {
            // Skip, Log warning if logger has implemented.
        }

        if (filePath == null) {
            return;
        }

        FileSystemUtils.deleteRecursively(filePath.toFile());
    }

    private static String hashFileNameMd5(String originalFilename) {
        String ext = getFileExtension(originalFilename);
        String hashedFilename = null;
        try {
            String toHash = originalFilename + LocalDateTime.now().toString();

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toHash.getBytes());

            byte[] digest = md.digest();
            hashedFilename = String.format("%032x", new BigInteger(1, digest)) + "." + ext;
        } catch (Exception ex) {
            throw new StorageException("Failed to hash file name.", ex);
        }

        return hashedFilename;
    }

    private static String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1);
        }

        return "";
    }
}
