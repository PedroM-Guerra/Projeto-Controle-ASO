package PedroM_Guerra.controle_aso.services;

import PedroM_Guerra.controle_aso.config.FileStorageConfig;
import PedroM_Guerra.controle_aso.exception.FileNotFoundException;
import PedroM_Guerra.controle_aso.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath()
                .toAbsolutePath().normalize();

        this.fileStorageLocation = path;
        try {
            logger.info("Creating Directories");
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e){
            logger.error("Could not create the directory where files will be stored!");
            throw new FileStorageException("Could not create the directory where files will be stored!", e);
        }
    }

    public String storeFile(MultipartFile file){

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (originalFileName.contains("..")){
                logger.error("Sorry, FileName contains a invalid path sequence \" + originalFileName");
                throw new FileStorageException("Sorry, FileName contains a invalid path sequence " + originalFileName);
            }

            //Cria um nome único baseado no tempo atual em milissegundos para evitar duplicidade
            String uniqueFileName = System.currentTimeMillis() + "-" + originalFileName;

            logger.info("Saving file in Disk");
            Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return uniqueFileName;
        } catch (Exception e){
            logger.error("Could not store file \" + originalFileName + \". Please try again!");
            throw new FileStorageException("Could not store file " + originalFileName + ". Please try again!", e);
        }

    }

    public Resource loadFileAsResource(String fileName){
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()){
                return resource;
            } else {
                logger.error("File not found " + fileName);
                throw new  FileNotFoundException("File not found " + fileName);
            }
        } catch (Exception e){
            logger.error("File not found " + fileName);
            throw new FileNotFoundException("File not found " + fileName, e);
        }
    }
}
