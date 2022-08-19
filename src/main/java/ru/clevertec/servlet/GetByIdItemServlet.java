package ru.clevertec.servlet;

import com.google.gson.Gson;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.clevertec.configuration.CheckConfiguration;
import ru.clevertec.dto.ItemDto;
import ru.clevertec.exception.ServiceException;
import ru.clevertec.service.ItemService;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/items/id")
public class GetByIdItemServlet extends HttpServlet {

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
        try {
            ItemDto itemDto = service.findById(Integer.parseInt(req.getParameter("id")));
            String json = new Gson().toJson(itemDto);
            try (PrintWriter out = resp.getWriter()) {
                out.write(json);
                resp.setStatus(200);
            }
        } catch (ServiceException e) {
            resp.sendError(400, "Item not found");
        }
    }
}
