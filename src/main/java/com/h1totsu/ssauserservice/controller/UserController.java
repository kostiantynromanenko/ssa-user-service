package com.h1totsu.ssauserservice.controller;

import static java.util.Objects.nonNull;

import java.util.UUID;

import com.h1totsu.ssauserservice.dto.UserDto;
import com.h1totsu.ssauserservice.dto.UserSignUpError;
import com.h1totsu.ssauserservice.dto.UserSignUpRequest;
import com.h1totsu.ssauserservice.enums.SignUpErrorCode;
import com.h1totsu.ssauserservice.exception.SignUpException;
import com.h1totsu.ssauserservice.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(info = @Info(title = "User API", description = "API for managing user accounts",
    version = "1.0"))
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final ModelMapper modelMapper;

  @GetMapping("/{id}")
  @Operation(summary = "Get a user by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User found"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  public ResponseEntity<?> getUserById(
      @Parameter(description = "ID of the user to retrieve", required = true)
      @PathVariable UUID id) {
    final var user = userService.getUserById(id);
    if (nonNull(user)) {
      final var userDto = modelMapper.map(user, UserDto.class);
      return ResponseEntity.ok(userDto);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping(path = "/sign-up")
  @Operation(summary = "Sign up a new user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User account created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request body or email/username already exists",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserSignUpError.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<?> signUp(@RequestBody @Valid UserSignUpRequest userSignUpRequest) {
    try {
      userService.signUp(userSignUpRequest);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (SignUpException e) {
      if (e.getErrorCode() == SignUpErrorCode.SERVICE_ERROR) {
        return ResponseEntity.internalServerError().build();
      } else {
        final var signUpError = new UserSignUpError(HttpStatus.BAD_REQUEST.value(), e.getErrorCode().getValue());
        return ResponseEntity.badRequest().body(signUpError);
      }
    }
  }
}
