/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 项目档案Entity
 * @author maliang
 * @version 2016-01-25
 */
@Alias("SupplyJobBasFil")
public class JobBasFil extends DataEntity<JobBasFil> {
	
	private static final long serialVersionUID = 1L;
	
	private List<String> idList;//ID集合
	private JobMngFil jmf;//项目管理档案

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public JobMngFil getJmf() {
		return jmf;
	}

	public void setJmf(JobMngFil jmf) {
		this.jmf = jmf;
	}

	//--------------------------Entity---------------------------------
	private String jobcode;		// 项目编码
	private String jobname;		// 项目名称
	
	public JobBasFil() {
		super();
	}

	public JobBasFil(String id){
		super(id);
	}

	@Length(min=0, max=64, message="项目编码长度必须介于 0 和 64 之间")
	public String getJobcode() {
		return jobcode;
	}

	public void setJobcode(String jobcode) {
		this.jobcode = jobcode;
	}
	
	@Length(min=0, max=256, message="项目名称长度必须介于 0 和 256 之间")
	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	
}