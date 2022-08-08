package ru.clevertec.service;

import java.util.Map;

public interface CheckService {

    CheckService getProxyCheckService();

    void calculateCheck(Map<String, String[]> map);
}
