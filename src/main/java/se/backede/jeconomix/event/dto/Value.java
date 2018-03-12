/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event.dto;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 * @param <T>
 */
public class Value<T> {

    private final T value;
    private final Enum field;

    public Value(T value, Enum field) {
        this.field = field;
        this.value = value;
    }

    public String getFieldName() {
        return field.name();
    }

    public DataGetter getValue() {
        return new DataGetter(value);
    }

    public Class getClassType() {
        return value.getClass();
    }

    public String getFullClassName() {
        return value.getClass().getCanonicalName();
    }

    public boolean isNull() {
        return value == null;
    }

    public boolean isNotNull() {
        return value != null;
    }

    public boolean isInteger() {
        return getValue().getInteger() != null;
    }

    public boolean isString() {
        return getValue().getString() != null;
    }

    public boolean isBoolean() {
        return getValue().getBoolean() != null;
    }

    public boolean isCollection() {
        return getValue().getCollection() != null;
    }

    public boolean isDto() {
        return getValue().getDto() != null;
    }

    @Override
    public String toString() {
        if (!isNull()) {
            return value.toString();
        } else {
            return "";
        }
    }
}
