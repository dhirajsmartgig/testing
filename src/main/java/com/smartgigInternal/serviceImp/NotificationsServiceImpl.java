package com.smartgigInternal.serviceImp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartgigInternal.entity.Notifications;
import com.smartgigInternal.repository.NotificationsRepository;
import com.smartgigInternal.service.NotificationsService;

@Service
public class NotificationsServiceImpl implements NotificationsService {

	@Autowired
	private NotificationsRepository notificationsRepository;

	@Override
	public Map<String, Object> saveNotifications(Notifications notifications) {

		Notifications savedNotifications = notificationsRepository.save(notifications);
		Map<String, Object> map = new HashMap<>();
		if (savedNotifications != null) {
			map.put("msg", "notification added");
			map.put("NotificationId", savedNotifications.getId());
		}
		return map;
	}

	@Override
	public Optional<Notifications> getNotifactionDetails(int id) {
	Optional<Notifications> noti = notificationsRepository.findById(id);
		return noti;
	}

	@Override
	public List<Notifications> getAllNotification() {
		return notificationsRepository.findAll();
	}

}
