package PedroM_Guerra.controle_aso.controllers.docs;

import PedroM_Guerra.controle_aso.data.dto.UploadFileResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "File Endpoint", description = "Endpoints para upload e download de arquivos em disco")
public interface FileControllerDocs {

    @Operation(summary = "Upload a file to disk",
            description = "Uploads a single file (like an ASO PDF) to the server and returns its metadata.",
            tags = {"File Endpoint"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UploadFileResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    UploadFileResponseDTO uploadFile(
            @Parameter(description = "The file to be uploaded", required = true)
            @RequestParam("file")
            MultipartFile file);

    @Operation(summary = "Download a file from disk",
            description = "Downloads a specific file by its unique name.",
            tags = {"File Endpoint"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                    schema = @Schema(implementation = Resource.class)
                            )
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    ResponseEntity<Resource> downloadFile(
            @Parameter(description = "The unique filename stored in database", required = true, example = "1716142345678-meu_aso.pdf")
            @PathVariable("fileName") String fileName,

            @Parameter(hidden = true) // Esconde o HttpServletRequest do painel visual do Swagger
            HttpServletRequest request
    );
}
