package ru.clevertec.servlet;

import com.google.gson.Gson;
import ru.clevertec.entity.Card;
import ru.clevertec.exception.ServiceException;
import ru.clevertec.service.CardService;
import ru.clevertec.service.Service;
import ru.clevertec.task.collection.CustomList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/cards")
public class CardServlet extends HttpServlet {

    private final Service<Card> cardService = CardService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pageSize = req.getParameter("page_size");
        String page = req.getParameter("page");
        CustomList<Card> listOfCards = cardService.findAll(pageSize, page);
        String json = new Gson().toJson(listOfCards);
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Card cardFromRequest = new Gson().fromJson(req.getReader(), Card.class);
        int number = cardFromRequest.getNumber();
        int discount = cardFromRequest.getDiscount();
        Card card = cardService.save(Card.builder()
                .number(number)
                .discount(discount)
                .build());
        String json = new Gson().toJson(card);
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Card cardFromRequest = new Gson().fromJson(req.getReader(), Card.class);
        int id = cardFromRequest.getId();
        int number = cardFromRequest.getNumber();
        int discount = cardFromRequest.getDiscount();
        try {
            Card card = cardService.update(id, new Card(id, number, discount));
            String json = new Gson().toJson(card);
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
        int id = Integer.parseInt(req.getParameter("card_id"));
        try {
            cardService.delete(id);
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
