package com.qualibrate.api.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.qualibrate.api.file.repository.FileDTO;
import com.qualibrate.api.file.service.FileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * File upload controller
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 * Controller for Files Entity
 */
@Validated
@RestController
@RequestMapping("/api/v1/")
@Api(description = "Handling of attachments and image upload for Qualibrate",
    tags = "files", consumes = "application/json,image/png,application/pdf,image/jpeg,image/gif,text/plain")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "Get List of files", notes = "Get Files")
    @RequestMapping(method = RequestMethod.GET, value = "/files")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<FileDTO>> getFiles(@PageableDefault(value = 50) Pageable pageable) {
        return ResponseEntity.ok(fileService.getFiles(pageable));
    }

    @ApiOperation(value = "upload a file", notes = "Uploads a new file into Qualibrate."
        + "There is a restricting policy for the types of files\n"
        + " that can be uploaded in the platform."
        + " Allowance: [.pdf, .jpeg, .jpg, .gif, .png, .txt]")
    @RequestMapping(method = RequestMethod.POST, value = "/files/{userId}", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public FileDTO uploadFile(@RequestPart(value = "file", required = false) MultipartFile file,
        @PathVariable long userId) {
        return fileService.uploadAndSaveLogs(file, userId);
    }
}
