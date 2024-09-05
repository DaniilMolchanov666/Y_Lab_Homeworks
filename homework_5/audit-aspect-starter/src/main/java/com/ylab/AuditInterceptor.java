package com.ylab;

import com.ylab.model.AspectEntry;
import com.ylab.service.AspectEntryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

/**
 * Аспект для логирования и вывода длительности выполнения сервлета
 */
@Component
@RequiredArgsConstructor
public class AuditInterceptor implements HandlerInterceptor {

    private final AspectEntryService aspectEntryService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AspectEntry aspectEntry = new AspectEntry();

        if (handler instanceof HandlerMethod handlerMethod) {
            aspectEntry.setClassName(handlerMethod.getBeanType().getName());
            aspectEntry.setMethodName(handlerMethod.getMethod().getName());
        }
        aspectEntry.setCreatedAt(String.valueOf(LocalDateTime.now()));
        aspectEntry.setEndPoint(request.getRequestURI());

        aspectEntryService.save(aspectEntry);

        request.setAttribute("id", aspectEntry.getId());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        int auditLogId = (Integer) request.getAttribute("id");
        AspectEntry auditLog = aspectEntryService.findById(auditLogId).orElseThrow();
        auditLog.setResponseStatus(response.getStatus());

        aspectEntryService.save(auditLog);
    }
}
