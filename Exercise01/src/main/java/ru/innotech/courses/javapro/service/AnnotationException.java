package ru.innotech.courses.javapro.service;

/**
 * @class   Класс исключений, возникающих при проверке аннотаций
*/
public class AnnotationException extends Exception
{
    public AnnotationException (String errorMessage) {
        super(errorMessage);
    }
}
