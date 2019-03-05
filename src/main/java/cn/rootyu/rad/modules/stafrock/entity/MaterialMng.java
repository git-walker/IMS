package cn.rootyu.rad.modules.stafrock.entity;

import cn.rootyu.rad.common.persistence.DataEntity;
import cn.rootyu.rad.modules.supply.entity.MeasDoc;

/**
 * 物料(存货档案)管理Entity
 * @author jinkf
 * @version 2015-10-17
 */
public class MaterialMng extends DataEntity<MaterialMng> {
	
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
	private MeasDoc measdoc;         //单位
	private Invcl invcl;         //存货分类
	private String wholemanaflag; //批次管理
	private String serialmanaflag;//序列号管理
	
	public MaterialMng() {
		super();
	}
	public MaterialMng(String id) {
		super(id);
	}
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
	public String getForinvname() {
		return forinvname;
	}
	public void setForinvname(String forinvname) {
		this.forinvname = forinvname;
	}
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
	public String getInvbarcode() {
		return invbarcode;
	}
	public void setInvbarcode(String invbarcode) {
		this.invbarcode = invbarcode;
	}
	public String getInvcode() {
		return invcode;
	}
	public void setInvcode(String invcode) {
		this.invcode = invcode;
	}
	public String getInvmnecode() {
		return invmnecode;
	}
	public void setInvmnecode(String invmnecode) {
		this.invmnecode = invmnecode;
	}
	public String getInvname() {
		return invname;
	}
	public void setInvname(String invname) {
		this.invname = invname;
	}
	public String getInvpinpai() {
		return invpinpai;
	}
	public void setInvpinpai(String invpinpai) {
		this.invpinpai = invpinpai;
	}
	public String getInvshortname() {
		return invshortname;
	}
	public void setInvshortname(String invshortname) {
		this.invshortname = invshortname;
	}
	public String getInvspec() {
		return invspec;
	}
	public void setInvspec(String invspec) {
		this.invspec = invspec;
	}
	public String getInvtype() {
		return invtype;
	}
	public void setInvtype(String invtype) {
		this.invtype = invtype;
	}
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getPkCorp() {
		return pkCorp;
	}
	public void setPkCorp(String pkCorp) {
		this.pkCorp = pkCorp;
	}
	public String getPkInvcl() {
		return pkInvcl;
	}
	public void setPkInvcl(String pkInvcl) {
		this.pkInvcl = pkInvcl;
	}
	public String getPkMeasdoc() {
		return pkMeasdoc;
	}
	public void setPkMeasdoc(String pkMeasdoc) {
		this.pkMeasdoc = pkMeasdoc;
	}
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
	public String getProductline() {
		return productline;
	}
	public void setProductline(String productline) {
		this.productline = productline;
	}
	public String getTaxitems() {
		return taxitems;
	}
	public void setTaxitems(String taxitems) {
		this.taxitems = taxitems;
	}
	public void setMeasdoc(MeasDoc measdoc) {
		this.measdoc = measdoc;
	}
	public MeasDoc getMeasdoc() {
		return measdoc;
	}
	public Invcl getInvcl() {
		return invcl;
	}
	public void setInvcl(Invcl invcl) {
		this.invcl = invcl;
	}
	public String getWholemanaflag() {
		return wholemanaflag;
	}
	public void setWholemanaflag(String wholemanaflag) {
		this.wholemanaflag = wholemanaflag;
	}
	public String getSerialmanaflag() {
		return serialmanaflag;
	}
	public void setSerialmanaflag(String serialmanaflag) {
		this.serialmanaflag = serialmanaflag;
	}

}
