package com.productManagement.OrderService.External.Decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productManagement.OrderService.Exception.CustomException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.ErrorResponse;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper=new ObjectMapper();

        log.info("::{}",response.request().headers());
        log.info("::{}",response.request().url());

        try {
            ErrorResponse errorResponse=objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
            return new CustomException(errorResponse.getDetailMessageCode(), errorResponse.getTitleMessageCode(), response.status());

        } catch (IOException e) {
            throw new CustomException("Internal Server Error", "INTERNAL_SERVER_ERROR", 500);
        }

    }
}
