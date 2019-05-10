package org.opensrp.repository.postgres.mapper;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.PlanMetadataExample;
import org.opensrp.domain.postgres.PlanMetadataKey;

import java.util.List;

public interface PlanMetadataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Fri May 10 11:11:46 EAT 2019
     */
    long countByExample(PlanMetadataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Fri May 10 11:11:46 EAT 2019
     */
    int deleteByExample(PlanMetadataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Fri May 10 11:11:46 EAT 2019
     */
    int deleteByPrimaryKey(PlanMetadataKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Fri May 10 11:11:46 EAT 2019
     */
    int insert(PlanMetadataKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Fri May 10 11:11:46 EAT 2019
     */
    int insertSelective(PlanMetadataKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Fri May 10 11:11:46 EAT 2019
     */
    List<PlanMetadataKey> selectByExample(PlanMetadataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Fri May 10 11:11:46 EAT 2019
     */
    int updateByExampleSelective(@Param("record") PlanMetadataKey record, @Param("example") PlanMetadataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Fri May 10 11:11:46 EAT 2019
     */
    int updateByExample(@Param("record") PlanMetadataKey record, @Param("example") PlanMetadataExample example);
}