package ru.clevertec.format;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface Format {

    ResponseEntity<byte[]> setFormat(Map<String, String[]> map);
}
