/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package cn.rootyu.rad.modules.supply.service;

import cn.rootyu.rad.common.persistence.Page;
import cn.rootyu.rad.common.service.BaseService;
import cn.rootyu.rad.common.utils.ConstantUtils;
import cn.rootyu.rad.common.utils.NotifyUtils;
import cn.rootyu.rad.modules.supply.dao.ArriveOrderDao;
import cn.rootyu.rad.modules.supply.dao.BuyOrderDao;
import cn.rootyu.rad.modules.supply.dao.EnWarehouseDao;
import cn.rootyu.rad.modules.supply.entity.*;
import cn.rootyu.rad.modules.supply.utils.SupplyDataUtils;
import cn.rootyu.rad.modules.sys.entity.User;
import cn.rootyu.rad.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入库Service
 * @author maliang
 * @version 2015-10-17
 */
@Service
@Transactional(readOnly = true)
public class EnWarehouseService extends BaseService {

	@Autowired
	private EnWarehouseDao enWarehouseDao;
	@Autowired
	private ArriveOrderDao arriveOrderDao;
	@Autowired
	private BuyOrderDao buyOrderDao;
	
	//错误信息
	private String errmsg;
	
	/**
	 * 分页查询主表数据列表
	 * @param page 分页对象
	 * @author maliang
	 * @version 2015-10-27
	 */
	public Page<EnWarehouse> findPage(Page<EnWarehouse> page, EnWarehouse entity) {
		//查询当前用户
		User userCur = UserUtils.getUser();
		if (userCur == null) {
			page.setList(null);
			return page;
		}
		if (entity == null) {
			entity = new EnWarehouse();
		}
		EnWarehouse ewWhere = new EnWarehouse();
		ewWhere.setCoperatorid(userCur.getId());
		if (!(entity.getFbillflag() == null || entity.getFbillflag().equals(""))) {
			ewWhere.setFbillflag(entity.getFbillflag());
//			if (entity.getFbillflag().equals(ConstantUtils.EnWarehouse.ORDER_MAKE)) {
//				ewWhere.setFbillflag(entity.getFbillflag());
//			}else{
//				List<String> fbillflagList = new ArrayList<String>();
//				fbillflagList.add(ConstantUtils.EnWarehouse.ORDER_CONFIRM);
//				fbillflagList.add(ConstantUtils.EnWarehouse.ORDER_FINISH);
//				ewWhere.setFbillflagList(fbillflagList);
//			}
		}
		ewWhere.setDelFlag(EnWarehouse.DEL_FLAG_NORMAL);
		StorDoc sd = new StorDoc();
		sd.setDelFlag(StorDoc.DEL_FLAG_NORMAL);
		ewWhere.setSd(sd);
		ewWhere.setPage(page);
		page.setList(enWarehouseDao.findList(ewWhere));
		return page;
	}
	
