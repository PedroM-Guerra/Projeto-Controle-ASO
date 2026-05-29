package PedroM_Guerra.controle_aso.integrationtests.controllers;

import PedroM_Guerra.controle_aso.data.dto.UploadFileResponseDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "file.upload-dir=${java.io.tmpdir}/controle-aso-it-uploads"
})
class FileControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    @Test
    void uploadAndDownloadFile_success() throws Exception {
        byte[] content = "CONTEUDO_PDF_FAKE".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "aso-teste.pdf", "application/pdf", content);

        MvcResult uploadResult = mockMvc.perform(
                        multipart("/api/file/v1/uploadFile").file(file)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String uploadJson = uploadResult.getResponse().getContentAsString();
        UploadFileResponseDTO response = mapper.readValue(uploadJson, UploadFileResponseDTO.class);

        assertNotNull(response);
        assertNotNull(response.getFileName());
        assertTrue(response.getFileName().endsWith("aso-teste.pdf"));
        assertNotNull(response.getFileDowloadUri());
        assertTrue(response.getFileDowloadUri().contains("/api/file/v1/downloadFile/"));
        assertEquals("application/pdf", response.getFileType());
        assertEquals(content.length, response.getSize());

        MvcResult downloadResult = mockMvc.perform(
                        get("/api/file/v1/downloadFile/{fileName}", response.getFileName())
                )
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition",
                        org.hamcrest.Matchers.containsString("inline; filename=\"" + response.getFileName() + "\"")))
                .andReturn();

        byte[] downloaded = downloadResult.getResponse().getContentAsByteArray();
        assertArrayEquals(content, downloaded);
    }
}
