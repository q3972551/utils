package org.peanut.common.utils;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;


/**
 * @author peanut
 * @date   2018/05/16
 *
 * 必须读取一个"resources/redis.properties"
 * 例：
 * host = 192.168.0.105
 * passwd = 123456
 * port = 6379
 * index = 0 (对应的db号)
 */
public class RedisTools
{
	private static Logger m_logger = Logger.getLogger(RedisTools.class);
	private static JedisPool m_pool  = null;

	private static int m_selectindex = 0;
	static{

		if (m_pool == null)
		{
			ResourceBundle resource = ResourceBundle.getBundle("properties/redis");
			String host = resource.getString("host");
			String passwd = resource.getString("passwd");
			int port = Integer.valueOf(resource.getString("port"));
			m_selectindex = Integer.valueOf(resource.getString("index"));

			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(100);
			config.setMaxWaitMillis(50* 1000);
			config.setMaxTotal(15);
			config.setTestOnBorrow(true);
			config.setTestOnReturn(true);
			config.setTestWhileIdle(true);

			JedisPool pool = new JedisPool(config,host,
					port,3000,passwd);
			m_pool  = pool;

		}
	}

	/**
	 * 得到自增key的count
	 * @return
	 */
	public static int getCountAutoIncrememtal(String key){
		int count = 0;
		String value = getValue(key);
		if(value != null){
			count = Integer.parseInt(value);
		}
		count ++;
		setKeyAndValue(key, String.valueOf(count));
		return count;
	}

	public void release()
	{
		m_pool.destroy();
	}

	public static boolean setKeyAndValue(String key,String value)
	{
		boolean isOK = false;
		Jedis jedis = null;

		try{
			jedis = getResource();
			if ("OK".equals(jedis.set(key, value)))
			{
				isOK = true;
			}
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}

		return isOK;
	}

	public static String getValue(String key)
	{
		Jedis  jedis = null;
		String value = null;
		try{
			jedis = getResource();
			value = jedis.get(key);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
		return value;
	}

	public static List<String> getAllList(String key)
	{
		List<String> list = null;
		Jedis jedis = null;
		try{
			jedis = getResource();
			list = jedis.lrange(key, 0, -1);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
		return list;
	}

	public static void addList(String key,String item)
	{
		Jedis jedis = null;
		try{
			jedis = getResource();
			jedis.lpush(key, item);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 移除list 中的item
	 * @param key
	 * @param count 数量 count>0从标头开始搜索，count<0从表尾开始搜索 ，count = 0，移除全部
	 * @param item
	 */
	public static void removeList(String key,int count,String item)
	{
		Jedis jedis = null;
		try{
			jedis = getResource();
			jedis.lrem(key, count, item);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
	}
	
	public static long getSizeForList(String key)
	{
		Jedis jedis = null;
		long len = 0;
		try{
			jedis = getResource();
			len = jedis.llen(key);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
		
		return len;
	}

	public static boolean isExistValueForList(String key,String value)
	{
		Jedis jedis = null;
		List<String> list = null;
		try{
			jedis	= getResource();
			list = jedis.lrange(key, 0, -1);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}

		if (list == null || list.isEmpty())
		{
			return false;
		}

		for (String str :list)
		{
			if(str.equals(value))
			{
				return true;
			}
		}

		return false;
	}

	public static String getPopForList(String key)
	{
		Jedis  jedis = null;
		String value = null;
		try{
			jedis = getResource();
			value = jedis.lpop(key);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
		return value;
	}

	public static List<String> getPopForAllList(String key)
	{
		List<String> list = getAllList(key);
		removeKey(key);
		return list;
	}

	public static void removeKey(String key)
	{
		Jedis jedis = null;
		try{
			jedis = getResource();

			if(jedis.exists(key)){
				jedis.del(key);
			}
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
	}

	public static void putForHash(String key,String field,String value)
	{
		Jedis jedis = null;
		try{
			jedis = getResource();
			jedis.hset(key, field, value);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
			e.printStackTrace();
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
	}

	public static void putForHash(String key,Map<String,String> value)
	{

		for (Entry<String, String> entry: value.entrySet())
		{
			putForHash(key,entry.getKey(),entry.getValue());
		}

	}

	public static String getForHash(String key,String field)
	{
		Jedis  jedis = null;
		String value = null;
		try{
			jedis = getResource();
			value = jedis.hget(key, field);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
		return value;
	}

	public static void removeForHash(String key,String field)
	{
		Jedis  jedis = null;
		try{
			jedis = getResource();
			jedis.hdel(key, field);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}

	}

	public static Map<String,String> getForHash(String key)
	{
		Jedis  jedis = null;
		Map<String,String> value = null;
		try{
			jedis = getResource();
			value = jedis.hgetAll(key);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
		return value;
	}

	public static Set<String> getKeys(String pattern)
	{
		Jedis        jedis = null;
		Set<String>  keys  = null;
		try{
			jedis = getResource();
			keys  = jedis.keys(pattern);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
		return keys;
	}

	public static Jedis getResource()
	{
		Jedis jedis = m_pool.getResource();
		jedis.select(m_selectindex);
		return jedis;
	}
	
	/**
	 * 设置带超时时间
	 * @param key
	 * @param value
	 * @param time 单位秒
	 */
	public static void setKeyAndValue(String key,String value,int time)
	{
		Jedis        jedis = null;
		try{
			jedis = getResource();
			jedis.setex(key, time, value);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 设置带超时时间
	 * @param key
	 * @param time 单位秒
	 */
	public static void setKeyAndValue(String key,int time)
	{
		Jedis        jedis = null;
		try{
			jedis = getResource();
			jedis.expire(key, time);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
	}
	
	public static Long getKeySurTime(String key)
	{
		Jedis jedis = null;
		try{
			jedis = getResource();
			return jedis.ttl(key);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
		return null;
		
	}
	
	public static void addSet(String key,String value)
	{
		Jedis        jedis = null;
		try{
			jedis = getResource();
			jedis.sadd(key, value);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
	}
	
	public static Set<String> getSet(String key)
	{
		Jedis        jedis = null;
		Set<String>  set   = null;
		try{
			jedis = getResource();
			set   = jedis.sinter(key);
		}
		catch(Exception e)
		{
			m_logger.error("RedisTools.class", e);
		}
		finally
		{
			m_pool.returnResourceObject(jedis);
		}
		
		return set;
	}
}
