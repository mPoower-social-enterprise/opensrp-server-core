package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.postgres.WebNotification;

public interface WebNotificationRepository {
	
	List<WebNotification> getWebNotificationsByusername(String username, Long timestamp);
	
}
