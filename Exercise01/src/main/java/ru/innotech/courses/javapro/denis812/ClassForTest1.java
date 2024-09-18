package ru.innotech.courses.javapro.denis812;

import ru.innotech.courses.javapro.service.*;

public class ClassForTest1 {
    private static final String packageName = "ru.innotech.courses.javapro.mod1.denis812";
    private static final String className = "ClassForTest1";
    private static String environmentName;

    private static int testCounter = 0;

    private long serialNumber;
    private String deviceName;
    private double deviceWeight;

    public ClassForTest1 () {
        System.out.println(">>> ClassForTest1() ");
    }

    @BeforeSuite
    @CsvSource("Test environment")
    public static String initTestingEnvironment( String envName) {
        environmentName = envName;
        return packageName + "." +className + ": " + environmentName;
    }

    @AfterSuite
    public static String getTestingStatus() {
        return environmentName;
    }

    @Test(priority = 6)
    @CsvSource("345678214")
    public String convertLongToString (long number) {
        return String.valueOf(number);
    }

    @Test(priority = 6)
    @CsvSource("45.025")
    public String convertDoubleToString (double number) {
        return String.valueOf(number);
    }

    @Test(priority = 8)
    @CsvSource("this is an example to upper case")
    public String getUppercasedText (String text) {
        return text.toUpperCase();
    }

    @Test(priority = 8)
    @CsvSource("ATTENTION! THIS TEXT WILL BE LOWERED!")
    public String getLowercasedText (String text) {
        return text.toLowerCase();
    }

    @BeforeTest
    private void OpeningTest() {
        System.out.println("\t\t [Test counter value is " + (++testCounter) + "]");
    }

    @AfterTest
    private void ClosingTest() {
        System.out.println("\t\t [<<<---***--->>>]");
    }

}
