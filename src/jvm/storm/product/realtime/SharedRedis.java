/**
 * 
 */
package realtime;

import java.io.Serializable;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * @author eking
 *
 */
public class SharedRedis implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8255896719473882227L;
	private static JedisPool pool;
	static {
		JedisPoolConfig conf = new JedisPoolConfig();
        conf.setMaxWait(10001);
        conf.setTestOnBorrow(true);
        conf.setMinEvictableIdleTimeMillis(1000*60*60);
		pool = new JedisPool(conf, LocalProps.REDIS_CONN, LocalProps.REDIS_PORT);
	}
	public static void setConfig(String host, int port) {
		
	}

    public void resetJedisPool(){
		JedisPoolConfig conf = new JedisPoolConfig();
        conf.setMaxWait(10001);
        conf.setTestOnBorrow(true);
        conf.setMinEvictableIdleTimeMillis(1000*60*60);
		pool = new JedisPool(conf, LocalProps.REDIS_CONN, LocalProps.REDIS_PORT);
    }
	
	public Jedis getResource()
	{
		return pool.getResource();
	}
	
	public void returnResource(Jedis redis) {
		pool.returnResource(redis);
	}
	
	public void destroy() {
		pool.destroy();
	}
	
	public void returnBrokenResource(Jedis redis) {
		pool.returnBrokenResource(redis);
	}
}
 
