package web_filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TimeZone;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse resp,
                            FilterChain chain) throws IOException, ServletException {

        String timezoneParameter = req.getParameter("timezone");

        if(timezoneParameter != null && !timezoneParameter.isEmpty()){
            if (!isValidTimezone(timezoneParameter)) {
                resp.setStatus(400);
                resp.getWriter().write("{\"Error\": \"Invalid timezone\"}");
                resp.getWriter().close();
            }
            return;
        }
        chain.doFilter(req, resp);
    }
    private boolean isValidTimezone(String timezone) {
        try {
            TimeZone.getTimeZone(timezone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
