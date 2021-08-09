package com.ycitus.utils;

import java.util.Random;

public class NumberUtil {

	/** 输入double, 保留2位小数 **/
	public static double formatDigit(double number) {
		return formatDigit(number, 2);
	}

	/** 输入double, 保留指定digit位小数 **/
	public static double formatDigit(double number, int dight) {
		return Double.parseDouble(String.format("%." + dight + "f", number));
	}

	/** 获取一个足够大的数字 **/
	public static int getBigEnoughNumber() {
		return 1000000;
	}

	/** 输入小数, 返回百分数文本 **/
	public static String getFormatedPercentage(double number) {
		return (formatDigit(number * 100)) + "%";
	}

	/** 随机获取1~30000的整数 **/
	public static int getRandomNumber() {
		return getRandomNumber(1, 30000);
	}

	/** 取随机数 **/
	public static int getRandomNumber(double min, double max) {
		Random random = new Random();
		return (int) (random.nextInt((int) (max - min + 1)) + min);
	}

	/** 取随机数 **/
	public static int getRandomNumber(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}

	/** 该方法只能判断 正整数 **/
	public static boolean isNumber(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

}
