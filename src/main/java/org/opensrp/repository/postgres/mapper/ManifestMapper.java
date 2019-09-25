package org.opensrp.repository.postgres.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.Manifest;
import org.opensrp.domain.postgres.ManifestExample;

public interface ManifestMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.manifest
     *
     * @mbg.generated Tue Sep 24 18:14:31 EAT 2019
     */
    long countByExample(ManifestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.manifest
     *
     * @mbg.generated Tue Sep 24 18:14:31 EAT 2019
     */
    int deleteByExample(ManifestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.manifest
     *
     * @mbg.generated Tue Sep 24 18:14:31 EAT 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.manifest
     *
     * @mbg.generated Tue Sep 24 18:14:31 EAT 2019
     */
    int insert(Manifest record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.manifest
     *
     * @mbg.generated Tue Sep 24 18:14:31 EAT 2019
     */
    int insertSelective(Manifest record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.manifest
     *
     * @mbg.generated Tue Sep 24 18:14:31 EAT 2019
     */
    List<Manifest> selectByExample(ManifestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.manifest
     *
     * @mbg.generated Tue Sep 24 18:14:31 EAT 2019
     */
    Manifest selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.manifest
     *
     * @mbg.generated Tue Sep 24 18:14:31 EAT 2019
     */
    int updateByExampleSelective(@Param("record") Manifest record, @Param("example") ManifestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.manifest
     *
     * @mbg.generated Tue Sep 24 18:14:31 EAT 2019
     */
    int updateByExample(@Param("record") Manifest record, @Param("example") ManifestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.manifest
     *
     * @mbg.generated Tue Sep 24 18:14:31 EAT 2019
     */
    int updateByPrimaryKeySelective(Manifest record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core.manifest
     *
     * @mbg.generated Tue Sep 24 18:14:31 EAT 2019
     */
    int updateByPrimaryKey(Manifest record);
}