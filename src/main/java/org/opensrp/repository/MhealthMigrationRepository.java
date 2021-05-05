package org.opensrp.repository;

import org.opensrp.domain.postgres.MhealthMigration;

public interface MhealthMigrationRepository {
	
	Integer addMigration(MhealthMigration migration);
	
}
