package ru.clevertec.servlet;

import com.google.gson.Gson;
import ru.clevertec.entity.Item;
import ru.clevertec.exception.ItemNotFoundException;
import ru.clevertec.service.ItemService;
import ru.clevertec.service.Service;
import ru.clevertec.task.collection.CustomList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

@WebServlet("/api/items")
public class ItemServlet extends HttpServlet {

    private final Service<Item> itemService = ItemService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pageSize = req.getParameter("page_size");
        String page = req.getParameter("page");
        CustomList<Item> listOfItems = itemService.findAll(pageSize, page);
        String json = new Gson().toJson(listOfItems);
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Item itemFromRequest = new Gson().fromJson(req.getReader(), Item.class);
        String name = itemFromRequest.getName();
        BigDecimal price = itemFromRequest.getPrice();
        boolean promotion = itemFromRequest.isPromotion();
        Item item = itemService.add(new Item(name, price, promotion));
        String json = new Gson().toJson(item);
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Item itemFromRequest = new Gson().fromJson(req.getReader(), Item.class);
        int id = itemFromRequest.getId();
        String name = itemFromRequest.getName();
        BigDecimal price = itemFromRequest.getPrice();
        boolean promotion = itemFromRequest.isPromotion();
        try {
            Item item = itemService.update(id, new Item(name, price, promotion));
            String json = new Gson().toJson(item);
            try (PrintWriter out = resp.getWriter()) {
                out.write(json);
                resp.setStatus(200);
            }
        } catch (ItemNotFoundException e) {
            resp.sendError(400, "Item not updated");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("item_id"));
        try {
            itemService.delete(id);
            String json = new Gson().toJson(String.format("Item with id %d deleted", id));
            try (PrintWriter out = resp.getWriter()) {
                out.write(json);
                resp.setStatus(200);
            }
        } catch (ItemNotFoundException e) {
            resp.sendError(400, "Item not deleted");
        }
    }
}
