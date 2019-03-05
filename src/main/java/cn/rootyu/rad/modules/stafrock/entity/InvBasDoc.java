/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.stafrock.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 配送策划管理Entity
 * @author chunze.cao
 * @version 2015-10-19
 */
public class InvBasDoc extends DataEntity<InvBasDoc> {
	
	private static final long serialVersionUID = 1L;
	private String asset;		// 设备卡片管理
	private String discount;		// 价格折扣
	private String forinvname;		// 外文名称
	private String graphid;		// 图号
	private String height;		// 高度
	private String invbarcode;		// 条形码
	private String invcode;		// 存货编码
	private String invmnecode;		// 助记码
	private String invname;		// 存货名称
	private String invpinpai;		// 品牌
	private String invshortname;		// 存货简称
	private String invspec;		// 规格
	private String invtype;		// 型号
	private String laborflag;		// 应税劳务
	private String length;		// 长度
	private String memo;		// 备注
	private String pkCorp;		// 公司主键
	private String pkInvcl;		// 存货分类主键
	private String pkMeasdoc;		// 主计量单位主键
	private String setpartsflag;		// 成套件
	private String unitvolume;		// 单位体积
	private String unitweight;		// 单位重量
	private String width;		// 宽度
	private String productline;		// 产品线
	private String taxitems;		// 税目
	private String invproperty;    //物料特殊性(1：一般物件；2；超长超大物件；3：化工料；4：消耗料；5：内装件)
	private String delmethod;      //配送方式(1:按计划工位配送；2:直供车；3：定额范围内，电联配送)
	private String dismodels;      //配送车型(1：A型车；2：B型车；3：C型车；4:D型车)
	private String invmandocid;    //存货管理档案id
	
	public InvBasDoc() {
		super();
	}
	public InvBasDoc(String id) {
		super(id);
	}
	
	@Length(min=0, max=64, message="设备卡片管理长度必须介于 0 和 64 之间")
	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}
	
	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	@Length(min=0, max=256, message="外文名称长度必须介于 0 和 256 之间")
	public String getForinvname() {
		return forinvname;
	}

	public void setForinvname(String forinvname) {
		this.forinvname = forinvname;
	}
	
	@Length(min=0, max=64, message="图号长度必须介于 0 和 64 之间")
	public String getGraphid() {
		return graphid;
	}

	public void setGraphid(String graphid) {
		this.graphid = graphid;
	}
	
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	
	@Length(min=0, max=64, message="条形码长度必须介于 0 和 64 之间")
	public String getInvbarcode() {
		return invbarcode;
	}

	public void setInvbarcode(String invbarcode) {
		this.invbarcode = invbarcode;
	}
	
	@Length(min=0, max=64, message="存货编码长度必须介于 0 和 64 之间")
	public String getInvcode() {
		return invcode;
	}

	public void setInvcode(String invcode) {
		this.invcode = invcode;
	}
	
	@Length(min=0, max=64, message="助记码长度必须介于 0 和 64 之间")
	public String getInvmnecode() {
		return invmnecode;
	}

	public void setInvmnecode(String invmnecode) {
		this.invmnecode = invmnecode;
	}
	
	@Length(min=0, max=256, message="存货名称长度必须介于 0 和 256 之间")
	public String getInvname() {
		return invname;
	}

	public void setInvname(String invname) {
		this.invname = invname;
	}
	
	@Length(min=0, max=64, message="品牌长度必须介于 0 和 64 之间")
	public String getInvpinpai() {
		return invpinpai;
	}

	public void setInvpinpai(String invpinpai) {
		this.invpinpai = invpinpai;
	}
	
	@Length(min=0, max=512, message="存货简称长度必须介于 0 和 512 之间")
	public String getInvshortname() {
		return invshortname;
	}

	public void setInvshortname(String invshortname) {
		this.invshortname = invshortname;
	}
	
	@Length(min=0, max=256, message="规格长度必须介于 0 和 256 之间")
	public String getInvspec() {
		return invspec;
	}

	public void setInvspec(String invspec) {
		this.invspec = invspec;
	}
	
	@Length(min=0, max=256, message="型号长度必须介于 0 和 256 之间")
	public String getInvtype() {
		return invtype;
	}

	public void setInvtype(String invtype) {
		this.invtype = invtype;
	}
	
	@Length(min=0, max=1, message="应税劳务长度必须介于 0 和 1 之间")
	public String getLaborflag() {
		return laborflag;
	}

	public void setLaborflag(String laborflag) {
		this.laborflag = laborflag;
	}
	
	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
	
	@Length(min=0, max=256, message="备注长度必须介于 0 和 256 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Length(min=0, max=64, message="公司主键长度必须介于 0 和 64 之间")
	public String getPkCorp() {
		return pkCorp;
	}

	public void setPkCorp(String pkCorp) {
		this.pkCorp = pkCorp;
	}
	
	@Length(min=0, max=64, message="存货分类主键长度必须介于 0 和 64 之间")
	public String getPkInvcl() {
		return pkInvcl;
	}

	public void setPkInvcl(String pkInvcl) {
		this.pkInvcl = pkInvcl;
	}
	
	@Length(min=0, max=64, message="主计量单位主键长度必须介于 0 和 64 之间")
	public String getPkMeasdoc() {
		return pkMeasdoc;
	}

	public void setPkMeasdoc(String pkMeasdoc) {
		this.pkMeasdoc = pkMeasdoc;
	}
	
	@Length(min=0, max=1, message="成套件长度必须介于 0 和 1 之间")
	public String getSetpartsflag() {
		return setpartsflag;
	}

	public void setSetpartsflag(String setpartsflag) {
		this.setpartsflag = setpartsflag;
	}
	
	public String getUnitvolume() {
		return unitvolume;
	}

	public void setUnitvolume(String unitvolume) {
		this.unitvolume = unitvolume;
	}
	
	public String getUnitweight() {
		return unitweight;
	}

	public void setUnitweight(String unitweight) {
		this.unitweight = unitweight;
	}
	
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	
	@Length(min=0, max=64, message="产品线长度必须介于 0 和 64 之间")
	public String getProductline() {
		return productline;
	}

	public void setProductline(String productline) {
		this.productline = productline;
	}
	
	@Length(min=0, max=256, message="税目长度必须介于 0 和 256 之间")
	public String getTaxitems() {
		return taxitems;
	}

	public void setTaxitems(String taxitems) {
		this.taxitems = taxitems;
	}

	public String getInvproperty() {
		return invproperty;
	}

	public void setInvproperty(String invproperty) {
		this.invproperty = invproperty;
	}

	public String getDelmethod() {
		return delmethod;
	}

	public void setDelmethod(String delmethod) {
		this.delmethod = delmethod;
	}

	public String getDismodels() {
		return dismodels;
	}

	public void setDismodels(String dismodels) {
		this.dismodels = dismodels;
	}
	public void setInvmandocid(String invmandocid) {
		this.invmandocid = invmandocid;
	}
	public String getInvmandocid() {
		return invmandocid;
	}
	
}