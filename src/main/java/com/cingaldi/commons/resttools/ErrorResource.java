package com.cingaldi.commons.resttools;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErrorResource {

    @JsonProperty("error")
    private ErrorResponse error;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class ErrorResponse {

        @JsonProperty("message")
        private String message;
    }
}
