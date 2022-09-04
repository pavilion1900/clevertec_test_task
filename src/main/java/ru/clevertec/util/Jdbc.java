package ru.clevertec.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Jdbc {

    private String driver;
    private String url;
    private String username;
    private String password;
    private String poolSize;
}
