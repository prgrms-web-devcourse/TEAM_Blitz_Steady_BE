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
        if (handler instanceof HandlerMethod handlerMethod) {
            String controllerInfo = getHandlerInfo(handlerMethod);
            String ip = request.getHeader("X-Forwarded-For");

            MDC.put(REQUEST_CONTROLLER_MDC_KEY, controllerInfo);
            MDC.put("ip", ip);
            MDC.put("query", "0");
            MDC.put("start", String.valueOf(System.currentTimeMillis()));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String start = MDC.get("start");
        Long startTime = Long.valueOf(start);
        String queryCount = MDC.get("query");
        String handlerInfo = MDC.get(REQUEST_CONTROLLER_MDC_KEY);
        log.info("핸들러: {} | 요청 총 소요 시간: {}ms | 총 쿼리 카운트: {} ",
                handlerInfo,
                (System.currentTimeMillis() - startTime),
                queryCount);
        MDC.clear();
    }

    private String getHandlerInfo(HandlerMethod handlerMethod) {
        String handlerName = handlerMethod.getBeanType().getSimpleName();
        String methodName = handlerMethod.getMethod().getName();
        return String.format("%s.%s", handlerName, methodName);
    }

}
