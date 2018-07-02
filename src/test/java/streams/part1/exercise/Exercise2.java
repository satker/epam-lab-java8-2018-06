package streams.part1.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Exercise2 {

    @Test
    public void calcAverageAgeOfEmployees() {
        List<Employee> employees = getEmployees();

        Double expected = employees.stream().
                mapToInt(employee -> employee.getPerson().getAge()).
                average().
                getAsDouble();

        assertEquals(33.66, expected, 0.1);
    }

    @Test
    public void findPersonWithLongestFullName() {
        List<Employee> employees = getEmployees();

        Person expected = employees.stream().
                map(Employee::getPerson).
                max(Comparator.comparingInt(person -> person.getFullName().length())).
                get();

        assertEquals(expected, employees.get(1).getPerson());
    }

    @Test
    public void findEmployeeWithMaximumDurationAtOnePosition() {
        List<Employee> employees = getEmployees();

        Employee expected = employees.stream().
                max(Comparator.comparingInt(employee -> employee.getJobHistory().
                        stream().
                        mapToInt(JobHistoryEntry::getDuration).
                        max().
                        getAsInt())).
                get();

        assertEquals(expected, employees.get(4));
    }

    /**
     * Вычислить общую сумму заработной платы для сотрудников.
     * Базовая ставка каждого сотрудника составляет 75_000.
     * Если на текущей позиции (последняя в списке) он работает больше трех лет - ставка увеличивается на 20%
     */
    @Test
    public void calcTotalSalaryWithCoefficientWorkExperience() {
        List<Employee> employees = getEmployees();

        final int CUSTOM_RATE = 75_000;
        final int MAX_RATE = 90_000;

        int expected = employees.stream().
                map(Employee::getJobHistory).
                map(jobHistoryEntries -> jobHistoryEntries.
                        get(jobHistoryEntries.size() - 1)).
                mapToInt(job -> job.getDuration() > 3 ?
                        MAX_RATE : CUSTOM_RATE).
                sum();

        assertEquals(465000.0, expected, 0.001);
    }

    private static List<Employee> getEmployees() {
        return Arrays.asList(
                new Employee(
                        new Person("Иван", "Мельников", 30),
                        Arrays.asList(
                                new JobHistoryEntry(2, "dev", "EPAM"),
                                new JobHistoryEntry(1, "dev", "google")
                        )),
                new Employee(
                        new Person("Александр", "Дементьев", 28),
                        Arrays.asList(
                                new JobHistoryEntry(1, "tester", "EPAM"),
                                new JobHistoryEntry(1, "dev", "EPAM"),
                                new JobHistoryEntry(1, "dev", "google")
                        )),
                new Employee(
                        new Person("Дмитрий", "Осинов", 40),
                        Arrays.asList(
                                new JobHistoryEntry(3, "QA", "yandex"),
                                new JobHistoryEntry(1, "QA", "mail.ru"),
                                new JobHistoryEntry(1, "dev", "mail.ru")
                        )),
                new Employee(
                        new Person("Анна", "Светличная", 21),
                        Collections.singletonList(
                                new JobHistoryEntry(1, "tester", "T-Systems")
                        )),
                new Employee(
                        new Person("Игорь", "Толмачёв", 50),
                        Arrays.asList(
                                new JobHistoryEntry(5, "tester", "EPAM"),
                                new JobHistoryEntry(6, "QA", "EPAM")
                        )),
                new Employee(
                        new Person("Иван", "Александров", 33),
                        Arrays.asList(
                                new JobHistoryEntry(2, "QA", "T-Systems"),
                                new JobHistoryEntry(3, "QA", "EPAM"),
                                new JobHistoryEntry(1, "dev", "EPAM")
                        ))
        );
    }
}