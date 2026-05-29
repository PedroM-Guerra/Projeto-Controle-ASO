package PedroM_Guerra.controle_aso.unittests.services;

import PedroM_Guerra.controle_aso.config.FileStorageConfig;
import PedroM_Guerra.controle_aso.exception.FileNotFoundException;
import PedroM_Guerra.controle_aso.exception.FileStorageException;
import PedroM_Guerra.controle_aso.services.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FileStorageServiceTest {

    @TempDir
    Path tempDir;

    private FileStorageService service;

    @BeforeEach
    void setup() {
        FileStorageConfig cfg = Mockito.mock(FileStorageConfig.class);
        when(cfg.getUploadDir()).thenReturn(tempDir.toString());
        service = new FileStorageService(cfg);
    }

    @Test
    void storeFile_shouldSaveAndReturnUniqueName() throws Exception {
        byte[] content = "conteudo de teste".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "aso-teste.pdf", "application/pdf", content);

        String savedName = service.storeFile(file);

        assertNotNull(savedName);
        assertTrue(savedName.endsWith("-aso-teste.pdf") || savedName.endsWith("aso-teste.pdf"));
        Path savedPath = tempDir.resolve(savedName);
        assertTrue(Files.exists(savedPath));
        assertEquals(content.length, Files.size(savedPath));
    }

    @Test
    void storeFile_shouldRejectPathTraversal() {
        MockMultipartFile file = new MockMultipartFile("file", "../evil.txt", "text/plain", "x".getBytes());

        assertThrows(FileStorageException.class, () -> service.storeFile(file));
    }

    @Test
    void loadFileAsResource_shouldLoadPreviouslyStoredFile() throws Exception {
        byte[] content = "abc123".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "doc.pdf", "application/pdf", content);
        String savedName = service.storeFile(file);

        Resource resource = service.loadFileAsResource(savedName);

        assertNotNull(resource);
        assertTrue(resource.exists());
        assertEquals(content.length, resource.getContentAsByteArray().length);
    }

    @Test
    void loadFileAsResource_shouldThrowWhenNotFound() {
        assertThrows(FileNotFoundException.class, () -> service.loadFileAsResource("inexistente.pdf"));
    }
}
