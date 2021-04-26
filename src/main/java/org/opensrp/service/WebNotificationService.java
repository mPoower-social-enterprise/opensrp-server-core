package org.opensrp.service;

import java.util.List;

import org.opensrp.domain.postgres.WebNotification;
import org.opensrp.repository.WebNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebNotificationService {
	
	private WebNotificationRepository webNotificationRepository;
	
	@Autowired
	public WebNotificationService(WebNotificationRepository webNotificationRepository) {
		this.webNotificationRepository = webNotificationRepository;
	}
	
	public List<WebNotification> getWebNotificationsByUsername(String username, Long timestamp) {
		return webNotificationRepository.getWebNotificationsByUsername(username, timestamp);
	}
}
