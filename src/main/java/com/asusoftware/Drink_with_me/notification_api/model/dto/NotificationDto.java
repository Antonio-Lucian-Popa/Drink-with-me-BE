package com.asusoftware.Drink_with_me.notification_api.model.dto;

import com.asusoftware.Drink_with_me.notification_api.model.Notification;
import com.asusoftware.Drink_with_me.notification_api.model.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class NotificationDto {

    private UUID id;
    private String message;
    private NotificationType type;
    private boolean read;
    private LocalDateTime createdAt;
    private UUID recipientUserId; // The recipient of the notification
    private UUID initiatorUserId; // The ID of the user who triggered the notification
    private String initiatorFirstName; // Initiator's first name
    private String profileImageUrl; // URL or relative path of the user's profile image

    // Constructors
    public static NotificationDto toDto(Notification notification, String profileImageUrl) {
        NotificationDto notificationDTO = new NotificationDto();
        notificationDTO.setId(notification.getId());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setType(notification.getType());
        notificationDTO.setRead(notification.isRead());
        notificationDTO.setCreatedAt(notification.getCreatedAt());
        notificationDTO.setRecipientUserId(notification.getRecipient().getId());
        notificationDTO.setInitiatorUserId(notification.getInitiator().getId());
        notificationDTO.setInitiatorFirstName(notification.getInitiator().getFirstName());
        notificationDTO.setProfileImageUrl(profileImageUrl);
        return notificationDTO;
    }

}