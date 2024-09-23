package ru.innotech.courses.javapro;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Exercise02Main {
    public static void main(String[] args) {
        System.out.println(">>> InnoTech Java Pro courses: module 1. Exercise 02 (2024-09-19) ==>>>\n");

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
        Optional<Integer> result = numbers.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst();
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
        Optional<Integer> result = numbers.stream()
                .sorted((a,b)-> b-a)
                .distinct()
                .skip(2)
                .findFirst();
        System.out.println(result);
        System.out.println("\n");
    }

}
