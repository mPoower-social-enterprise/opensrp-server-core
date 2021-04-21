package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.postgres.MhealthStockInformation;

public interface MhealthStockInformationRepository {
	
	List<MhealthStockInformation> getStockInformationByUsername(String username, Long timestamp);
	
}
