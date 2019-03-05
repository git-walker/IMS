package cn.rootyu.ims.webService.entity;

import java.util.Date;
import java.util.List;

/**
 * 非接触式测量检验工件表
 * @author lishanzhi
 *
 */
public class NocmTaskWceWS{
	
	private String id;
	private String taskId;// 任务Id
	private String wceId; // 工件信息Id
	private String serial; // 工件序列号
	private String status;// 状态
	private String flag;// 合格标识
	private String yl1;// 预留1
	private String yl2;// 预览2
	private String operType;// 数据标识 
	private String wceName;// 工件名称
	private String wceCode;//工件编码
	private String planFlag;// 策划Flag(0:所有项点策划完成,1:为策划完)
	private Date updateDate;
	private String remarks;
	private List<NocmTaskResultWS> list;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getWceId() {
		return wceId;
	}
	public void setWceId(String wceId) {
		this.wceId = wceId;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getWceName() {
		return wceName;
	}
	public void setWceName(String wceName) {
		this.wceName = wceName;
	}
	public String getWceCode() {
		return wceCode;
	}
	public void setWceCode(String wceCode) {
		this.wceCode = wceCode;
	}
	public String getPlanFlag() {
		return planFlag;
	}
	public void setPlanFlag(String planFlag) {
		this.planFlag = planFlag;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public List<NocmTaskResultWS> getList() {
		return list;
	}
	public void setList(List<NocmTaskResultWS> list) {
		this.list = list;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
