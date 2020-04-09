package com.weitongming.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author weitongming
 */
@Data
@Accessors(chain = true)
@Table(name = "vin_userid_relation")
public class VinUserIdRelation {



    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;


    @Column(name = "vin")
    private String vin ;


    @Column(name = "user_id")
    private Integer userId ;


    @Column(name = "create_time")
    private Double createTime ;
}
