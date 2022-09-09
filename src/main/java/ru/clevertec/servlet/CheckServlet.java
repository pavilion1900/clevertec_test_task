package ru.clevertec.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.clevertec.service.CheckService;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@WebServlet("/api/check")
public class CheckServlet extends HttpServlet {

    @Autowired
    private CheckService service;

    @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String[]> map = req.getParameterMap();
        resp.setHeader("Content-Disposition", "attachment; filename=\"check.pdf\"");
        try (OutputStream out = resp.getOutputStream()) {
            service.calculateCheck(map, out);
        }
    }
}
