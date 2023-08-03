package com.example.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${file.dir}")
    private String fileDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // addResourceHandlers() 리소스 경로와 연결된 URL 경로를 작성한다.
        // 리소스는 자원이고 현재 우리가 필요한 자원은 이미지 파일이다.
        registry.addResourceHandler("/upload/**")
                // 실제 리소스가 존재하는 외부 경로를 알려준다.
                .addResourceLocations("file:" + fileDir);
        // 로컬디스크 경로는 file: 을 반드시 사용해야 한다.
    }
}
