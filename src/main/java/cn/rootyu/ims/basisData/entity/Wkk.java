/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.ims.basisData.entity;

import cn.rootyu.rad.common.persistence.DataEntity;

/**
 * WK Entity
 * @author DHC
 * @version 2016-09-05
 */
public class Wkk extends DataEntity<Wkk> {

	private static final long serialVersionUID = 1L;
	private String flmc;            //工作中心分类名称
	private String gzzxbm;			//工作中心编码
	private String gzzxmc;			//工作中心名称
	private String gsbm;			//公司编码
	private String flid;	        //工作中心分类主键
	private String ssbmid;			//所属部门ID
	

	public Wkk(String flmc,String gzzxbm, String gzzxmc, String gsbm, String flid,
			String ssbmid) {
		super();
		this.flmc = flmc;
		this.gzzxbm = gzzxbm;
		this.gzzxmc = gzzxmc;
		this.gsbm = gsbm;
		this.flid = flid;
		this.ssbmid = ssbmid;
	}
	
	public Wkk() {
		super();
	}

	public Wkk(String id){
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

	public String getGsbm() {
		return gsbm;
	}

	public void setGsbm(String gsbm) {
		this.gsbm = gsbm;
	}

	public String getSsbmid() {
		return ssbmid;
	}

	public void setSsbmid(String ssbmid) {
		this.ssbmid = ssbmid;
	}

	public String getFlmc() {
		return flmc;
	}

	public void setFlmc(String flmc) {
		this.flmc = flmc;
	}

	public String getFlid() {
		return flid;
	}

	public void setFlid(String flid) {
		this.flid = flid;
	}

	@Override
	public String toString(){
		return gzzxmc;
	}


}