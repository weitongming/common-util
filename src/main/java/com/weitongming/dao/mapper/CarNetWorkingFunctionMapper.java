package com.weitongming.dao.mapper;

import com.weitongming.dao.entity.CarNetWorkingFunction;
import com.weitongming.dao.entity.IOVStatistic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述:
 *
 * @outhor WeiTongMing
 * @create 2020-02-09 21:48
 */
@Repository
public interface CarNetWorkingFunctionMapper extends Mapper<CarNetWorkingFunction> {

    @Select({"<script>",
            "SELECT \n" +
                    "substr(happen_time,1,10) as day,vin,cf.behavior_description AS behavior , cf.`behavior_id`,COUNT(1) as count FROM `sgmw_temp`.`car_networking_origin` c LEFT JOIN `sgmw`.`car_networking_function` cf\n" +
                    "ON c.`behavior_id` = cf.`behavior_id`\n" +
                    "WHERE  c.happen_time > #{dayBegin} AND c.`happen_time` &lt; #{dayBehind}\n" +
                    "AND c.`behavior_id` IN ('2001130',\n" +
                    "                    '2001140' ,\n" +
                    "                    '2101010' ,\n" +
                    "                    '2001730' ,\n" +
                    "                    '1026013' ,\n" +
                    "                    '1026004' ,\n" +
                    "                    '1026015' ,\n" +
                    "                    '1026012' ,\n" +
                    "                    '1026006' \n" +
                    "                    ) \n" +
                    "AND c.vin IN " +
                    "<foreach collection='vinList' item='vin' open='(' separator=',' close=')'>",
                    "#{vin}",
                    "</foreach>",
                    "\n" +
                    "GROUP BY c.`vin`,cf.`behavior_description`,cf.`behavior_id`,substr(happen_time,1,10)\n" +
                    "\n" +
                    "LIMIT 100000",
            "</script>"})
    List<IOVStatistic> selectByDateAndVin(@Param("dayBegin") String dayBegin, @Param("dayBehind") String dayBehind ,@Param("vinList") List<String> vinList );
}

