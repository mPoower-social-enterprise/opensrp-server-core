package org.opensrp.repository.postgres.mapper.custom;

import java.util.List;

import org.opensrp.domain.postgres.MhealthMigration;
import org.springframework.data.repository.query.Param;

public interface CustomMhealthMigrationMapper {
	
	Integer insertMigration(MhealthMigration migration);
	
	List<String> selectMigratedList(@Param("provider") String provider, @Param("type") String type,
	                                @Param("timestamp") Long timestamp);
	
	List<String> selectRejectedList(@Param("provider") String provider, @Param("type") String type,
	                                @Param("timestamp") Long timestamp);
	
	MhealthMigration selectMigrationById(Long id);
	
	List<MhealthMigration> selectMigrationByIdRelationId(@Param("relationalId") String relationalId,
	                                                     @Param("status") String status);
	
	Integer updateMigration(MhealthMigration migration);
	
	Integer updateMigrationStatusById(@Param("id") Long id, @Param("status") String status);
	
	Integer updateMigrationStatusByRelationalId(@Param("relationalId") String relationalId, @Param("status") String status);
	
	MhealthMigration selectFirstMigrationBybaseEntityId(@Param("baseEntityId") String baseEntityId);
	
}
