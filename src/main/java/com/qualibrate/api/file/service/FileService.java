package com.qualibrate.api.file.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.qualibrate.api.commons.transformer.EntityToDtoTransformer;
import com.qualibrate.api.exceptions.ErrorCodes;
import com.qualibrate.api.exceptions.InvalidRequestException;
import com.qualibrate.api.exceptions.ResourceNotAvailableException;
import com.qualibrate.api.exceptions.UnSupportedFormatException;
import com.qualibrate.api.file.repository.File;
import com.qualibrate.api.file.repository.FileDTO;
import com.qualibrate.api.file.repository.FileRecord;
import com.qualibrate.api.file.repository.FileRepository;
import com.qualibrate.api.file.repository.FileStorageProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 * Service Layer for Project
 */
@Service
@Slf4j
public class FileService {

    @Autowired
    private FileRepository fileRepo;

    @Autowired
    private FileStorageProperties fileStorageProperties;

    @Autowired
    private EntityToDtoTransformer<FileRecord, FileDTO> fileEntityToDtoConverter;

    public Page<FileDTO> getFiles(Pageable pageable) {
        return fileRepo.findAll(pageable).map(fileEntityToDtoConverter);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public FileDTO createFile(File file) {
        FileRecord saved;
        try {
            saved = fileRepo.save(new FileRecord(file));
        } catch (DataIntegrityViolationException e) {
            log.error("File with uuid {} already exists", file.getUuid());
            throw new InvalidRequestException("error creating new File. uuid must be unique",
                ErrorCodes.ProjectEntityAPIErrors.PROJECTENTITY_ALREADY_EXISTS);
        }
        return fileEntityToDtoConverter.apply(saved);
    }

    public FileDTO uploadAndSaveLogs(@Valid MultipartFile file, long userId) {
        FileRecord saved = null;
        try {
            if (file != null) {
                String contentType = file.getContentType();
                if (contentType != null) {
                    MediaType mediaType = MediaType.parseMediaType(contentType);
                    if (mediaType != null && mediaType.equals(MediaType.APPLICATION_PDF)
                            || mediaType.equals(MediaType.IMAGE_GIF)
                            || mediaType.equals(MediaType.IMAGE_PNG)
                            || mediaType.equals(MediaType.IMAGE_JPEG)) {
                        File fileRecord = uploadFileToLocalDirectory(file);
                        if (fileRecord != null) {
                            fileRecord.setUserId(userId);
                            saved = fileRepo.save(new FileRecord(fileRecord));
                        }
                    } else {
                        throw new InvalidRequestException("File type is not allowed", ErrorCodes.VALIDATION_EXCEPTION);
                    }
                } else {
                    throw new InvalidRequestException("Invalid MIME type found.", ErrorCodes.VALIDATION_EXCEPTION);
                }
            } else {
                throw new ResourceNotAvailableException("File not found.", ErrorCodes.NOT_FOUND_EXCEPTION);
            }
        } catch (InvalidRequestException e) {
            throw e;
        } catch (IOException e) {
            throw new InvalidRequestException("Failed to upload file.", ErrorCodes.INTERNAL_SERVER_ERROR);
        }
        return fileEntityToDtoConverter.apply(saved);
    }

    private FileDTO uploadFileToLocalDirectory(@Valid MultipartFile file) throws IOException {
        Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
               .toAbsolutePath().normalize();
        Files.createDirectories(fileStorageLocation);
        String originalFileName = file.getOriginalFilename();
        if (originalFileName != null) {
            String fileName = StringUtils.cleanPath(originalFileName);
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new UnSupportedFormatException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            FileDTO fileDTO = new FileDTO();
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String mimeType = MediaType.parseMediaType(file.getContentType()).getType();
            if (mimeType != null) {
                fileDTO.setMime(mimeType);
            }
            fileDTO.setName(fileName);
            String targetLocationPath = targetLocation.getParent().toAbsolutePath().toString();
            if (targetLocationPath != null) {
                fileDTO.setPath(targetLocationPath);
            }
            fileDTO.setUuid(UUID.randomUUID().toString());
            return fileDTO;
        }
        return null;
    }
}
