package org.peanut.common.utils; /**
 * Author  : Cao_Xiao_Bin
 * Created on : 2015骞�鏈�鏃ヤ笂鍗�1:46:25
 * Content : uuid鐢熸垚绫�
 * Example :
 */

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author peanut,hanwu
 * @date   2018/05/16
 */
public class StringUtil
{
	private static Pattern NUMBER_PARTERN      = Pattern.compile("[0-9]*");
	private static Pattern CHINESECHAR_PARTERN = Pattern.compile("[\u4e00-\u9fa5]");
	private static Pattern TEL_PARTERN          = Pattern.compile("1[0-9]{10}");
	public static String getUUID()
	{
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		return id;
	}
	
	public static boolean isNumeric(String str)
	{
		Matcher isNum = NUMBER_PARTERN.matcher(str);
		if (!isNum.matches())
		{
			return false;
		}
		return true;
	}

	public static boolean isChineseChar(String str)
	{
		boolean temp = false;
		Matcher m = CHINESECHAR_PARTERN.matcher(str);
		if (m.find())
		{
			temp = true;
		}
		return temp;
	}

	public static boolean isExsitName(String content)
	{
		return content.contains("@");
	}
	
	/**
	 * 得到N位的数字码
	 * @param digit
	 * @return
	 */
	public static String getDigitCode(int digit)
	{
		Random  r   = new Random();
		int maxCount= 1;
		for (int i = 0 ; i < digit ; i ++)
		{
			maxCount *= 10;
		}
		String body = String.valueOf(r.nextInt(maxCount));
		int length  = digit - body.length();
		if (length > 0 )
		{
			for (int i = 0 ; i < length ; i ++)
			{
				body = "0" + body;
			}
		}
		
		return body;

	}
	
	/**
	 * 得到字母码
	 * @param digit
	 * @return
	 */
	public static String getAlphaCode(int digit)
	{
		String chars   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String newChar = "";
		for(int i = 0 ; i < digit ; i ++)
		{
			newChar += chars.charAt((int)(Math.random() * 26));
		}
		return newChar;
	}
	
	/**
	 * 得到N为的数字，用0来填充位数
	 * @param digit
	 * @param code
	 * @return
	 */
	public static String getDigitCode(int digit,int code)
	{
		String codeStr = String.valueOf(code);
		String str = null;
		if (codeStr.length() >= digit)
		{
			str = String.valueOf(code);
		}
		else
		{
			int size = digit - codeStr.length();
			
			str = String.valueOf(code);
			for (int i = 0 ; i < size ; i ++)
			{
				str = "0" + str;
			}
		}
		
		return str;
	}
	
	/**
	 * 输入字符传得到可识别的字符串包括中文，英文字母，数字加下划线
	 * @param str
	 * @return
	 */
	public static String getRecognizedChar(String str)
	{
		String reg = "[\u4e00-\u9fa5\\w]+";
		
		Pattern p=Pattern.compile(reg); 
	    Matcher m=p.matcher(str); 
	    
	    String nStr = "";
	    while(m.find()){ 
	    
	    	nStr = nStr + m.group();
	   }
	    
	   return nStr;
	}
	
	public static boolean isTel(String tel)
	{
		if(tel == null)
		{
			return false;
		}
		Matcher matcher = TEL_PARTERN.matcher(tel);
		if (!matcher.matches())
		{
			return false;
		}
		return true;
	}
	
	public static int[] toIntArray(String[] str)
	{
		int[] arr = new int[str.length];
		for (int i = 0; i < str.length; i++) 
		{
			arr[i] = Integer.valueOf(str[i]);
		}
		return arr;
	}
	
	public static String arrayToString(int[] arr)
	{

		String str  = "";
		for (int i = 0; i < arr.length; i++) 
		{
			if(i+1 == arr.length)
			{
				str  += arr[i];
			}
			else
			{
				str += arr[i] + ",";
			}	
		}
		return str;
			
	}

	
}