	/**
	 * 查询子表数据列表
	 * @author maliang
	 * @version 2015-10-27
	 */
	public Map<String,List<EnWarehouseZ>> findChildList(Page<EnWarehouseZ> page, EnWarehouseZ entity) {
		Map<String,List<EnWarehouseZ>> resMap = new HashMap<String,List<EnWarehouseZ>>();
		//查询当前用户
		User userCur = UserUtils.getUser();
		if (userCur == null) {
			return resMap;
		}
		if (entity == null) {
			return resMap;
		}
		if (entity.getPkEwhid() == null || entity.getPkEwhid().equals("")) {
			return resMap;
		}
		
		//是否可以看此单下的存货
		EnWarehouse ewWhere = new EnWarehouse();
		ewWhere.setId(entity.getPkEwhid());
		ewWhere.setCoperatorid(userCur.getId());
		ewWhere.setDelFlag(EnWarehouse.DEL_FLAG_NORMAL);
		EnWarehouse ewInfo = enWarehouseDao.findInfo(ewWhere);
		if (ewInfo == null) {
			return resMap;
		}
		boolean fullData = false;
		if (ewInfo.getFbillflag().equals(ConstantUtils.EnWarehouse.ORDER_MAKE)) {
			fullData = true;
		}
		
		EnWarehouseZ ewzWhere = new EnWarehouseZ();
		ewzWhere.setPkEwhid(entity.getPkEwhid());
		ewzWhere.setDelFlag(EnWarehouseZ.DEL_FLAG_NORMAL);
		InvBasDoc ibdWhere = new InvBasDoc();
		ibdWhere.setDelFlag(InvBasDoc.DEL_FLAG_NORMAL);
		MeasDoc md = new MeasDoc();
		md.setDelFlag(MeasDoc.DEL_FLAG_NORMAL);
		ibdWhere.setMd(md);
		ewzWhere.setIbd(ibdWhere);
		Station sta = new Station();
		sta.setDelFlag(Station.DEL_FLAG_NORMAL);
		ewzWhere.setSta(sta);
		CargDoc cd = new CargDoc();
		cd.setDelFlag(CargDoc.DEL_FLAG_NORMAL);
		ewzWhere.setCd(cd);
		User kgy = new User();
		kgy.setDelFlag(User.DEL_FLAG_NORMAL);
		ewzWhere.setKgy(kgy);
		ewzWhere.setPage(page);
		List<EnWarehouseZ> ewzList = enWarehouseDao.findChildList(ewzWhere);
		
		EnWarehouseZ ewzInfoWhere = new EnWarehouseZ();
		ewzInfoWhere.setPkEwhid(entity.getPkEwhid());
		ewzInfoWhere.setDelFlag(EnWarehouseZ.DEL_FLAG_NORMAL);
		List<EnWarehouseZ> ewzInfoList = enWarehouseDao.findChildList(ewzInfoWhere);
		
		Map<String, EnWarehouseZ> tmpMap = new HashMap<String, EnWarehouseZ>();//填补数据(无工位)
		Map<String,List<EnWarehouseZ>> hasInvMap = new HashMap<String,List<EnWarehouseZ>>();//已经存在的工位物料
		Map<String,Map<String,List<String>>> pkInvMap = new HashMap<String,Map<String,List<String>>>();//项目仓库下的物料
		for (EnWarehouseZ ewzItem : ewzList) {
			if (!(ewzItem.getBatchcodenum() == null || ewzItem.getBatchcodenum().equals(""))) {
				ewzItem.setBatchcodenum("展开查看");
			}
			StringBuffer strbuff = new StringBuffer();
			strbuff.append(ewzItem.getCsourcebillbid());
			String str = strbuff.toString();
			strbuff.append(",");
			strbuff.append(ewzItem.getPkStationid());
			String hasStr = strbuff.toString();
			if (!hasInvMap.containsKey(hasStr)) {
				hasInvMap.put(hasStr, new ArrayList<EnWarehouseZ>());
			}
			hasInvMap.get(hasStr).add(ewzItem);
			
			if (tmpMap.containsKey(str)) {
				continue;
			}
			tmpMap.put(str, ewzItem);
			
			String jmfKeyTmp = ewzItem.getCprojectid();
			String invKeyTmp = ewzItem.getCinvbasid();
			if (!pkInvMap.containsKey(jmfKeyTmp)) {
				pkInvMap.put(jmfKeyTmp, new HashMap<String,List<String>>());
			}
			Map<String,List<String>> pkInvMapTmp = pkInvMap.get(jmfKeyTmp);
			if (!pkInvMapTmp.containsKey(invKeyTmp)) {
				pkInvMapTmp.put(invKeyTmp, new ArrayList<String>());
			}
			pkInvMapTmp.get(invKeyTmp).add(ewzItem.getCsourcebillbid());
		}
		
		if (fullData) {
			String storId = ewInfo.getCwarehouseid();
			// 物料下的工位
			for (String jmfItem : pkInvMap.keySet()) {
				Map<String, List<String>> pkInvMapTmp = pkInvMap.get(jmfItem);
				for (String invItemTmp : pkInvMapTmp.keySet()) {
					InvRelation irWhere = new InvRelation();
					irWhere.setPkInvbasdoc(invItemTmp);
					StaManFil smfWhere = new StaManFil();
					smfWhere.setPkCwarehouseid(storId);
					irWhere.setSmf(smfWhere);
					OnhandNum ohnWhere = new OnhandNum();
					ohnWhere.setDelFlag(OnhandNum.DEL_FLAG_NORMAL);
					irWhere.setOhn(ohnWhere);
					List<InvRelation> invRelationFullListTmp = SupplyDataUtils.findInvRelationList(irWhere);
					List<InvRelation> invRelationListTmp = new ArrayList<InvRelation>();
					for (InvRelation staItem : invRelationFullListTmp) {
						if (staItem.getSmf() != null) {
							if (!(staItem.getSmf().getPkCwarehouseid() == null || "".equals(staItem.getSmf().getPkCwarehouseid()))) {
								invRelationListTmp.add(staItem);
							}
						}
					}
					// TODO 物料找不到工位的时候，这样处理（匹配所用工位）
					if (invRelationListTmp == null || invRelationListTmp.size() < 1) {
//					if (invRelationFullListTmp == null || invRelationFullListTmp.size() < 1) {
						// invRelationList = new ArrayList<InvRelation>();
						// InvRelation irTmp = new InvRelation();
						// StaManFil smfTmp = new StaManFil();
						// smfTmp.setPkWkcenter("1006AA100000004HF47T");
						// smfTmp.setPkStorekeeper("65a6e368e46f4845b2baa70fccd173e5");
						// irTmp.setSmf(smfTmp);
						// invRelationList.add(irTmp);
						invRelationListTmp = SupplyDataUtils.findInvRelationNoRtList(irWhere);
						if (invRelationListTmp == null || invRelationListTmp.size() < 1) {
							return resMap;
						}
					} 
//					else {
//						if (invRelationListTmp == null || invRelationListTmp.size() < 1) {
//							return resMap;
//						}
//					}
					List<String> wybs = pkInvMapTmp.get(invItemTmp);
					// 填补缺失的数据
					for (InvRelation staItem : invRelationListTmp) {
						staItem.getSmf().setPkCwarehouseid(storId);
						staItem.setPkInvbasdoc(invItemTmp);
						if (staItem.getOhn() == null) {
							OnhandNum ohnTmp = new OnhandNum();
							staItem.setOhn(ohnTmp);
						}
						
						for (String dhbidItem : wybs) {
							StringBuffer strbuff = new StringBuffer();
							strbuff.append(dhbidItem);
							String str = strbuff.toString();
							strbuff.append(",");
							strbuff.append(staItem.getSmf().getPkWkcenter());
							String hasStr = strbuff.toString();
							
							if (!hasInvMap.containsKey(hasStr)) {
								EnWarehouseZ ewzFatherTmp = tmpMap.get(str);
								if (ewzFatherTmp == null) {
									return resMap;
								}
								EnWarehouseZ ewzTmp = (EnWarehouseZ) ewzFatherTmp.clone();
								StringBuffer idTmp = new StringBuffer();
								idTmp.append(staItem.getSmf().getPkWkcenter());
								idTmp.append(dhbidItem);
								ewzTmp.setIbd(ewzFatherTmp.getIbd());
								ewzTmp.setCd(null);
								ewzTmp.setKgy(null);
								ewzTmp.setSta(null);
								ewzTmp.setId(idTmp.toString());
								ewzTmp.setCinvbasid(staItem.getPkInvbasdoc());
								ewzTmp.setNinnum(0.0);
								ewzTmp.setNaccuminnum(0.0);
								ewzTmp.setNaccumactinnum(0.0);
								ewzTmp.setShelvesflag(ConstantUtils.EnWarehouseZ.SHELVES_FLAG_NOT);
								ewzTmp.setPkStokeepid(staItem.getSmf().getPkStorekeeper());
								ewzTmp.setPkStationid(staItem.getSmf().getPkWkcenter());
								ewzTmp.setPkCspaceid(staItem.getOhn().getPkGoodid());
								if (!(ewzTmp.getPkStokeepid() == null || "".equals(ewzTmp.getPkStokeepid()))) {
									User kgyTmp = UserUtils.get(ewzTmp.getPkStokeepid());
									ewzTmp.setKgy(kgyTmp);
								}
								if (!(ewzTmp.getPkStationid() == null || "".equals(ewzTmp.getPkStationid()))) {
									Station staWhereTmp = new Station();
									staWhereTmp.setId(ewzTmp.getPkStationid());
									staWhereTmp.setDelFlag(Station.DEL_FLAG_NORMAL);
									Station staTmp = SupplyDataUtils.findStationInfo(staWhereTmp);
									ewzTmp.setSta(staTmp);
								}
								if (!(ewzTmp.getPkCspaceid() == null || "".equals(ewzTmp.getPkCspaceid()))) {
									CargDoc cdWhereTmp = new CargDoc();
									cdWhereTmp.setId(ewzTmp.getPkCspaceid());
									cdWhereTmp.setDelFlag(CargDoc.DEL_FLAG_NORMAL);
									CargDoc cdTmp = SupplyDataUtils.findCargDocInfo(cdWhereTmp);
									ewzTmp.setCd(cdTmp);
								}
								ewzList.add(ewzTmp);
							}
						}
					}
				}
			}
		}

		//格式化数据
		Map<String, EnWarehouseZ> invMap = new HashMap<String, EnWarehouseZ>();
		List<EnWarehouseZ> invList = new ArrayList<EnWarehouseZ>();
		for (EnWarehouseZ ewzItem : ewzList) {
			if (ewzItem.getNinnum() == null) {
				ewzItem.setNinnum(0.0);
			}
			if (ewzItem.getNaccuminnum() == null) {
				ewzItem.setNaccuminnum(0.0);
			}
			if (ewzItem.getNaccumactinnum() == null) {
				ewzItem.setNaccumactinnum(0.0);
			}
			if (!fullData && ewzItem.getNinnum() < 1 && ewzItem.getNaccuminnum() < 1) {
				continue;
			}
			if (ewzItem.getKgy() == null) {
				ewzItem.setKgy(new User());
			}
			if (ewzItem.getSta() == null) {
				ewzItem.setSta(new Station());
			}
			if (ewzItem.getCd() == null) {
				ewzItem.setCd(new CargDoc());
			}
			StringBuffer strbuff = new StringBuffer();
			strbuff.append(ewzItem.getPkStationid());
			strbuff.append(",");
			strbuff.append(ewzItem.getCsourcebillbid());
			String str = strbuff.toString();
			if (invMap.containsKey(str)) {
				EnWarehouseZ ewzItemTmp = invMap.get(str);
				ewzItemTmp.setNinnum(ewzItemTmp.getNinnum() + ewzItem.getNinnum());
				ewzItemTmp.setNaccuminnum(ewzItemTmp.getNaccuminnum() + ewzItem.getNaccuminnum());
				ewzItemTmp.setNaccumactinnum(ewzItemTmp.getNaccumactinnum() + ewzItem.getNaccumactinnum());
			}else{
				invMap.put(str, ewzItem);
				invList.add(ewzItem);
			}
		}
		
		resMap.put("rows", invList);
		resMap.put("serial", ewzInfoList);
		return resMap;
	}
	
