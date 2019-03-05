package cn.rootyu.rad.modules.stafrock.entity;



import cn.rootyu.rad.common.persistence.DataEntity;
import cn.rootyu.rad.modules.sys.entity.User;

/**
 * 调拨管理Controller
 * @author zhixin.wang
 * @version 2016-1-12
 */
public class AllocateTask extends DataEntity<AllocateTask> {

	
	private static final long serialVersionUID = 1L;
	private String id;
	private String batchSequence;
	private String batchCode;
	private String allocNum;
	private String allocStationid;
	private String allocedStationid;
	private String allocGoodid;
	private String allocedGoodid;
	private String allocStorid;
	private String allocedStorid;
	private String planTime;
	private String actualTime;
	private String actualNum;
	private String pkInvbasdoc;
	private String allocUserid;
	private String allocBillcode;
	private String statusFlag;
	private String pkRdcl;
	private String pkRdcled;
	private Stordoc stordoc;
	private InvBasDoc invbasdoc;
	private CargDoc cargdoc;
	private Wk wk;
	private User user;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBatchSequence() {
		return batchSequence;
	}
	public void setBatchSequence(String batchSequence) {
		this.batchSequence = batchSequence;
	}
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	public String getAllocNum() {
		return allocNum;
	}
	public void setAllocNum(String allocNum) {
		this.allocNum = allocNum;
	}
	public String getAllocStationid() {
		return allocStationid;
	}
	public void setAllocStationid(String allocStationid) {
		this.allocStationid = allocStationid;
	}
	public String getAllocedStationid() {
		return allocedStationid;
	}
	public void setAllocedStationid(String allocedStationid) {
		this.allocedStationid = allocedStationid;
	}
	public String getAllocGoodid() {
		return allocGoodid;
	}
	public void setAllocGoodid(String allocGoodid) {
		this.allocGoodid = allocGoodid;
	}
	public String getAllocedGoodid() {
		return allocedGoodid;
	}
	public void setAllocedGoodid(String allocedGoodid) {
		this.allocedGoodid = allocedGoodid;
	}
	public String getAllocStorid() {
		return allocStorid;
	}
	public void setAllocStorid(String allocStorid) {
		this.allocStorid = allocStorid;
	}
	public String getAllocedStorid() {
		return allocedStorid;
	}
	public void setAllocedStorid(String allocedStorid) {
		this.allocedStorid = allocedStorid;
	}
	public String getPlanTime() {
		return planTime;
	}
	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}
	public String getActualTime() {
		return actualTime;
	}
	public void setActualTime(String actualTime) {
		this.actualTime = actualTime;
	}
	public String getActualNum() {
		return actualNum;
	}
	public void setActualNum(String actualNum) {
		this.actualNum = actualNum;
	}
	public String getPkInvbasdoc() {
		return pkInvbasdoc;
	}
	public void setPkInvbasdoc(String pkInvbasdoc) {
		this.pkInvbasdoc = pkInvbasdoc;
	}
	public String getAllocUserid() {
		return allocUserid;
	}
	public void setAllocUserid(String allocUserid) {
		this.allocUserid = allocUserid;
	}
	public String getAllocBillcode() {
		return allocBillcode;
	}
	public void setAllocBillcode(String allocBillcode) {
		this.allocBillcode = allocBillcode;
	}
	public String getStatusFlag() {
		return statusFlag;
	}
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	public String getPkRdcl() {
		return pkRdcl;
	}
	public void setPkRdcl(String pkRdcl) {
		this.pkRdcl = pkRdcl;
	}
	public String getPkRdcled() {
		return pkRdcled;
	}
	public void setPkRdcled(String pkRdcled) {
		this.pkRdcled = pkRdcled;
	}
	public Stordoc getStordoc() {
		return stordoc;
	}
	public void setStordoc(Stordoc stordoc) {
		this.stordoc = stordoc;
	}
	public InvBasDoc getInvbasdoc() {
		return invbasdoc;
	}
	public void setInvbasdoc(InvBasDoc invbasdoc) {
		this.invbasdoc = invbasdoc;
	}
	public CargDoc getCargdoc() {
		return cargdoc;
	}
	public void setCargdoc(CargDoc cargdoc) {
		this.cargdoc = cargdoc;
	}
	public Wk getWk() {
		return wk;
	}
	public void setWk(Wk wk) {
		this.wk = wk;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}