package com.interswitchgroup.discoverpostinjectweb.api.V1;

import com.interswitchgroup.discoverpostinjectweb.api.ApiGenericResponse;
import com.interswitchgroup.discoverpostinjectweb.api.UploadFileResponse;
import com.interswitchgroup.discoverpostinjectweb.dto.request.CreateTransactionRequest;
import com.interswitchgroup.discoverpostinjectweb.dto.request.QueryRequest;
import com.interswitchgroup.discoverpostinjectweb.exception.RequestNotValidException;
import com.interswitchgroup.discoverpostinjectweb.model.Page;
import com.interswitchgroup.discoverpostinjectweb.model.Transaction;
import com.interswitchgroup.discoverpostinjectweb.service.FileStorageService;
import com.interswitchgroup.discoverpostinjectweb.service.ProcessFileContents;
import com.interswitchgroup.discoverpostinjectweb.service.TransactionService;
import com.interswitchgroup.discoverpostinjectweb.util.DateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@CrossOrigin()
@RestController
@RequestMapping("/api/v1/filemanagement")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final String terminalId = "23456789";

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    ProcessFileContents processFileContents;

    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Callable<UploadFileResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        return () -> {
            try {
                if (file.isEmpty()) {
                }
            } catch (Exception ex) {
                throw new RequestNotValidException("You can not upload an empty file", ex);
            }
            String fileName = fileStorageService.storeFile(file);
            String result = processFileContents.processContent(fileStorageService.getFileStorageLocation().toString(),
                    fileStorageService.getFileStorageLocation().toString(), terminalId);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest();
            createTransactionRequest.setFilename(fileName);
            createTransactionRequest.setInitiator("chinedu.mefendja");
            createTransactionRequest.setDateConverted(DateFormat.newDate());
            createTransactionRequest.setTotalTransaction(result.substring(0,5));
            transactionService.create(createTransactionRequest);

            return new UploadFileResponse(fileName,fileDownloadUri,file.getContentType(), file.getSize());
        };
    }

    @PostMapping("/uploadMultipleFiles")
    public List<Callable<UploadFileResponse>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {

        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @RequestMapping("/transactions")
    public Page<Transaction> getTransactions(QueryRequest request) {
        return transactionService.getAll(request);
    }
}
