package com.weitongming.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ligl
 * @since 2020-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "T_B_VEHICLE_PROJECTINFO")
public class TBVehicleProjectinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "VIN")
    private String vin;

    @Column(name = "PROJECTNAME")
    private String projectname;

    /**
     * 车型
     */
    @Column(name = "model")
    private String model;

    @Column(name = "MAKEDATE")
    private String makedate;

    @Column(name = "INSERTTIME")
    private String inserttime;

    @Column(name = "WHFS")
    private String whfs;


}
