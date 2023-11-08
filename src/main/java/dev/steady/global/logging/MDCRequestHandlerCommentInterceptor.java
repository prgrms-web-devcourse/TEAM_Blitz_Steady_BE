package dev.steady.global.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MDCRequestHandlerCommentInterceptor implements StatementInspector {

    @Override
    public String inspect(String sql) {
        String count = MDC.get("query");
        MDC.put("query", String.valueOf(Integer.valueOf(count) + 1));
        return sql;
    }

}
