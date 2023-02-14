package com.smartgigInternal.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.smartgigInternal.entity.Notifications;
import com.smartgigInternal.service.NotificationsService;
import com.smartgigInternal.serviceImp.S3bucketStorageService;

@Controller
@RequestMapping("/smg/notifications/")
public class NotificationsController {

	@Autowired
	S3bucketStorageService s3Bucket;

	@Autowired
	private NotificationsService notificationsService;

	@PostMapping("/saveNotifications")
	public ResponseEntity<?> savecandidateDetails(@RequestPart Notifications notifications,
			@RequestPart(value = "attachment", required = false) MultipartFile attachment) throws IOException {

		notifications.setAttachment(s3Bucket.uploadFile(attachment));
		return new ResponseEntity<>(notificationsService.saveNotifications(notifications), HttpStatus.OK);
	}
	
	@GetMapping("/getNotifactionDetails/{id}")
	public ResponseEntity<?> getNotifactionDetails(@PathVariable int id) {
		return new ResponseEntity<>(notificationsService.getNotifactionDetails(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllNotification")
	public ResponseEntity<?> getAllNotification() {
		return new ResponseEntity<>(notificationsService.getAllNotification(), HttpStatus.OK);
	}
}
