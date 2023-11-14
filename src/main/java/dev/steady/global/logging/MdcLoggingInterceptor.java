package dev.steady.global.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class MdcLoggingInterceptor implements HandlerInterceptor {
    public static final String REQUEST_CONTROLLER_MDC_KEY = "request";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestInfo = getHandlerInfo(request);
        String ip = request.getHeader("X-Forwarded-For");

        MDC.put(REQUEST_CONTROLLER_MDC_KEY, requestInfo);
        MDC.put("ip", ip);
        MDC.put("query", "0");
        MDC.put("start", String.valueOf(System.currentTimeMillis()));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String start = MDC.get("start");
        long startTime = Long.parseLong(start);
        String queryCount = MDC.get("query");
        String requestInfo = MDC.get(REQUEST_CONTROLLER_MDC_KEY);
        log.info("요청 정보: {} | 요청 총 소요 시간: {}ms | 총 쿼리 카운트: {} ",
                requestInfo,
                (System.currentTimeMillis() - startTime),
                queryCount);
        MDC.clear();
    }

    private String getHandlerInfo(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String HTTPMethod = request.getMethod();
        return String.format("%s.%s", requestURI, HTTPMethod);
    }

}
