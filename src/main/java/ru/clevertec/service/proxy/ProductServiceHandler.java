package ru.clevertec.service.proxy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.clevertec.service.ProductService;
import ru.clevertec.service.Service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProductServiceHandler implements InvocationHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class.getName());
    private final Service service;

    public ProductServiceHandler(Service service) {
        this.service = service;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Gson gson = new GsonBuilder().create();
        LOG.debug("Method name: {}, Input arguments: {}", method.getName(), gson.toJson(args));
        Object result = method.invoke(service, args);
        LOG.debug("Returning value of method: {}", gson.toJson(result));
        return result;
    }
}
