package cn.rootyu.rad.common.utils;

/**
 * 常量类
 *
 * @author maliang
 * @version 2015-11-03
 */
public class ConstantUtils {


	/**
	 * 采购订单主表
	 */
	public static class BuyOrder {
		public static final String ORDER_NORMAL = "3";// 订单正常
	}

	/**
	 * 采购订单子表
	 */
	public static class BuyOrderB {
		public static final String ORDER_NORMAL = "0";// 订单正常
	}

	/**
	 * 到货单主表
	 */
	public static class ArriveOrder {
		public static final String RECEIVE_NOT = "0";// 未确认
		public static final String RECEIVE = "1";// 确认收货
		public static final String SIGN_NOT = "0";// 未确认签字
		public static final String SIGN = "1";// 确认签字
		public static final String CLOSED_NOT = "0";// 订单正常
		public static final String CLOSED = "1";// 订单关闭

		public static final String E_NORMAL = "0";// 正常
		public static final String E_YZPD = "1";// 预知判断超时
		public static final String E_ZJ = "2";// 质检超时
		public static final String E_RZ = "3";// 入账超时
		public static final String E_RK = "4";// 入库超时
	}

	/**
	 * 入库主表
	 */
	public static class EnWarehouse {
		public static final String ORDER_MAKE = "1";// 生成
		public static final String ORDER_CONFIRM = "2";// 下发

	}

	/**
	 * 入库子表
	 */
	public static class EnWarehouseZ {

		public static final String SHELVES_FLAG_NOT = "0";// 未上架
		public static final String SHELVES_FLAG = "1";// 上架
	}


	/**
	 * 存货管理档案
	 */
	public static class InvManDoc {
		public static final String INV_INSTANCY = "1";// 紧急用料
	}

	/**
	 * 备料计划单子表
	 */
	public static class PickmB {

		public static final String storcode = "X123";// 新区紧固件库项目仓库编码

	}

	/**
	 * 是否
	 */
	public static class YesOrNo {
		// 是
		public static final String YES = "1";
		// 否
		public static final String NO = "0";
	}

	/**
	 * 菜单Id
	 */
	public static class MenuProcess {

		public static final String MENU_INQUIRE_TASK = "463382bb8a6b4761b05a6ed5d25353b3";// 检验任务查询
		public static final String MENU_QMIS = "b95e7b34b087431481f372b4067da883";// 质量检验
		public static final String MENU_NCR = "d6c3de8d6e7e4d3fa167cd748a453a7b";// NCR
	}


}
