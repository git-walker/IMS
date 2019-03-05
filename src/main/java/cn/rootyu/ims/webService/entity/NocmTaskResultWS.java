package cn.rootyu.ims.webService.entity;

import java.util.Date;

/**
 * 非接触式测量任务结果表
 * @author lishanzhi
 *
 */
public class NocmTaskResultWS{

	private String id;
	private String pointId;// 项点Id
	private String taskWceId; // 检验工件表Id
	private String valueType; // 数值类型(0:固定值;1:区间值;2:无上限;3:无下限)
	private Double normValue;// 标准值
	private Double upValue;// 上偏差
	private Double resultValue;// 结果值
	private String status;// 状态
	private Double downValue;// 下偏差
	private String flag;// 0:合格,1:不合格
	private String yl1;// 预留1
	private String yl2;// 预览2
	private Date updateDate;// 更新时间
	private String updateBy;// 更新人
	private Date createDate;
	private String createBy;
	private String pointName;
	private String loaction;
	private String demand;
	private String valueTypeName;
	private String remarks;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	public String getTaskWceId() {
		return taskWceId;
	}
	public void setTaskWceId(String taskWceId) {
		this.taskWceId = taskWceId;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getNormValue() {
		return normValue;
	}
	public void setNormValue(Double normValue) {
		this.normValue = normValue;
	}
	public Double getUpValue() {
		return upValue;
	}
	public void setUpValue(Double upValue) {
		this.upValue = upValue;
	}
	public Double getResultValue() {
		return resultValue;
	}
	public void setResultValue(Double resultValue) {
		this.resultValue = resultValue;
	}
	public Double getDownValue() {
		return downValue;
	}
	public void setDownValue(Double downValue) {
		this.downValue = downValue;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getLoaction() {
		return loaction;
	}
	public void setLoaction(String loaction) {
		this.loaction = loaction;
	}
	public String getDemand() {
		return demand;
	}
	public void setDemand(String demand) {
		this.demand = demand;
	}
	public String getValueTypeName() {
		return valueTypeName;
	}
	public void setValueTypeName(String valueTypeName) {
		this.valueTypeName = valueTypeName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
