package org.opensrp.repository.postgres;

import java.util.List;

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
	
	@Override
	public List<String> getMigratedList(String provider, String type, Long timestamp) {
		return customMhealthMigrationMapper.selectMigratedList(provider, type, timestamp);
	}
	
	@Override
	public List<String> getRejectedList(String provider, String type, Long timestamp) {
		return customMhealthMigrationMapper.selectRejectedList(provider, type, timestamp);
	}
	
	@Override
	public MhealthMigration findMigrationById(Long id) {
		return customMhealthMigrationMapper.selectMigrationById(id);
	}
	
	@Override
	public List<MhealthMigration> findMigrationByIdRelationId(String relationalId, String status) {
		return customMhealthMigrationMapper.selectMigrationByIdRelationId(relationalId, status);
	}
	
	@Override
	public Integer updateMigration(MhealthMigration migration, String baseEntityId) {
		return customMhealthMigrationMapper.updateMigration(migration);
	}
	
	@Override
	public Integer updateMigrationStatusById(Long id, String status) {
		return customMhealthMigrationMapper.updateMigrationStatusById(id, status);
	}
	
	@Override
	public Integer updateMigrationStatusByRelationalId(String relationalId, String status) {
		return customMhealthMigrationMapper.updateMigrationStatusByRelationalId(relationalId, status);
	}
	
	@Override
	public MhealthMigration findFirstMigrationBybaseEntityId(String baseEntityId) {
		return customMhealthMigrationMapper.selectFirstMigrationBybaseEntityId(baseEntityId);
	}
	
}
