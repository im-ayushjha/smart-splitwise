package com.ayush.smart_splitwise.core.group.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class GroupSummaryResponse {

    private Long id;
    private String name;
    private String description;
    private UserSummary createdBy;
    private Long memberCount;
    private LocalDateTime createdAt;
}
