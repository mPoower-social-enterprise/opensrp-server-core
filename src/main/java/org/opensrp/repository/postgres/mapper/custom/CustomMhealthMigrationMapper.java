package org.opensrp.repository.postgres.mapper.custom;

import org.opensrp.domain.postgres.MhealthMigration;

public interface CustomMhealthMigrationMapper {
	
	Integer insertMigration(MhealthMigration migration);
	
}
