package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.postgres.TargetDetails;

public interface TargetDetailsRepository {
	
	List<TargetDetails> getTargetDetailsByUsername(String username, Long timestamp);
}
