package com.weitongming.dao.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Table(name = "sys_region")
@Accessors(chain = true)
public class SysRegion extends BaseModel {

	@Column(name = "module_name")
	private String moduleName;

	@Column(name = "region_name")
    private String regionName;


	@Column(name = "region_status")
	private Integer regionStatus;
}