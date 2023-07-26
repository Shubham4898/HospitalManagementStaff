package com.hms.dto;

import lombok.Data;
import org.springframework.web.service.annotation.GetExchange;

@Data
public class ResponseDto{
    private String status;
    private int statusCode;
}
