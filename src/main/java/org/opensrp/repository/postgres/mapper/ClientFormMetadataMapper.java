package org.opensrp.repository.postgres.mapper;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.ClientFormMetadata;
import org.opensrp.domain.postgres.ClientFormMetadataExample;

import java.util.List;

public interface ClientFormMetadataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.client_form_metadata
     *
     * @mbg.generated Fri Mar 20 12:11:34 EAT 2020
     */
    long countByExample(ClientFormMetadataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.client_form_metadata
     *
     * @mbg.generated Fri Mar 20 12:11:34 EAT 2020
     */
    int deleteByExample(ClientFormMetadataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.client_form_metadata
     *
     * @mbg.generated Fri Mar 20 12:11:34 EAT 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.client_form_metadata
     *
     * @mbg.generated Fri Mar 20 12:11:34 EAT 2020
     */
    int insert(ClientFormMetadata record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.client_form_metadata
     *
     * @mbg.generated Fri Mar 20 12:11:34 EAT 2020
     */
    int insertSelective(ClientFormMetadata record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.client_form_metadata
     *
     * @mbg.generated Fri Mar 20 12:11:34 EAT 2020
     */
    List<ClientFormMetadata> selectByExample(ClientFormMetadataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.client_form_metadata
     *
     * @mbg.generated Fri Mar 20 12:11:34 EAT 2020
     */
    ClientFormMetadata selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.client_form_metadata
     *
     * @mbg.generated Fri Mar 20 12:11:34 EAT 2020
     */
    int updateByExampleSelective(@Param("record") ClientFormMetadata record, @Param("example") ClientFormMetadataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.client_form_metadata
     *
     * @mbg.generated Fri Mar 20 12:11:34 EAT 2020
     */
    int updateByExample(@Param("record") ClientFormMetadata record, @Param("example") ClientFormMetadataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.client_form_metadata
     *
     * @mbg.generated Fri Mar 20 12:11:34 EAT 2020
     */
    int updateByPrimaryKeySelective(ClientFormMetadata record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.client_form_metadata
     *
     * @mbg.generated Fri Mar 20 12:11:34 EAT 2020
     */
    int updateByPrimaryKey(ClientFormMetadata record);
}