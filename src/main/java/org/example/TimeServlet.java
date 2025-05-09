package org.example;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(TimeServlet.class.getName());
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'x");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String timezoneParam = (String) request.getAttribute("validatedTimezone");
            if (timezoneParam == null || timezoneParam.isEmpty()) {
                timezoneParam = request.getParameter("timezone");
            }

            ZoneId zoneId;

            if (timezoneParam == null || timezoneParam.isEmpty()) {
                zoneId = ZoneOffset.UTC;
            } else if (timezoneParam.startsWith("UTC") && timezoneParam.length() > 3) {
                String offsetStr = timezoneParam.substring(3);
                zoneId = ZoneOffset.of(offsetStr);
            } else {
                zoneId = ZoneId.of(timezoneParam);
            }

            ZonedDateTime now = ZonedDateTime.now(zoneId);
            String formattedTime = now.format(FORMATTER);

            response.setContentType("text/html; charset=UTF-8");

            try (PrintWriter out = response.getWriter()) {
                out.println("<html><body>");
                out.println("<h1>Current time: " + formattedTime + "</h1>");
                out.println("</body></html>");
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing request", e);
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
            } catch (IOException ioException) {
                logger.log(Level.SEVERE, "Failed to send error response", ioException);
            }
        }
    }
}
