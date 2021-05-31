package org.opensrp.repository.postgres;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.opensrp.domain.postgres.MhealthStockInformation;
import org.opensrp.repository.MhealthStockInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MhealthStockInformationRepositoryTest extends BaseRepositoryTest {
	
	@Autowired
	private MhealthStockInformationRepository mhealthStockInformationRepository;
	
	@Override
	protected Set<String> getDatabaseScripts() {
		Set<String> scripts = new HashSet<>();
		scripts.add("practitioner_details.sql");
		scripts.add("product_stock_details.sql");
		return scripts;
	}
	
	@Test
	public void testGetStockInformationByUsername() {
		List<MhealthStockInformation> mhealthStockInformations = mhealthStockInformationRepository
		        .getStockInformationByUsername("p1", 0l);
		assertEquals(1, mhealthStockInformations.size());
		assertEquals("Women package", mhealthStockInformations.get(0).getProductName());
	}
	
	@Test
	public void testshouldReturnReturnEmptyGetStockInformationByUsername() {
		List<MhealthStockInformation> mhealthStockInformations = mhealthStockInformationRepository
		        .getStockInformationByUsername("p3", 0l);
		assertEquals(0, mhealthStockInformations.size());
	}
}
