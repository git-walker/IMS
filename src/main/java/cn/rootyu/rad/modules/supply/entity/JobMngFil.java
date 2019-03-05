/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

/**
 * 项目管理档案Entity
 * @author maliang
 * @version 2016-01-25
 */
@Alias("SupplyJobMngFil")
public class JobMngFil extends DataEntity<JobMngFil> {
	
	private static final long serialVersionUID = 1L;
	
	private String pkCorp;		// 对应公司
	private String pkJobbasfil;		// 项目基本档案主键
	private String pkDeptdoc;		// 所属部门
	private String pkPsndoc;		// 负责人
	private String pkCustdoc;		// 主要客户
	private String pkVendoc;		// 主要供应商
	
	public JobMngFil() {
		super();
	}

	public JobMngFil(String id){
		super(id);
	}

	@Length(min=0, max=64, message="对应公司长度必须介于 0 和 64 之间")
	public String getPkCorp() {
		return pkCorp;
	}

	public void setPkCorp(String pkCorp) {
		this.pkCorp = pkCorp;
	}
	
	@Length(min=0, max=64, message="项目基本档案主键长度必须介于 0 和 64 之间")
	public String getPkJobbasfil() {
		return pkJobbasfil;
	}

	public void setPkJobbasfil(String pkJobbasfil) {
		this.pkJobbasfil = pkJobbasfil;
	}
	
	@Length(min=0, max=64, message="所属部门长度必须介于 0 和 64 之间")
	public String getPkDeptdoc() {
		return pkDeptdoc;
	}

	public void setPkDeptdoc(String pkDeptdoc) {
		this.pkDeptdoc = pkDeptdoc;
	}
	
	@Length(min=0, max=64, message="负责人长度必须介于 0 和 64 之间")
	public String getPkPsndoc() {
		return pkPsndoc;
	}

	public void setPkPsndoc(String pkPsndoc) {
		this.pkPsndoc = pkPsndoc;
	}
	
	@Length(min=0, max=64, message="主要客户长度必须介于 0 和 64 之间")
	public String getPkCustdoc() {
		return pkCustdoc;
	}

	public void setPkCustdoc(String pkCustdoc) {
		this.pkCustdoc = pkCustdoc;
	}
	
	@Length(min=0, max=64, message="主要供应商长度必须介于 0 和 64 之间")
	public String getPkVendoc() {
		return pkVendoc;
	}

	public void setPkVendoc(String pkVendoc) {
		this.pkVendoc = pkVendoc;
	}
	
}