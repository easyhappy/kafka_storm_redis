package realtime;

public class LocalProps {

	public static final String REDIS_M_SERV_HOST = "ip"; //redis master ip
	public static final int REDIS_M_SERV_PORT = 9506;
	public static String GROUP = "my_kafka_test_2"; 
	public static String TOPIC = "test_Products"; 
	public static long START_OFFSET = -1;
	public static Integer SPOUT_PARALLELISM = 3;
	public static Integer BOLT_PARALLELISM = 3;
	public static String KFK_SERV_HOSTS = "ip1, ip2"; // kafka ips
	public static int KFK_SERV_PORT = 9092;
	public static int KFK_BUFFER_SIZE = 1024*1024;
	public static int PART_NUMS = 3;
	
	public static String ZK_ROOT = "/product_redis_2";
   //public static String ZK_ROOT = "/rtdp_new";
	public static String ZK_HOSTS = "ip1, ip2....."; //zookeeper ips
	public static int ZK_PORT = 2181;
	public static int CONN_TIMEOUT_MS = 0;

	
	public static String REDIS_CONN = "redis_ip";  //redis ip
	public static int REDIS_PORT = 8507;
	public static int REDIS_POOL_SIZE = 3; 
	public static int REDIS_LIST_OFFSET = -20;
	public static int REDIS_KEY_TTL = 2*24*3600;
	public static int REDIS_DB = 14;
//	public static int REDIS_DB = 4;
	
	public static boolean DEBUG = true; 
	
	public static void setTopic(String topic){
		TOPIC = topic;
	}  
}
