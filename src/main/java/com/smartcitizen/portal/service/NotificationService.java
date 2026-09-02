package com.smartcitizen.portal.service;

import java.util.List;

import com.smartcitizen.portal.dto.NotificationDto;

public interface NotificationService {

    NotificationDto createNotification(NotificationDto notificationDto);

    List<NotificationDto> getNotificationsByUser(Long userId);

    NotificationDto markAsRead(Long notificationId);
}