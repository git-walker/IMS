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
import java.util.Date;
import java.util.List;

/**
 * 到货Service
 * @author maliang
 * @version 2015-10-17
 */
@Service
@Transactional(readOnly = true)
public class ArriveOrderService extends BaseService {

	@Autowired
	private EnWarehouseDao enWarehouseDao;
	@Autowired
	private ArriveOrderDao arriveOrderDao;
	@Autowired
	private BuyOrderDao buyOrderDao;
	
	//错误信息
	private String errmsg;
	
	public static final int DATA4OTHER = 0;//其他
	public static final int DATA4ZJ = 1;//质检
	public static final int DATA4BG = 2;//保供
	public static final int DATA4RK = 3;//入库管理员
	public static final int DATA4RKSTEP1 = 4;//入库管理员(第一步)
	public static final int DATA4RKSTEP2 = 5;//入库管理员(第二步)
	public static final int DATA4SHOW = 6;//展示页面
	
	/**
	 * 分页查询到货单数据列表
	 * @param page 分页对象
	 * @author maliang
	 * @version 2015-10-13
	 * update 2015-10-22
	 */
	public Page<ArriveOrder> findPage(Page<ArriveOrder> page, ArriveOrder entity , int type) {
		//查询当前用户
		User userCur = UserUtils.getUser();
		if (userCur == null) {
			page.setList(null);
			return page;
		}
		if (entity == null) {
			entity = new ArriveOrder();
		}
		ArriveOrder aoWhere = new ArriveOrder();
		if (!(entity.getVarrordercode() == null || "".equals(entity.getVarrordercode()))) {
			aoWhere.setVarrordercode(entity.getVarrordercode());
		}
		switch (type) {
			case DATA4BG:
				aoWhere.setCoperator(userCur.getId());
				break;
			case DATA4RKSTEP1:
				if (!(entity.getReceiveflag() == null || entity.getReceiveflag().equals(""))) {
					aoWhere.setReceiveflag(entity.getReceiveflag());
				}
				aoWhere.setCreceivepsn(userCur.getId());
				break;
			//质检员可看入库管理员确认后的到货单
			//具体到货单分配靠工作流
			case DATA4ZJ:
				aoWhere.setReceiveflag(ConstantUtils.ArriveOrder.RECEIVE);
				if (!(entity.getZjsignState() == null || entity.getZjsignState().equals(""))) {
					aoWhere.setZjsignState(entity.getZjsignState());
				}
				break;
			//保供人员 入库管理员可看全部的到货单
			//具体到货单分配靠工作流
			case DATA4RKSTEP2:
				aoWhere.setZjsignState(ConstantUtils.ArriveOrder.SIGN);
				aoWhere.setHclosed(ConstantUtils.ArriveOrder.CLOSED);
				if (!(entity.getHclosed() == null || "".equals(entity.getHclosed()))) {
					aoWhere.setHclosed(entity.getHclosed());
				}
				aoWhere.setCreceivepsn(userCur.getId());
				break;
			case DATA4SHOW:
				aoWhere.setHclosed(ConstantUtils.ArriveOrder.CLOSED_NOT);
				break;
			default:
				break;
		}
		aoWhere.setDelFlag(ArriveOrder.DEL_FLAG_NORMAL);
		User cg = new User();
		cg.setDelFlag(User.DEL_FLAG_NORMAL);
		aoWhere.setCg(cg);
		User rk = new User();
		rk.setDelFlag(User.DEL_FLAG_NORMAL);
		aoWhere.setRk(rk);
		User zj = new User();
		zj.setDelFlag(User.DEL_FLAG_NORMAL);
		aoWhere.setZj(zj);
		User createBy = new User();
		createBy.setDelFlag(User.DEL_FLAG_NORMAL);
		aoWhere.setCreateBy(createBy);
		aoWhere.setPage(page);
		CuManDoc cmd = new CuManDoc();
		cmd.setDelFlag(CuManDoc.DEL_FLAG_NORMAL);
		aoWhere.setCmd(cmd);
		CuBasDoc cbd = new CuBasDoc();
		cbd.setDelFlag(CuBasDoc.DEL_FLAG_NORMAL);
		aoWhere.setCbd(cbd);
		
		List<ArriveOrder> aoList = arriveOrderDao.findList(aoWhere);
		switch (type) {
			case DATA4SHOW:
				Date nowTimeTmp = new Date();
				for (ArriveOrder aobItem : aoList) {
					aobItem.setEstatus(SupplyDataUtils.getEstatus(aobItem, nowTimeTmp));
				}
				break;
			default:
				break;
		}
		page.setList(aoList);
		return page;
	}
		
