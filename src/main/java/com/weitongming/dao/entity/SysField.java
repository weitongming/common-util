package com.weitongming.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author weitongming
 * @Classname SysField
 * @Description TODO
 * @Date 2020/1/15 8:28
 * @Version V1.0
 */
@Table(name = "sys_field")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysField {


    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;


    @Column(name = "mark")
    private String mark ;


    @Column(name = "module_name")
    private String moduleName ;


    @Column(name = "length")
    private Integer length ;


    @Column(name = "factor")
    private Double factor ;

    @Column(name = "offset")
    private Double offset ;


    @Column(name = "unit")
    private String unit ;


    @Column(name = "ord")
    private Integer ord ;


    @Column(name = "cn_Name")
    private String cnName ;
    
    @Column(name = "data_type")
    private String dataType ;


    @Column(name = "en_name")
    private String enName;

    @Column(name = "create_user")
    private String createUser ;

    @Column(name = "create_time")
    private Integer createTime ;

    @Column(name = "update_user")
    private String  updateUser ;

    @Column(name = "update_time")
    private Integer updateTime ;


}
