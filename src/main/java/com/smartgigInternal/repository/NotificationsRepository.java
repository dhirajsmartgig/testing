package com.smartgigInternal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartgigInternal.entity.Notifications;

public interface NotificationsRepository extends JpaRepository<Notifications, Integer> {

}
