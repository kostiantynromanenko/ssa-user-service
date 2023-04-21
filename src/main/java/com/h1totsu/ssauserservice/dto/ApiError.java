package com.h1totsu.ssauserservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

  private int status;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String message;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<String> fieldErrors;

  public HttpStatusCode getStatus() {
    return HttpStatusCode.valueOf(status);
  }
}
