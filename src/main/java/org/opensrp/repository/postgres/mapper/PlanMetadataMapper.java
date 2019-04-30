package org.opensrp.repository.postgres.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.PlanMetadata;
import org.opensrp.domain.postgres.PlanMetadataExample;
import org.opensrp.domain.postgres.PlanMetadataKey;

public interface PlanMetadataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    long countByExample(PlanMetadataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int deleteByExample(PlanMetadataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int deleteByPrimaryKey(PlanMetadataKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int insert(PlanMetadata record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int insertSelective(PlanMetadata record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    List<PlanMetadata> selectByExample(PlanMetadataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    PlanMetadata selectByPrimaryKey(PlanMetadataKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int updateByExampleSelective(@Param("record") PlanMetadata record, @Param("example") PlanMetadataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int updateByExample(@Param("record") PlanMetadata record, @Param("example") PlanMetadataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int updateByPrimaryKeySelective(PlanMetadata record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan_metadata
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int updateByPrimaryKey(PlanMetadata record);
}