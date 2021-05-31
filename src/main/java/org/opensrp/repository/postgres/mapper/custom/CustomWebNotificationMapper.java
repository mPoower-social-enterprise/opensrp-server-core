package org.opensrp.repository.postgres.mapper.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.WebNotification;

public interface CustomWebNotificationMapper {
	
	List<WebNotification> selectWebNotificationsByUsername(@Param("username") String username,
	                                                       @Param("timestamp") Long timestamp);
	
}
