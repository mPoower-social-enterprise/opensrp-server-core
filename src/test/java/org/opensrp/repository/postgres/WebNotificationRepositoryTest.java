package org.opensrp.repository.postgres;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.opensrp.domain.postgres.WebNotification;
import org.opensrp.repository.WebNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class WebNotificationRepositoryTest extends BaseRepositoryTest {
	
	@Autowired
	private WebNotificationRepository webNotificationRepository;
	
	@Override
	protected Set<String> getDatabaseScripts() {
		Set<String> scripts = new HashSet<>();
		scripts.add("practitioner_details.sql");
		scripts.add("web_notification.sql");
		return scripts;
	}
	
	@Test
	public void testGetWebNotificationsByusername() {
		List<WebNotification> webNotifications = webNotificationRepository.getWebNotificationsByUsername("p1", 0l);
		assertEquals(1, webNotifications.size());
		assertEquals("test", webNotifications.get(0).getTitle());
	}
	
	@Test
	public void testShouldReturnEmptyGetWebNotificationsByusername() {
		List<WebNotification> webNotifications = webNotificationRepository.getWebNotificationsByUsername("p122", 0l);
		assertEquals(0, webNotifications.size());
		
	}
	
}
