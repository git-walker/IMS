package cn.rootyu.rad.modules.stafrock.entity;



import cn.rootyu.rad.common.persistence.DataEntity;
import cn.rootyu.rad.modules.sys.entity.User;

/**
 * 借料管理Controller
 * @author zhixin.wang
 * @version 2016-1-4
 */
public class BorrowTask extends DataEntity<BorrowTask> {

	
	private static final long serialVersionUID = 1L;
	private String id;
	private String batchSequence;
	private String batchCode;
	private String borrowNum;
	private String borrowStationid;
	private String borrowedStationid;
	private String borrowGoodid;
	private String borrowedGoodid;
	private String borrowStorid;
	private String borrowedStorid;
	private String planTime;
	private String actualTime;
	private String actualNum;
	private String pkInvbasdoc;
	private String borrowUserid;
	private String borrowBillcode;
	private String statusFlag;
	private String pkRdcl;
	private String pkRdcled;
	private Stordoc stordoc;
	private InvBasDoc invbasdoc;
	private CargDoc cargdoc;
	private Wk wk;
	private User user;


	public BorrowTask(String id) {
		super(id);
	}
	public BorrowTask() {
		super();
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Wk getWk() {
		return wk;
	}
	public void setWk(Wk wk) {
		this.wk = wk;
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
	public String getBorrowNum() {
		return borrowNum;
	}
	public void setBorrowNum(String borrowNum) {
		this.borrowNum = borrowNum;
	}
	public String getBorrowStationid() {
		return borrowStationid;
	}
	public void setBorrowStationid(String borrowStationid) {
		this.borrowStationid = borrowStationid;
	}
	public String getBorrowedStationid() {
		return borrowedStationid;
	}
	public void setBorrowedStationid(String borrowedStationid) {
		this.borrowedStationid = borrowedStationid;
	}
	public String getBorrowGoodid() {
		return borrowGoodid;
	}
	public void setBorrowGoodid(String borrowGoodid) {
		this.borrowGoodid = borrowGoodid;
	}
	public String getBorrowedGoodid() {
		return borrowedGoodid;
	}
	public void setBorrowedGoodid(String borrowedGoodid) {
		this.borrowedGoodid = borrowedGoodid;
	}
	public String getBorrowStorid() {
		return borrowStorid;
	}
	public void setBorrowStorid(String borrowStorid) {
		this.borrowStorid = borrowStorid;
	}
	public String getBorrowedStorid() {
		return borrowedStorid;
	}
	public void setBorrowedStorid(String borrowedStorid) {
		this.borrowedStorid = borrowedStorid;
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
	public String getBorrowUserid() {
		return borrowUserid;
	}
	public void setBorrowUserid(String borrowUserid) {
		this.borrowUserid = borrowUserid;
	}
	public String getBorrowBillcode() {
		return borrowBillcode;
	}
	public void setBorrowBillcode(String borrowBillcode) {
		this.borrowBillcode = borrowBillcode;
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
}