	/**
	 * 查询到货单子表数据列表
	 * @author maliang
	 * @version 2015-10-15
	 * update 2015-10-22
	 */
	public List<ArriveOrderB> findChildList(Page<ArriveOrderB> page, ArriveOrderB entity, int type) {
		//查询当前用户
		User userCur = UserUtils.getUser();
		if (userCur == null) {
			return null;
		}
		if (entity == null) {
			return null;
		}
		if (entity.getCarriveorderid() == null || entity.getCarriveorderid().equals("")) {
			return null;
		}
		
		//是否可以看此单下的存货
		ArriveOrder aoWhere = new ArriveOrder();
		//TODO 单据查看权限
		switch (type) {
			case DATA4BG:
				aoWhere.setCoperator(userCur.getId());
				break;
			case DATA4RKSTEP1:
				aoWhere.setCreceivepsn(userCur.getId());
				break;
			case DATA4ZJ:
				aoWhere.setReceiveflag(ConstantUtils.ArriveOrder.RECEIVE);
				break;
			case DATA4RKSTEP2:
				aoWhere.setZjsignState(ConstantUtils.ArriveOrder.SIGN);
				aoWhere.setCreceivepsn(userCur.getId());
				break;
			default:
				break;
		}
		aoWhere.setId(entity.getCarriveorderid());
		aoWhere.setDelFlag(ArriveOrder.DEL_FLAG_NORMAL);
		ArriveOrder aoInfo = arriveOrderDao.findInfo(aoWhere);
		if (aoInfo == null) {
			return null;
		}
		
		ArriveOrderB aobWhere = new ArriveOrderB();
		aobWhere.setCarriveorderid(entity.getCarriveorderid());
		aobWhere.setDelFlag(ArriveOrderB.DEL_FLAG_NORMAL);
		InvBasDoc ibdWhere = new InvBasDoc();
		ibdWhere.setDelFlag(InvBasDoc.DEL_FLAG_NORMAL);
		MeasDoc md = new MeasDoc();
		md.setDelFlag(MeasDoc.DEL_FLAG_NORMAL);
		ibdWhere.setMd(md);
		aobWhere.setIbd(ibdWhere);
		StorDoc sd = new StorDoc();
		sd.setDelFlag(StorDoc.DEL_FLAG_NORMAL);
		aobWhere.setSd(sd);
		aobWhere.setPage(page);
		List<ArriveOrderB> aobList = arriveOrderDao.findChildList(aobWhere);
		
		//TODO 单据查看权限
		boolean bgFlag = false;
		boolean zjFlag = false;
		boolean rkFlag = false;
		//是保供人员
		if (type == DATA4BG) {
			bgFlag = true;
		}
		//质检签字或者是质检
		if (type == DATA4ZJ || aoInfo.getZjsignState().equals(ConstantUtils.ArriveOrder.SIGN)) {
			zjFlag = true;
		}
		//是入库管理员
		if (type == DATA4RKSTEP2 || aoInfo.getRksignState().equals(ConstantUtils.ArriveOrder.SIGN)) {
			rkFlag = true;
		}
		//数据过滤
		for (ArriveOrderB aobItem : aobList) {
			if (!bgFlag) {
				aobItem.setOverstocknum(null);
				aobItem.setOverstockflag(null);
			}
			if (!zjFlag) {
				aobItem.setNelignum(null);
				aobItem.setNnotelignum(null);
			}
			if (!rkFlag) {
				aobItem.setNowNinnum(null);
				aobItem.setNaccumwarehousenum(null);
			}
		}
		return aobList;
	}
	
