package org.opensrp.repository.postgres;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.domain.postgres.HouseholdId;
import org.opensrp.repository.HouseholdIdRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class householdIdRepositoryTest extends BaseRepositoryTest {
	
	@Autowired
	private HouseholdIdRepository householdIdRepository;
	
	private Integer HOUSEHOLD_ID_LIMIT = 200;
	
	@BeforeClass
	public static void bootStrap() {
		tableNames = Arrays.asList("core.household_id", "core.household_id_guest");
	}
	
	@Override
	protected Set<String> getDatabaseScripts() {
		return null;
	}
	
	@Test
	public void testInsertHouseholdId() {
		int insert = householdIdRepository.insertHouseholdId(generateHouseholdId());
		assertEquals(1, insert);
	}
	
	@Test
	public void testGetMaxHouseholdIdByLocation() {
		int insert = householdIdRepository.insertHouseholdId(generateHouseholdId());
		assertEquals(1, insert);
		Integer maxhealthId = householdIdRepository.getMaxHouseholdIdByLocation(22);
		assertEquals(201, maxhealthId.intValue());
		
	}
	
	@Test
	public void testGetSeriesOfHouseholdId() {
		
		List<String> series = householdIdRepository.getSeriesOfHouseholdId(1);
		assertEquals(201, series.size());
		assertNotNull(series);
		
	}
	
	@Test
	public void testInsertGuestHouseholdId() {
		int insert = householdIdRepository.insertGuestHouseholdId(generateHouseholdId());
		assertEquals(1, insert);
	}
	
	@Test
	public void testGetMaxGuestHouseholdIdByLocation() {
		int insert = householdIdRepository.insertGuestHouseholdId(generateHouseholdId());
		assertEquals(1, insert);
		Integer maxhealthId = householdIdRepository.getMaxGuestHouseholdIdByLocation(22);
		assertEquals(201, maxhealthId.intValue());
		
	}
	
	@Test
	public void testGetSeriesOfGuestHouseholdId() {
		
		List<String> series = householdIdRepository.getSeriesOfGuestHouseholdId(1);
		assertEquals(201, series.size());
		assertNotNull(series);
		
	}
	
	private HouseholdId generateHouseholdId() {
		HouseholdId healthId = new HouseholdId();
		healthId.setCreated(new Date());
		healthId.sethId(String.valueOf(1 + HOUSEHOLD_ID_LIMIT));
		healthId.setLocationId(22);
		healthId.setStatus(true);
		return healthId;
	}
	
}
