package com.ayush.smart_splitwise.core.group.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateGroupRequest {
    @NotBlank
    private String name;

    private String description;
}