	/**
	 * 生成入库单数据
	 * 入库管理员签字确认数量
	 * @author maliang
	 * @version 2015-10-22
	 * update 2015-10-26
	 * @throws Exception 
	 */
//	@Transactional(readOnly = false, rollbackFor = { Exception.class })
//	public boolean addData(String arriveOrderId) throws Exception {
//		//实收数量作为累计入库数量
//		boolean b = false;
//		//查询当前用户
//		User userCur = UserUtils.getUser();
//		if (userCur == null) {
//			return b;
//		}
//		if (arriveOrderId == null || arriveOrderId.equals("")) {
//			this.errmsg = "到货单不存在！";
//			return b;
//		}
//		//是否可以看此单下的存货
//		ArriveOrder aoWhere = new ArriveOrder();//查询条件
//		//TODO 一个仓库对应一个管理员 关联
//		//交给工作流处理
//		aoWhere.setId(arriveOrderId);
//		aoWhere.setCreceivepsn(userCur.getId());
//		aoWhere.setReceiveflag(ConstantUtils.ArriveOrder.RECEIVE);
//		aoWhere.setZjsignState(ConstantUtils.ArriveOrder.SIGN);
//		aoWhere.setDelFlag(ArriveOrder.DEL_FLAG_NORMAL);
//		ArriveOrder aoInfo = arriveOrderDao.findInfo(aoWhere);
//		if (aoInfo == null) {
//			this.errmsg = "到货单不存在！";
//			return b;
//		}
//		
//		//到货单子表查询条件
//		ArriveOrderB aobWhere = new ArriveOrderB();
//		aobWhere.setCarriveorderid(arriveOrderId);
//		aobWhere.setDelFlag(ArriveOrderB.DEL_FLAG_NORMAL);
//		InvManDoc imdWhere = new InvManDoc();
//		imdWhere.setDelFlag(InvBasDoc.DEL_FLAG_NORMAL);
//		aobWhere.setImd(imdWhere);
//		//子表数据
//		List<ArriveOrderB> aobList = arriveOrderDao.findChildList(aobWhere);
//		if (aobList == null || aobList.size() < 1) {
//			this.errmsg = "到货单不存在！";
//			return b;
//		}
//		
//		//采购单子表查询条件
//		BuyOrderB bobWhere = new BuyOrderB();
//		bobWhere.setCorderid(aobList.get(0).getCsourcebillid());
//		if (bobWhere.getCorderid() == null || "".equals(bobWhere.getCorderid())) {
//			this.errmsg = "到货单中来源采购单不存在！";
//			return b;
//		}
//		bobWhere.setDelFlag(BuyOrderB.DEL_FLAG_NORMAL);
//		List<BuyOrderB> bobList = buyOrderDao.findChildList(bobWhere);//采购单子表数据
//		if (bobList == null || bobList.size() < 1) {
//			this.errmsg = "到货单中来源采购单不存在！";
//			return b;
//		}
//		
//		//项目仓库下的物料
//		Map<String,Map<String,List<String>>> pkInvMap = new HashMap<String,Map<String,List<String>>>();
//		//检测是否有重复物料
//		Map<String,String> invIdMapTmp = new HashMap<String, String>();
//		for (ArriveOrderB aobItem : aobList) {
//			if (aobItem.getCbaseid() == null || aobItem.getCbaseid().equals("")) {
//				this.errmsg = "到货单数据（存货基础档案）异常！";
//				return b;
//			}
//			if (aobItem.getCprojectid() == null || aobItem.getCprojectid().equals("")) {
//				this.errmsg = "到货单数据（项目管理档案）异常！";
//				return b;
//			}
//			if (aobItem.getImd() == null) {
//				this.errmsg = "到货单数据（存货管理档案）异常！";
//				return b;
//			}
//			if (aobItem.getNowNinnum() == null) {
//				this.errmsg = "到货单数据（本次实收数量）异常！";
//				return b;
//			}
//			if (aobItem.getNaccumwarehousenum() == null) {
//				aobItem.setNaccumwarehousenum(0.0);
//			}
//			if (aobItem.getNowNinnum() == 0) {
//				continue;
//			}
//			String invIdTmp = aobItem.getCbaseid();
//			if (invIdMapTmp.containsKey(invIdTmp)) {
//				this.errmsg = "请将重复物料分批入库！";
//				return b;
//			}else{
//				invIdMapTmp.put(invIdTmp, invIdTmp);
//			}
//			String jmfKeyTmp = aobItem.getCprojectid();
//			String storKeyTmp = aobItem.getCwarehouseid();
//			if (!pkInvMap.containsKey(jmfKeyTmp)) {
//				pkInvMap.put(jmfKeyTmp, new HashMap<String,List<String>>());
//			}
//			Map<String,List<String>> tmpMap = pkInvMap.get(jmfKeyTmp);
//			if (!tmpMap.containsKey(storKeyTmp)) {
//				tmpMap.put(storKeyTmp, new ArrayList<String>());
//			}
//			tmpMap.get(storKeyTmp).add(aobItem.getCbaseid());
//		}
//		
//		//物料下的工位
//		Map<String,List<InvRelation>> invRelationMap = new HashMap<String,List<InvRelation>>();
//		for (String item : pkInvMap.keySet()) {
//			Map<String,List<String>> tmpMap = pkInvMap.get(item);
//			for (String itemTmp : tmpMap.keySet()) {
//				List<String> pkInvbasdocListTmp = tmpMap.get(itemTmp);
//				for (String invItemTmp : pkInvbasdocListTmp) {
//					InvRelation irWhere = new InvRelation();
//					irWhere.setPkInvbasdoc(invItemTmp);
//					StaManFil smfWhere = new StaManFil();
//					smfWhere.setPkCwarehouseid(itemTmp);
//					irWhere.setSmf(smfWhere);
//					OnhandNum ohnWhere = new OnhandNum();
//					ohnWhere.setDelFlag(OnhandNum.DEL_FLAG_NORMAL);
//					irWhere.setOhn(ohnWhere);
//					List<InvRelation> invRelationFullListTmp = SupplyDataUtils.findInvRelationList(irWhere);
//					List<InvRelation> invRelationListTmp = new ArrayList<InvRelation>();
//					for (InvRelation staItem : invRelationFullListTmp) {
//						if (staItem.getSmf() != null) {
//							if (!(staItem.getSmf().getPkCwarehouseid() == null || "".equals(staItem.getSmf().getPkCwarehouseid()))) {
//								invRelationListTmp.add(staItem);
//							}
//						}
//					}
//					//TODO 物料找不到工位的时候，这样处理
//					if (invRelationListTmp == null || invRelationListTmp.size() < 1) {
////					if (invRelationFullListTmp == null || invRelationFullListTmp.size() < 1) {
////						invRelationListTmp = new ArrayList<InvRelation>();
////						InvRelation irTmp = new InvRelation();
////						StaManFil smfTmp = new StaManFil();
////						smfTmp.setPkWkcenter("1006AA100000004HF47T");
////						smfTmp.setPkStorekeeper("65a6e368e46f4845b2baa70fccd173e5");
////						irTmp.setSmf(smfTmp);
////						invRelationListTmp.add(irTmp);
//						invRelationListTmp = SupplyDataUtils.findInvRelationNoRtList(irWhere);
//						if (invRelationListTmp == null || invRelationListTmp.size() < 1) {
//							InvBasDoc ibdWhereErr = new InvBasDoc();
//							ibdWhereErr.setId(invItemTmp);
//							ibdWhereErr.setDelFlag(InvBasDoc.DEL_FLAG_NORMAL);
//							InvBasDoc ibdInfoErr = SupplyDataUtils.findInvBasDocInfo(ibdWhereErr);
//							StringBuffer strErr = new StringBuffer();
//							strErr.append(ibdInfoErr.getInvname());
//							strErr.append("(");
//							strErr.append(ibdInfoErr.getInvcode());
//							strErr.append(")");
//							strErr.append("在工艺中未找到工位，并且系统中的工位也未匹配！");
//							this.errmsg = strErr.toString();
//							return b;
//						}
//					}
////					else{
////						if (invRelationListTmp == null || invRelationListTmp.size() < 1) {
////							InvBasDoc ibdWhereErr = new InvBasDoc();
////							ibdWhereErr.setId(invItemTmp);
////							ibdWhereErr.setDelFlag(InvBasDoc.DEL_FLAG_NORMAL);
////							InvBasDoc ibdInfoErr = SupplyDataUtils.findInvBasDocInfo(ibdWhereErr);
////							StringBuffer strErr = new StringBuffer();
////							strErr.append(ibdInfoErr.getInvname());
////							strErr.append("(");
////							strErr.append(ibdInfoErr.getInvcode());
////							strErr.append(")");
////							strErr.append("工艺中的工位未在系统中匹配！");
////							this.errmsg = strErr.toString();
////							return b;
////						}
////					}
//					for (InvRelation staItem : invRelationListTmp) {
//						staItem.getSmf().setPkCwarehouseid(itemTmp);
//						staItem.setPkInvbasdoc(invItemTmp);
//					}
//					Map<String,List<InvRelation>> invRelationMapTmp = new HashMap<String,List<InvRelation>>();
//					StringBuffer strTmp = new StringBuffer();
//					strTmp.append(item);
//					strTmp.append(itemTmp);
//					strTmp.append(invItemTmp);
//					invRelationMapTmp.put(strTmp.toString(), invRelationListTmp);
//					invRelationMap.putAll(invRelationMapTmp);
//				}
//			}
//		}
//		
//		// 要插入的入库单主表对象
//		EnWarehouse ewAdd = new EnWarehouse();
//		ewAdd.preInsert();
//		ewAdd.setVbillcode(CodeRuleUtils.getBusSerialCode(CodeRuleUtils.WAREHOUSING_LISTS_PREFIX));//单据号
//		ewAdd.setDbilldate(ewAdd.getCreateDate());//单据日期
//		ewAdd.setCwarehouseid(aobList.get(0).getCwarehouseid());//仓库ID
//		ewAdd.setPkCalbody(aoInfo.getCstoreorganization());//库存组织PK
//		ewAdd.setCbiztype(aoInfo.getCbiztype());//业务类型ID
//		ewAdd.setCdispatcherid(ConstantUtils.EnWarehouse.C_DISPATCHER_ID);//收发类型ID
//		ewAdd.setCwhsmanagerid(aoInfo.getCreceivepsn());//入库管理员ID
//		ewAdd.setCdptid(aoInfo.getCdeptid());//采购部门ID
//		ewAdd.setCbizid(aoInfo.getCemployeeid());//采购员ID
//		ewAdd.setCproviderid(aoInfo.getCvendormangid());//供应商ID
//		ewAdd.setVnote(null);//备注
//		ewAdd.setFreplenishflag(aoInfo.getBisback());
//		ewAdd.setPkCorp(aoInfo.getPkCorp());//公司ID
//		ewAdd.setCoperatorid(ewAdd.getCreateBy().getId());//制单人
//		ewAdd.setTmaketime(ewAdd.getCreateDate());//制单时间
//		ewAdd.setCregister(ewAdd.getCreateBy().getId());//库房签字人
//		ewAdd.setTaccounttime(ewAdd.getCreateDate());//库房签字时间
//		ewAdd.setCbilltypecode(ConstantUtils.EnWarehouse.C_BILL_TYPE_CODE);//库存单据类型编码
//		ewAdd.setFbillflag(ConstantUtils.EnWarehouse.ORDER_MAKE);//单据状态（1.生成 2.下发 3.上架完毕）
//		ewAdd.setSupplyemp(aoInfo.getCreateBy().getId());
//				
//		//要新增的子数据
//		List<EnWarehouseZ> ewzAddList = new ArrayList<EnWarehouseZ>();
//		List<BuyOrderB> bobUpdateList = new ArrayList<BuyOrderB>();
//		List<ArriveOrderB> aobUpdateList = new ArrayList<ArriveOrderB>();
//		DecimalFormat df = new DecimalFormat("000");
//		//---------------------第一层循环（物料）------------------------------
//		for (ArriveOrderB aobItem : aobList) {
//			//回写到货单累计入库数量
//			ArriveOrderB aobUpdate = new ArriveOrderB();
//			aobUpdate.preUpdate();
//			aobUpdate.setId(aobItem.getId());
//			aobUpdate.setNaccumwarehousenum(aobItem.getNaccumwarehousenum() + aobItem.getNowNinnum());
//			//仅供入库管理员更新本次实入数量时使用
//			aobUpdate.setNowNinnum(aobItem.getNelignum() - aobUpdate.getNaccumwarehousenum());//本次应收数量
//			aobUpdateList.add(aobUpdate);
//			
//			//忽略应收数量为0的数据，因（仅供入库管理员更新本次实入数量时使用）所以要放在后面
//			if (aobItem.getNowNinnum() == 0) {
//				continue;
//			}
//			
//			if (aobItem.getNowNinnum() > (aobItem.getNelignum() - aobItem.getNaccumwarehousenum())) {
//				this.errmsg  = "部分物料数据超出累计入库数量！";
//				return b;
//			}
//			
//			for (BuyOrderB bobItem : bobList) {
//				if (bobItem.getCbaseid().equals(aobItem.getCbaseid())) {
//					if (bobItem.getNaccumstorenum() == null) {
//						bobItem.setNaccumstorenum(0.0);
//					}
//					//回写采购单累计入库数量
//					BuyOrderB bobUpdate = new BuyOrderB();
//					bobUpdate.preUpdate();
//					bobUpdate.setId(bobItem.getId());
//					bobUpdate.setNaccumstorenum(bobItem.getNaccumstorenum() + aobItem.getNowNinnum());
//					bobUpdateList.add(bobUpdate);
//					break;
//				}
//			}
//			
//			StringBuffer strTmp = new StringBuffer();
//			strTmp.append(aobItem.getCprojectid());
//			strTmp.append(aobItem.getCwarehouseid());
//			strTmp.append(aobItem.getCbaseid());
//			String invRelationKey = strTmp.toString();
//			List<InvRelation> invRelationList = invRelationMap.get(invRelationKey);
//			if (invRelationList == null || invRelationList.size() < 1) {
//				JobBasFil jbfWhereErr = new JobBasFil();
//				jbfWhereErr.setDelFlag(JobBasFil.DEL_FLAG_NORMAL);
//				JobMngFil jmfErr = new JobMngFil();
//				jmfErr.setId(aobItem.getCprojectid());
//				jmfErr.setDelFlag(JobMngFil.DEL_FLAG_NORMAL);
//				jbfWhereErr.setJmf(jmfErr);
//				JobBasFil jbfInfoErr = SupplyDataUtils.findJobBasFilInfo(jbfWhereErr);
//				StorDoc sdWhereErr = new StorDoc();
//				sdWhereErr.setId(aobItem.getCwarehouseid());
//				sdWhereErr.setDelFlag(StorDoc.DEL_FLAG_NORMAL);
//				StorDoc sdInfoErr = SupplyDataUtils.findStorDocInfo(sdWhereErr);
//				InvBasDoc ibdWhereErr = new InvBasDoc();
//				ibdWhereErr.setId(aobItem.getCbaseid());
//				ibdWhereErr.setDelFlag(InvBasDoc.DEL_FLAG_NORMAL);
//				InvBasDoc ibdInfoErr = SupplyDataUtils.findInvBasDocInfo(ibdWhereErr);
//				StringBuffer strErr = new StringBuffer();
//				strErr.append(jbfInfoErr.getJobname());
//				strErr.append(" ");
//				strErr.append(sdInfoErr.getStorname());
//				strErr.append(" ");
//				strErr.append(ibdInfoErr.getInvname());
//				strErr.append("(");
//				strErr.append(ibdInfoErr.getInvcode());
//				strErr.append(")");
//				strErr.append("关系缺失！");
//				this.errmsg = strErr.toString();
//				return b;
//			}
//			//填补空缺的现存量数据
//			for (InvRelation tmpItem : invRelationList) {
//				if (tmpItem.getSmf() == null) {
//					return b;
//				}
//				if (tmpItem.getSmf().getPkStorekeeper() == null || tmpItem.getSmf().getPkStorekeeper().equals("")) {
//					//提供错误信息
//					Map<String,List<String>> storStaMap4Err = new HashMap<String,List<String>>();
//					List<String> storIdList4Err = new ArrayList<String>();
//					List<String> staIdList4Err = new ArrayList<String>();
//					for (InvRelation tmpItem4Err : invRelationList) {
//						if (tmpItem4Err.getSmf() == null) {
//							return b;
//						}
//						if (tmpItem4Err.getSmf().getPkStorekeeper() == null || tmpItem4Err.getSmf().getPkStorekeeper().equals("")) {
//							if (tmpItem4Err.getSmf().getPkCwarehouseid() == null || tmpItem4Err.getSmf().getPkCwarehouseid().equals("")) {
//								return b;
//							}
//							if (tmpItem4Err.getSmf().getPkWkcenter() == null || tmpItem4Err.getSmf().getPkWkcenter().equals("")) {
//								return b;
//							}
//							storIdList4Err.add(tmpItem4Err.getSmf().getPkCwarehouseid());
//							staIdList4Err.add(tmpItem4Err.getSmf().getPkWkcenter());
//							String storStaMap4ErrKey = tmpItem4Err.getSmf().getPkCwarehouseid();
//							if (!storStaMap4Err.containsKey(storStaMap4ErrKey)) {
//								storStaMap4Err.put(storStaMap4ErrKey, new ArrayList<String>());
//							}
//							storStaMap4Err.get(storStaMap4ErrKey).add(tmpItem4Err.getSmf().getPkWkcenter());
//						}
//					}
//					if (!(storIdList4Err.size() > 0 && staIdList4Err.size() > 0)) {
//						return b;
//					}
//					StorDoc sdWhereErr = new StorDoc();
//					sdWhereErr.setIdList(storIdList4Err);
//					sdWhereErr.setDelFlag(StorDoc.DEL_FLAG_NORMAL);
//					Station staWhereErr = new Station();
//					staWhereErr.setIdList(staIdList4Err);
//					staWhereErr.setDelFlag(Station.DEL_FLAG_NORMAL);
//					Map<String,StorDoc> sdMapErr = SupplyDataUtils.findStorDocMap("id", sdWhereErr);
//					Map<String,Station> staMapErr = SupplyDataUtils.findStationMap("id", staWhereErr);
//					StringBuffer strErr = new StringBuffer();
//					for (String keyErr : storStaMap4Err.keySet()) {
//						StorDoc sdInfoErr = sdMapErr.get(keyErr);
//						if (sdInfoErr == null) {
//							this.errmsg = "不存在的项目仓库！";
//							return b;
//						}
//						strErr.append(sdInfoErr.getStorname());
//						strErr.append(" -- ");
//						List<String> staListErrTmp = storStaMap4Err.get(keyErr);
//						int staListSizeTmp = staListErrTmp.size();
//						for (int i = 0; i < staListSizeTmp; i++) {
//							Station staInfoErr = staMapErr.get(staListErrTmp.get(i));
//							if (staInfoErr == null) {
//								this.errmsg = "不存在的工位！";
//								return b;
//							}
//							strErr.append(staInfoErr.getGzzxbm());
//							strErr.append("（");
//							strErr.append(staInfoErr.getGzzxmc());
//							strErr.append("）");
//							if (i < staListSizeTmp - 1) {
//								strErr.append("、");
//							}
//						}
//						strErr.append("未指定库管员！<br>");
//					}
//					this.errmsg = strErr.toString();
//					return b;
//				}
//				if (tmpItem.getOhn() == null) {
//					OnhandNum ohnTmp = new OnhandNum();
//					ohnTmp.setBalance(0.0);
//					tmpItem.setOhn(ohnTmp);
//				}
//			}
//			// 这里应该是实收数量
//			double nelignum = aobItem.getNowNinnum();
//			double balanceSum = 0;//填补安全库存需要的数量总和
//			for (InvRelation irItem : invRelationList) {
//				if (irItem.getOhn().getBalance() == null) {
//					irItem.getOhn().setBalance(0.0);
//				}
//				double numTmp = irItem.getOhn().getBalance();
//				if (numTmp > 0) {
//					balanceSum += numTmp;
//				}
//			}
//			double nelignumSurplus = nelignum - balanceSum;//剩余量
//			int stationNum = invRelationList.size();//工位数量
//			double remainder = 0;//剩余量平分后的剩余量
//			long nelignumTmp = 0;//剩余量平分
//			if (nelignumSurplus > 0) {
//				long nelignumSurplusTmp = (long) nelignumSurplus;
//				nelignumTmp = (nelignumSurplusTmp - nelignumSurplusTmp % stationNum) / stationNum;
//				remainder = nelignumSurplus - nelignumTmp * stationNum;
//			}
//			String batchCode = null;
//			if ((aobItem.getImd().getWholemanaflag() != null && aobItem.getImd().getWholemanaflag().equals(ConstantUtils.InvManDoc.BATCH_NUM))
//					|| (aobItem.getImd().getSerialmanaflag() != null && aobItem.getImd().getSerialmanaflag().equals(ConstantUtils.InvManDoc.SERIAL_NUM))) {
//				batchCode = CodeRuleUtils.getBusSerialCode(CodeRuleUtils.BATCH_CODE_PREFIX);// 批次号
//			}
//			int xlhNum = 1;
//			//---------------------第二层循环（工位）------------------------------
//			for (InvRelation staItem : invRelationList) {
//				EnWarehouseZ ewzAdd = new EnWarehouseZ();
//				ewzAdd.setNinnum(0.0);
//				//根据差额分配，分配上限不超过安全库存
//				double balanceTmp = staItem.getOhn().getBalance();
//				if (balanceTmp > 0 && nelignum > 0) {
//					if (balanceTmp < nelignum) {
//						ewzAdd.setNinnum(balanceTmp);
//					}else{
//						ewzAdd.setNinnum(nelignum);
//					}
//				}
//				//减去分配的量
//				nelignum = nelignum - ewzAdd.getNinnum();
//				//剩余按比例分配
//				//TODO 目前是1：1：1
//				if (nelignumSurplus > 0) {
//					ewzAdd.setNinnum(ewzAdd.getNinnum() + nelignumTmp);
//					if (remainder > 0) {
//						ewzAdd.setNinnum(ewzAdd.getNinnum() + remainder);
//						remainder = 0;
//					}
//				}
//				ewzAdd.setPkEwhid(ewAdd.getId());// 入库单主表ID
//				ewzAdd.setCinvbasid((aobItem.getCbaseid()));//存货基础ID
//				ewzAdd.setCinventoryid(aobItem.getImd().getId());//存货管理ID
//				ewzAdd.setFlargess(aobItem.getBlargess());//是否赠品
//				ewzAdd.setBsourcelargess(aobItem.getBlargess());//上游是否赠品行
//				ewzAdd.setCfirstbillhid(aobItem.getCsourcebillid());//订单ID
//				ewzAdd.setCsourcebillhid(aobItem.getCarriveorderid());//到货单ID
//				ewzAdd.setCfirstbillbid(aobItem.getCsourcebillrowid());
//				ewzAdd.setCsourcebillbid(aobItem.getId());
//				ewzAdd.setCprojectid(aobItem.getCprojectid());//项目
//				ewzAdd.setCrowno(aobItem.getCrowno());//行号
//				ewzAdd.setPkStationid(staItem.getSmf().getPkWkcenter());//工位
//				ewzAdd.setPkCspaceid(staItem.getOhn().getPkGoodid());//货位
//				ewzAdd.setShelvesflag(ConstantUtils.EnWarehouseZ.SHELVES_FLAG_NOT);//上架标识
//				ewzAdd.setNinsumnum(aobItem.getNowNinnum());//实到总数量(冗余数据，用于查询)
//				ewzAdd.setNaccuminnum(0.0);//累计上架数量
//				ewzAdd.setNaccumactinnum(0.0);//累计实际上架数量
//				ewzAdd.setInvstatus(aobItem.getInvstatus());//物料状态（0.正常 1.紧急物料）
//				ewzAdd.setPkStokeepid(staItem.getSmf().getPkStorekeeper());//库管员（上架人员）
//				ewzAdd.setNprice(aobItem.getNprice());
//				ewzAdd.setNmoney(0.0);
//				if (aobItem.getImd().getSerialmanaflag() != null && aobItem.getImd().getSerialmanaflag().equals(ConstantUtils.InvManDoc.SERIAL_NUM)) {
//					double sfSize = ewzAdd.getNinnum();
//					long sfSizeTmp = (long) sfSize;
//					long i;
//					//---------------------第三层循环（序列号）------------------------------
//					for (i = 0; i < sfSizeTmp; i++) {
//						//复制实体
//						EnWarehouseZ ewzAddC = (EnWarehouseZ) ewzAdd.clone();
//						if (ewzAddC == null) {
//							return b;
//						}
//						if (batchCode == null) {
//							return b;
//						}
//						ewzAddC.setBatchcodenum(batchCode + df.format(xlhNum));//序列号
//						ewzAddC.setNinnum(1.0);
//						ewzAddC.preInsert();
//						ewzAddList.add(ewzAddC);
//						xlhNum++;
//					}
//					if (sfSize - sfSizeTmp > 0) {
//						//复制实体
//						EnWarehouseZ ewzAddC = (EnWarehouseZ) ewzAdd.clone();
//						if (ewzAddC == null) {
//							return b;
//						}
//						if (batchCode == null) {
//							return b;
//						}
//						ewzAddC.setBatchcodenum(batchCode + df.format(xlhNum));//序列号
//						ewzAddC.setNinnum(sfSize - sfSizeTmp);
//						ewzAddC.preInsert();
//						ewzAddList.add(ewzAddC);
//					}
//				}else if (aobItem.getImd().getWholemanaflag() != null && aobItem.getImd().getWholemanaflag().equals(ConstantUtils.InvManDoc.BATCH_NUM)) {
//					ewzAdd.setBatchcode(batchCode);//批次号
//					ewzAdd.preInsert();
//					ewzAddList.add(ewzAdd);
//				}else{
//					ewzAdd.preInsert();
//					ewzAddList.add(ewzAdd);
//				}
//			}
//		}
//		
//		if (ewzAddList.size() < 1) {
//			this.errmsg  = "没有可入库的物料！";
//			return b;
//		}
//		
//		//可以比到货单小
////		if (ewzAddList.size() < aobList.size()) {
////			return false;
////		}
//		
//		//更新采购订单
//		//采购订单子表累加相应数量
//		//自己回写采购单数量（现在由ERP回写，如果同时自己和ERP都回写，会失败）
////		for (BuyOrderB bobItem : bobUpdateList) {
////			buyOrderDao.updateChild(bobItem);
////		}
//		//更新到货单
//		//到货单子表累加相应数量
//		//自己回写到货单数量（现在由自己回写，如果同时自己和ERP都回写，会失败）
//		for (ArriveOrderB aobItem : aobUpdateList) {
//			arriveOrderDao.updateChild(aobItem);
//		}
//		//到货单子表查询条件
//		ArriveOrderB aobWhereNew = new ArriveOrderB();
//		aobWhereNew.setCarriveorderid(arriveOrderId);
//		aobWhereNew.setDelFlag(ArriveOrderB.DEL_FLAG_NORMAL);
//		//子表数据
//		List<ArriveOrderB> aobListNew = arriveOrderDao.findChildList(aobWhereNew);
//		int aobNotFinishSize = 0;
//		for (ArriveOrderB aobItem : aobListNew) {
//			if (aobItem.getNaccumwarehousenum() == null) {
//				aobItem.setNaccumwarehousenum(0.0);
//			}
//			if (aobItem.getNaccumwarehousenum() < aobItem.getNelignum()) {
//				aobNotFinishSize++;
//			}
//		}
//		if (aobNotFinishSize <= 0) {
//			aoInfo.setHclosed(ConstantUtils.ArriveOrder.CLOSED);
//		}
//		
//		// 插入主表
//		enWarehouseDao.insert(ewAdd);
//		//插入子表
//		for (EnWarehouseZ addItem : ewzAddList) {
//			addItem.setDbizdate(addItem.getCreateDate());
//			enWarehouseDao.insertChild(addItem);
//		}
//		
//		//更新签字状态
//		aoInfo.preUpdate();
////		aoInfo.setPkRkmanage(ewAdd.getCreateBy().getId());
//		aoInfo.setRksignState(ConstantUtils.ArriveOrder.SIGN);
//		aoInfo.setRkdate(aoInfo.getUpdateDate());
//		arriveOrderDao.update(aoInfo);
//		
//		//同步到ERP实体对象
//		EnWarehouseERP ewERP = new EnWarehouseERP();
//		ewERP.entity2EntityERP(ewAdd, ewzAddList);
//		WebServiceParam wsp = new WebServiceParam();
//		wsp.setSourceSystem("L2M");
//		wsp.setAction("Insert");
//		wsp.setXml(JaxbObjectAndXmlUtil.object2Xml(ewERP));
////		wsp.setUrl("http://172.20.45.3/uapws/service/NCERPWSserviceeMain");
//		wsp.setNamespace("http://importaction.impl.ui.nc/NCERPWSserviceMain");
//		wsp.setMethod("saveNCBILL");
//		wsp.setBistype("45");
//		try {
//			SendWebService.doWebService(wsp);
//		} catch (Exception e) {
//			e.printStackTrace();
//			b = false;
//			this.errmsg  = "同步ERP异常！";
//			throw new Exception();
//		}
//		
//		b = true;
//		return b;
//	}
	
