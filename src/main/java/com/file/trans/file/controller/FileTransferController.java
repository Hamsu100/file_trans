package com.file.trans.file.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.file.trans.file.services.MassFileUploadService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
public class FileTransferController {
    private final MassFileUploadService mfus;

    @GetMapping("/octet/{fileName}")
    public ResponseEntity<Resource> getFileAsOctet(@PathVariable String fileName) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attchment; filename=\"" + fileName + "\"")
                .body(mfus.getFile("localfilepath", fileName));
    }

    @GetMapping("/multipart/{fileName}")
    public ResponseEntity<MultiValueMap<String, Object>> getFileAsMulti(@PathVariable String fileName) {
        Resource rs = mfus.getFile("localpath", fileName);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", rs);
        map.add("otherInfo", "otherinfo");
        return ResponseEntity.ok().contentType(MediaType.MULTIPART_FORM_DATA).body(map);
    }
}
