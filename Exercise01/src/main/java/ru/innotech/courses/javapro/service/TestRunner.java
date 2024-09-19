package ru.innotech.courses.javapro.service;

import java.lang.reflect.*;
import java.util.ArrayList;

/**
 *
 */
public class TestRunner {
    // Константы-параметры проверки аннотаций
    private static final int MAX_PRIORITY_VALUE = 10;       // Максимальный приоритет тестового метода
    private static final int MAX_BEFORESUITE_AMOUNT = 1;    // Максимальное количество методов в группе BeforeSuite
    private static final int MAX_AFTERSUITE_AMOUNT = 1;     // Максимальное количество методов в группе AfterSuite
    private static final int MAX_BEFORETEST_AMOUNT = 1;     // Максимальное количество методов в группе BeforeTest
    private static final int MAX_AFTERTEST_AMOUNT = 1;      // Максимальное количество методов в группе AfterTest

    // Списки методов тестируемого класса
    private static Class                    classData;
    private static ArrayList<Method>        beforeSuiteArray;
    private static ArrayList<Method>        afterSuiteArray;
    private static ArrayList<Method>        beforeTestArray;
    private static ArrayList<Method>        afterTestArray;
    private static ArrayList<Method>[]      testArray;

    static {
        testArray = new ArrayList[MAX_PRIORITY_VALUE];
        for (int i = 0; i < MAX_PRIORITY_VALUE; i++)
            testArray[i] = new ArrayList<Method>();

        beforeSuiteArray = new ArrayList<Method>();
        afterSuiteArray = new ArrayList<Method>();
        beforeTestArray = new ArrayList<Method>();
        afterTestArray = new ArrayList<Method>();
    }

