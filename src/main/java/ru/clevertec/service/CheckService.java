package ru.clevertec.service;

import java.io.OutputStream;
import java.util.Map;

public interface CheckService {

    void calculateCheck(Map<String, String[]> map, OutputStream out);
}
