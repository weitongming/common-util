package com.weitongming.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;

@Data
@Accessors(chain = true)
public class RegionField {

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "cn_Name")
    private String cnName ;

    @Column(name = "en_name")
    private String enName;


    @Column(name = "mark")
    private String mark;

}
