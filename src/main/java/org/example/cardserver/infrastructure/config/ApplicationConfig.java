package org.example.cardserver.infrastructure.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Main application configuration class for dependency injection setup.
 *
 * This class ensures proper component scanning and bean registration across all layers:
 * - Controllers (infrastructure.controller)
 * - Services (application.service)
 * - Repositories (infrastructure.repository)
 * - Mappers (application.mapper)
 *
 * All dependencies are injected via constructor injection for better testability
 * and immutability.
 */
@Configuration
@ComponentScan(basePackages = "org.example.cardserver")
public class ApplicationConfig {

    // Additional custom beans can be defined here if needed in the future

}

