package io.github.guilhermemgoncalves.rest.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Age is required")
    private Integer age;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

}
