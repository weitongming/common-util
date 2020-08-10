package com.weitongming.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Table;


/**
 * @author Wei TongMing
 */
@Data
@Accessors(chain = true)
@Table(name = "t_vehicleaddress_province")
public class IgnitionNumDistribute {

    @Column(name = "date")
    private String date ;

    @Column(name = "vin")
    private String vin ;

    @Column(name = "province")
    private String province ;

    @Column(name = "city")
    private String city ;

}
