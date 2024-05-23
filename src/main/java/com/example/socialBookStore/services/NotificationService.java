package com.example.socialBookStore.services;

import com.example.socialBookStore.models.Notification;
import com.example.socialBookStore.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public boolean save(Notification notification) {
        try {
            notificationRepository.save(notification);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void deleteNotificationById(Integer id) {
        notificationRepository.deleteById(id);
    }
}
