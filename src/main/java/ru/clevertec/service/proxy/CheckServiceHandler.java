package ru.clevertec.service.proxy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.clevertec.service.CheckService;
//import ru.clevertec.service.CheckServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//public class CheckServiceHandler implements InvocationHandler {
//
//    private static final Logger LOG = LoggerFactory.getLogger(CheckServiceImpl.class.getName());
//    private final CheckService checkService;
//
//    public CheckServiceHandler(CheckService checkService) {
//        this.checkService = checkService;
//    }
//
//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        Gson gson = new GsonBuilder().create();
//        LOG.debug("Method name: {}, Input arguments: {}", method.getName(), gson.toJson(args));
//        Object result = method.invoke(checkService, args);
//        LOG.debug("Returning value of method: {}", gson.toJson(result));
//        return result;
//    }
//}
