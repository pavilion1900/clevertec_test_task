package ru.clevertec.servlet;

import com.google.gson.Gson;
import ru.clevertec.entity.Card;
import ru.clevertec.exception.ServiceException;
import ru.clevertec.service.CardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/cards/number")
public class GetByNumberCardServlet extends HttpServlet {

    private final CardService cardService = CardService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Card card = cardService.findByNumber(req.getParameter("card_number"));
            String json = new Gson().toJson(card);
            try (PrintWriter out = resp.getWriter()) {
                out.write(json);
                resp.setStatus(200);
            }
        } catch (ServiceException e) {
            resp.sendError(400, "Card not found");
        }
    }
}