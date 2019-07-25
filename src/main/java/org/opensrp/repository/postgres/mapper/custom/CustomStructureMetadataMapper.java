package org.opensrp.repository.postgres.mapper.custom;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.Location;
import org.opensrp.domain.postgres.Structure;
import org.opensrp.domain.postgres.StructureMetadataExample;
import org.opensrp.repository.postgres.mapper.StructureMetadataMapper;

public interface CustomStructureMetadataMapper extends StructureMetadataMapper {

	Structure findById(String id);

	List<Structure> selectMany(@Param("example") StructureMetadataExample locationMetadataExample,
			@Param("offset") int offset, @Param("limit") int limit);

	List<Location> selectManyByProperties(@Param("example") StructureMetadataExample locationMetadataExample,
			@Param("properties") Map<String, String> properties, @Param("geometry") boolean returnGeometry,
			@Param("offset") int offset, @Param("limit") int limit);

}