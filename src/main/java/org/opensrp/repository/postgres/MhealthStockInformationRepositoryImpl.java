package org.opensrp.repository.postgres;

import java.util.List;

import org.opensrp.domain.postgres.MhealthStockInformation;
import org.opensrp.repository.MhealthStockInformationRepository;
import org.opensrp.repository.postgres.mapper.custom.CustomMhealthStockInformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MhealthStockInformationRepositoryImpl implements MhealthStockInformationRepository {
	
	@Autowired
	private CustomMhealthStockInformationMapper customMhealthStockInformationMapper;
	
	@Override
	public List<MhealthStockInformation> getStockInformationByUsername(String username, Long timestamp) {
		
		return customMhealthStockInformationMapper.selectStockInformationByUsername(username, timestamp);
	}
	
}