	/**
	 * 生成到货订单
	 * @author maliang
	 * @version 2015-10-13
	 * update 2015-10-22
	 * @throws Exception 
	 */
//	@Transactional(readOnly = false, rollbackFor = { Exception.class })
//	public boolean addData(ArriveOrder entity) throws Exception {
//		boolean b = false;
//		//查询当前用户
//		User userCur = UserUtils.getUser();
//		if (userCur == null) {
//			return b;
//		}
//		if (entity == null) {
//			return b;
//		}
//		// 取子列表
//		List<ArriveOrderB> l = entity.getAobList();
//		String buyOrderId = null;// 来源订单号
//		// 获取来源订单号
//		for (ArriveOrderB item : l) {
//			buyOrderId = item.getCsourcebillid();
//			break;
//		}
//		if (buyOrderId == null || buyOrderId.equals("")) {
//			return b;
//		}
//		// 查询采购订单
//		//采购单主表查询条件
//		BuyOrder boWhere = new BuyOrder();
//		boWhere.setId(buyOrderId);
//		//TODO 单据操作权限
////		boWhere.setCemployeeid(userCur.getId());
//		boWhere.setForderstatus(ConstantUtils.BuyOrder.ORDER_NORMAL);
//		boWhere.setDelFlag(BuyOrder.DEL_FLAG_NORMAL);
//		//采购单主表数据
//		BuyOrder boInfo = buyOrderDao.findInfo(boWhere);
//		if (boInfo == null) {
//			return b;
//		}
//		//采购单子表查询条件
//		BuyOrderB bobWhere = new BuyOrderB();
//		bobWhere.setCorderid(boInfo.getId());
//		bobWhere.setIisactive(ConstantUtils.BuyOrderB.ORDER_NORMAL);
//		bobWhere.setDelFlag(BuyOrderB.DEL_FLAG_NORMAL);
//		InvBasDoc ibdWhere = new InvBasDoc();
//		ibdWhere.setDelFlag(InvBasDoc.DEL_FLAG_NORMAL);
//		MeasDoc md = new MeasDoc();
//		md.setDelFlag(MeasDoc.DEL_FLAG_NORMAL);
//		ibdWhere.setMd(md);
//		bobWhere.setIbd(ibdWhere);
//		InvManDoc imdWhere = new InvManDoc();
//		imdWhere.setDelFlag(InvManDoc.DEL_FLAG_NORMAL);
//		bobWhere.setImd(imdWhere);
//		
//		List<BuyOrderB> bobList = buyOrderDao.findChildList(bobWhere);//采购单子表数据
//		if (bobList == null || bobList.size() < 1) {
//			return b;
//		}
//		//强检查
//		if (bobList.size() < l.size()) {
//			return b;
//		}
//		Map<String,List<String>> pkInvMap = new HashMap<String,List<String>>();//项目仓库下的物料
//		Map<String,ArriveOrder> aoAddMap = new HashMap<String,ArriveOrder>();//插入的到货单主表
//		Map<String,List<ArriveOrderB>> aobListAddMap = new HashMap<String,List<ArriveOrderB>>();//插入的到货单子表
//		List<BuyOrderB> bobListUpdate = new ArrayList<BuyOrderB>();//需要更新的采购单
//		//采购单数据校验，获取库存组织，单据物料
//		for (ArriveOrderB aobItem : l) {
//			for (BuyOrderB bobItem : bobList) {
//				//采购单ID和到货单来源单据行ID
//				if (bobItem.getId().equals(aobItem.getCsourcebillrowid())) {
//					if (bobItem.getIbd() == null) {
//						this.errmsg = "请完善存货基础档案！";
//						return false;
//					}
//					if (bobItem.getIbd().getMd() == null) {
//						this.errmsg = "请完善计量档案！";
//						return false;
//					}
//					if (bobItem.getImd() == null) {
//						this.errmsg = "请完善存货管理档案！";
//						return false;
//					}
//					if (bobItem.getNordernum() == null) {
//						bobItem.setNordernum(0.0);
//					}
//					if (bobItem.getNaccumarrvnum() == null) {
//						bobItem.setNaccumarrvnum(0.0);
//					}
//					if (bobItem.getNaccumstorenum() == null) {
//						bobItem.setNaccumstorenum(0.0);
//					}
//					//应收数量小于0，或者大于默认应收数量（有可能大于）阈值为0
//					if (aobItem.getNarrvnum() == null || aobItem.getNarrvnum() < 0 || aobItem.getNarrvnum() > (bobItem.getNordernum() - bobItem.getNaccumstorenum() + ConstantUtils.ArriveOrderB.ALLOW_OVER)) {
//						this.errmsg = "应收数量异常！";
//						return false;
//					}
//					if (aobItem.getNprice() == null || aobItem.getNprice() < 0) {
//						this.errmsg = "单价异常！";
//						return false;
//					}
//					//忽略应收数量为0的数据
//					if (aobItem.getNarrvnum() == 0) {
//						break;
//					}
//					String storKey = bobItem.getCwarehouseid();
//					if (!pkInvMap.containsKey(storKey)) {
//						pkInvMap.put(storKey, new ArrayList<String>());
//					}
//					pkInvMap.get(storKey).add(bobItem.getCbaseid());
//					if (!aoAddMap.containsKey(storKey)) {
//						// 要插入的到货单主表对象
//						ArriveOrder aoAdd = new ArriveOrder();
//						aoAdd.preInsert();
//						aoAdd.setVarrordercode(CodeRuleUtils.getBusSerialCode(CodeRuleUtils.ARRIVING_LISTS_PREFIX));//到货单号
//						aoAdd.setDreceivedate(aoAdd.getCreateDate());//到货日期
//						aoAdd.setCbiztype(boInfo.getCbiztype());//业务类型ID
//						aoAdd.setCvendormangid(boInfo.getCvendormangid());//供应商管理ID
//						aoAdd.setCemployeeid(boInfo.getCemployeeid());//采购员ID
//						aoAdd.setCdeptid(boInfo.getCdeptid());
//						aoAdd.setCtransmodeid(boInfo.getCtransmodeid());
//						aoAdd.setCstoreorganization(bobItem.getPkArrvstoorg());//库存组织ID(在子表中)
//						aoAdd.setBisback(boInfo.getBreturn());//是否退货
//						aoAdd.setVbackreasonh(null);//退货理由
//						aoAdd.setVmemo(null);//备注
//						aoAdd.setCoperator(aoAdd.getCreateBy().getId());//制单人ID
//						aoAdd.setTmaketime(aoAdd.getCreateDate());//制单时间
//						aoAdd.setCauditpsn(aoAdd.getCreateBy().getId());//审批人ID
//						aoAdd.setTaudittime(aoAdd.getCreateDate());//审批时间
//						aoAdd.setPkCorp(boInfo.getPkCorp());//公司主键
//						aoAdd.setReceiveflag(ConstantUtils.ArriveOrder.RECEIVE_NOT);
//						aoAdd.setRksignState(ConstantUtils.ArriveOrder.SIGN_NOT);//入库管理员签字状态
//						//TODO 后期需要完善
//						aoAdd.setPkZjmanage("1006A0100000000JRO08");//质检员
//						aoAdd.setZjsignState(ConstantUtils.ArriveOrder.SIGN_NOT);//质检员签字状态
//						aoAdd.setHclosed(ConstantUtils.ArriveOrder.CLOSED_NOT);//是否关闭
//						aoAddMap.put(storKey, aoAdd);
//					}
//					if (!aobListAddMap.containsKey(storKey)) {
//						aobListAddMap.put(storKey, new ArrayList<ArriveOrderB>());
//					}
//					ArriveOrderB aobAdd = new ArriveOrderB();
//					aobAdd.preInsert();
//					aobAdd.setCarriveorderid(aoAddMap.get(storKey).getId());// 到货单ID
//					aobAdd.setCbaseid(aobItem.getCbaseid());//存货基础ID
//					aobAdd.setCmangid(bobItem.getImd().getId());//存货管理档案ID
//					aobAdd.setBlargess(bobItem.getBlargess());//是否赠品（可在到货单修改）
//					aobAdd.setBlargessuprow(bobItem.getBlargess());//来源订单行是否赠品
//					aobAdd.setNarrvnum(aobItem.getNarrvnum());//应到数量
//					//提前插入合格数量，仅供质检人员第一次修改
//					aobAdd.setNelignum(aobItem.getNarrvnum());//累计合格数量
//					aobAdd.setNnotelignum(0.0);//累计不合格数量
//					aobAdd.setCwarehouseid(bobItem.getCwarehouseid());//仓库ID
//					aobAdd.setVproducenum(bobItem.getVproducenum());
//					aobAdd.setCsourcebillid(boInfo.getId());// 来源单据ID
//					aobAdd.setCsourcebillrowid(bobItem.getId());
//					aobAdd.setCprojectid(bobItem.getCprojectid());// 项目ID
//					aobAdd.setCrowno(bobItem.getCrowno());
//					aobAdd.setNaccumwarehousenum(0.0);//累计入库数量
//					aobAdd.setNprice(aobItem.getNprice());//本币单价
//					aobAdd.setNmoney(aobAdd.getNarrvnum() * aobAdd.getNprice());//本币金额
//					aobAdd.setInvstatus(ConstantUtils.ArriveOrderB.INV_NORMAL);// 物料状态
//					aobAdd.setOverstocknum(0.0);// 超库存数量
//					aobAdd.setOverstockflag(ConstantUtils.ArriveOrderB.NOT_OVER_STOCK);
//					aobAdd.setNowNinnum(0.0);// 本次实入数量
//					if (bobItem.getImd().getInstancyflag().equals(ConstantUtils.InvManDoc.INV_INSTANCY)) {
//						aobAdd.setInvstatus(ConstantUtils.InvManDoc.INV_INSTANCY);
//					}
//					aobListAddMap.get(storKey).add(aobAdd);
//					
//					//回写采购单累计到货数量
//					BuyOrderB bobUpdate = new BuyOrderB();
//					bobUpdate.preUpdate();
//					bobUpdate.setId(bobItem.getId());
//					bobUpdate.setNaccumarrvnum(bobItem.getNaccumarrvnum() + aobItem.getNarrvnum());
//					bobUpdate.setNaccumstorenum(bobItem.getNaccumstorenum());
//					bobUpdate.setIisactive(bobItem.getIisactive());
//					if (bobUpdate.getNaccumarrvnum() >= bobItem.getNordernum()) {
//						bobUpdate.setIisactive(ConstantUtils.BuyOrderB.ORDER_CLOSED);
//					}
//					bobListUpdate.add(bobUpdate);
//				}
//			}
//		}
//		//查询库存量、项目仓库所属库区、入库管理员、待通知用户
//		Map<String,List<OnhandNum>> onhandNumMap = new HashMap<String,List<OnhandNum>>();
//		Map<String,User> acceptUserMap = new HashMap<String,User>();
//		for (String item : pkInvMap.keySet()) {
//			//查询库存量
//			List<String> pkInvbasdocList = pkInvMap.get(item);
//			OnhandNum onWhere = new OnhandNum();
//			onWhere.setPkInvbasdocList(pkInvbasdocList);
//			onWhere.setPkStordoc(item);
//			onWhere.setDelFlag(OnhandNum.DEL_FLAG_NORMAL);
//			Map<String,List<OnhandNum>> onhandNumMapTmp = SupplyDataUtils.findOnhandNumMap("stor_inv", onWhere);
//			//填补未知数据
//			for (String invItem : pkInvbasdocList) {
//				String mapKey = item + invItem;
//				if (!onhandNumMapTmp.containsKey(mapKey)) {
//					OnhandNum onTmp = new OnhandNum();
//					onTmp.setCurrentamount(0.0);
//					onTmp.setMaxstornum(ConstantUtils.MAX_STOR_NUM);
//					List<OnhandNum> onListTmp = new ArrayList<OnhandNum>();
//					onListTmp.add(onTmp);
//					onhandNumMapTmp.put(mapKey, onListTmp);
//				}
//			}
//			onhandNumMap.putAll(onhandNumMapTmp);
//			//项目仓库所属库区
//			StorDoc sdWhere = new StorDoc();
//			sdWhere.setId(item);
//			sdWhere.setDelFlag(StorDoc.DEL_FLAG_NORMAL);
//			StorDoc sdInfo = SupplyDataUtils.findStorDocInfo(sdWhere);
//			if (sdInfo == null) {
//				this.errmsg = "收货仓库不存在！";
//				return false;
//			}
//			if (sdInfo.getPkReservoirid() == null || sdInfo.getPkReservoirid().equals("")) {
//				StringBuffer strTmp = new StringBuffer();
//				strTmp.append("收货仓库“");
//				strTmp.append(sdInfo.getStorname());
//				strTmp.append("”未指定库区！");
//				this.errmsg = strTmp.toString();
//				return false;
//			}
//			//入库管理员
//			User uWhere = new User();
//			Office officeWhere = new Office();
//			officeWhere.setId(sdInfo.getPkReservoirid());
//			uWhere.setOffice(officeWhere);
//			Role rWhere = new Role();
//			rWhere.setEnname(ConstantUtils.Role.ROLE_RK);
//			uWhere.setRole(rWhere);
//			List<User> userList = SupplyDataUtils.findUserList(uWhere);
//			User userInfo = null;
//			for (User uItem : userList) {
//				userInfo = uItem;
//				break;
//			}
//			if (userInfo == null) {
//				StringBuffer strTmp = new StringBuffer();
//				strTmp.append("收货仓库“");
//				strTmp.append(sdInfo.getStorname());
//				strTmp.append("”未指定入库管理员！");
//				this.errmsg = strTmp.toString();
//				return false;
//			}
//			acceptUserMap.put(userInfo.getId(), userInfo);
//			aoAddMap.get(item).setCreceivepsn(userInfo.getId());//收货人ID
//			aoAddMap.get(item).setPkRkmanage(userInfo.getId());//入库管理员
//		}
//			
//		//业务填充
//		for (String item : aobListAddMap.keySet()) {
//			List<ArriveOrderB> aobListTmp = aobListAddMap.get(item);
//			for (ArriveOrderB aobItem : aobListTmp) {
//				//物料状态判断
//				//是否超库存入库
//				String keyTmp = aobItem.getCwarehouseid() + aobItem.getCbaseid();
//				List<OnhandNum> onhandNumList = onhandNumMap.get(keyTmp);
//				double maxstornumSum = 0;//最高库存
//				double currentamountSum = 0;// 现存量
//				for (OnhandNum itemTmp : onhandNumList) {
//					if (itemTmp.getMaxstornum() == null) {
//						itemTmp.setMaxstornum(ConstantUtils.MAX_STOR_NUM);
//					}
//					if (itemTmp.getCurrentamount() == null) {
//						itemTmp.setCurrentamount(0.0);
//					}
//					maxstornumSum += itemTmp.getMaxstornum();
//					currentamountSum += itemTmp.getCurrentamount();
//				}
//				double overstocknum = aobItem.getNarrvnum() + currentamountSum - maxstornumSum;
//				if (overstocknum > 0) {
//					//超库存
//					aobItem.setOverstockflag(ConstantUtils.ArriveOrderB.OVER_STOCK);
//					aobItem.setOverstocknum(overstocknum);
//				}
//			}
//		}
//		
//		if (aoAddMap.size() < 1) {
//			this.errmsg = "没有可用物料！";
//			return false;
//		}
//		
//		//更新采购订单
//		//采购订单子表累加相应数量，达到确认数量后，子单完成；全部子单完成，主单完成
//		//自己回写采购单数量（现在由ERP回写，如果同时自己和ERP都回写，会失败）
////		for (BuyOrderB bobItem : bobListUpdate) {
////			buyOrderDao.updateChild(bobItem);
////		}
////		//采购订单数据（包含子表数据）,查询条件同上（现在不需要了）
////		BuyOrderB bobWhereNew = new BuyOrderB();
////		bobWhereNew.setCorderid(boInfo.getId());
////		bobWhereNew.setIisactive(ConstantUtils.BuyOrderB.ORDER_NORMAL);
////		bobWhereNew.setDelFlag(BuyOrderB.DEL_FLAG_NORMAL);
////		
////		List<BuyOrderB> bobNewList = buyOrderDao.findChildList(bobWhereNew);//采购单子表数据
////		if (bobNewList == null || bobNewList.size() < 1) {
////			boInfo.preUpdate();
////			boInfo.setForderstatus(ConstantUtils.BuyOrder.ORDER_CLOSED);
////			//更新采购订单主表
////			buyOrderDao.update(boInfo);
////		}
//		
//		for (String item : aoAddMap.keySet()) {
//			// 插入到货单主表
//			arriveOrderDao.insert(aoAddMap.get(item));
//			List<ArriveOrderB> aobListTmp = aobListAddMap.get(item);
//			for (ArriveOrderB addItem : aobListTmp) {
//				// 插入到货单子表
//				arriveOrderDao.insertChild(addItem);
//			}
//		}
//		
//		// Start a process instance
////	    String procId = runtimeService.startProcessInstanceByKey("financialReport").getId();
//		for (String item : aoAddMap.keySet()) {
//			ArriveOrder aoTmp = aoAddMap.get(item);
//			if (aoTmp == null) {
//				continue;
//			}
//			StringBuffer notifyTitle = new StringBuffer();
//			notifyTitle.append("【来料通知】到货单 ");
//			notifyTitle.append(aoTmp.getVarrordercode());
//			StringBuffer notifyContent = new StringBuffer();
//			notifyContent.append("请与保供人员【");
//			notifyContent.append(userCur.getName());
//			notifyContent.append("】物料交接。");
//			notifyContent.append("单据号为【");
//			notifyContent.append(aoTmp.getVarrordercode());
//			notifyContent.append("】");
//			List<User> acceptUserListTmp = new ArrayList<User>();
//			acceptUserListTmp.add(acceptUserMap.get(aoTmp.getCreceivepsn()));
//			NotifyUtils.notify(notifyTitle.toString(), notifyContent.toString(), false, acceptUserListTmp, true, "2");
//		}
//		
//		//同步到ERP到货单实体对象
//		for (String item : aoAddMap.keySet()) {
//			List<ArriveOrderB> aobListTmp = aobListAddMap.get(item);
//			
//			ArriveOrderERP aoERP = new ArriveOrderERP();
//			aoERP.entity2EntityERP(aoAddMap.get(item), aobListTmp);
//			List<ArriveOrderBERP> aobERPList = aoERP.getAobList();
//			for (ArriveOrderBERP aobERPItem : aobERPList) {
//				aobERPItem.setNelignum(0.0);
//			}
//			WebServiceParam wsp = new WebServiceParam();
//			wsp.setSourceSystem("L2M");
//			wsp.setAction("Insert");
//			wsp.setXml(JaxbObjectAndXmlUtil.object2Xml(aoERP));
////			wsp.setUrl("http://172.20.45.3/uapws/service/NCERPWSserviceeMain");
//			wsp.setNamespace("http://importaction.impl.ui.nc/NCERPWSserviceMain");
//			wsp.setMethod("saveNCBILL");
//			wsp.setBistype("DH");
//			try {
//				SendWebService.doWebService(wsp);
//			} catch (Exception e) {
//				e.printStackTrace();
//				b = false;
//				this.errmsg  = "同步ERP异常！";
//				throw new Exception();
//			}
//		}
//		
//		b = true;
//		return b;
//	}
	
