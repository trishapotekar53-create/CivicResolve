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


    // =========================================
    // CREATE NOTIFICATION
    // =========================================

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


    // =========================================
    // GET ALL NOTIFICATIONS
    // Used by Admin
    // =========================================

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {

        return ResponseEntity.ok(
                notificationService.getAllNotifications()
        );
    }


    // =========================================
    // GET NOTIFICATIONS BY USER
    // Used by Citizen
    // =========================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getNotificationsByUser(userId)
        );
    }


    // =========================================
    // MARK NOTIFICATION AS READ
    // =========================================

    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationDto> markAsRead(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                notificationService.markAsRead(id)
        );
    }
}