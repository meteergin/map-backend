package com.ericsson.mapbackend.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class NearbySearchNotFoundException extends RuntimeException {
}
