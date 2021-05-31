package org.opensrp.repository.postgres.mapper.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.MhealthStockInformation;

public interface CustomMhealthStockInformationMapper {
	
	List<MhealthStockInformation> selectStockInformationByUsername(@Param("username") String username,
	                                                               @Param("timestamp") Long timestamp);
	
}
