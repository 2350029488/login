package com.huanglong.login.handler;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huanglong.login.utils.Message;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * gateway全局异常处理器
 */
@Order(-2) // 保证优先级低于默认的ResponseStatusExceptionHandler,这样能拿到响应状态码
@Configuration
public class GlobalErrorExceptionHandler implements ErrorWebExceptionHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        // 将返回格式设为JSON
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//        if (ex instanceof ResponseStatusException) {
//            response.setStatusCode(((ResponseStatusException) ex).getStatus());
//        }
        // 改变请求响应返回行为
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            Message error = Message.fail().add("error", ex.getMessage());
            try {
                // Message
                return bufferFactory.wrap(objectMapper.writeValueAsBytes(error));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }

}
