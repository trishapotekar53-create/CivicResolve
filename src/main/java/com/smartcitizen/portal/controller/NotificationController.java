package com.smartcitizen.portal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartcitizen.portal.dto.NotificationDto;
import com.smartcitizen.portal.service.NotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    private final NotificationService notificationService;


    // Create Notification
    @PostMapping
    public ResponseEntity<NotificationDto> createNotification(
            @Valid @RequestBody NotificationDto notificationDto) {

        NotificationDto savedNotification =
                notificationService.createNotification(notificationDto);

        return new ResponseEntity<>(
                savedNotification,
                HttpStatus.CREATED
        );
    }


    // Get notifications of a particular user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getNotificationsByUser(userId)
        );
    }


    // Mark notification as read
    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationDto> markAsRead(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                notificationService.markAsRead(id)
        );
    }
}