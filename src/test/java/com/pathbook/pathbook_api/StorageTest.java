package com.pathbook.pathbook_api;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pathbook.pathbook_api.dto.FileDto;
import com.pathbook.pathbook_api.exception.StorageFileNotFoundException;
import com.pathbook.pathbook_api.storage.StorageService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class StorageTest {
    @Autowired private MockMvc mvc;

    @MockitoBean StorageService storageService;

    @Test
    public void testFileSystemInit() throws Exception {
        storageService.init();
    }

    @Test
    public void shouldFindFile() throws Exception {
        FileDto fileDto = new FileDto();
        fileDto.setFilename("first.txt");
        fileDto.setOriginalFilename("first.txt");
        fileDto.setContentType("text/plain");
        fileDto.setResource(
                new org.springframework.core.io.ByteArrayResource("dummy content".getBytes()));

        given(storageService.loadFull("first.txt")).willReturn(fileDto);

        mvc.perform(get("/file/f/" + fileDto.getFilename())).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(
            username = "mockuser",
            roles = {"USER"})
    public void shouldSaveUploadedFile() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile(
                        "file", "test.txt", "text/plain", "Spring Framework".getBytes());

        mvc.perform(multipart("/file/upload").file(multipartFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url", Matchers.containsString("test.txt")));

        then(storageService).should().store(multipartFile, "mockuser");
    }

    @Test
    public void should404WhenMissingFile() throws Exception {
        String fileName = "missing-file.txt";

        given(storageService.loadFull(fileName)).willThrow(StorageFileNotFoundException.class);

        mvc.perform(get("/file/f/" + fileName)).andExpect(status().isNotFound());
    }
}
