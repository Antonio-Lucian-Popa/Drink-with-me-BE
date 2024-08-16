package com.asusoftware.Drink_with_me.notification_api.controller;

import com.asusoftware.Drink_with_me.notification_api.model.dto.NotificationDto;
import com.asusoftware.Drink_with_me.notification_api.service.NotificationService;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Data
@RestController
@RequestMapping(path = "/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    // Endpoint for testing purposes
    /*
    @PostMapping("/notifications/send")
    public ResponseEntity<?> sendNotification(@RequestParam UUID senderUserId, @RequestParam UUID recipientUserId, @RequestParam UUID postId, @RequestParam NotificationType notificationType) {
        Notification notification = notificationService.createNotification(senderUserId, recipientUserId,postId, notificationType);
        return ResponseEntity.ok(notification);
    } */

    @GetMapping(path = "/findNotifications/{recipientId}")
    public ResponseEntity<Page<NotificationDto>> getNotifications(@PathVariable(name = "recipientId") UUID recipientId, Pageable pageable) {
        Page<NotificationDto> notifications = notificationService.findAllByRecipientId(pageable, recipientId);
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable UUID notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }
}
