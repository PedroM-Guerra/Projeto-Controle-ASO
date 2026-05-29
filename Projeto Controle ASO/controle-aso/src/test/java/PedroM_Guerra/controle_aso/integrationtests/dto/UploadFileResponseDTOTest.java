package PedroM_Guerra.controle_aso.integrationtests.dto;

import PedroM_Guerra.controle_aso.data.dto.UploadFileResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UploadFileResponseDTOTest {

    @Test
    void gettersSettersEqualsHashCode() {
        UploadFileResponseDTO dto1 = new UploadFileResponseDTO();
        dto1.setFileName("arq.pdf");
        dto1.setFileDowloadUri("/api/file/v1/downloadFile/arq.pdf");
        dto1.setFileType("application/pdf");
        dto1.setSize(123L);

        assertEquals("arq.pdf", dto1.getFileName());
        assertEquals("/api/file/v1/downloadFile/arq.pdf", dto1.getFileDowloadUri());
        assertEquals("application/pdf", dto1.getFileType());
        assertEquals(123L, dto1.getSize());

        UploadFileResponseDTO dto2 = new UploadFileResponseDTO("arq.pdf",
                "/api/file/v1/downloadFile/arq.pdf", "application/pdf", 123L);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2.setSize(124L);
        assertNotEquals(dto1, dto2);
    }
}
