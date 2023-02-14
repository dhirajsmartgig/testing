package com.smartgigInternal.service;

import java.util.List;
import java.util.Map;

import com.smartgigInternal.entity.Notifications;

public interface NotificationsService {

	Map<String, Object> saveNotifications(Notifications notificationsRequestDto);

	public Object getNotifactionDetails(int id);

	public List<Notifications> getAllNotification();
}
