package com.meteergin.mapbackend.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class NearbySearchNotFoundException extends RuntimeException {
    public NearbySearchNotFoundException() {
        super();
    }
    public NearbySearchNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public NearbySearchNotFoundException(String message) {
        super(message);
    }
    public NearbySearchNotFoundException(Throwable cause) {
        super(cause);
    }
}
