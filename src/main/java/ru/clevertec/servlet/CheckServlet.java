package ru.clevertec.servlet;

import ru.clevertec.service.CheckService;
import ru.clevertec.service.CheckServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

@WebServlet("/api/check")
public class CheckServlet extends HttpServlet {

    private final CheckService checkService = CheckServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String[]> map = req.getParameterMap();
        resp.setHeader("Content-Disposition", "attachment; filename=\"check.pdf\"");
        try (OutputStream out = resp.getOutputStream()) {
            checkService.calculateCheck(map, out);
        }
    }
}
