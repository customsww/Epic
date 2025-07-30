package com.example.backend.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    public Boolean status;
    public String message;
}
