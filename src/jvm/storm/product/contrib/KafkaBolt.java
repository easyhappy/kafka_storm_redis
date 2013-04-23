package contrib;

import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;     
import java.util.Date;

import org.apache.log4j.Logger;

import realtime.LocalProps;
import realtime.ServLogic;
import realtime.SharedRedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

/**
 * @author yuecong
 */
public class KafkaBolt implements IRichBolt {

	private static final long MASTER_SWITCH_TIMEOUT_MS = 20000;
	private static String REDIS_HOST = LocalProps.REDIS_CONN;
	private static int REDIS_PORT = LocalProps.REDIS_PORT;
	
	private static final long serialVersionUID = 4087400545121603772L;
	private OutputCollector _collector;
	public static final Logger LOG = Logger.getLogger(KafkaBolt.class);
	private ServLogic servlogic = new ServLogic();
//	private static JedisPool redisPool;
//	static {
//		JedisPoolConfig conf = new JedisPoolConfig();
//		redisPool = new JedisPool(conf, REDIS_HOST, REDIS_PORT);
//	} 
	private SharedRedis redisPool = new SharedRedis();
	private Jedis redis;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		_collector = collector;
		redis = redisPool.getResource();
		redis.select(LocalProps.REDIS_DB);
	}

	@Override
	public void execute(Tuple input) {
		LOG.info("kafkabolt_excute" + input + "come_on");
		String data = input.getString(0);
		String[] splits = data.trim().split("::");
		rpushToRedis(splits[0], splits[1]);
		/***
		if (servlogic.isPassed(data_arr)) {
			//rpushToRedis(servlogic.getPermId(data_arr),
			//		servlogic.getValue(data_arr));
			if (LocalProps.DEBUG) {
				servlogic.getTimeDelay(data_arr);
			}
		}
		***/
		_collector.ack(input);
	}

	public void rpushToRedis(String key, String value) {
		try {
            SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd HH:mm:ss");     
            Long current_time = System.currentTimeMillis();
            Date   curDate   =   new   Date(current_time);//获取当前时间     
            String   str   =   formatter.format(curDate);   
            value = value + "\t" + str + "\t" + String.valueOf(current_time);
			redis.set(key, value);
        }catch (Exception ex) {
			handleJedisException();
			redis.select(LocalProps.REDIS_DB);
			redis.set(key, value);
			LOG.warn(ex + ": redis got broken client, new client started!");
		}
	}
	
	public void handleJedisException() {
		try {
			Thread.sleep(MASTER_SWITCH_TIMEOUT_MS);
			Jedis monitor = new Jedis(LocalProps.REDIS_M_SERV_HOST, LocalProps.REDIS_M_SERV_PORT);
			List<String> master = monitor.sentinel("get-master-addr-by-name", "mymaster");
			String host = master.get(0);
			String port = master.get(1);
            LOG.info("host " + host + " port " + port);
			if (host.equals(REDIS_HOST) && port.equals(Long.toString(REDIS_PORT))) {
				redisPool.returnBrokenResource(redis);
				redis = redisPool.getResource();
			}else{
				redisPool.returnResource(redis);
				redisPool.destroy();
				redisPool = new SharedRedis();
				redis = redisPool.getResource();	
				REDIS_HOST=host;
				REDIS_PORT=Integer.parseInt(port);
			}
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	@Override
	public void cleanup() { 
		// TODO Auto-generated method stub]
		redisPool.returnResource(redis);
		redisPool.destroy();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
}
