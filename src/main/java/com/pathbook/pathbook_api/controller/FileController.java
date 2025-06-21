package com.pathbook.pathbook_api.controller;

import com.pathbook.pathbook_api.dto.FileDto;
import com.pathbook.pathbook_api.dto.FileMeta;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.exception.StorageFileNotFoundException;
import com.pathbook.pathbook_api.storage.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("file")
public class FileController {
    @Autowired private StorageService storageService;

    /*
    @GetMapping("/list")
    public ResponseEntity<?> getFileList() {
        List<String> files =
                storageService
                        .loadAll()
                        .map(
                                path ->
                                        MvcUriComponentsBuilder.fromMethodName(
                                                        FileController.class,
                                                        "serveFile",
                                                        path.getFileName().toString())
                                                .build()
                                                .toUri()
                                                .toString())
                        .collect(Collectors.toList());

        return new ResponseEntity<>(Map.of("files", files), HttpStatus.OK);
    }
    */

    @GetMapping("/f/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        FileDto file = storageService.loadFull(filename);

        if (file == null) {
            throw new StorageFileNotFoundException(filename);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        headers.setContentDisposition(
                ContentDisposition.parse(
                        String.format("attachment; filename=\"%s\"", file.getOriginalFilename())));

        return new ResponseEntity<>(file.getResource(), headers, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> postUploadFile(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam MultipartFile file) {
        FileMeta storedFileMeta = storageService.store(file, userPrincipal);
        String fileUrl = resolveFileUrlEndpoint(storedFileMeta.getFilename());

        return new ResponseEntity<>(
                Map.of(
                        "message",
                        "You successfully uploaded " + file.getOriginalFilename() + "!",
                        "url",
                        fileUrl),
                HttpStatus.OK);
    }

    public static String resolveFileUrlEndpoint(String filename) {
        return MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFile", filename)
                .build()
                .toUri()
                .toString();
    }
}
