package com.ylab.config;

import com.ylab.AuditInterceptor;
import com.ylab.condition.EnableAuditCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@Conditional(EnableAuditCondition.class)
public class AuditAspectConfiguration implements WebMvcConfigurer {

    private final AuditInterceptor loggingAspect;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingAspect);
    }
}
