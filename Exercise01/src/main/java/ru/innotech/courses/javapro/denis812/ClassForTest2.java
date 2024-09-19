package ru.innotech.courses.javapro.denis812;

import ru.innotech.courses.javapro.service.*;

public class ClassForTest2 {
    private final String packageName = "ru.innotech.courses.javapro.mod1.denis812";
    private final String className = "ClassForTest2";
    private String environmentName;

    private long serialNumber;
    private String deviceName;
    private double deviceWeight;

    public ClassForTest2 () {
        System.out.println(">>> ClassForTest2() ");
    }

    // Ошибка: аннотация задана для нестатического метода
    @BeforeSuite
    public String initTestingEnvironment( String envName) {
        environmentName = envName;
        return packageName + "." +className + ": " + environmentName;
    }

    // Ошибка: аннотация задана для нестатического метода
    @AfterSuite
    public String getTestingStatus() {
        return environmentName;
    }

    // Ошибка: приоритет со значением вне установленного диапазона
    @Test(priority = 15)
    public String convertLongToString (long number) {
        return String.valueOf(number);
    }

    // Ошибка: приоритет со значением вне установленного диапазона
    @Test(priority = 15)
    public String convertDoubleToString (double number) {
        return String.valueOf(number);
    }

    // Ошибка: приоритет со значением вне установленного диапазона
    @Test(priority = -2)
    public String getUppercasedText (String text) {
        return text.toUpperCase();
    }

    // Ошибка: приоритет со значением вне установленного диапазона
    @Test(priority = -2)
    public String getLowercasedText (String text) {
        return text.toLowerCase();
    }

}
