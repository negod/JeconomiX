/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event.dto;

import java.util.EnumMap;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
@Slf4j
public class DtoErrorHandler {

    public enum DtoError {

        ERROR_MESSAGE, ERROR_CODE;
    }

    EnumMap<DtoError, Value> errors = null;

    public DtoErrorHandler() {
    }

    public DtoErrorHandler(String message, String code) {
        errors = new EnumMap<>(DtoError.class);
        errors.put(DtoError.ERROR_MESSAGE, new Value(message, DtoError.ERROR_MESSAGE));
        errors.put(DtoError.ERROR_CODE, new Value(code, DtoError.ERROR_CODE));
        log.info("Sending ErrorMessage through DTO :" + message);
    }

    public boolean hasDtoError() {
        if (errors == null) {
            return false;
        } else {
            return true;
        }
    }

    public DtoErrorViewer getError() {
        return new DtoErrorViewer(errors);
    }

    public class DtoErrorViewer {

        private final EnumMap<DtoError, Value> errors;

        public DtoErrorViewer(EnumMap<DtoError, Value> errors) {
            this.errors = errors;
        }

        public Optional<String> getMessage() {
            return errors.get(Dto.DtoError.ERROR_MESSAGE).getValue().getString();
        }

        public Optional<String> getErrorCode() {
            return errors.get(Dto.DtoError.ERROR_CODE).getValue().getString();
        }
    }

}
