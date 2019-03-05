package cn.rootyu.rad.modules.stafrock.entity;



import cn.rootyu.rad.common.persistence.DataEntity;
import cn.rootyu.rad.modules.sys.entity.User;

/**
 * 盘库管理Controller
 * @author zhixin.wang
 * @version 2015-12-16
 */
public class StocktakingTask extends DataEntity<StocktakingTask> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String pkInvid;
	private String pkStationid;
	private String pkChecker;
	private String pkAassigner;
	private String pkStordoc;
	private User user;
	private Stordoc stordoc;
	private InvBasDoc invbasdoc;
	private Wk wk;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPkInvid() {
		return pkInvid;
	}
	public void setPkInvid(String pkInvid) {
		this.pkInvid = pkInvid;
	}
	public String getPkStationid() {
		return pkStationid;
	}
	public void setPkStationid(String pkStationid) {
		this.pkStationid = pkStationid;
	}
	public String getPkChecker() {
		return pkChecker;
	}
	public void setPkChecker(String pkChecker) {
		this.pkChecker = pkChecker;
	}
	public String getPkAassigner() {
		return pkAassigner;
	}
	public void setPkAassigner(String pkAassigner) {
		this.pkAassigner = pkAassigner;
	}
	public String getPkStordoc() {
		return pkStordoc;
	}
	public void setPkStordoc(String pkStordoc) {
		this.pkStordoc = pkStordoc;
	}
	
}