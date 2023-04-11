/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main entry class to xpanse runtime. This class can be directly executed to start the server.
 */
@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = "org.eclipse.xpanse")
@EntityScan(basePackages = "org.eclipse.xpanse")
@ComponentScan(basePackages = "org.eclipse.xpanse")
@EnableJpaAuditing
public class XpanseTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(XpanseTestApplication.class, args);
    }
}