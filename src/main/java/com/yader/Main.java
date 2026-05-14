package com.yader;

import java.util.List;

/**
 * Точка входа: загружает данные из {@code foreign_names.csv}
 * и выводит сводку в консоль.
 */
public class Main {

    private static final String CSV_FILE = "foreign_names.csv";

    public static void main(String[] args) {
        try {
            CsvLoader loader = new CsvLoader();
            List<Employee> employees = loader.load(CSV_FILE, true);

            System.out.println("Loaded employees: " + employees.size());
            System.out.println();
            System.out.println("First entry: " + employees.get(0));
            System.out.println("Last entry:  " + employees.get(employees.size() - 1));
        } catch (Exception e) {
            System.err.println("Failed to load CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}