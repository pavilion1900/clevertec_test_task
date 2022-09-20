package ru.clevertec.service;

import java.util.Map;

public interface CheckService {

    CheckInfo calculateCheck(Map<String, String[]> map);
}
