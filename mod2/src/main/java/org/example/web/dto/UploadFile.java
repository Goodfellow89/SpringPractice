package org.example.web.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadFile {

    MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
