package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();

        System.out.println("Servlet init!");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write("<br>TIME<br>");

        String currentTime;
        String timezone = req.getParameter("timezone");
        if (timezone != null && !timezone.isEmpty()) {
            currentTime = LocalDateTime
                    .now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            resp.getWriter().write(currentTime + " " + timezone);
        } else {
            currentTime = LocalDateTime
                    .now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            timezone = "UTC";
            resp.getWriter().write(currentTime + " " + timezone);
        }
        resp.getWriter().close();
    }
}
