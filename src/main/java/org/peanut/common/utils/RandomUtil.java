package org.peanut.common.utils;

import java.util.Random;

/**
 * @author  hanwu
 * @date    2018/05/16
 */
public class RandomUtil
{
	/**
	 * 根据给定的范围生成随机数
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getDigital(int start,int end){
		Random rd  = new Random();
		int value = rd.nextInt(end - start + 1) + start; 	
		return value;
	}
}
