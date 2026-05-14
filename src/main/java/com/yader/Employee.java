package com.yader;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Сотрудник организации.
 * Содержит личные данные (имя, пол, дату рождения),
 * информацию о зарплате и привязку к подразделению.
 */
public class Employee {

    private final long id;
    private final String name;
    private final String gender;
    private final LocalDate birthDate;
    private final Subdivision subdivision;
    private final int salary;

    /**
     * Создаёт сотрудника со всеми полями.
     *
     * @param id          идентификатор сотрудника (из CSV)
     * @param name        имя сотрудника
     * @param gender      пол ("Male" / "Female")
     * @param birthDate   дата рождения
     * @param subdivision подразделение, в котором работает сотрудник
     * @param salary      зарплата
     */
    public Employee(long id, String name, String gender,
                    LocalDate birthDate, Subdivision subdivision, int salary) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.subdivision = subdivision;
        this.salary = salary;
    }

    public long getId()                 { return id; }
    public String getName()             { return name; }
    public String getGender()           { return gender; }
    public LocalDate getBirthDate()     { return birthDate; }
    public Subdivision getSubdivision() { return subdivision; }
    public int getSalary()              { return salary; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee e)) return false;
        return id == e.id
                && salary == e.salary
                && Objects.equals(name, e.name)
                && Objects.equals(gender, e.gender)
                && Objects.equals(birthDate, e.birthDate)
                && Objects.equals(subdivision, e.subdivision);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender, birthDate, subdivision, salary);
    }

    @Override
    public String toString() {
        return "Employee{id=" + id
                + ", name='" + name + '\''
                + ", gender='" + gender + '\''
                + ", birthDate=" + birthDate
                + ", subdivision=" + subdivision
                + ", salary=" + salary + '}';
    }
}