	/**
	 * 入库管理员确认收货
	 * @author maliang
	 * @version 2015-10-22
	 */
	@Transactional(readOnly = false)
	public boolean saveData4Receive(ArriveOrder entity) {
		boolean b = false;
		//查询当前用户
		User userCur = UserUtils.getUser();
		if (userCur == null) {
			return false;
		}
		if (entity == null) {
			return false;
		}
		if (entity.getId() == null || entity.getId().equals("")) {
			return false;
		}
		//查询条件
		ArriveOrder aoWhere = new ArriveOrder();
		//TODO 单据查看权限
		aoWhere.setId(entity.getId());
		aoWhere.setCreceivepsn(userCur.getId());
		aoWhere.setReceiveflag(ConstantUtils.ArriveOrder.RECEIVE_NOT);
		aoWhere.setDelFlag(ArriveOrder.DEL_FLAG_NORMAL);
		//主表数据
		ArriveOrder aoInfo = arriveOrderDao.findInfo(aoWhere);
		if (aoInfo == null) {
			return false;
		}
		ArriveOrderB aobWhere = new ArriveOrderB();
		aobWhere.setCarriveorderid(aoInfo.getId());
		aobWhere.setDelFlag(ArriveOrderB.DEL_FLAG_NORMAL);
		List<ArriveOrderB> aobList = arriveOrderDao.findChildList(aobWhere);
		boolean isUrgent = false;
		for (ArriveOrderB aobItem : aobList) {
			if (aobItem.getInvstatus() != null && aobItem.getInvstatus().equals(ConstantUtils.InvManDoc.INV_INSTANCY)) {
				isUrgent = true;
			}
		}
		
		//质检员
		List<User> acceptUserList = new ArrayList<User>();
		User userInfo = UserUtils.get(aoInfo.getPkZjmanage());
		if (userInfo == null) {
			this.errmsg = "收货仓库未指定质检员！";
			return false;
		}
		acceptUserList.add(userInfo);
		StringBuffer notifyTitle = new StringBuffer();
		notifyTitle.append("【来料质检】到货单 ");
		notifyTitle.append(aoInfo.getVarrordercode());
		StringBuffer notifyContent = new StringBuffer();
		notifyContent.append("请与保供人员【");
		User bgTmp = UserUtils.getUserById(aoInfo.getCreateBy().getId());
		if (bgTmp == null) {
			bgTmp = new User();
		}
		notifyContent.append(bgTmp.getName());
		notifyContent.append("】及入库管理员【");
		notifyContent.append(userCur.getName());
		notifyContent.append("】进行来料质量检验。");
		notifyContent.append("单据号为【");
		notifyContent.append(aoInfo.getVarrordercode());
		notifyContent.append("】");
		NotifyUtils.notify(notifyTitle.toString(), notifyContent.toString(), isUrgent, acceptUserList, true, "2");
		
		aoInfo.preUpdate();
		aoInfo.setReceiveflag(ConstantUtils.ArriveOrder.RECEIVE);
		aoInfo.setInfQcDate(aoInfo.getUpdateDate());
		arriveOrderDao.update(aoInfo);
		b = true;
		return b;
	}
	
