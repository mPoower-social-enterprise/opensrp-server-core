package org.opensrp.service;

import java.util.List;

import org.opensrp.domain.postgres.MhealthStockInformation;
import org.opensrp.repository.MhealthStockInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MhealthStockInformationService {
	
	private MhealthStockInformationRepository mhealthStockInformationRepository;
	
	@Autowired
	public MhealthStockInformationService(MhealthStockInformationRepository mhealthStockInformationRepository) {
		this.mhealthStockInformationRepository = mhealthStockInformationRepository;
	}
	
	public List<MhealthStockInformation> getStockInformationByUsername(String username, Long timestamp) {
		return mhealthStockInformationRepository.getStockInformationByUsername(username, timestamp);
	}
}
