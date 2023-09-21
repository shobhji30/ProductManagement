package com.productManagement.OrderService.External.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public
class CustomErrorResponse {

    String errorCode;
    String errorMessage;


}
