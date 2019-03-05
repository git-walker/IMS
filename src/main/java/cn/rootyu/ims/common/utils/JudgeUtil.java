package cn.rootyu.ims.common.utils;

/**
 * 数值判断工具
 * @author lichuang
 *
 */
public class JudgeUtil {
	/**
	 * 
	 * @param num1 减数
	 * @param num2 被减数
	 * @return
	 */
	public static boolean JudgeDouble(double num1,double num2){
		float standard = 0.0000000001f;
		boolean flag = true;
		double result = num1-num2;
		if(result < standard){
			return flag;
		}else{
			flag = false;
			return flag;
		}
	}

}
