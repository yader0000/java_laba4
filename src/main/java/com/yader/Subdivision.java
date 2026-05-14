package com.yader;

import java.util.Objects;

/**
 * Подразделение организации.
 * Имеет уникальный идентификатор, который генерируется программой,
 * и название, считанное из CSV-файла.
 */
public class Subdivision {

    private final int id;
    private final String name;

    /**
     * Создаёт подразделение с заданным идентификатором и названием.
     *
     * @param id   уникальный идентификатор подразделения
     * @param name название подразделения
     */
    public Subdivision(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return идентификатор подразделения
     */
    public int getId() {
        return id;
    }

    /**
     * @return название подразделения
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subdivision s)) return false;
        return id == s.id && Objects.equals(name, s.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Subdivision{id=" + id + ", name='" + name + "'}";
    }
}