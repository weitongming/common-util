package com.sgmw.configuration;

/**
 * @author weitongming
 * @Classname CommonConstant
 * @Description CommonConstant
 * @Date 2019/12/30 11:11
 * @Version V1.0
 */
public class Constants{


    /**
     * 里程mapReduce key的前缀
     */
    public static final String KEY_MILEAGE_FAULT_PREFIX  = "mileage";

    /**
     * 区域mapReduce key的前缀
     */
    public static final String KEY_REGION_FAULT_PREFIX  = "region";


    /**
     * 车身部位mapReduce key的前缀
     */
    public static final String KEY_CAR_COMPONENT_FAULT_PREFIX = "component";




    public static final String BAIDU_API_URL = "http://api.map.baidu.com/reverse_geocoding/v3/?ak=I35Y6ihnvXCqzn87kCxj9wkGmCmtK2GW&output=json&coordtype=wgs84ll&location=%s,%s";

    /**
     * mapreduce key 的分隔符
     */
    public static final String KEY_SEPARATOR = "_" ;


    /**
     * hadoop的地址
     */
    public static final String HDFS_URL = "hdfs://hadoop.sgmw.com";
}