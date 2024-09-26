package ru.innotech.courses.javapro.denis812;

public class Employee {
    private String      name;
    private int         age;
    private String      position;

    /**
     *
     */
    public Employee() {
        name = null;
        age = 0;
        position = null;
    }

    /**
     *
     * @param name
     * @param age
     * @param position
     */
    public Employee(String name, int age, String position) {
        this.name = name;
        this.age = age;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPosition() {
        return position;
    }

}
