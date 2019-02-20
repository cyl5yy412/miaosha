package com.lnsoft.mapper;

import com.lnsoft.dataobject.ItemInfoDO;
import com.lnsoft.dataobject.ItemInfoDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemInfoDOMapper {
    List<ItemInfoDO> itemList();
    int increaseSales(@Param("id") Integer id,@Param("amount") Integer amount);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 19:36:56 CST 2019
     */
    long countByExample(ItemInfoDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 19:36:56 CST 2019
     */
    int deleteByExample(ItemInfoDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 19:36:56 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 19:36:56 CST 2019
     */
    int insert(ItemInfoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 19:36:56 CST 2019
     */
    int insertSelective(ItemInfoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 19:36:56 CST 2019
     */
    List<ItemInfoDO> selectByExample(ItemInfoDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 19:36:56 CST 2019
     */
    ItemInfoDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 19:36:56 CST 2019
     */
    int updateByExampleSelective(@Param("record") ItemInfoDO record, @Param("example") ItemInfoDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 19:36:56 CST 2019
     */
    int updateByExample(@Param("record") ItemInfoDO record, @Param("example") ItemInfoDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 19:36:56 CST 2019
     */
    int updateByPrimaryKeySelective(ItemInfoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 19:36:56 CST 2019
     */
    int updateByPrimaryKey(ItemInfoDO record);
}