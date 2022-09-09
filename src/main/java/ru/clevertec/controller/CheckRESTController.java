package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.service.CheckService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@RestController
@RequestMapping("/api/check")
@RequiredArgsConstructor
public class CheckRESTController {

    @Value("${check.pathCheck}")
    private final String pathCheck;
    private final CheckService service;

    @GetMapping
    public ResponseEntity<byte[]> getCheck(HttpServletRequest req) throws IOException {
        Map<String, String[]> map = req.getParameterMap();
        service.calculateCheck(map);
        byte[] content = Files.readAllBytes(Path.of(pathCheck));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(content.length)
                .body(content);
    }
}
