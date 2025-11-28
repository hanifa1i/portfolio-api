package com.hanif.portfolioapi.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ApiResponse {
    private boolean success;
    private String message;
    private Long id;

    public static ResponseEntity<ApiResponse> success(String message, Long id) {
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message(message)
                .id(id)
                .build()
        );
    }
}