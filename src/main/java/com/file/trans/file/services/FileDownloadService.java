package com.file.trans.file.services;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FileDownloadService {

    public final RestClient fileDownloadRestClient;

    public void downloadFile(String fileUrl, String savePath) {
        fileDownloadRestClient.get()
                .uri(fileUrl)
                .exchange((request, response) -> {
                    Path path = Path.of(savePath);
                    try (InputStream in = response.getBody()) {
                        Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
                    }
                    return null;
                });
    }
}
