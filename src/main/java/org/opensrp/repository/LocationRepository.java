package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.PhysicalLocation;
import org.opensrp.domain.postgres.StructureFamilyDetails;

public interface LocationRepository extends BaseRepository<PhysicalLocation> {

	PhysicalLocation getStructure(String id);

	List<PhysicalLocation> findLocationsByServerVersion(long serverVersion);

	List<PhysicalLocation> findLocationsByNames(String locationNames, long serverVersion);

	List<PhysicalLocation> findStructuresByParentAndServerVersion(String parentId, long serverVersion);

	List<PhysicalLocation> findByEmptyServerVersion();

	List<PhysicalLocation> findStructuresByEmptyServerVersion();

	List<PhysicalLocation> getAllStructures();

	List<StructureFamilyDetails> findStructureAndFamilyDetails(double latitude, double longitude, double radius);
}
