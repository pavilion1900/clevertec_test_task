package ru.clevertec.servlet;

import com.google.gson.Gson;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.clevertec.configuration.CheckConfiguration;
import ru.clevertec.dto.ItemDto;
import ru.clevertec.entity.Item;
import ru.clevertec.exception.ServiceException;
import ru.clevertec.service.ItemService;
import ru.clevertec.task.collection.CustomList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/items")
public class ItemServlet extends HttpServlet {

    private ItemService service;

    @PostConstruct
    public void init() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(CheckConfiguration.class);
        service = context.getBean("itemService", ItemService.class);
        context.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pageSize = req.getParameter("pageSize");
        String page = req.getParameter("page");
        CustomList<ItemDto> listOfItems = service.findAll(pageSize, page);
        String json = new Gson().toJson(listOfItems);
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Item item = new Gson().fromJson(req.getReader(), Item.class);
        ItemDto itemDto = service.save(item);
        String json = new Gson().toJson(itemDto);
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Item item = new Gson().fromJson(req.getReader(), Item.class);
        try {
            ItemDto itemDto = service.update(item.getId(), item);
            String json = new Gson().toJson(itemDto);
            try (PrintWriter out = resp.getWriter()) {
                out.write(json);
                resp.setStatus(200);
            }
        } catch (ServiceException e) {
            resp.sendError(400, "Item not updated");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            service.delete(id);
            String json = new Gson().toJson(String.format("Item with id %d deleted", id));
            try (PrintWriter out = resp.getWriter()) {
                out.write(json);
                resp.setStatus(200);
            }
        } catch (ServiceException e) {
            resp.sendError(400, "Item not deleted");
        }
    }
}