	/**
	 * 更新入库单
	 * @author maliang
	 * @version 2015-10-20
	 * update 2015-10-28
	 */
	@Transactional(readOnly = false)
	public boolean saveData(EnWarehouse entity) {
		boolean b = false;
		//查询当前用户
		User userCur = UserUtils.getUser();
		if (userCur == null) {
			return b;
		}
		if (entity == null) {
			return b;
		}
		if (entity.getId() == null || entity.getId().equals("")) {
			return b;
		}
		if (entity.getEwzList().size() < 1) {
			return b;
		}
		// 取子列表
		List<EnWarehouseZ> l = entity.getEwzList();
		
		//主表查询条件
		EnWarehouse ewWhere = new EnWarehouse();
		ewWhere.setId(entity.getId());
		//TODO 条件不全
		ewWhere.setCoperatorid(userCur.getId());
		ewWhere.setFbillflag(ConstantUtils.EnWarehouse.ORDER_MAKE);
		ewWhere.setDelFlag(EnWarehouse.DEL_FLAG_NORMAL);
		//主表数据
		EnWarehouse ewInfo = enWarehouseDao.findInfo(ewWhere);
		if (ewInfo == null) {
			return b;
		}
		//子表查询条件
		EnWarehouseZ ewzWhere = new EnWarehouseZ();
		ewzWhere.setPkEwhid(ewInfo.getId());
		ewzWhere.setDelFlag(EnWarehouseZ.DEL_FLAG_NORMAL);
		//子表数据
		List<EnWarehouseZ> ewzList = enWarehouseDao.findChildList(ewzWhere);
		if (ewzList == null || ewzList.size() < 1) {
			return b;
		}
		
		List<EnWarehouseZ> ewzSaveList = new ArrayList<EnWarehouseZ>();//子表要更新的数据
		List<EnWarehouseZ> ewzAddList = new ArrayList<EnWarehouseZ>();//子表要新增的数据
		Map<String,Double> inSumNumMap = new HashMap<String,Double>();//某个物料实入数量总和
		Map<String, EnWarehouseZ> tmpMap = new HashMap<String, EnWarehouseZ>();//填补数据(无工位)
		Map<String,List<EnWarehouseZ>> hasInvMap = new HashMap<String,List<EnWarehouseZ>>();//已经存在的工位物料
		//某一个项目下的项目仓库下的物料
		Map<String,Map<String,List<String>>> pkInvMap = new HashMap<String,Map<String,List<String>>>();
		//用于验证表单数据的合法性
		for (EnWarehouseZ formItem : l) {
			if (formItem.getNinnum() == null || formItem.getNinnum() < 0) {
				return b;
			}
			if (formItem.getCinvbasid() == null || formItem.getCinvbasid().equals("")) {
				return b;
			}
			if (formItem.getId() == null || formItem.getId().equals("")) {
				return b;
			}
			String invId = formItem.getCsourcebillbid();
			if (!inSumNumMap.containsKey(invId)) {
				inSumNumMap.put(invId, 0.0);
			}
			inSumNumMap.put(invId, inSumNumMap.get(invId) + formItem.getNinnum());
		}
		for (EnWarehouseZ ewzItem : ewzList) {
			if (ewzItem.getNaccuminnum() == null) {
				ewzItem.setNaccuminnum(0.0);
			}
			String invId = ewzItem.getCsourcebillbid();
			if (inSumNumMap.get(invId) == null) {
				return b;
			}
			inSumNumMap.put(invId, inSumNumMap.get(invId) + ewzItem.getNaccuminnum());
			
			StringBuffer strbuff = new StringBuffer();
			strbuff.append(invId);
			String str = strbuff.toString();
			strbuff.append(",");
			strbuff.append(ewzItem.getPkStationid());
			String hasStr = strbuff.toString();
			if (!hasInvMap.containsKey(hasStr)) {
				hasInvMap.put(hasStr, new ArrayList<EnWarehouseZ>());
			}
			hasInvMap.get(hasStr).add(ewzItem);
			
			if (tmpMap.containsKey(str)) {
				continue;
			}
			tmpMap.put(str, ewzItem);
			
			String jmfKeyTmp = ewzItem.getCprojectid();
			String invKeyTmp = ewzItem.getCinvbasid();
			if (!pkInvMap.containsKey(jmfKeyTmp)) {
				pkInvMap.put(jmfKeyTmp, new HashMap<String,List<String>>());
			}
			Map<String,List<String>> pkInvMapTmp = pkInvMap.get(jmfKeyTmp);
			if (!pkInvMapTmp.containsKey(invKeyTmp)) {
				pkInvMapTmp.put(invKeyTmp, new ArrayList<String>());
			}
			pkInvMapTmp.get(invKeyTmp).add(ewzItem.getCsourcebillbid());
		}
		int checkSize = 0;
		for (String keyItem : inSumNumMap.keySet()) {
			for (EnWarehouseZ ewzItem : ewzList) {
				if (ewzItem.getCsourcebillbid().equals(keyItem)) {
					if (ewzItem.getNinsumnum().compareTo(inSumNumMap.get(keyItem)) != 0) {
						return b;
					}
					checkSize++;
					break;
				}
			}
		}
		if (checkSize < 1 || checkSize < inSumNumMap.size()) {
			return b;
		}
		String storId = ewInfo.getCwarehouseid();
		//物料下的工位
		Map<String,List<InvRelation>> invRelationMap = new HashMap<String,List<InvRelation>>();
		for (String jmfItem : pkInvMap.keySet()) {
			Map<String,List<String>> pkInvMapTmp = pkInvMap.get(jmfItem);
			for (String invItemTmp : pkInvMapTmp.keySet()) {
				InvRelation irWhere = new InvRelation();
				irWhere.setPkInvbasdoc(invItemTmp);
				StaManFil smfWhere = new StaManFil();
				smfWhere.setPkCwarehouseid(storId);
				irWhere.setSmf(smfWhere);
				OnhandNum ohnWhere = new OnhandNum();
				ohnWhere.setDelFlag(OnhandNum.DEL_FLAG_NORMAL);
				irWhere.setOhn(ohnWhere);
				List<InvRelation> invRelationFullListTmp = SupplyDataUtils.findInvRelationList(irWhere);
				List<InvRelation> invRelationListTmp = new ArrayList<InvRelation>();
				for (InvRelation staItem : invRelationFullListTmp) {
					if (staItem.getSmf() != null) {
						if (!(staItem.getSmf().getPkCwarehouseid() == null || "".equals(staItem.getSmf().getPkCwarehouseid()))) {
							invRelationListTmp.add(staItem);
						}
					}
				}
				// 物料找不到工位的时候，这样处理
				if (invRelationListTmp == null || invRelationListTmp.size() < 1) {
//				if (invRelationFullListTmp == null || invRelationFullListTmp.size() < 1) {
					invRelationListTmp = SupplyDataUtils.findInvRelationNoRtList(irWhere);
					if (invRelationListTmp == null || invRelationListTmp.size() < 1) {
						InvBasDoc ibdWhereErr = new InvBasDoc();
						ibdWhereErr.setId(invItemTmp);
						ibdWhereErr.setDelFlag(InvBasDoc.DEL_FLAG_NORMAL);
						InvBasDoc ibdInfoErr = SupplyDataUtils.findInvBasDocInfo(ibdWhereErr);
						StringBuffer strErr = new StringBuffer();
						strErr.append(ibdInfoErr.getInvname());
						strErr.append("(");
						strErr.append(ibdInfoErr.getInvcode());
						strErr.append(")");
						strErr.append("在工艺中未找到工位，并且系统中的工位也未匹配！");
						this.errmsg = strErr.toString();
						return b;
					}
				}
//				else{
//					if (invRelationListTmp == null || invRelationListTmp.size() < 1) {
//						InvBasDoc ibdWhereErr = new InvBasDoc();
//						ibdWhereErr.setId(invItemTmp);
//						ibdWhereErr.setDelFlag(InvBasDoc.DEL_FLAG_NORMAL);
//						InvBasDoc ibdInfoErr = SupplyDataUtils.findInvBasDocInfo(ibdWhereErr);
//						StringBuffer strErr = new StringBuffer();
//						strErr.append(ibdInfoErr.getInvname());
//						strErr.append("(");
//						strErr.append(ibdInfoErr.getInvcode());
//						strErr.append(")");
//						strErr.append("工艺中的工位未在系统中匹配！");
//						this.errmsg = strErr.toString();
//						return b;
//					}
//				}
				
				List<String> wybs = pkInvMapTmp.get(invItemTmp);
				for (InvRelation staItem : invRelationListTmp) {
					staItem.getSmf().setPkCwarehouseid(storId);
					staItem.setPkInvbasdoc(invItemTmp);
					if (staItem.getOhn() == null) {
						OnhandNum ohnTmp = new OnhandNum();
						staItem.setOhn(ohnTmp);
					}

					for (String dhbidItem : wybs) {
						StringBuffer strbuff = new StringBuffer();
						strbuff.append(dhbidItem);
						String str = strbuff.toString();
						strbuff.append(",");
						strbuff.append(staItem.getSmf().getPkWkcenter());
						String hasStr = strbuff.toString();
						
						if (!hasInvMap.containsKey(hasStr)) {
							EnWarehouseZ ewzFatherTmp = tmpMap.get(str);
							if (ewzFatherTmp == null) {
								return b;
							}
							EnWarehouseZ ewzTmp = (EnWarehouseZ) ewzFatherTmp.clone();
							StringBuffer idTmp = new StringBuffer();
							idTmp.append(staItem.getSmf().getPkWkcenter());
							idTmp.append(dhbidItem);
							ewzTmp.setId(idTmp.toString());
							ewzTmp.setCinvbasid(staItem.getPkInvbasdoc());
							ewzTmp.setNinnum(0.0);
							ewzTmp.setNaccuminnum(0.0);
							ewzTmp.setNaccumactinnum(0.0);
							ewzTmp.setNmoney(0.0);
							ewzTmp.setShelvesflag(ConstantUtils.EnWarehouseZ.SHELVES_FLAG_NOT);
							ewzTmp.setPkStokeepid(staItem.getSmf().getPkStorekeeper());
							ewzTmp.setPkStationid(staItem.getSmf().getPkWkcenter());
							ewzTmp.setPkCspaceid(staItem.getOhn().getPkGoodid());
							ewzList.add(ewzTmp);
						}
					}
				}
				
				Map<String,List<InvRelation>> invRelationMapTmp = new HashMap<String,List<InvRelation>>();
				StringBuffer strTmp = new StringBuffer();
				strTmp.append(jmfItem);
				strTmp.append(storId);
				strTmp.append(invItemTmp);
				invRelationMapTmp.put(strTmp.toString(), invRelationListTmp);
				invRelationMap.putAll(invRelationMapTmp);		
			}
		}
		//数据合法性检验与封装
		for (EnWarehouseZ ewzItem : ewzList) {
			for (EnWarehouseZ formItem : l) {
				//ID相同
				if (ewzItem.getId().equals(formItem.getId())) {			
					if (ewzItem.getBatchcodenum() == null || ewzItem.getBatchcodenum().equals("")) {
						StringBuffer idTmp = new StringBuffer();
						idTmp.append(ewzItem.getPkStationid());
						idTmp.append(ewzItem.getCsourcebillbid());
						if (ewzItem.getId().equals(idTmp.toString())) {
							ewzItem.setId(null);
							ewzItem.setNinnum(formItem.getNinnum());
							ewzItem.preInsert();
							ewzAddList.add(ewzItem);
							continue;
						}else{
							ewzItem.setNinnum(formItem.getNinnum());
						}
					}else{
						//序列号管理的换工位和货位、上架人员
						if (formItem.getPkStationid() == null || formItem.getPkStationid().equals("")) {
							return b;
						}
						StringBuffer strTmp = new StringBuffer();
						strTmp.append(ewzItem.getCprojectid());
						strTmp.append(ewInfo.getCwarehouseid());
						strTmp.append(ewzItem.getCinvbasid());
						String invRelationKey = strTmp.toString();
						List<InvRelation> invRelationList = invRelationMap.get(invRelationKey);
						//判断该工位是否存在
						boolean hasStaFlag = false;
						for (InvRelation staItem : invRelationList) {
							if (staItem.getSmf().getPkWkcenter().equals(formItem.getPkStationid())) {
								ewzItem.setPkStationid(staItem.getSmf().getPkWkcenter());
								ewzItem.setPkStokeepid(staItem.getSmf().getPkStorekeeper());
								if (ewzItem.getPkStokeepid() == null || ewzItem.getPkStokeepid().equals("")) {
									this.errmsg = "工位未指定库管员！";
									return b;
								}
								ewzItem.setPkCspaceid(staItem.getOhn().getPkGoodid());
								hasStaFlag = true;
								break;
							}
						}
						if (!hasStaFlag) {
							this.errmsg = "不存在的工位！";
							return b;
						}
					}
					if (ewzItem.getNinnum() > 0) {
						ewzItem.setShelvesflag(ConstantUtils.EnWarehouseZ.SHELVES_FLAG_NOT);
					}else{
						ewzItem.setShelvesflag(ConstantUtils.EnWarehouseZ.SHELVES_FLAG);
					}
					ewzItem.preUpdate();
					ewzSaveList.add(ewzItem);
					break;
				}
			}
		}
		//强验证
		if (l.size() != (ewzSaveList.size() + ewzAddList.size())) {
			return b;
		}
		
		// 更新子表
		for (EnWarehouseZ saveItem : ewzSaveList) {
			enWarehouseDao.updateChild(saveItem);
		}
		// 插入子表
		for (EnWarehouseZ addItem : ewzAddList) {
			enWarehouseDao.insertChild(addItem);
		}
		b = true;
		return b;
	}
	
