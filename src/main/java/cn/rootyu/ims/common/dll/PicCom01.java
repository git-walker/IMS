package cn.rootyu.ims.common.dll;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;

public class PicCom01 {
	
	public interface JnaOpencvIniInterface extends StdCallLibrary {

		JnaOpencvIniInterface Instance = (JnaOpencvIniInterface) Native.loadLibrary((Platform.isWindows() ? "201715" : "c"),  
				JnaOpencvIniInterface.class);  

//		//判断图片是否有文字-DV110V
//		public int identity_dc110v1(String path);

		
//		1、DC110V_mate(char*，char*)，DC110V标志的识别
//		   第一个参数为标准的匹配模板，为样例图片中的0.jpg图片；
//		   第二个参数传入的为待测图片路径，对应样例8.jpg
		public int DC110V_mate(String match_pic_path, String pic_path);
		
//		2、LCD_mate(char*)，LCD显示屏的识别
//		   对应样例中图片9.jpg
		public int LCD_mate(String pic_path);
		
//		3、Brake_valve_mate(char*)，制动单元阀的识别
//		   对应样例中图片10.jpg
		public int Brake_valve_mate(String pic_path);
		
//		4、door_mate(char*)，车门的识别
//		   对应样例图片11.jpg
		public int door_mate(String pic_path);
		
//		5、camera_mate(char*)，客室摄像头的识别
//		   对应样例图片12.jpg
		public int camera_mate(String pic_path);
		
		
		public int Ventilator_gap(String pic_path, Pointer p_result);
		
//		//白底缝隙检测
//		//左上角
		public int upper_left_corner(String pic_path, Pointer p_result);
//		//右上角
		public int upper_right_corner(String pic_path, Pointer p_result);
//		//右下角
		public int lower_right_corner(String pic_path, Pointer p_result);
//		//左下角
		public int lower_left_corner(String pic_path, Pointer p_result);
//		//左竖边
		public int left_side(String pic_path, Pointer p_result);
//		//上边
		public int right_side(String pic_path, Pointer p_result);
//		//右竖边
		public int cross_border(String pic_path, Pointer p_result);
		
	}

}
