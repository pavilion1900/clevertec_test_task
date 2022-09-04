package ru.clevertec.servlet;

import com.google.gson.Gson;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.clevertec.configuration.CheckConfiguration;
import ru.clevertec.dto.CardDto;
import ru.clevertec.exception.ServiceException;
import ru.clevertec.service.CardService;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/cards/id")
public class GetByIdCardServlet extends HttpServlet {

    private CardService service;

    @PostConstruct
    public void init() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(CheckConfiguration.class);
        service = context.getBean("cardService", CardService.class);
        context.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            CardDto cardDto = service.findById(Integer.parseInt(req.getParameter("id")));
            String json = new Gson().toJson(cardDto);
            try (PrintWriter out = resp.getWriter()) {
                out.write(json);
                resp.setStatus(200);
            }
        } catch (ServiceException e) {
            resp.sendError(400, "Card not found");
        }
    }
}
