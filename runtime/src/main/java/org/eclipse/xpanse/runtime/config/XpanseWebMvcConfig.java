package org.eclipse.xpanse.runtime.config;

import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class XpanseWebMvcConfig implements WebMvcConfigurer {

    @Value("${log.path}")
    private String logPath;

    public static final String PROJECT_PATH = "file:" +
            System.getProperty("user.dir") + File.separator;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/logs/*").addResourceLocations(logPath)
                .addResourceLocations(PROJECT_PATH + logPath);
    }
}
