package com.weitongming.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Table(name = "sys_region_field")
@Accessors(chain = true)
public class SysRegionField {

    @Id
    @GeneratedValue(generator = "JDBC"    )
    private Integer id;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private Integer createTime;

	@Column(name = "region_id")
    private Integer regionId;

	@Column(name = "field_id")
    private Integer fieldId;
}