	/**
	 * 确认入库单（取消操作）
	 * @author maliang
	 * @version 2016-02-29
	 */
	public boolean confirmSaveCancel(EnWarehouse entity){
		boolean b = false;
		//查询当前用户
		User userCur = UserUtils.getUser();
		if (userCur == null) {
			return b;
		}
		if (entity == null) {
			return b;
		}
		if (entity.getId() == null || entity.getId().equals("")) {
			return b;
		}
		
		//主表查询条件
		EnWarehouse ewWhere = new EnWarehouse();
		ewWhere.setId(entity.getId());
		//TODO 条件不全(单据可修改和不可修改状态区分)
		ewWhere.setCoperatorid(userCur.getId());
		ewWhere.setFbillflag(ConstantUtils.EnWarehouse.ORDER_CONFIRM);
		ewWhere.setDelFlag(EnWarehouse.DEL_FLAG_NORMAL);
		//主表数据
		EnWarehouse ewInfo = enWarehouseDao.findInfo(ewWhere);
		if (ewInfo == null) {
			return b;
		}
		//更新入库单状态
		ewInfo.preUpdate();
		ewInfo.setFbillflag(ConstantUtils.EnWarehouse.ORDER_MAKE);
		enWarehouseDao.update(ewInfo);
		b = true;
		return b;
	}
	
