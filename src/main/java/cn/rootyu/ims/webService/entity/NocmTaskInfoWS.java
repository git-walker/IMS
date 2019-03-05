package cn.rootyu.ims.webService.entity;

import java.util.Date;
import java.util.List;

/**
 * 非接触式测量任务信息
 * @author lishanzhi
 *
 */
public class NocmTaskInfoWS {

	private String id;
	private String taskCode; // 任务编号
	private Date taskDate; // 任务日期
	private String urgence; // 紧急程度
	private String urgenceName;// 紧急程度
	private Date comDate;// 完成时间
	private String status;// 状态
	private String yl1;// 预留1
	private String yl2;// 预览2
	private String startDate;
	private String endDate;
	private String createBy;
	private String createDate;
	private String updateBy;
	private Date updateDate;
	private String statusName;
	private String remarks;
	private List<NocmTaskWceWS> list;
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public Date getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}
	public String getUrgence() {
		return urgence;
	}
	public void setUrgence(String urgence) {
		this.urgence = urgence;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getYl1() {
		return yl1;
	}
	public void setYl1(String yl1) {
		this.yl1 = yl1;
	}
	public String getYl2() {
		return yl2;
	}
	public void setYl2(String yl2) {
		this.yl2 = yl2;
	}
	public Date getComDate() {
		return comDate;
	}
	public void setComDate(Date comDate) {
		this.comDate = comDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getUrgenceName() {
		return urgenceName;
	}
	public void setUrgenceName(String urgenceName) {
		this.urgenceName = urgenceName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public List<NocmTaskWceWS> getList() {
		return list;
	}
	public void setList(List<NocmTaskWceWS> list) {
		this.list = list;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
