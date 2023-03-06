/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.runtime.config;

import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * XpanseWebMvcConfig.
 */
@Configuration
public class XpanseWebMvcConfig implements WebMvcConfigurer {

    public static final String PROJECT_PATH = "file:"
            + System.getProperty("user.dir") + File.separator;

    @Value("${log.path}")
    private String logPath;

    @Value("${log.url}")
    private String logUrl;

    /**
     * log view.
     *
     * @param registry ResourceHandlerRegistry.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(logUrl).addResourceLocations(logPath)
                .addResourceLocations(PROJECT_PATH + logPath);
    }
}