	/**
	 * 入库管理员确认收货（取消操作）
	 * @author maliang
	 * @version 2016-01-21
	 */
	@Transactional(readOnly = false)
	public boolean saveData4ReceiveCancel(ArriveOrder entity) {
		boolean b = false;
		//查询当前用户
		User userCur = UserUtils.getUser();
		if (userCur == null) {
			return false;
		}
		if (entity == null) {
			return false;
		}
		if (entity.getId() == null || entity.getId().equals("")) {
			return false;
		}
		//查询条件
		ArriveOrder aoWhere = new ArriveOrder();
		//TODO 单据查看权限
		aoWhere.setId(entity.getId());
		aoWhere.setCreceivepsn(userCur.getId());
		aoWhere.setReceiveflag(ConstantUtils.ArriveOrder.RECEIVE);
		aoWhere.setZjsignState(ConstantUtils.ArriveOrder.SIGN_NOT);
		aoWhere.setDelFlag(ArriveOrder.DEL_FLAG_NORMAL);
		//主表数据
		ArriveOrder aoInfo = arriveOrderDao.findInfo(aoWhere);
		if (aoInfo == null) {
			return false;
		}
		
		aoInfo.preUpdate();
		aoInfo.setReceiveflag(ConstantUtils.ArriveOrder.RECEIVE_NOT);
		arriveOrderDao.update(aoInfo);
		b = true;
		return b;
	}
	
