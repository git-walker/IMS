/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

/**
 * 计量档案Entity
 * @author maliang
 * @version 2015-11-14
 */
@Alias("SupplyMeasDoc")
public class MeasDoc extends DataEntity<MeasDoc> {
	
	private static final long serialVersionUID = 1L;
	
	//--------------------------Entity---------------------------------
	private String measname;		// 计量单位名称
	
	public MeasDoc() {
		super();
	}

	public MeasDoc(String id){
		super(id);
	}

	@Length(min=0, max=256, message="计量单位名称长度必须介于 0 和 256 之间")
	public String getMeasname() {
		return measname;
	}

	public void setMeasname(String measname) {
		this.measname = measname;
	}
	
}