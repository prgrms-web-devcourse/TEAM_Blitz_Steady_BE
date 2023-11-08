package dev.steady.global.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class MdcLoggingInterceptor implements HandlerInterceptor {
    public static final String REQUEST_CONTROLLER_MDC_KEY = "handler";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String controllerInfo = getHandlerInfo(handlerMethod);
            String ip = request.getHeader("X-Forwarded-For");

            MDC.put(REQUEST_CONTROLLER_MDC_KEY, controllerInfo);
            MDC.put("ip", ip);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }

    private String getHandlerInfo(HandlerMethod handlerMethod) {
        String handlerName = handlerMethod.getBeanType().getSimpleName();
        String methodName = handlerMethod.getMethod().getName();
        return String.format("%s.%s", handlerName, methodName);
    }

}