	/**
	 * 质检人员更新
	 * @author maliang
	 * @version 2015-10-22
	 */
	@Transactional(readOnly = false)
	public boolean saveData4Check(ArriveOrder entity) {
		boolean b = false;
		if (entity == null) {
			return false;
		}
		if (entity.getId() == null || entity.getId().equals("")) {
			return false;
		}
		if (entity.getAobList().size() < 1) {
			return false;
		}
		// 取子列表
		List<ArriveOrderB> l = entity.getAobList();
		//主表查询条件
		ArriveOrder aoWhere = new ArriveOrder();
		//TODO 单据查看权限
		aoWhere.setId(entity.getId());
		aoWhere.setReceiveflag(ConstantUtils.ArriveOrder.RECEIVE);
		aoWhere.setZjsignState(ConstantUtils.ArriveOrder.SIGN_NOT);
		aoWhere.setDelFlag(ArriveOrder.DEL_FLAG_NORMAL);
		//主表数据
		ArriveOrder aoInfo = arriveOrderDao.findInfo(aoWhere);
		if (aoInfo == null) {
			return false;
		}
		//子表查询条件
		ArriveOrderB aobWhere = new ArriveOrderB();
		aobWhere.setCarriveorderid(aoInfo.getId());
		aobWhere.setDelFlag(ArriveOrderB.DEL_FLAG_NORMAL);
		//子表数据
		List<ArriveOrderB> aobList = arriveOrderDao.findChildList(aobWhere);
		if (aobList == null || aobList.size() < 1) {
			return false;
		}
		//强验证
		if (aobList.size() < l.size()) {
			return false;
		}
		
		List<ArriveOrderB> aobSaveList = new ArrayList<ArriveOrderB>();//子表要更新的数据
		//数据合法性检验与封装
		for (ArriveOrderB aobItem : aobList) {
			for (ArriveOrderB formItem : l) {
				//ID相同
				if (aobItem.getId().equals(formItem.getId())) {
					if (formItem.getNelignum() == null || formItem.getNelignum() < 0) {
						return false;
					}
					if (formItem.getNnotelignum() == null || formItem.getNnotelignum() < 0) {
						return false;
					}
					//合格+不合格=应到数量
					if ((formItem.getNelignum() + formItem.getNnotelignum()) != aobItem.getNarrvnum()) {
						return false;
					}
					aobItem.preUpdate();
					aobItem.setNelignum(formItem.getNelignum());//累计合格数量
					aobItem.setNnotelignum(formItem.getNnotelignum());//累计不合格数量
					aobSaveList.add(aobItem);
					break;
				}
			}
		}
		//强验证
		if (l.size() != aobSaveList.size()) {
			return false;
		}
		// 更新子表
		for (ArriveOrderB saveItem : aobSaveList) {
			arriveOrderDao.updateChild(saveItem);
		}

		b = true;
		return b;
	}
	
	/**
	 * 质检签字
	 * @author maliang
	 * @version 2015-10-22
	 */
	@Transactional(readOnly = false)
	public boolean saveData4ZJSign(ArriveOrder entity) {
		boolean b = false;
		if (entity == null) {
			return false;
		}
		if (entity.getId() == null || entity.getId().equals("")) {
			return false;
		}
		//查询条件
		ArriveOrder aoWhere = new ArriveOrder();
		//TODO 单据查看权限
		aoWhere.setId(entity.getId());
		aoWhere.setReceiveflag(ConstantUtils.ArriveOrder.RECEIVE);
		aoWhere.setZjsignState(ConstantUtils.ArriveOrder.SIGN_NOT);
		aoWhere.setDelFlag(ArriveOrder.DEL_FLAG_NORMAL);
		//主表数据
		ArriveOrder aoInfo = arriveOrderDao.findInfo(aoWhere);
		if (aoInfo == null) {
			return false;
		}
		//子表查询条件
		ArriveOrderB aobWhere = new ArriveOrderB();
		aobWhere.setCarriveorderid(aoInfo.getId());
		aobWhere.setDelFlag(ArriveOrderB.DEL_FLAG_NORMAL);
		//子表数据
		List<ArriveOrderB> aobList = arriveOrderDao.findChildList(aobWhere);
		List<ArriveOrderB> aobSaveList = new ArrayList<ArriveOrderB>();//子表要更新的数据
		for (ArriveOrderB aobItem : aobList) {
			//仅供入库管理员更新本次实入数量时使用
			aobItem.setNowNinnum(aobItem.getNelignum() - aobItem.getNaccumwarehousenum());//本次应收数量
			aobSaveList.add(aobItem);
		}
		// 更新子表
		for (ArriveOrderB saveItem : aobSaveList) {
			arriveOrderDao.updateChild(saveItem);
		}
		aoInfo.preUpdate();
		User user = UserUtils.getUser();
		aoInfo.setPkZjmanage(user.getId());
		aoInfo.setZjsignState(ConstantUtils.ArriveOrder.SIGN);
		aoInfo.setZjdate(aoInfo.getUpdateDate());
		arriveOrderDao.update(aoInfo);
		b = true;
		return b;
	}
	
