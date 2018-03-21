/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event.dto;

import java.util.Collection;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
@Slf4j
public class DataGetter<T> {

    Object value;

    public DataGetter(Object value) {
        this.value = value;
    }

    public boolean isNotNull() {
        return value != null;
    }

    public Optional<T> getObject() {
        try {
            return (T) value != null ? Optional.ofNullable((T) value) : Optional.empty();
        } catch (Exception e) {
            log.error("Error when converting to String", e);
        }
        return Optional.empty();
    }

    public Optional<String> getString() {
        try {
            return value != null ? Optional.ofNullable(String.valueOf(value)) : Optional.empty();
        } catch (Exception e) {
            log.error("Error when converting to String", e);
        }
        return Optional.empty();
    }

    public Optional<Integer> getInteger() {
        try {
            return Optional.ofNullable(Integer.parseInt(convertToString(value)));
        } catch (Exception e) {
            log.error("Error when converting to Integer", e);
            return Optional.empty();
        }
    }

    public Optional<Double> getDouble() {
        try {
            return Optional.ofNullable(Double.parseDouble(convertToString(value)));
        } catch (NumberFormatException e) {
            log.error("Error when converting to Double", e);
            return Optional.empty();
        }
    }

    public Optional<Long> getLong() {
        try {
            return Optional.ofNullable(Long.parseLong(convertToString(value)));
        } catch (NumberFormatException e) {
            log.error("Error when converting to Long", e);
            return Optional.empty();
        }
    }

    public Optional<Boolean> getBoolean() {
        try {
            return Optional.ofNullable(Boolean.parseBoolean(convertToString(value)));
        } catch (NumberFormatException e) {
            log.error("Error when converting to Boolean", e);
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
            log.error("Error when converting to Collection", e);
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
            log.error("Error when converting to Dto", e);
            return Optional.empty();
        }
    }

    private String convertToString(Object value) {
        try {
            return String.valueOf(value);
        } catch (Exception e) {
            log.error("Error when converting to String", e);
            return "";
        }
    }

}
