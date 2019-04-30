package org.opensrp.repository.postgres.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.Plan;
import org.opensrp.domain.postgres.PlanExample;

public interface PlanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    long countByExample(PlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int deleteByExample(PlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int insert(Plan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int insertSelective(Plan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    List<Plan> selectByExample(PlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    Plan selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int updateByExampleSelective(@Param("record") Plan record, @Param("example") PlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int updateByExample(@Param("record") Plan record, @Param("example") PlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int updateByPrimaryKeySelective(Plan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.plan
     *
     * @mbg.generated Tue Apr 30 21:54:17 EAT 2019
     */
    int updateByPrimaryKey(Plan record);
}