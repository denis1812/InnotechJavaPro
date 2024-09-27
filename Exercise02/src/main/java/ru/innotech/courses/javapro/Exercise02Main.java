package ru.innotech.courses.javapro;

import ru.innotech.courses.javapro.denis812.Employee;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.awt.SystemColor.text;

public class Exercise02Main {
    public static void main(String[] args) {
        System.out.println(">>> InnoTech Java Pro courses: module 1. Exercise 02 (2024-09-19) ==>>>\n");

        //-------------------------------------------
        // Задачи на список чисел
        List<Integer> numbersList = new ArrayList<>();
        numbersList.add(5);
        numbersList.add(12);
        numbersList.add(3);
        numbersList.add(1);
        numbersList.add(17);
        numbersList.add(5);
        numbersList.add(25);
        numbersList.add(3);
        numbersList.add(14);
        numbersList.add(17);

        processTask01(numbersList);
        processTask02(numbersList);
        processTask03(numbersList);

        //-------------------------------------------
        // Задачи на список сотрудников
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("Иванов", 45, "Инженер"));
        employeeList.add(new Employee("Петров", 28, "Токарь"));
        employeeList.add(new Employee("Сидоров", 32, "Инженер"));
        employeeList.add(new Employee("Орлов", 45, "Фрезеровщик"));
        employeeList.add(new Employee("Соколов", 39, "Инженер"));
        employeeList.add(new Employee("Воробьев", 54, "Инженер"));
        employeeList.add(new Employee("Журавлев", 42, "Инженер"));

        processTask04(employeeList);
        processTask05(employeeList);

        //-------------------------------------------
        // Задачи на список слов
        List<String> stringList1 = Arrays.asList("Найдите", "пожалуйста", "в", "списке", "слов", "самое", "длинное",
                "и", "левое", "правое", "понятие");

        processTask06(stringList1);

        //---
        String text1 = "word letter sign word space sign letter word spase semicolon letter word sign";
        processTask07(text1);

        //---
        processTask08(stringList1);

        //---
        String[] strings = {"у окна стою я как", "у холста ах какая за", "окном красота будто кто-то перепутал",
                "цвета и Дзержинку и Манеж" , "по мосту идет оранжевый кот", "он так стесняется своей белизны",
                "рубают пацаны фиолетовый пломбир"};
        processTask09(strings);

    }

    /**
     * Реализуйте удаление из листа всех дубликатов
     *
     * @param numbers  список для обработки
     */
    public static void processTask01 (List<Integer> numbers) {
        System.out.println("===>> Task 01: ");
        List<Integer> result = numbers.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(result);
        System.out.println("\n");
    }

    /**
     * Найдите в списке целых чисел 3-е наибольшее число (пример: 5 2 10 9 4 3 10 1 13 => 10)
     *
     * @param numbers  список для обработки
     */
    public static void processTask02 (List<Integer> numbers) {
        System.out.println("===>> Task 02: ");

        Integer result = numbers.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElse(-1);

        System.out.println(result);
        System.out.println("\n");
    }

    /**
     * Найдите в списке целых чисел 3-е наибольшее «уникальное» число (пример: 5 2 10 9 4 3 10 1 13 => 9, в отличие от
     * прошлой задачи здесь разные 10 считает за одно число)
     *
     * @param numbers  список для обработки
     */
    public static void processTask03 (List<Integer> numbers) {
        System.out.println("===>> Task 03: ");

        Integer result = numbers.stream()
                .sorted((a,b)-> b-a)
                .distinct()
                .skip(2)
                .findFirst()
                .orElse(-1);

        System.out.println(result);
        System.out.println("\n");
    }

    /**
     * Необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста
     * @param employees
     */
    public static void processTask04 (List<Employee> employees) {
        System.out.println("===>> Task 04: ");

        List<String> result = employees.stream()
                .filter(emp->"Инженер".equals(emp.getPosition()))
                .sorted(Comparator.comparingInt(Employee::getAge).reversed())
                .limit(3)
                .map(Employee::getName)
                .collect(Collectors.toList());

        result.forEach(System.out::println);
        System.out.println("\n");
    }

    /**
     * Посчитать средний возраст сотрудников с должностью «Инженер»
     * @param employees
     */
    public static void processTask05 (List<Employee> employees) {
        System.out.println("===>> Task 05: ");

        double result = employees.stream()
                .filter(emp->"Инженер".equals(emp.getPosition()))
                .mapToDouble(Employee::getAge)
                .average()
                .orElse(0);

        System.out.println(result);
        System.out.println("\n");
    }

    /**
     * Найдите в списке слов самое длинное
     *
     * @param strings  список для обработки
     */
    public static void processTask06 (List<String> strings) {
        System.out.println("===>> Task 06: ");

        String result = strings.stream()
                .sorted(Comparator.comparingInt(String::length).reversed())
                .findFirst()
                .orElse("");

        System.out.println(result);
        System.out.println("\n");
    }

    /**
     * Имеется строка с набором слов в нижнем регистре, разделенных пробелом. Построить хеш-мап, в котором будут
     * хранится пары: слово - сколько раз оно встречается во входной строке
     *
     * @param text  строка  для обработки
     */
    public static void processTask07 (String text) {
        System.out.println("===>> Task 07: ");

        Map<String, Long> wordCount = Arrays.stream(text.split(" "))
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        System.out.println(wordCount);
        System.out.println("\n");
    }

    /**
     * Отпечатайте в консоль строки из списка в порядке увеличения длины слова, если слова имеют одинаковую длину,
     * то должен быть сохранен алфавитный порядок
     *
     * @param strings  список для обработки
     */
    public static void processTask08 (List<String> strings) {
        System.out.println("===>> Task 08: ");

        List<String> result = strings.stream()
                .sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
                .collect(Collectors.toList());

        result.forEach(System.out::println);
        System.out.println("\n");
    }

    /**
     * Имеется массив строк, в каждой из которых лежит набор из 5 слов, разделенных пробелом, найдите среди всех
     * слов самое длинное, если таких слов несколько, получите любое из них
     * @param strings
     */
    public static void processTask09 (String[] strings) {
        System.out.println("===>> Task 09: ");

        String result = Arrays.stream(strings)
                    .flatMap(s -> Arrays.stream(s.split(" ")))
                    .sorted(Comparator.comparingInt(String::length).reversed())
                    .findFirst()
                    .orElse("");

        System.out.println(result);
        System.out.println("\n");
    }

}
