package com.api.kwanza_services.Dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class DefaultDto {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID id;
    private Boolean isActive;
}