	/**
	 * 质检签字（取消操作）
	 * @author maliang
	 * @version 2016-01-21
	 */
	@Transactional(readOnly = false)
	public boolean saveData4ZJSignCancel(ArriveOrder entity) {
		boolean b = false;
		if (entity == null) {
			return false;
		}
		if (entity.getId() == null || entity.getId().equals("")) {
			return false;
		}
		//查询条件
		ArriveOrder aoWhere = new ArriveOrder();
		//TODO 单据查看权限
		aoWhere.setId(entity.getId());
		aoWhere.setReceiveflag(ConstantUtils.ArriveOrder.RECEIVE);
		aoWhere.setZjsignState(ConstantUtils.ArriveOrder.SIGN);
		aoWhere.setRksignState(ConstantUtils.ArriveOrder.SIGN_NOT);
		aoWhere.setDelFlag(ArriveOrder.DEL_FLAG_NORMAL);
		//主表数据
		ArriveOrder aoInfo = arriveOrderDao.findInfo(aoWhere);
		if (aoInfo == null) {
			return false;
		}
//		//子表查询条件
//		ArriveOrderB aobWhere = new ArriveOrderB();
//		aobWhere.setCarriveorderid(aoInfo.getId());
//		aobWhere.setDelFlag(ArriveOrderB.DEL_FLAG_NORMAL);
//		//子表数据
//		List<ArriveOrderB> aobList = arriveOrderDao.findChildList(aobWhere);
//		List<ArriveOrderB> aobSaveList = new ArrayList<ArriveOrderB>();//子表要更新的数据
//		for (ArriveOrderB aobItem : aobList) {
//			//仅供入库管理员第一次更新确认数量时使用
//			aobItem.setNaccumwarehousenum(aobItem.getNelignum());//累计入库数量
//			aobSaveList.add(aobItem);
//		}
//		// 更新子表
//		for (ArriveOrderB saveItem : aobSaveList) {
//			arriveOrderDao.updateChild(saveItem);
//		}
		aoInfo.preUpdate();
		aoInfo.setZjsignState(ConstantUtils.ArriveOrder.SIGN_NOT);
		arriveOrderDao.update(aoInfo);
		b = true;
		return b;
	}
	
	/**
	 * 入库管理员更新确认数量
	 * @author maliang
	 * @version 2015-10-22
	 */
	@Transactional(readOnly = false)
	public boolean saveData4Confirm(ArriveOrder entity) {
		boolean b = false;
		//查询当前用户
		User userCur = UserUtils.getUser();
		if (userCur == null) {
			return false;
		}
		if (entity == null) {
			return false;
		}
		if (entity.getId() == null || entity.getId().equals("")) {
			return false;
		}
		if (entity.getAobList().size() < 1) {
			return false;
		}
		// 取子列表
		List<ArriveOrderB> l = entity.getAobList();
		//主表查询条件
		ArriveOrder aoWhere = new ArriveOrder();
		//TODO 单据查看权限
		aoWhere.setId(entity.getId());
		aoWhere.setCreceivepsn(userCur.getId());
		aoWhere.setReceiveflag(ConstantUtils.ArriveOrder.RECEIVE);
		aoWhere.setZjsignState(ConstantUtils.ArriveOrder.SIGN);
//		aoWhere.setRksignState(ConstantUtils.ArriveOrder.SIGN_NOT);
		aoWhere.setDelFlag(ArriveOrder.DEL_FLAG_NORMAL);
		//主表数据
		ArriveOrder aoInfo = arriveOrderDao.findInfo(aoWhere);
		if (aoInfo == null) {
			return false;
		}
		//子表查询条件
		ArriveOrderB aobWhere = new ArriveOrderB();
		aobWhere.setCarriveorderid(aoInfo.getId());
		aobWhere.setDelFlag(ArriveOrderB.DEL_FLAG_NORMAL);
		//子表数据
		List<ArriveOrderB> aobList = arriveOrderDao.findChildList(aobWhere);
		if (aobList == null || aobList.size() < 1) {
			return false;
		}
		//强验证
		if (aobList.size() < l.size()) {
			return false;
		}
		
		List<ArriveOrderB> aobSaveList = new ArrayList<ArriveOrderB>();//子表要更新的数据
		//数据合法性检验与封装
		for (ArriveOrderB aobItem : aobList) {
			for (ArriveOrderB formItem : l) {
				//ID相同
				if (aobItem.getId().equals(formItem.getId())) {
					if (formItem.getNowNinnum() == null || formItem.getNowNinnum() < 0) {
						return false;
					}
					if (formItem.getNowNinnum() > (aobItem.getNelignum() - aobItem.getNaccumwarehousenum())) {
						this.errmsg  = "部分物料数据超出累计入库数量！";
						return b;
					}
					aobItem.preUpdate();
					aobItem.setNowNinnum(formItem.getNowNinnum());
					
					aobSaveList.add(aobItem);
					break;
				}
			}
		}
		//强验证
		if (l.size() != aobSaveList.size()) {
			return false;
		}
		
		// 更新子表
		for (ArriveOrderB saveItem : aobSaveList) {
			arriveOrderDao.updateChild(saveItem);
		}

		b = true;
		return b;
	}
	
//	/**
//	 * 入库管理员签字
//	 * @author maliang
//	 * @version 2015-10-22
//	 */
//	@Transactional(readOnly = false)
//	public boolean saveData4RKSign(ArriveOrder entity) {
//		boolean b = false;
//		//查询当前用户
//		User userCur = UserUtils.getUser();
//		if (userCur == null) {
//			return false;
//		}
//		if (entity == null) {
//			return false;
//		}
//		if (entity.getId() == null || entity.getId().equals("")) {
//			return false;
//		}
//		//查询条件
//		ArriveOrder aoWhere = new ArriveOrder();
//		//TODO 单据查看权限
//		aoWhere.setId(entity.getId());
//		aoWhere.setCreceivepsn(userCur.getId());
//		aoWhere.setReceiveflag(ConstantUtils.ArriveOrder.RECEIVE);
//		aoWhere.setZjsignState(ConstantUtils.ArriveOrder.SIGN);
//		aoWhere.setRksignState(ConstantUtils.ArriveOrder.SIGN_NOT);
//		aoWhere.setDelFlag(ArriveOrder.DEL_FLAG_NORMAL);
//		//主表数据
//		ArriveOrder aoInfo = arriveOrderDao.findInfo(aoWhere);
//		if (aoInfo == null) {
//			return false;
//		}
//		aoInfo.preUpdate();
//		User user = UserUtils.getUser();
//		aoInfo.setPkRkmanage(user.getId());
//		aoInfo.setRksignState(ConstantUtils.ArriveOrder.SIGN);
//		aoInfo.setRkdate(aoInfo.getUpdateDate());
//		arriveOrderDao.update(aoInfo);
//		b = true;
//		return b;
//	}
	
