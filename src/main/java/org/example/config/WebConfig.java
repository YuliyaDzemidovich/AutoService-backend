package org.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Spring MVC Configuration.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.example")
public class WebConfig {
}
