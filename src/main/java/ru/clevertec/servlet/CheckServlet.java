package ru.clevertec.servlet;

import ru.clevertec.service.CheckService;
import ru.clevertec.service.CheckServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@WebServlet("/api/check")
public class CheckServlet extends HttpServlet {

    private static final String PATH =
            "C:\\projects\\clevertec_test_task\\src\\main\\resources\\check.pdf";
    private final CheckService checkService = CheckServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String[]> map = req.getParameterMap();
        checkService.calculateCheck(map);
        byte[] bytes = Files.readAllBytes(Path.of(PATH));
        try (OutputStream out = resp.getOutputStream()) {
            out.write(bytes);
        }
    }
}
