package realtime;

import java.nio.ByteBuffer;

import redis.clients.jedis.Jedis;

import kafka.message.Message;

public class RJUtils {
	
	  public static SharedRedis redisPool = new SharedRedis();
	  public static Jedis redis = redisPool.getResource();
	  
	  public static String msgToString(Message message)
	  {
	    ByteBuffer buffer = message.payload();
	    byte [] bytes = new byte[buffer.remaining()];
	    buffer.get(bytes);
	    return new String(bytes);
	  } 
	  
      public static boolean rpushToRedis(String key, String value){
    	  try{
				redis.rpush(key, value);
//				redis.ltrim(key, LocalProps.REDIS_LIST_OFFSET,-1);
				return true;
			}catch(Exception ex){
				return false;
			}	
	  }
}
  