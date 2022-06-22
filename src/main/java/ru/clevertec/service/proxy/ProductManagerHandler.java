package ru.clevertec.service.proxy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.clevertec.service.Manager;
import ru.clevertec.service.ProductManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProductManagerHandler implements InvocationHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ProductManager.class.getName());
    private Manager productManager;

    public ProductManagerHandler(Manager productManager) {
        this.productManager = productManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Gson gson = new GsonBuilder().create();
        LOG.debug("Method name: {}, Input arguments: {}", method.getName(), gson.toJson(args));
        Object result = method.invoke(productManager, args);
        LOG.debug("Returning value of method: {}", gson.toJson(result));
        return result;
    }
}
