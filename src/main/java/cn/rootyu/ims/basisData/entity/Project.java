package cn.rootyu.ims.basisData.entity;


import cn.rootyu.rad.common.persistence.DataEntity;

public class Project extends DataEntity<Project> {


	private static final long serialVersionUID = 1L;
	private String invclasscode;
	private String invclassname;
	private String system;//项目分类
	private String systemValue;//项目分类
	private String charge;// 项目经理
	private String qualityCharge;// 质量接口人
	private String afterSaleCharge;// 售后接口人
	private String qualityChargeIds; // 质量接口人Id
	private String afterSaleChargeIds; // 售后接口人Ids 
//	private List<User> chargeList =  Lists.newArrayList();
	public Project() {
		super();
	}
	
	public Project(String id){
		super(id);
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSystemValue() {
		return systemValue;
	}

	public void setSystemValue(String systemValue) {
		this.systemValue = systemValue;
	}

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

	public String getQualityCharge() {
		return qualityCharge;
	}

	public void setQualityCharge(String qualityCharge) {
		this.qualityCharge = qualityCharge;
	}

//	public List<User> getChargeList() {
//		return chargeList;
//	}
//	
//	public void setChargeList(List<User> chargeList) {
//		this.chargeList = chargeList;
//	}
	
	public String getAfterSaleCharge() {
		return afterSaleCharge;
	}
	
	public void setAfterSaleCharge(String afterSaleCharge) {
		this.afterSaleCharge = afterSaleCharge;
	}

	public String getQualityChargeIds() {
		return qualityChargeIds;
	}

	public void setQualityChargeIds(String qualityChargeIds) {
		this.qualityChargeIds = qualityChargeIds;
	}

	public String getAfterSaleChargeIds() {
		return afterSaleChargeIds;
	}

	public void setAfterSaleChargeIds(String afterSaleChargeIds) {
		this.afterSaleChargeIds = afterSaleChargeIds;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

//	@JsonIgnore
//	public List<String> getChargeIdList() {
//		List<String> chargeIdList = Lists.newArrayList();
//		for (User user : chargeList) {
//			chargeIdList.add(user.getId());
//		}
//		return chargeIdList;
//	}
//
//	public void setChargeIdList(List<String> chargeIdList) {
//		chargeList = Lists.newArrayList();
//		if(chargeIdList != null){
//			for (String chargeId : chargeIdList) {
//				User user = new User();
//				user.setId(chargeId);
//				chargeList.add(user);
//			}
//		}
//	}
	
}
