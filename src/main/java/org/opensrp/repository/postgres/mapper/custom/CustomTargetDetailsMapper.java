package org.opensrp.repository.postgres.mapper.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.TargetDetails;

public interface CustomTargetDetailsMapper {
	
	List<TargetDetails> selectTargetDetailsByUsername(@Param("username") String username,
	                                                  @Param("timestamp") Long timestamp);
	
}
