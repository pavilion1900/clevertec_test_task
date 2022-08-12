package ru.clevertec.servlet;

import com.google.gson.Gson;
import ru.clevertec.entity.Item;
import ru.clevertec.exception.ServiceException;
import ru.clevertec.service.ItemService;
import ru.clevertec.service.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/items/id")
public class GetByIdItemServlet extends HttpServlet {

    private final Service<Item> itemService = ItemService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Item item = itemService.findById(Integer.parseInt(req.getParameter("item_id")));
            String json = new Gson().toJson(item);
            try (PrintWriter out = resp.getWriter()) {
                out.write(json);
                resp.setStatus(200);
            }
        } catch (ServiceException e) {
            resp.sendError(400, "Item not found");
        }
    }
}
