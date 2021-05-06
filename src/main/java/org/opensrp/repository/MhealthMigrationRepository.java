package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.postgres.MhealthMigration;

public interface MhealthMigrationRepository {
	
	Integer addMigration(MhealthMigration migration);
	
	List<String> getMigratedList(String provider, String type, Long timestamp);
	
	List<String> getRejectedList(String provider, String type, Long timestamp);
	
	MhealthMigration findMigrationById(Long id);
	
	List<MhealthMigration> findMigrationByIdRelationId(String relationalId, String status);
	
	Integer updateMigration(MhealthMigration migration, String baseEntityId);
	
	Integer updateMigrationStatusById(Long id, String status);
	
	Integer updateMigrationStatusByRelationalId(String relationalId, String status);
	
	MhealthMigration findFirstMigrationBybaseEntityId(String baseEntityId);
	
}
