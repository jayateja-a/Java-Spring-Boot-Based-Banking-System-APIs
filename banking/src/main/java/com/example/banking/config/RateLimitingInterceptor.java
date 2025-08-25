package com.example.banking.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    private static final int MAX_REQUESTS = 10; // 10 requests
    private static final long TIME_WINDOW = 60; // 60 seconds
    private final ConcurrentHashMap<String, RequestCounter> requestCounts = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = request.getRemoteAddr();
        String endpoint = request.getRequestURI();
        String key = clientIp + ":" + endpoint;

        RequestCounter counter = requestCounts.computeIfAbsent(key, k -> new RequestCounter());

        synchronized (counter) {
            if (System.currentTimeMillis() - counter.timestamp > TimeUnit.SECONDS.toMillis(TIME_WINDOW)) {
                counter.reset();
            }

            if (counter.count >= MAX_REQUESTS) {
                response.sendError(429, "Rate limit exceeded. Try again in " + TIME_WINDOW + " seconds");
                return false;
            }

            counter.increment();
        }

        return true;
    }

    private static class RequestCounter {
        int count = 0;
        long timestamp = System.currentTimeMillis();

        void increment() {
            count++;
        }

        void reset() {
            count = 1;
            timestamp = System.currentTimeMillis();
        }
    }
}