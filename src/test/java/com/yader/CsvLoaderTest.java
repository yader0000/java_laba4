package com.yader;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Юнит-тесты для {@link CsvLoader}.
 */
class CsvLoaderTest {

    private static final String SAMPLE = "sample.csv";

    @Test
    void loadsAllRowsFromFile() throws IOException {
        List<Employee> employees = new CsvLoader().load(SAMPLE, false);
        assertEquals(4, employees.size());
    }

    @Test
    void firstEmployeeHasCorrectFields() throws IOException {
        Employee first = new CsvLoader().load(SAMPLE, false).get(0);

        assertEquals(1001L, first.getId());
        assertEquals("John Smith", first.getName());
        assertEquals("Male", first.getGender());
        assertEquals(LocalDate.of(1990, 5, 15), first.getBirthDate());
        assertEquals(5000, first.getSalary());
        assertEquals("Alpha", first.getSubdivision().getName());
    }

    @Test
    void sameSubdivisionNameSharesSameObject() throws IOException {
        List<Employee> employees = new CsvLoader().load(SAMPLE, false);

        Subdivision alphaFromFirst = employees.get(0).getSubdivision();
        Subdivision alphaFromThird = employees.get(2).getSubdivision();

        assertSame(alphaFromFirst, alphaFromThird);
    }

    @Test
    void differentSubdivisionsHaveDifferentIds() throws IOException {
        List<Employee> employees = new CsvLoader().load(SAMPLE, false);

        int alphaId = employees.get(0).getSubdivision().getId();
        int betaId  = employees.get(1).getSubdivision().getId();
        int gammaId = employees.get(3).getSubdivision().getId();

        assertNotEquals(alphaId, betaId);
        assertNotEquals(alphaId, gammaId);
        assertNotEquals(betaId, gammaId);
    }

    @Test
    void skipHeaderFlagSkipsFirstRow() throws IOException {
        List<Employee> withHeader = new CsvLoader().load(SAMPLE, true);
        assertEquals(3, withHeader.size());
    }

    @Test
    void missingResourceThrowsIOException() {
        assertThrows(IOException.class,
                () -> new CsvLoader().load("does_not_exist.csv", false));
    }
}