    /**
     * Метод, полностью реализующий логику сбора информации о методах и запуске их на исполнение
     *
     * @param classInfo метаданные класса
     */
    public static void runTests (Class classInfo) {
        if (classInfo == null )
            throw new IllegalArgumentException();
        classData = classInfo;
        beforeSuiteArray.clear();
        afterSuiteArray.clear();
        beforeTestArray.clear();
        afterTestArray.clear();
        for (int i = 0; i < MAX_PRIORITY_VALUE; i++)
            testArray[i].clear();

        try {
            getClassInfo(classData);
            runTestPlan();
        } catch (AnnotationException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Проверка аннотаций на допустимость области применения и корректность значений, а также заполнение списков
     * методов, соответствующих различным аннотациям
     *
     * @param classInfo метаданные класса
     */
    private static void getClassInfo (Class classInfo) throws AnnotationException {
        if (classInfo == null)
            throw new IllegalArgumentException();
        int beforeSuiteCounter = 0,
                afterSuiteCounter = 0,
                beforeTestCounter = 0,
                afterTestCounter = 0;
        for (Method method: classInfo.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                beforeSuiteCounter++;
                if (!Modifier.isStatic(method.getModifiers()))
                    throw new AnnotationException("Method "+ method.getName()+ " annotated with @BeforeSuite must be static");
                if (beforeSuiteCounter > MAX_BEFORESUITE_AMOUNT)
                    throw new AnnotationException("Maximum allowed quantity of @BeforeSuite annotation exceeded");
                else
                    beforeSuiteArray.add(method);
            }
            else if (method.isAnnotationPresent(AfterSuite.class)) {
                afterSuiteCounter++;
                if (!Modifier.isStatic(method.getModifiers()))
                    throw new AnnotationException("Method "+ method.getName()+ " annotated with @AfterSuite must be static");
                if (afterSuiteCounter > MAX_AFTERSUITE_AMOUNT)
                    throw new AnnotationException("Maximum allowed quantity of @AfterSuite annotation exceeded");
                else
                    afterSuiteArray.add(method);
            } else if (method.isAnnotationPresent(BeforeTest.class)) {
                beforeTestCounter++;
                if (beforeTestCounter > MAX_BEFORETEST_AMOUNT)
                    throw new AnnotationException("Maximum allowed quantity of @BeforeTest annotation exceeded");
                else
                    beforeTestArray.add(method);
            }
            else if (method.isAnnotationPresent(AfterTest.class)) {
                afterTestCounter++;
                if (afterTestCounter > MAX_AFTERSUITE_AMOUNT)
                    throw new AnnotationException("Maximum allowed quantity of @AfterTest annotation exceeded");
                else
                    afterTestArray.add(method);
            }
            else if (method.isAnnotationPresent(Test.class)) {
                int priority = method.getAnnotation(Test.class).priority();
                if (priority < 1 || priority > 10)
                    throw new AnnotationException("Parameter priority for annotation @Test has invalid value "+ priority);
                else
                    testArray[priority-1].add(method);
            }
        }
    }

    /**
     *  Вызывает на исполнение методы тестируемого класса в соответствии с аннотациями
     */
    private static void runTestPlan() {
        // Run methods annotated by @BeforeSuite
        System.out.println("*************************************\n" +
                "Class: "+ classData.getName()+
                "\n*************************************");

        Object classInstance = null;
        try {
            classInstance = classData.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            System.out.println("Class instance creating error");
            ex.printStackTrace();
        }
        if (classInstance == null)
            return;

        System.out.println("1. Running methods annotated by @BeforeSuite");
        for (Method method: beforeSuiteArray) {
            System.out.println("\t===> Method: " + method.getName());
            try {
                invokeMethod(classInstance, method);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("\n2. Running methods annotated by @Test");
        for (int i= MAX_PRIORITY_VALUE - 1; i >= 0; i--) {
            if ( !testArray[i].isEmpty()) {
                System.out.println("\tTest priority = " + (i+1));
                for (Method method : testArray[i]) {
                    try {
                        for(Method beforeTestMethod: beforeTestArray) {
                            System.out.println("\t\t===> Before test method: " + beforeTestMethod.getName());
                            invokeMethod(classInstance, beforeTestMethod);
                        }
                        System.out.println("\t===> Method: " + method.getName());
                        invokeMethod(classInstance, method);
                        for(Method afterTestMethod: afterTestArray) {
                            System.out.println("\t\t===> After test method: " + afterTestMethod.getName());
                            invokeMethod(classInstance, afterTestMethod);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        // Run methods annotated by @AfterSuite
        System.out.println("\n3. Running methods annotated by @AfterSuite");
        for (Method method: afterSuiteArray) {
            System.out.println("\t===> Method: " + method.getName());
            try {
                invokeMethod(classInstance, method);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Вызывает тестируемый метод на исполнение с учетом примененных аннотаций
     *
     * @param instance      экземпляр объекта тестируемого класса
     * @param methodInfo    метаданные метода
     */
    private static void invokeMethod (Object instance, Method methodInfo) throws InvocationTargetException, IllegalAccessException, AnnotationException {
        Object result;
        Object[] parameters = null;

        Class[] parameterTypes = methodInfo.getParameterTypes();
        int parametersAmount = parameterTypes.length;
        if (parametersAmount > 0) {
            parameters = new Object[methodInfo.getParameterCount()];
            if (methodInfo.isAnnotationPresent(CsvSource.class)) {
                CsvSource parametersString = methodInfo.getAnnotation(CsvSource.class);
                String[] params = parametersString.value().split(",");
                if (params.length != parametersAmount)
                    throw new AnnotationException ("Invalid parameters amount in @CsvSource annotation");
                for (int i = 0; i < parametersAmount; i++) {
                    parameters[i] = convertStringToSpecificType(parameterTypes[i], params[i]);
                }
            } else {
                for (int i = 0; i < parametersAmount; i++) {
                    parameters[i] = convertStringToSpecificType(parameterTypes[i], null);
                }
            }
        }

        boolean isMethodStatic = Modifier.isStatic(methodInfo.getModifiers());
        methodInfo.setAccessible(true);
        result = methodInfo.invoke(isMethodStatic?null: instance, parameters);
        System.out.println(result);
    }

    /**
     * Формирует параметр метода из строки аннотации @CsvSource и информации о типе параметра из метаданных метода
     *
     * @param paramType         тип параметра
     * @param paramStringValue  строковое представление параметра. Если равно null, то параметр получает значение
     *                          по умолчанию (0- для числовых параметров и пустая строка для строковых)
     * @return                  возвращаемое методм значение
     */
    private static Object convertStringToSpecificType (Class paramType, String paramStringValue) {
        Object result = null;
        if (paramType == int.class) {
            result = (paramStringValue != null)? Integer.parseInt(paramStringValue): 0;
        } else if (paramType == long.class) {
            result = (paramStringValue != null)? Long.parseLong(paramStringValue) : 0L;
        } else if (paramType == double.class) {
            result = (paramStringValue != null)? Double.parseDouble(paramStringValue) : 0.0;
        } else if (paramType == String.class) {
            result = (paramStringValue != null)? new String (paramStringValue) : "";
        }
        return result;
    }
}
