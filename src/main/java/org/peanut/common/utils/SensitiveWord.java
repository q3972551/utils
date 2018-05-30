package org.peanut.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author peanut
 * @date   2018/05/16
 *
 * 屏蔽字库
 */
public class SensitiveWord
{
	private static List<String> strList = new ArrayList<String>();
	
	public static void init()
	{	
		BufferedReader reader = null;
		try
		{
			InputStream in =  SensitiveWord.class.getResourceAsStream("/word.csv");
			InputStreamReader inputStreamReader;
			inputStreamReader = new InputStreamReader(in, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			
			String tempString = null;
			while((tempString = reader.readLine()) != null)
			{
				String[] strs = tempString.split(",");
				for (String str:strs)
				{
					strList.add(str);
				}
			}
			reader.close();
		
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String filter(String word)
	{
		for (String str : strList)
		{
			word.replace(str, "");
		}
		
		return word;
	}
	
	public static boolean isExist(String word)
	{
		String word1 = word;
		boolean isExsit= false;
		for (String str : strList)
		{
			if(!word.replace(str, "").equals(word1))
			{
				isExsit = true;
				break;
			}
		}
		
		return isExsit;
	}
}
