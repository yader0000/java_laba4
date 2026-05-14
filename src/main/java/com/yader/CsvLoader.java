package com.yader;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Загружает список сотрудников из CSV-файла.
 * <p>
 * Подразделения создаются "на лету": если такого названия ещё не встречалось —
 * создаётся новое {@link Subdivision} с уникальным идентификатором.
 * Если такое название уже было — переиспользуется существующий объект.
 */
public class CsvLoader {

    /** Разделитель полей в CSV. */
    private static final char SEPARATOR = ';';

    /** Формат даты рождения в CSV. */
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /** Минимальное количество колонок в корректной строке. */
    private static final int COLUMNS_PER_ROW = 6;

    /** Кэш уже созданных подразделений: имя → объект. */
    private final Map<String, Subdivision> subdivisionsByName = new HashMap<>();

    /** Счётчик для генерации новых ID подразделений. */
    private int nextSubdivisionId = 1;

    /**
     * Считывает сотрудников из CSV-файла, лежащего в ресурсах проекта.
     *
     * @param resourceName имя файла в {@code src/main/resources}
     *                     (или {@code src/test/resources} при запуске тестов)
     * @param skipHeader   если {@code true}, первая строка считается заголовком
     * @return список сотрудников в том порядке, в котором они идут в файле
     * @throws IOException если файл не найден или возникла ошибка чтения
     */
    public List<Employee> load(String resourceName, boolean skipHeader) throws IOException {
        InputStream stream = getClass()
                .getClassLoader()
                .getResourceAsStream(resourceName);
        if (stream == null) {
            throw new IOException("Resource not found: " + resourceName);
        }

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(SEPARATOR)
                .withIgnoreQuotations(true)
                .build();

        List<Employee> employees = new ArrayList<>();
        try (InputStream in = stream;
             CSVReader reader = new CSVReaderBuilder(
                     new InputStreamReader(in, StandardCharsets.UTF_8))
                     .withSkipLines(skipHeader ? 1 : 0)
                     .withCSVParser(parser)
                     .build()) {

            String[] row;
            while ((row = reader.readNext()) != null) {
                if (row.length < COLUMNS_PER_ROW) {
                    continue;
                }
                employees.add(parseRow(row));
            }
        } catch (CsvValidationException e) {
            throw new IOException("CSV parsing error in " + resourceName, e);
        }
        return employees;
    }

    /** Преобразует одну строку CSV в объект {@link Employee}. */
    private Employee parseRow(String[] row) {
        long id            = Long.parseLong(row[0].trim());
        String name        = row[1].trim();
        String gender      = row[2].trim();
        LocalDate born     = LocalDate.parse(row[3].trim(), DATE_FORMAT);
        String subName     = row[4].trim();
        int salary         = Integer.parseInt(row[5].trim());

        Subdivision subdivision = subdivisionsByName.computeIfAbsent(
                subName,
                key -> new Subdivision(nextSubdivisionId++, key)
        );
        return new Employee(id, name, gender, born, subdivision, salary);
    }
}