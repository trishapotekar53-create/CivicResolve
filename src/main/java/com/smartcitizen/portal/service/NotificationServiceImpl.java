package com.smartcitizen.portal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.smartcitizen.portal.dto.NotificationDto;
import com.smartcitizen.portal.model.Notification;
import com.smartcitizen.portal.model.User;
import com.smartcitizen.portal.repository.NotificationRepository;
import com.smartcitizen.portal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;


    @Override
    public NotificationDto createNotification(
            NotificationDto notificationDto) {

        Notification notification = new Notification();

        notification.setTitle(notificationDto.getTitle());

        notification.setMessage(notificationDto.getMessage());

        notification.setRead(false);

        notification.setCreatedAt(LocalDateTime.now());


        if (notificationDto.getUserId() != null) {

            User user = userRepository
                    .findById(notificationDto.getUserId())
                    .orElseThrow(
                            () -> new RuntimeException("User not found")
                    );

            notification.setUser(user);
        }


        Notification saved =
                notificationRepository.save(notification);

        return convertToDto(saved);
    }


    @Override
    public List<NotificationDto> getNotificationsByUser(
            Long userId) {

        return notificationRepository
                .findByUserUserid(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    public NotificationDto markAsRead(
            Long notificationId) {

        Notification notification =
                notificationRepository
                        .findById(notificationId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Notification not found"
                                )
                        );

        notification.setRead(true);

        Notification updated =
                notificationRepository.save(notification);

        return convertToDto(updated);
    }


    private NotificationDto convertToDto(
            Notification notification) {

        NotificationDto dto =
                new NotificationDto();

        dto.setNotificationId(
                notification.getNotificationId()
        );

        dto.setTitle(
                notification.getTitle()
        );

        dto.setMessage(
                notification.getMessage()
        );

        dto.setRead(
                notification.isRead()
        );

        dto.setCreatedAt(
                notification.getCreatedAt()
        );


        if (notification.getUser() != null) {

            dto.setUserId(
                    notification.getUser().getUserid()
            );
        }

        return dto;
    }
}