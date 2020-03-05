package com.weitongming.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述:
 * 车联网功能清单
 *
 * @outhor WeiTongMing
 * @create 2020-02-09 21:19
 */

@Data
@Accessors(chain = true)
@Table(name = "car_networking_function")
public class CarNetWorkingFunction {

    @Id
    @GeneratedValue(generator = "JDBC"    )
    private Integer id ;

    @Column(name = "tag")
    private String tag;

    @Column(name = "behavior_id")
    private Integer behaviorId;

    @Column(name = "behavior_description")
    private String behaviorDescription;

    @Column(name = "behavior_on_time")
    private String behaviorOnTime;


}

