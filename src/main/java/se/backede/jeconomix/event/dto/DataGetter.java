/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event.dto;

import java.util.Collection;
import java.util.Optional;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
public class DataGetter {

    Object value;

    public DataGetter(Object value) {
        this.value = value;
    }

    public boolean isNotNull() {
        if (value == null) {
            return false;
        } else {
            return true;
        }
    }

    public Optional<String> getString() {
        try {
            if (value != null) {
                return Optional.ofNullable(String.valueOf(value));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Integer> getInteger() {
        try {
            return Optional.ofNullable(Integer.getInteger(convertToString(value)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Double> getDouble() {
        try {
            return Optional.ofNullable(Double.valueOf(convertToString(value)));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Optional<Long> getLong() {
        try {
            return Optional.ofNullable(Long.valueOf(convertToString(value)));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Optional<Boolean> getBoolean() {
        try {
            return Optional.ofNullable(Boolean.valueOf(convertToString(value)));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Optional<Collection> getCollection() {
        try {
            if (value instanceof Collection) {
                return Optional.ofNullable((Collection) value);
            } else {
                return Optional.empty();
            }
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Optional<Dto> getDto() {
        try {
            if (value instanceof Dto) {
                return Optional.ofNullable((Dto) value);
            } else {
               return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private String convertToString(Object value) {
        try {
            return String.valueOf(value);
        } catch (Exception e) {
            return "";
        }
    }

}
