package org.chkaur99;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final long ALLOWED_REQUESTS = 5;
    private static final long TIME_WINDOW_MILLIS = 60 * 1000; // 1 minute

    private final Map<String, List<Long>> ipRequestTimestamps = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String clientIp = getClientIP(request);

        List<Long> requestTimes = ipRequestTimestamps.computeIfAbsent(clientIp, k -> Collections.synchronizedList(new ArrayList<>()));

        long currentTime = System.currentTimeMillis();

        System.out.println("==========================================");
        System.out.println("Client IP: " + clientIp);
        System.out.println("Current time: " + currentTime);
        System.out.println("Previous timestamps: " + new ArrayList<>(requestTimes));
        System.out.println("List size before clean: " + requestTimes.size());

        synchronized (requestTimes) {
            // Remove timestamps older than 1 minute
            requestTimes.removeIf(timestamp -> currentTime - timestamp > TIME_WINDOW_MILLIS);

            if (requestTimes.size() >= ALLOWED_REQUESTS) {
                System.out.println("Rate limit exceeded for IP: " + clientIp);
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"message\": \"Rate limit exceeded. Try again later.\"}");
                return;
            }

            requestTimes.add(currentTime);
            System.out.println("Request allowed. Updated timestamps: " + new ArrayList<>(requestTimes));
            System.out.println("==========================================");
        }

        filterChain.doFilter(request, response);
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
