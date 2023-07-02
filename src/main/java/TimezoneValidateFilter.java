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
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String timezoneParameter = req.getParameter("timezone");

        if (timezoneParameter != null && !timezoneParameter.isEmpty()) {
            if (!isValidTimezone(timezoneParameter)) {
                resp.setStatus(400);
                resp.setContentType("text/plain; charset=utf-8");
                resp.getWriter().write("Invalid timezone");
                resp.getWriter().close();
                return; // Важливо додати повернення з методу після написання відповіді
            }
        }

        chain.doFilter(req, resp); // Викликати ланцюжок фільтрів та сервлету
    }
    private boolean isValidTimezone(String timezone) {
        TimeZone.getTimeZone(timezone);
        return true;
    }
}