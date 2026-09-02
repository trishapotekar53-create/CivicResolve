package com.smartcitizen.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartcitizen.portal.model.Notification;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByUserUserid(Long userId);

    void deleteByUserUserid(Long userId);
}