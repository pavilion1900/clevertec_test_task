package ru.clevertec.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YamlProperties {

    private Jdbc jdbc;
    private Check check;
    private Hibernate hibernate;
}
