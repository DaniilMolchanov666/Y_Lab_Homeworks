package com.ylab.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.service.LogEntryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoggingAspectBeanPostProcessor implements BeanPostProcessor {

    private final LogEntryService logEntryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof LoggingAspect) {
            ((LoggingAspect) bean).setLogEntryService(logEntryService);
            ((LoggingAspect) bean).setObjectMapper(objectMapper);
        }
        return bean;
    }
}
