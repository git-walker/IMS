/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 存货分类Entity
 * @author xiujun.xu
 * @version 2015-10-28
 */
public class Invcl extends DataEntity<Invcl> {
	
	private static final long serialVersionUID = 1L;
	
	private String invclasscode;		// 存货分类编码
	private String invclassname;		// 存货分类名称
	private String pk_corp;				// 所属公司主键
	private String sealdate;		    // 封存标志，N或""为不封存，Y为封存
	
	public Invcl() {
		super();
	}
	public Invcl(String id) {
		super(id);
	}
	@Length(min=0, max=64, message="分类编码长度必须介于 0 和 64 之间")
	public String getInvclasscode() {
		return invclasscode;
	}
	public void setInvclasscode(String invclasscode) {
		this.invclasscode = invclasscode;
	}
	public String getInvclassname() {
		return invclassname;
	}
	public void setInvclassname(String invclassname) {
		this.invclassname = invclassname;
	}
	public String getPk_corp() {
		return pk_corp;
	}
	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}
	public String getSealdate() {
		return sealdate;
	}
	public void setSealdate(String sealdate) {
		this.sealdate = sealdate;
	}
}