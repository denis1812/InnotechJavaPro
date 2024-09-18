package ru.innotech.courses.javapro;

import ru.innotech.courses.javapro.denis812.*;
import ru.innotech.courses.javapro.service.*;

public class Exercise01Main {
    public static void main(String[] args) {
        System.out.println(">>> InnoTech Java Pro courses: module 1. Exercise 01 (2024-09-12) ==>>>\n");

        TestRunner.runTests(ClassForTest1.class);   // класс с корректно заданными аннотациями
        TestRunner.runTests(ClassForTest2.class);   // класс для проверки ошибок установки аннотаций.
    }
}