	/**
	 * 确认入库单
	 * @author maliang
	 * @version 2015-10-27
	 */
	@Transactional(readOnly = false)
	public boolean confirmSave(EnWarehouse entity) {
		boolean b = false;
		//查询当前用户
		User userCur = UserUtils.getUser();
		if (userCur == null) {
			return b;
		}
		if (entity == null) {
			return b;
		}
		if (entity.getId() == null || entity.getId().equals("")) {
			return b;
		}
		
		//主表查询条件
		EnWarehouse ewWhere = new EnWarehouse();
		ewWhere.setId(entity.getId());
		//TODO 条件不全(单据可修改和不可修改状态区分)
		ewWhere.setCoperatorid(userCur.getId());
		ewWhere.setFbillflag(ConstantUtils.EnWarehouse.ORDER_MAKE);
		ewWhere.setDelFlag(EnWarehouse.DEL_FLAG_NORMAL);
		//主表数据
		EnWarehouse ewInfo = enWarehouseDao.findInfo(ewWhere);
		if (ewInfo == null) {
			return b;
		}
		EnWarehouseZ ewzWhere = new EnWarehouseZ();
		ewzWhere.setPkEwhid(ewInfo.getId());
		ewzWhere.setDelFlag(EnWarehouseZ.DEL_FLAG_NORMAL);
		List<EnWarehouseZ> ewzList = enWarehouseDao.findChildList(ewzWhere);
		//库管员
		List<String> kgIdList = new ArrayList<String>();
		for (EnWarehouseZ ewzItem : ewzList) {
			if (ewzItem.getNinnum() > 0) {
				if (ewzItem.getPkStokeepid() == null || ewzItem.getPkStokeepid().equals("")) {
					this.errmsg = "数据异常，未指定库管员！";
					return b;
				}
				if (!kgIdList.contains(ewzItem.getPkStokeepid())) {
					kgIdList.add(ewzItem.getPkStokeepid());
				}
			}
		}
		
		//更新入库单状态
		ewInfo.preUpdate();
		ewInfo.setFbillflag(ConstantUtils.EnWarehouse.ORDER_CONFIRM);
		enWarehouseDao.update(ewInfo);
		
		//发送通知
		for (String strItem : kgIdList) {
			User uTmp = new User();
			uTmp.setId(strItem);
			List<User> acceptUserListTmp = new ArrayList<User>();
			acceptUserListTmp.add(uTmp);
			StringBuffer notifyTitle = new StringBuffer();
			notifyTitle.append("【上架通知】入库单 ");
			notifyTitle.append(ewInfo.getVbillcode());
			StringBuffer notifyContent = new StringBuffer();
			notifyContent.append("请联系入库管理员【");
			notifyContent.append(userCur.getName());
			notifyContent.append("】领取入库单。");
			notifyContent.append("单据号为【");
			notifyContent.append(ewInfo.getVbillcode());
			notifyContent.append("】");
			NotifyUtils.notify(notifyTitle.toString(), notifyContent.toString(), false, acceptUserListTmp, true, "2");
		}
		b = true;
		return b;
	}
	
