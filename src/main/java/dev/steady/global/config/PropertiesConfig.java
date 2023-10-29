package dev.steady.global.config;

import dev.steady.oauth.config.JwtProperties;
import dev.steady.oauth.config.KakaoOAuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {KakaoOAuthProperties.class, JwtProperties.class})
public class PropertiesConfig {

}
