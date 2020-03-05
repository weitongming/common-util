package com.weitongming.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 基础模型
 * @author Louis
 * @date Sep 13, 2018
 */
@Data
@NoArgsConstructor
public class BaseModel {

	@Id
	@GeneratedValue(generator = "JDBC"    )
	private Long id;

	@Column(name = "create_by")
    private String createBy;

	@Column(name = "create_time")
    private Date createTime;

	@Column(name = "last_update_by")
    private String lastUpdateBy;

	@Column(name = "last_update_time")
    private Date lastUpdateTime;


    
}
