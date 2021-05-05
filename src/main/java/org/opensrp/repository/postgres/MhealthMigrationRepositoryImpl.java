package org.opensrp.repository.postgres;

import org.opensrp.domain.postgres.MhealthMigration;
import org.opensrp.repository.MhealthMigrationRepository;
import org.opensrp.repository.postgres.mapper.custom.CustomMhealthMigrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MhealthMigrationRepositoryImpl implements MhealthMigrationRepository {
	
	@Autowired
	private CustomMhealthMigrationMapper customMhealthMigrationMapper;
	
	@Override
	public Integer addMigration(MhealthMigration migration) {
		return customMhealthMigrationMapper.insertMigration(migration);
	}
	
}
