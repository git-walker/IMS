/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import cn.rootyu.rad.modules.sys.entity.Office;

/**
 * 工作中心
 * @author xiujun.xu
 * @version 2015-12-3
 */
public class Wk extends DataEntity<Wk> {

	private static final long serialVersionUID = 1L;
	private String gzzxbm;			//工作中心编码
	private String gzzxmc;			//工作中心名称
	private String pk_corp;			//公司编码
	private String pk_wkclassid;	//工作中心分类主键
	private String ssbmid;			//所属部门ID
	private Office office;			//部门对象
	private String gzzxmced;
	private String borrowStorid;
	private String borrowedStorid;
	private String pkStordoc;
	private String allocStorid;
	private String allocedStorid;
	
	


	public String getAllocedStorid() {
		return allocedStorid;
	}

	public void setAllocedStorid(String allocedStorid) {
		this.allocedStorid = allocedStorid;
	}

	public String getAllocStorid() {
		return allocStorid;
	}

	public void setAllocStorid(String allocStorid) {
		this.allocStorid = allocStorid;
	}

	public String getPkStordoc() {
		return pkStordoc;
	}

	public void setPkStordoc(String pkStordoc) {
		this.pkStordoc = pkStordoc;
	}

	public String getBorrowedStorid() {
		return borrowedStorid;
	}

	public void setBorrowedStorid(String borrowedStorid) {
		this.borrowedStorid = borrowedStorid;
	}

	public String getBorrowStorid() {
		return borrowStorid;
	}

	public void setBorrowStorid(String borrowStorid) {
		this.borrowStorid = borrowStorid;
	}

	public String getGzzxmced() {
		return gzzxmced;
	}

	public void setGzzxmced(String gzzxmced) {
		this.gzzxmced = gzzxmced;
	}

	public Wk() {
		super();
	}

	public Wk(String id){
		super(id);
	}
	
	public String getGzzxbm() {
		return gzzxbm;
	}
	public void setGzzxbm(String gzzxbm) {
		this.gzzxbm = gzzxbm;
	}
	public String getGzzxmc() {
		return gzzxmc;
	}
	public void setGzzxmc(String gzzxmc) {
		this.gzzxmc = gzzxmc;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getPk_wkclassid() {
		return pk_wkclassid;
	}

	public void setPk_wkclassid(String pk_wkclassid) {
		this.pk_wkclassid = pk_wkclassid;
	}

	public String getSsbmid() {
		return ssbmid;
	}

	public void setSsbmid(String ssbmid) {
		this.ssbmid = ssbmid;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

}
