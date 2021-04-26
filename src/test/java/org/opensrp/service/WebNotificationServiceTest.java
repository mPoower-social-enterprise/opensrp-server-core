package org.opensrp.service;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.opensrp.domain.postgres.WebNotification;
import org.opensrp.repository.WebNotificationRepository;
import org.opensrp.repository.postgres.BaseRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;

public class WebNotificationServiceTest extends BaseRepositoryTest {
	
	private WebNotificationService webNotificationService;
	
	@Autowired
	private WebNotificationRepository webNotificationRepository;
	
	@Override
	protected Set<String> getDatabaseScripts() {
		Set<String> scripts = new HashSet<>();
		scripts.add("practitioner_details.sql");
		scripts.add("web_notification.sql");
		return scripts;
	}
	
	@Before
	public void setUpPostgresRepository() {
		webNotificationService = new WebNotificationService(webNotificationRepository);
	}
	
	@Test
	public void testGetWebNotificationsByusername() {
		List<WebNotification> webNotifications = webNotificationService.getWebNotificationsByUsername("p1", 0l);
		assertEquals(1, webNotifications.size());
		assertEquals("test", webNotifications.get(0).getTitle());
	}
	
	@Test
	public void testGetWebNotificationsByusernameReturnEmpty() {
		List<WebNotification> webNotifications = webNotificationService.getWebNotificationsByUsername("p122", 0l);
		assertEquals(0, webNotifications.size());
		
	}
}
