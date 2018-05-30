package org.peanut.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author peanut
 * @date   2018/05/18
 *
 * 读取csv 文件
 */
public class CSVReadUtil {

	/**
	 *
	 * @param path  地址
	 * @param count 行数
	 * @return
	 */
	public static List<String> readFile(String path,int count)
	{
		BufferedReader reader = null;
		List<String> nameList = new ArrayList<String>();
		try
		{
			
			InputStream in = CSVReadUtil.class.getResourceAsStream(path);
			InputStreamReader inputStreamReader;
			inputStreamReader = new InputStreamReader(in, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			for (int i = 0; i < count; i++) {
				String name = reader.readLine();
			
				if( name!= null)
				{
					nameList.add(name);
				}
			}
			reader.close();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
		return nameList;
	}
	
	public static List<String> readFile(String path)
	{
		BufferedReader reader = null;
		List<String> nameList = new ArrayList<String>();
		try
		{
			
			InputStream in = CSVReadUtil.class.getResourceAsStream(path);
			InputStreamReader inputStreamReader;
			inputStreamReader = new InputStreamReader(in, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String name = reader.readLine();
			while(name != null)
			{
				nameList.add(name);
				name = reader.readLine();
			}
			reader.close();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
		return nameList;
	}
}
