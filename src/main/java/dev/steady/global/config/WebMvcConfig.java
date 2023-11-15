package dev.steady.global.config;

import dev.steady.global.auth.AuthorizedArgumentResolver;
import dev.steady.global.auth.JwtAuthenticationInterceptor;
import dev.steady.global.converter.StringToSteadyStatusConverter;
import dev.steady.global.logging.MdcLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtAuthenticationInterceptor jwtAuthenticationInterceptor;
    private final AuthorizedArgumentResolver authorizedArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MdcLoggingInterceptor()).order(0)
                .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**");
        registry.addInterceptor(jwtAuthenticationInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authorizedArgumentResolver);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSteadyStatusConverter());
    }

}
