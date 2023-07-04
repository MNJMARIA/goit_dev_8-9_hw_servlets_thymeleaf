package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(value = "/time")
public class ThymeleafTestController extends HttpServlet {
    private TemplateEngine engine;
    @Override
    public void init() throws ServletException {
        super.init();
        //налаштовуємо шаблонізатор
        engine = new TemplateEngine();

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("C:\\Users\\armyl\\IdeaProjects\\goit_dev_8_hw_gradle\\templates\\");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Створюємо контекст для шаблонізатору
        resp.setContentType("text/html");
        String currentTime;
        String timezone = req.getParameter("timezone");

        resp.setHeader("Set-Cookie", "lastTimezone =45");

        if (timezone != null && !timezone.isEmpty()) {
            //save timezone in cookie
            resp.addCookie(new Cookie("lastTimezone", timezone));

            currentTime = ZonedDateTime
                    .now(ZoneId.of(timezone))
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("lastTimezone")) {
                        timezone = cookie.getValue();
                        break;
                    }
                }
            }
            //if there is no timezone in cookies then get default timezone UTC
            if(timezone == null || timezone.isEmpty()){
                timezone = "UTC";
            }

            currentTime = ZonedDateTime
                    .now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        // Створення контексту для шаблонізатора
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("currentTime", currentTime);
        params.put("timezone", timezone);
        Context context = new Context();
        context.setVariables(params);

        // Рендеринг шаблону Thymeleaf і передача результатів до відповіді
        String renderedHtml = engine.process("test", context);
        resp.getWriter().write(renderedHtml);
    }
}