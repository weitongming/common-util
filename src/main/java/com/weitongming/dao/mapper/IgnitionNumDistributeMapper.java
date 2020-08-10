package com.weitongming.dao.mapper;

import com.weitongming.dao.entity.IgnitionNumDistribute;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.Set;


/**
 * @author Wei TongMing
 */
public interface IgnitionNumDistributeMapper extends BaseMapper<IgnitionNumDistribute> {


    /**
     * 查询某段时间内全部的vin
     * @param dayBegin 开始时间
     * @param dayBehind 结束时间
     * @return
     */
    @Select({"<script>",
            "SELECT distinct(vin) from t_vehicleaddress_province",
            "WHERE  date >= #{dayBegin} AND date &lt;= #{dayBehind}",
            "</script>"})
    Set<String> selectDistinctVinBetweenDay(@Param("dayBegin") String dayBegin, @Param("dayBehind") String dayBehind);


}
