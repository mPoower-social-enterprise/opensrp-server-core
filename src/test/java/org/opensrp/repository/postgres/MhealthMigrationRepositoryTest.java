package org.opensrp.repository.postgres;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.domain.postgres.MhealthMigration;
import org.opensrp.repository.MhealthMigrationRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MhealthMigrationRepositoryTest extends BaseRepositoryTest {
	
	@Autowired
	private MhealthMigrationRepository mhealthMigrationRepository;
	
	@BeforeClass
	public static void bootStrap() {
		tableNames = Arrays.asList("core.migration");
	}
	
	@Override
	protected Set<String> getDatabaseScripts() {
		return null;
	}
	
	@Test
	public void testAddMigration() {
		MhealthMigration addMemberMigration = addMemberMigration();
		MhealthMigration addFamilyMigration = addFamilyMigration();
		int insertMember = mhealthMigrationRepository.addMigration(addMemberMigration);
		int insertFamily = mhealthMigrationRepository.addMigration(addFamilyMigration);
		assertEquals(1, insertMember);
		assertEquals(1, insertFamily);
		
	}
	
	@Test
	public void testGetMigratedList() {
		MhealthMigration addFamilyMigration = addFamilyMigration();
		addFamilyMigration.setStatus("ACCEPT");
		mhealthMigrationRepository.addMigration(addFamilyMigration);
		List<String> expectedMigratedList = mhealthMigrationRepository.getMigratedList("01313047271", "HH", 0l);
		List<String> actualMigratedList = new ArrayList<>();
		actualMigratedList.add("8a661876-111a-4976-9a39-2ba0e1e4d776");
		assertEquals(actualMigratedList, expectedMigratedList);
	}
	
	@Test
	public void testShouldReturnEmptyGetMigratedList() {
		MhealthMigration addFamilyMigration = addFamilyMigration();
		addFamilyMigration.setStatus("ACCEPT");
		mhealthMigrationRepository.addMigration(addFamilyMigration);
		List<String> expectedMigratedList = mhealthMigrationRepository.getMigratedList("013130472713", "HH", 0l);
		assertEquals(0, expectedMigratedList.size());
	}
	
	@Test
	public void testGetRejectedList() {
		MhealthMigration addFamilyMigration = addFamilyMigration();
		addFamilyMigration.setStatus("REJECT");
		mhealthMigrationRepository.addMigration(addFamilyMigration);
		List<String> expectedRejectedList = mhealthMigrationRepository.getRejectedList("01313047103", "HH", 0l);
		List<String> actualRejectedList = new ArrayList<>();
		actualRejectedList.add("8a661876-111a-4976-9a39-2ba0e1e4d776");
		assertEquals(actualRejectedList, expectedRejectedList);
	}
	
	@Test
	public void testShouldReturnEmptyGetRejectedList() {
		MhealthMigration addFamilyMigration = addFamilyMigration();
		addFamilyMigration.setStatus("REJECT");
		mhealthMigrationRepository.addMigration(addFamilyMigration);
		List<String> expectedRejectedList = mhealthMigrationRepository.getRejectedList("013130471063", "HH", 0l);
		assertEquals(0, expectedRejectedList.size());
	}
	
	@Test
	public void testFindMigrationById() {
		MhealthMigration addFamilyMigration = addFamilyMigration();
		mhealthMigrationRepository.addMigration(addFamilyMigration);
		MhealthMigration getMigrationByBaseEntityId = mhealthMigrationRepository
		        .findFirstMigrationBybaseEntityId("8a661876-111a-4976-9a39-2ba0e1e4d776");
		MhealthMigration getMigrationById = mhealthMigrationRepository.findMigrationById(getMigrationByBaseEntityId.getId());
		assertEquals("8a661876-111a-4976-9a39-2ba0e1e4d776", getMigrationById.getBaseEntityId());
		
	}
	
	@Test
	public void testFindMigrationByIdRelationId() {
		MhealthMigration addFamilyMigration = addMemberMigration();
		addFamilyMigration.setStatus("ACCEPT");
		mhealthMigrationRepository.addMigration(addFamilyMigration);
		List<MhealthMigration> findByRelationshipid = mhealthMigrationRepository
		        .findMigrationByIdRelationId("8a661876-111a-4976-9a39-2ba0e1e4d776", "ACCEPT");
		assertEquals(1, findByRelationshipid.size());
		assertEquals("8a661876-111a-4976-9a39-2ba0e1e4d776", findByRelationshipid.get(0).getRelationalIdIn());
	}
	
	@Test
	public void testUpdateMigration() {
		MhealthMigration addFamilyMigration = addFamilyMigration();
		mhealthMigrationRepository.addMigration(addFamilyMigration);
		assertEquals("PENDING", addFamilyMigration.getStatus());
		addFamilyMigration.setStatus("REJECT");
		int updated = mhealthMigrationRepository.updateMigration(addFamilyMigration, "8a661876-111a-4976-9a39-2ba0e1e4d776");
		assertEquals(1, updated);
		MhealthMigration getMigrationByBaseEntityId = mhealthMigrationRepository
		        .findFirstMigrationBybaseEntityId("8a661876-111a-4976-9a39-2ba0e1e4d776");
		assertEquals("REJECT", getMigrationByBaseEntityId.getStatus());
	}
	
	public MhealthMigration addMemberMigration() {
		MhealthMigration mhealthMigration = new MhealthMigration();
		mhealthMigration.setTimestamp(1612686348398l);
		mhealthMigration.setDivisionIdOut("9266");
		mhealthMigration.setDivisionIdIn("9266");
		mhealthMigration.setMotherId("");
		mhealthMigration.setDistrictIdOut("_88279");
		mhealthMigration.setDistrictIdIn("_9267");
		mhealthMigration.setBaseEntityId("f3f3c8b3-3566-4509-b5dc-b058d4313380");
		mhealthMigration.setVillageOut("CHANDANA(BOUBAZAR)-13");
		mhealthMigration.setVillageIn("KARAIL-SHAHEB ALIR BARI (KHAMAR BARI)");
		mhealthMigration.setVillageIDOut("134874");
		mhealthMigration.setVillageIDIn("9288");
		mhealthMigration.setUpazilaOut("GAZIPUR CITY CORPORATION");
		mhealthMigration.setUpazilaIn("DHAKA NORTH CITY CORPORATION");
		mhealthMigration.setUnionOut("WARD NO. 17");
		mhealthMigration.setUnionIn("WARD NO. 19 (PART)");
		mhealthMigration.setStatus("PENDING");
		mhealthMigration.setRelationalIdOut("8a661876-111a-4976-9a39-2ba0e1e4d776");
		mhealthMigration.setRelationalIdIn("8a661876-111a-4976-9a39-2ba0e1e4d776");
		mhealthMigration.setRelationWithHHOut("Father");
		mhealthMigration.setRelationWithHHIn("Father");
		mhealthMigration.setPourasavaOut("NOT POURASABHA");
		mhealthMigration.setPourasavaIn("NOT POURASABHA");
		mhealthMigration.setNumberOfMemberOut("6");
		mhealthMigration.setNumberOfMemberIn("5");
		mhealthMigration.setMigrationDate(new DateTime().toDate());
		mhealthMigration.setMemberType("Member");
		mhealthMigration.setMemberName("Baby");
		mhealthMigration.setMemberIDOut("30333300017030131320104");
		mhealthMigration.setMemberIDIn("30333300017030131320104");
		mhealthMigration.setIsMember("no");
		mhealthMigration.setDob(new DateTime().toDate());
		mhealthMigration.setDivisionOut("DHAKA");
		mhealthMigration.setDivisionIn("DHAKA");
		mhealthMigration.setDistrictOut("GAZIPUR");
		mhealthMigration.setDistrictIn("DHAKA");
		mhealthMigration.setBranchIDIn("8");
		mhealthMigration.setSKOut("01313047271");
		mhealthMigration.setSKIn("01313047103");
		mhealthMigration.setHHNameOut("Kitta");
		mhealthMigration.setHHNameIn("Kitta");
		mhealthMigration.setHHContactOut("01585785805");
		mhealthMigration.setHHContactIn("01585785805");
		mhealthMigration.setBranchIDOut("2982");
		return mhealthMigration;
	}
	
	public MhealthMigration addFamilyMigration() {
		MhealthMigration mhealthMigration = new MhealthMigration();
		mhealthMigration.setTimestamp(1612686348398l);
		mhealthMigration.setDivisionIdOut("9266");
		mhealthMigration.setDivisionIdIn("9266");
		mhealthMigration.setMotherId("");
		mhealthMigration.setDistrictIdOut("_88279");
		mhealthMigration.setDistrictIdIn("_9267");
		mhealthMigration.setBaseEntityId("8a661876-111a-4976-9a39-2ba0e1e4d776");
		mhealthMigration.setVillageOut("CHANDANA(BOUBAZAR)-13");
		mhealthMigration.setVillageIn("KARAIL-SHAHEB ALIR BARI (KHAMAR BARI)");
		mhealthMigration.setVillageIDOut("134874");
		mhealthMigration.setVillageIDIn("9288");
		mhealthMigration.setUpazilaOut("GAZIPUR CITY CORPORATION");
		mhealthMigration.setUpazilaIn("DHAKA NORTH CITY CORPORATION");
		mhealthMigration.setUnionOut("WARD NO. 17");
		mhealthMigration.setUnionIn("WARD NO. 19 (PART)");
		mhealthMigration.setStatus("PENDING");
		mhealthMigration.setRelationalIdOut("8a661876-111a-4976-9a39-2ba0e1e4d776");
		mhealthMigration.setRelationalIdIn("8a661876-111a-4976-9a39-2ba0e1e4d776");
		mhealthMigration.setRelationWithHHOut("Father");
		mhealthMigration.setRelationWithHHIn("Father");
		mhealthMigration.setPourasavaOut("NOT POURASABHA");
		mhealthMigration.setPourasavaIn("NOT POURASABHA");
		mhealthMigration.setNumberOfMemberOut("6");
		mhealthMigration.setNumberOfMemberIn("5");
		mhealthMigration.setMigrationDate(new DateTime().toDate());
		mhealthMigration.setMemberType("HH");
		mhealthMigration.setMemberName("Baby");
		mhealthMigration.setMemberIDOut("30333300017030131320104");
		mhealthMigration.setMemberIDIn("30333300017030131320104");
		mhealthMigration.setIsMember("no");
		mhealthMigration.setDob(new DateTime().toDate());
		mhealthMigration.setDivisionOut("DHAKA");
		mhealthMigration.setDivisionIn("DHAKA");
		mhealthMigration.setDistrictOut("GAZIPUR");
		mhealthMigration.setDistrictIn("DHAKA");
		mhealthMigration.setBranchIDIn("8");
		mhealthMigration.setSKOut("01313047271");
		mhealthMigration.setSKIn("01313047103");
		mhealthMigration.setHHNameOut("Kitta");
		mhealthMigration.setHHNameIn("Kitta");
		mhealthMigration.setHHContactOut("01585785805");
		mhealthMigration.setHHContactIn("01585785805");
		mhealthMigration.setBranchIDOut("2982");
		return mhealthMigration;
	}
	
}
