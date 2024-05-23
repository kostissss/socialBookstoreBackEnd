package com.example.socialBookStore.repositories;

import com.example.socialBookStore.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