	/**
	 * 关闭单据
	 * @author maliang
	 * @version 2016-02-27
	 */
	@Transactional(readOnly = false)
	public boolean closed(ArriveOrder entity) {
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
		ArriveOrder aoWhere = new ArriveOrder();
		//TODO 单据查看权限
		aoWhere.setId(entity.getId());
		aoWhere.setCreceivepsn(userCur.getId());
		aoWhere.setReceiveflag(ConstantUtils.ArriveOrder.RECEIVE);
		aoWhere.setZjsignState(ConstantUtils.ArriveOrder.SIGN);
		aoWhere.setRksignState(ConstantUtils.ArriveOrder.SIGN);
		aoWhere.setDelFlag(ArriveOrder.DEL_FLAG_NORMAL);
		//主表数据
		ArriveOrder aoInfo = arriveOrderDao.findInfo(aoWhere);
		if (aoInfo == null) {
			this.errmsg = "单据不存在！";
			return b;
		}
		//更新主表
		ArriveOrder aoUpdate = new ArriveOrder();
		aoUpdate.setId(entity.getId());
		aoUpdate.setHclosed(ConstantUtils.ArriveOrder.CLOSED);
		aoUpdate.preUpdate();
		arriveOrderDao.update(aoUpdate);
		b = true;
		return b;
	}
	
	/**
	 * 删除单据
	 * @author maliang
	 * @version 2016-02-28
	 * @throws Exception 
	 */
//	@Transactional(readOnly = false, rollbackFor = { Exception.class })
//	public boolean delete(ArriveOrder entity) throws Exception {
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
//		ArriveOrder aoWhere = new ArriveOrder();
//		//TODO 单据查看权限
//		boolean selFlag = false;
//		if (!(entity.getId() == null || entity.getId().equals(""))) {
//			selFlag = true;
//			aoWhere.setId(entity.getId());
//		}
//		if (!(entity.getVarrordercode() == null || entity.getVarrordercode().equals(""))) {
//			selFlag = true;
//			aoWhere.setVarrordercode(entity.getVarrordercode());
//		}
//		if (!selFlag) {
//			return b;
//		}
//		aoWhere.setDelFlag(ArriveOrder.DEL_FLAG_NORMAL);
//		//主表数据
//		ArriveOrder aoInfo = arriveOrderDao.findInfo(aoWhere);
//		if (aoInfo == null) {
//			this.errmsg = "单据不存在！";
//			return b;
//		}
//		if (!ConstantUtils.ArriveOrder.RECEIVE_NOT.equals(aoInfo.getReceiveflag())) {
//			this.errmsg = "该单据已被别人操作！";
//			return b;
//		}
//		EnWarehouseZ ewzWhere = new EnWarehouseZ();
//		ewzWhere.setCsourcebillhid(aoInfo.getId());
//		ewzWhere.setDelFlag(EnWarehouseZ.DEL_FLAG_NORMAL);
//		List<EnWarehouseZ> ewzList = enWarehouseDao.findChildList(ewzWhere);
//		if (ewzList != null && ewzList.size() > 0) {
//			this.errmsg = "该单据已被别人操作！";
//			return b;
//		}
//		
//		//子表数据
//		ArriveOrderB aobWhere = new ArriveOrderB();
//		aobWhere.setCarriveorderid(aoInfo.getId());
//		aobWhere.setDelFlag(ArriveOrderB.DEL_FLAG_NORMAL);
//		List<ArriveOrderB> aobList = arriveOrderDao.findChildList(aobWhere);
//		if (aobList == null || aobList.size() < 1) {
//			this.errmsg = "单据异常！";
//			return b;
//		}
//		
//		BuyOrder boWhere = new BuyOrder();
//		boWhere.setId(aobList.get(0).getCsourcebillid());
//		boWhere.setDelFlag(BuyOrder.DEL_FLAG_NORMAL);
//		BuyOrder boInfo = buyOrderDao.findInfo(boWhere);
//		if (boInfo == null || boInfo.getId() == null || "".equals(boInfo.getId())) {
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
//		//回写采购单（自己回写，ERP不回写）
//		List<BuyOrderB> bobSaveList = new ArrayList<BuyOrderB>();
//		for (BuyOrderB bobItem : bobList) {
//			for (ArriveOrderB aobItem : aobList) {
//				if (aobItem.getCsourcebillrowid().equals(bobItem.getId())) {
//					if (bobItem.getNaccumarrvnum() == null) {
//						bobItem.setNaccumarrvnum(0.0);
//					}
//					bobItem.setNaccumarrvnum(bobItem.getNaccumarrvnum() - aobItem.getNarrvnum());
//					if (bobItem.getNaccumarrvnum() <= 0) {
//						bobItem.setIisactive(ConstantUtils.BuyOrderB.ORDER_NORMAL);
//					}
//					bobItem.preUpdate();
//					bobSaveList.add(bobItem);
//					break;
//				}
//			}
//		}
//		
//		for (BuyOrderB bobItem : bobSaveList) {
//			buyOrderDao.updateChild(bobItem);
//		}
//		
//		arriveOrderDao.delete(aoInfo);
//
//		for (ArriveOrderB aobItem : aobList) {
//			arriveOrderDao.deleteChild(aobItem);
//		}
//
//		//同步到ERP到货单实体对象
//		ArriveOrderERP aoERP = new ArriveOrderERP();
//		aoERP.entity2EntityERP4delete(aoInfo, aobList);
//		List<ArriveOrderBERP> aobERPList = aoERP.getAobList();
//		for (ArriveOrderBERP aobERPItem : aobERPList) {
//			aobERPItem.setNelignum(0.0);
//		}
//		WebServiceParam wsp = new WebServiceParam();
//		wsp.setSourceSystem("L2M");
//		wsp.setAction("Delete");
//		wsp.setXml(JaxbObjectAndXmlUtil.object2Xml(aoERP));
////		wsp.setUrl("http://172.20.45.3/uapws/service/NCERPWSserviceeMain");
//		wsp.setNamespace("http://importaction.impl.ui.nc/NCERPWSserviceMain");
//		wsp.setMethod("saveNCBILL");
//		wsp.setBistype("DH");
//		try {
//			SendWebService.doWebService(wsp);
//		} catch (Exception e) {
//			e.printStackTrace();
//			b = false;
//			this.errmsg  = "同步ERP异常！";
//			throw new Exception();
//		}
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