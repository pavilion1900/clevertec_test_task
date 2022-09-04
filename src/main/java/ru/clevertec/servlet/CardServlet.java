package ru.clevertec.servlet;

import com.google.gson.Gson;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.clevertec.configuration.CheckConfiguration;
import ru.clevertec.dto.CardDto;
import ru.clevertec.entity.Card;
import ru.clevertec.exception.ServiceException;
import ru.clevertec.service.CardService;
import ru.clevertec.task.collection.CustomList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/cards")
public class CardServlet extends HttpServlet {

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
        String pageSize = req.getParameter("pageSize");
        String page = req.getParameter("page");
        CustomList<CardDto> listOfCards = service.findAll(pageSize, page);
        String json = new Gson().toJson(listOfCards);
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Card card = new Gson().fromJson(req.getReader(), Card.class);
        CardDto cardDto = service.save(card);
        String json = new Gson().toJson(cardDto);
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Card card = new Gson().fromJson(req.getReader(), Card.class);
        try {
            CardDto cardDto = service.update(card.getId(), card);
            String json = new Gson().toJson(cardDto);
            try (PrintWriter out = resp.getWriter()) {
                out.write(json);
                resp.setStatus(200);
            }
        } catch (ServiceException e) {
            resp.sendError(400, "Card not updated");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            service.delete(id);
            String json = new Gson().toJson(String.format("Card with id %d deleted", id));
            try (PrintWriter out = resp.getWriter()) {
                out.write(json);
                resp.setStatus(200);
            }
        } catch (ServiceException e) {
            resp.sendError(400, "Card not deleted");
        }
    }
}
