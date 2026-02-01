package com.example.JobPortalProject.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static void saveFile(String uploadDir, String filename , MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);// chuyen String thanh Path object
        if(!Files.exists(uploadPath)){ // kiem tra neu thu muc chua ton tai thi tao thu muc
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = multipartFile.getInputStream();){
            Path path = uploadPath.resolve(filename); // Kết hợp đường dẫn thư mục + tên file
            System.out.println("FilePath" + path);
            System.out.println("FileName" + filename);
            Files.copy(inputStream,path, StandardCopyOption.REPLACE_EXISTING); // Copy nội dung file từ InputStream vào đường dẫn đích
        } catch (IOException e) {
            throw new IOException("Could not save image file: " + filename,e);
        }
    }
}
