package com.example.JobPortalProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration // cho phep trinh duyet truy cap file anh da upload qua http url
public class MvcConfig implements WebMvcConfigurer {
    private static final String UPLOAD_DIR = "photos"; //Định nghĩa thư mục gốc chứa file upload


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory(UPLOAD_DIR,registry);
    }

    private void exposeDirectory(String uploadDir, ResourceHandlerRegistry registry) {
        Path path = Paths.get(uploadDir);

        registry.addResourceHandler("/" + uploadDir+"/**").addResourceLocations("file:" + path.toAbsolutePath() + "/");
    }
}