	/**
	 * 删除单据
	 * @author maliang
	 * @version 2016-3-7
	 * @throws Exception 
	 */
//	@Transactional(readOnly = false, rollbackFor = { Exception.class })
//	public boolean delete(EnWarehouse entity) throws Exception {
//		boolean b = false;
//		//查询当前用户
//		User userCur = UserUtils.getUser();
//		if (userCur == null) {
//			return b;
//		}
//		if (entity == null) {
//			return b;
//		}
//		//主表查询条件
//		EnWarehouse ewWhere = new EnWarehouse();
//		ewWhere.setId(entity.getId());
//		//TODO 单据查看权限
//		ewWhere.setDelFlag(EnWarehouse.DEL_FLAG_NORMAL);
//		//主表数据
//		EnWarehouse ewInfo = enWarehouseDao.findInfo(ewWhere);
//		if (ewInfo == null) {
//			this.errmsg = "单据不存在！";
//			return b;
//		}
//		if (!ConstantUtils.EnWarehouse.ORDER_MAKE.equals(ewInfo.getFbillflag())) {
//			this.errmsg = "该单据不允许此操作！";
//			return b;
//		}
//		EnWarehouseZ ewzWhere = new EnWarehouseZ();
//		ewzWhere.setPkEwhid(ewInfo.getId());
//		ewzWhere.setDelFlag(EnWarehouseZ.DEL_FLAG_NORMAL);
//		List<EnWarehouseZ> ewzList = enWarehouseDao.findChildList(ewzWhere);
//		
//		Map<String, EnWarehouseZ> ewzMap = new HashMap<String, EnWarehouseZ>();
//		for (EnWarehouseZ ewzItem : ewzList) {
//			if (ewzItem.getNaccuminnum() == null) {
//				ewzItem.setNaccuminnum(0.0);
//			}
//			if (ewzItem.getNaccuminnum() > 0) {
//				this.errmsg = "该单据不允许此操作！";
//				return b;
//			}
//			String ewzMapKeyTmp = ewzItem.getCsourcebillbid();
//			if (!ewzMap.containsKey(ewzMapKeyTmp)) {
//				ewzMap.put(ewzMapKeyTmp, ewzItem);
//			}
//		}
//		
//		if (ewzList == null || ewzList.size() < 1) {
//			this.errmsg = "单据异常！";
//			return b;
//		}
//		
//		ArriveOrder aoWhere = new ArriveOrder();
//		aoWhere.setId(ewzList.get(0).getCsourcebillhid());
//		aoWhere.setDelFlag(ArriveOrder.DEL_FLAG_NORMAL);
//		ArriveOrder aoInfo = arriveOrderDao.findInfo(aoWhere);
//		if (aoInfo == null) {
//			this.errmsg = "上游单据不存在！";
//			return b;
//		}
//		ArriveOrderB aobWhere = new ArriveOrderB();
//		aobWhere.setCarriveorderid(aoInfo.getId());
//		aobWhere.setDelFlag(ArriveOrderB.DEL_FLAG_NORMAL);
//		List<ArriveOrderB> aobList = arriveOrderDao.findChildList(aobWhere);
//		if (aobList == null || aobList.size() < 1) {
//			this.errmsg = "上游单据异常！";
//			return b;
//		}
//		
//		BuyOrder boWhere = new BuyOrder();
//		boWhere.setId(ewzList.get(0).getCfirstbillhid());
//		boWhere.setDelFlag(BuyOrder.DEL_FLAG_NORMAL);
//		BuyOrder boInfo = buyOrderDao.findInfo(boWhere);
//		if (boInfo == null) {
//			this.errmsg = "上游采购单据不存在！";
//			return b;
//		}
//		
//		BuyOrderB bobWhere = new BuyOrderB();
//		bobWhere.setCorderid(boInfo.getId());
//		bobWhere.setDelFlag(BuyOrderB.DEL_FLAG_NORMAL);
//		List<BuyOrderB> bobList = buyOrderDao.findChildList(bobWhere);
//		if (bobList == null || bobList.size() < 1) {
//			this.errmsg = "上游采购单据异常！";
//			return b;
//		}
//		
//		//回写采购单和到货单（自己回写，ERP不回写）
//		List<BuyOrderB> bobSaveList = new ArrayList<BuyOrderB>();
//		List<ArriveOrderB> aobSaveList = new ArrayList<ArriveOrderB>();
//		for (String keyItem : ewzMap.keySet()) {
//			EnWarehouseZ ewzItem = ewzMap.get(keyItem);
//			for (BuyOrderB bobItem : bobList) {
//				if (ewzItem.getCfirstbillbid().equals(bobItem.getId())) {
//					if (bobItem.getNaccumstorenum() == null) {
//						bobItem.setNaccumstorenum(0.0);
//					}
//					bobItem.setNaccumstorenum(bobItem.getNaccumstorenum() - ewzItem.getNinsumnum());
//					bobItem.preUpdate();
//					bobSaveList.add(bobItem);
//					break;
//				}
//			}
//			for (ArriveOrderB aobItem : aobList) {
//				if (ewzItem.getCsourcebillbid().equals(aobItem.getId())) {
//					if (aobItem.getNaccumwarehousenum() == null) {
//						aobItem.setNaccumwarehousenum(0.0);
//					}
//					aobItem.setNaccumwarehousenum(aobItem.getNaccumwarehousenum() - ewzItem.getNinsumnum());
//					aobItem.setNowNinnum(aobItem.getNowNinnum() + ewzItem.getNinsumnum());
//					aobItem.preUpdate();
//					aobSaveList.add(aobItem);
//					break;
//				}
//			}
//			
//		}
//		
//		for (BuyOrderB bobItem : bobSaveList) {
//			buyOrderDao.updateChild(bobItem);
//		}
//		
//		aoInfo.setHclosed(ConstantUtils.ArriveOrder.CLOSED_NOT);
//		arriveOrderDao.update(aoInfo);
//		for (ArriveOrderB aobItem : aobSaveList) {
//			arriveOrderDao.updateChild(aobItem);
//		}
//		
//		enWarehouseDao.delete(ewInfo);
//		//子表数据
//		for (EnWarehouseZ ewzItem : ewzList) {
//			enWarehouseDao.deleteChild(ewzItem);
//		}
//		
//		//同步到ERP实体对象
//		EnWarehouseERP ewERP = new EnWarehouseERP();
//		ewERP.entity2EntityERP4delete(ewInfo, ewzList);
//		WebServiceParam wsp = new WebServiceParam();
//		wsp.setSourceSystem("L2M");
//		wsp.setAction("Delete");
//		wsp.setXml(JaxbObjectAndXmlUtil.object2Xml(ewERP));
////		wsp.setUrl("http://172.20.45.3/uapws/service/NCERPWSserviceeMain");
//		wsp.setNamespace("http://importaction.impl.ui.nc/NCERPWSserviceMain");
//		wsp.setMethod("saveNCBILL");
//		wsp.setBistype("45");
//		try {
//			SendWebService.doWebService(wsp);
//		} catch (Exception e) {
//			e.printStackTrace();
//			b = false;
//			this.errmsg  = "同步ERP异常！";
//			throw new Exception();
//		}
//		
//		b = true;
//		return b;
//	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
}