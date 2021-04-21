package org.opensrp.repository.postgres;

import java.util.List;

import org.opensrp.domain.postgres.WebNotification;
import org.opensrp.repository.WebNotificationRepository;
import org.opensrp.repository.postgres.mapper.custom.CustomWebNotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WebNotificationRepositoryImpl implements WebNotificationRepository {
	
	@Autowired
	private CustomWebNotificationMapper CustomWebNotificationMapper;
	
	@Override
	public List<WebNotification> getWebNotificationsByusername(String username, Long timestamp) {
		
		return CustomWebNotificationMapper.selectWebNotificationsByUsername(username, timestamp);
	}
	
}
