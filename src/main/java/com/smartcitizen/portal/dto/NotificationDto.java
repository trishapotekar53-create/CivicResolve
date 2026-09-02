package com.smartcitizen.portal.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    private Long notificationId;

    private String title;

    private String message;

    private Boolean read;

    private LocalDateTime createdAt;

    private Long userId;
}