package org.opensrp.repository.postgres.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.PractitionerDetails;
import org.opensrp.domain.postgres.PractitionerDetailsExample;

public interface PractitionerDetailsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_details
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    long countByExample(PractitionerDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_details
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int deleteByExample(PractitionerDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_details
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_details
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int insert(PractitionerDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_details
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int insertSelective(PractitionerDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_details
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    List<PractitionerDetails> selectByExample(PractitionerDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_details
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    PractitionerDetails selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_details
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int updateByExampleSelective(@Param("record") PractitionerDetails record, @Param("example") PractitionerDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_details
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int updateByExample(@Param("record") PractitionerDetails record, @Param("example") PractitionerDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_details
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int updateByPrimaryKeySelective(PractitionerDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table team.practitioner_details
     *
     * @mbg.generated Thu Mar 25 11:29:40 BDT 2021
     */
    int updateByPrimaryKey(PractitionerDetails record);
}