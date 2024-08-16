package com.asusoftware.Drink_with_me.notification_api.repository;

import com.asusoftware.Drink_with_me.notification_api.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    @Query("SELECT n FROM Notification n WHERE n.recipient.id = ?1")
    Page<Notification> findByRecipientId(UUID recipientId, Pageable pageable);
}
