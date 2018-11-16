package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.PhysicalLocation;

public interface LocationRepository extends BaseRepository<PhysicalLocation> {

	PhysicalLocation getStructure(String id);

	List<PhysicalLocation> findLocationsByServerVersion(long serverVersion);

	List<PhysicalLocation> findStructuresByParentAndServerVersion(String parentId, long serverVersion);
}
