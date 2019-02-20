package com.lnsoft.mapper;

import com.lnsoft.dataobject.OrderInfoDo;
import com.lnsoft.dataobject.OrderInfoDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderInfoDoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Jan 07 20:24:53 CST 2019
     */
    long countByExample(OrderInfoDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Jan 07 20:24:53 CST 2019
     */
    int deleteByExample(OrderInfoDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Jan 07 20:24:53 CST 2019
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Jan 07 20:24:53 CST 2019
     */
    int insert(OrderInfoDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Jan 07 20:24:53 CST 2019
     */
    int insertSelective(OrderInfoDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Jan 07 20:24:53 CST 2019
     */
    List<OrderInfoDo> selectByExample(OrderInfoDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Jan 07 20:24:53 CST 2019
     */
    OrderInfoDo selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Jan 07 20:24:53 CST 2019
     */
    int updateByExampleSelective(@Param("record") OrderInfoDo record, @Param("example") OrderInfoDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Jan 07 20:24:53 CST 2019
     */
    int updateByExample(@Param("record") OrderInfoDo record, @Param("example") OrderInfoDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Jan 07 20:24:53 CST 2019
     */
    int updateByPrimaryKeySelective(OrderInfoDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Jan 07 20:24:53 CST 2019
     */
    int updateByPrimaryKey(OrderInfoDo record);
}