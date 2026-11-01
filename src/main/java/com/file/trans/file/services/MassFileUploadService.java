package com.file.trans.file.services;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MassFileUploadService {

    public Resource getFile(String filePath, String fileName) {
        try {
            Path fp = Paths.get(filePath).resolve(fileName).normalize();
            Resource rs = new FileSystemResource(fp);

            if (rs.exists() && rs.isReadable()) {
                return rs;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } catch (ResponseStatusException e) {
            throw e;    
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
