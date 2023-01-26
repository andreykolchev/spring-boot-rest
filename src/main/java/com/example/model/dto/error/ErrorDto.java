package com.example.model.dto.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

}