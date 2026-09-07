package com.smartcitizen.portal.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smartcitizen.portal.service.FileStorageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileStorageService fileStorageService;

    @Value("${file.upload-dir}")
    private String uploadDir;


    

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestParam("file") MultipartFile file) {

        String filePath =
                fileStorageService.uploadFile(file);

        Map<String, String> response =
                new HashMap<>();

        response.put("fileUrl", filePath);

        if (file.getContentType() != null &&
                file.getContentType().startsWith("image")) {

            response.put("fileType", "IMAGE");

        } else if (file.getContentType() != null &&
                file.getContentType().startsWith("video")) {

            response.put("fileType", "VIDEO");

        } else {

            response.put("fileType", "OTHER");
        }

        return ResponseEntity.ok(response);
    }


    

    @GetMapping("/view/{filename:.+}")
    public ResponseEntity<Resource> viewFile(
            @PathVariable String filename) {

        try {

            Path filePath = Paths
                    .get(uploadDir)
                    .toAbsolutePath()
                    .normalize()
                    .resolve(filename)
                    .normalize();


            // Security check
            Path uploadPath = Paths
                    .get(uploadDir)
                    .toAbsolutePath()
                    .normalize();

            if (!filePath.startsWith(uploadPath)) {

                return ResponseEntity.badRequest().build();
            }


            Resource resource =
                    new UrlResource(
                            filePath.toUri()
                    );


            if (!resource.exists() ||
                    !resource.isReadable()) {

                return ResponseEntity
                        .notFound()
                        .build();
            }


            // Detect file type
            String contentType =
                    Files.probeContentType(filePath);


            if (contentType == null) {

                contentType =
                        MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }


            return ResponseEntity.ok()

                    // Browser should try to display
                    // images/videos/PDFs instead of forcing download
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" +
                                    resource.getFilename() +
                                    "\""
                    )

                    .contentType(
                            MediaType.parseMediaType(
                                    contentType
                            )
                    )

                    .body(resource);


        } catch (IOException e) {

            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}