package com.nciebt.zyj.utils;

/**
 * 类名称：PadConfigInfo
 * 类描述：pad配置信息类
 * 创建人：
 * 创建时间：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * @version v1.0
 */
public class PadConfigInfo {

	private static float density=160;
	private static int width ;//得到宽度
	private static int height;//得到高度
	private static float density_divisor=1;
	
	public static float getDensity_divisor() {
		return density_divisor;
	}

	public static float getDensity() {
		return density;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static void setDensity(float density){
		PadConfigInfo.density=density;
		PadConfigInfo.density_divisor=density/160;
		if (PadConfigInfo.density_divisor != 1.0f) {
			PadConfigInfo.density_divisor = 2.0f;
		}
	}
	
	public static void setWidth(int width){
		PadConfigInfo.width=width;
	}
	
	public static void setHeight(int height){
		PadConfigInfo.height=height;
	}
}
