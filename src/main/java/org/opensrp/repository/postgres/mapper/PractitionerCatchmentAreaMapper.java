package org.opensrp.repository.postgres.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.PractitionerCatchmentArea;
import org.opensrp.domain.postgres.PractitionerCatchmentAreaExample;

public interface PractitionerCatchmentAreaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_catchment_area
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    long countByExample(PractitionerCatchmentAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_catchment_area
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int deleteByExample(PractitionerCatchmentAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_catchment_area
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_catchment_area
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int insert(PractitionerCatchmentArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_catchment_area
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int insertSelective(PractitionerCatchmentArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_catchment_area
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    List<PractitionerCatchmentArea> selectByExample(PractitionerCatchmentAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_catchment_area
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    PractitionerCatchmentArea selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_catchment_area
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int updateByExampleSelective(@Param("record") PractitionerCatchmentArea record, @Param("example") PractitionerCatchmentAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_catchment_area
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int updateByExample(@Param("record") PractitionerCatchmentArea record, @Param("example") PractitionerCatchmentAreaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_catchment_area
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int updateByPrimaryKeySelective(PractitionerCatchmentArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_catchment_area
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int updateByPrimaryKey(PractitionerCatchmentArea record);
}