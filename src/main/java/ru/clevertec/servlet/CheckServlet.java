package ru.clevertec.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.clevertec.configuration.CheckConfiguration;
import ru.clevertec.service.CheckServiceImpl;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

@WebServlet("/api/check")
public class CheckServlet extends HttpServlet {

    private CheckServiceImpl service;

    @PostConstruct
    public void init() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(CheckConfiguration.class);
        service = context.getBean("checkServiceImpl", CheckServiceImpl.class);
        context.close();
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
