package org.opensrp.service.formSubmission;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.AppStateToken;
import org.opensrp.repository.PlanRepository;
import org.opensrp.service.*;
import org.smartregister.domain.Client;
import org.smartregister.domain.Event;
import org.opensrp.repository.ClientsRepository;
import org.opensrp.repository.EventsRepository;
import org.opensrp.service.formSubmission.handler.EventsHandler;
import org.opensrp.service.formSubmission.handler.EventsRouter;
import org.opensrp.service.formSubmission.handler.IHandlerMapper;

public class EventListenerTest {
	
	@Rule
	public MockitoRule rule = MockitoJUnit.rule();
	
	@Mock
	private ConfigService configService;
	
	@Mock
	private EventsRepository allEvents;
	
	@Mock
	private ClientsRepository allClients;
	
	@Mock
	private ErrorTraceService errorTraceService;
	
	@Mock
	private ClientService clientService;
	
	@Mock
	private IHandlerMapper handlerMapper;

	@Mock
	private PlanRepository planRepository;

	@Mock
	private TaskGenerator taskGenerator;
	
	private EventService eventService;
	
	private EventsRouter eventsRouter;
	
	private EventsListener eventsListener;
	
	@Before
	public void setUp() {
		when(configService.registerAppStateToken(any(AllConstants.Config.class), Matchers.anyObject(), anyString(),
		    anyBoolean())).thenReturn(new AppStateToken("token", 01l, 02l));
		eventsRouter = new EventsRouter(handlerMapper, "/schedules/schedule-configs");
		eventService = new EventService(allEvents, clientService, taskGenerator, planRepository);
		eventsListener = new EventsListener(eventsRouter, configService, allEvents, eventService, errorTraceService,
		        allClients);
	}
	
	@Test
	public void shouldHandleNewEvent() throws Exception {
		EventsHandler eventHandler = mock(EventsHandler.class);
		Map<String, EventsHandler> handlerMap = new HashMap<>();
		handlerMap.put("VaccinesScheduleHandler", eventHandler);
		
		List<Client> clients = asList(new Client("2222"));
		List<Event> events = asList(
		    new Event().withIdentifier(AllConstants.Client.ZEIR_ID.toUpperCase(), "2").withEventType("Vaccination"),
		    new Event());
		
		when(allClients.findByEmptyServerVersion()).thenReturn(clients, Collections.<Client> emptyList());
		when(configService.getAppStateTokenByName(AllConstants.Config.EVENTS_PARSER_LAST_PROCESSED_EVENT))
		        .thenReturn(new AppStateToken("token", 1l, 0l));
		when(allEvents.findByEmptyServerVersion()).thenReturn(events, Collections.EMPTY_LIST);
		when(allEvents.findByServerVersion(1l)).thenReturn(events);
		when(clientService.findAllByIdentifier(AllConstants.Client.ZEIR_ID.toUpperCase(), "2")).thenReturn(clients);
		when(allEvents.findByBaseEntityAndType("222", "Birth Registration")).thenReturn(events);
		
		when(handlerMapper.handlerMap()).thenReturn(handlerMap);
		
		EventsListener spyEventListener = spy(eventsListener);
		when(spyEventListener.getCurrentMilliseconds()).thenReturn(0l);
		
		spyEventListener.processEvent();
		
		InOrder inOrder = inOrder(allClients, allEvents, eventHandler);
		clients.get(0).setServerVersion(System.currentTimeMillis());
		events.get(0).setServerVersion(System.currentTimeMillis());
		inOrder.verify(allClients).update(clients.get(0),true);
		inOrder.verify(allEvents).update(events.get(0),true);
		//inOrder.verify(eventHandler, atLeastOnce()).handle(eq(events.get(0)), any(JSONObject.class), eq("BCG"));
		
	}
	
	@Test
	public void testComparator() {
		
	}
	
}
