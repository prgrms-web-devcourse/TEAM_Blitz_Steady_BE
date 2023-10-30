package dev.steady.global.config;

import dev.steady.auth.config.JwtProperties;
import dev.steady.auth.config.KakaoOAuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {KakaoOAuthProperties.class})
public class PropertiesConfig {

}
