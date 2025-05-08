package org.example;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.TimeZone;
import java.util.regex.Pattern;

@WebFilter("/*")
public class TimezoneValidateFilter implements Filter {

    private static final Pattern UTC_OFFSET_PATTERN = Pattern.compile("UTC[+-]\\d{1,2}");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String timezone = request.getParameter("timezone");

        if (timezone != null && !timezone.isEmpty()) {
            timezone = timezone.replace(" ", "+");

            boolean isValid = Arrays.asList(TimeZone.getAvailableIDs()).contains(timezone)
                    || UTC_OFFSET_PATTERN.matcher(timezone).matches();

            if (!isValid) {
                HttpServletResponse httpResp = (HttpServletResponse) response;
                httpResp.setContentType("text/html; charset=UTF-8");
                httpResp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
                return;
            }

            request.setAttribute("validatedTimezone", timezone);
        }

        chain.doFilter(request, response);
    }
}
