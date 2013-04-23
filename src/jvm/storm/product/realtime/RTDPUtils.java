package realtime;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import kafka.message.Message;

public class RTDPUtils {
	  
	public static String msgToString(Message message)
	{
	    ByteBuffer buffer = message.payload();
	    byte [] bytes = new byte[buffer.remaining()];
	    buffer.get(bytes);
	    return new String(bytes);
	}

	public static List<String> getStaticZkHosts() {
		// TODO Auto-generated method stub
		List<String> zkhosts = new ArrayList<String>();
		for (String host :  LocalProps.ZK_HOSTS.split(",")) {
			zkhosts.add(host);
		}
		return zkhosts;
	}

	public static int getStaticZkPort() {
		// TODO Auto-generated method stub
		return LocalProps.ZK_PORT;
	} 
	
	public static List<String> getStaticKafkaHosts() {
		List<String> kfhosts = new ArrayList<String>();
		for (String host :  LocalProps.KFK_SERV_HOSTS.split(",")) {
			kfhosts.add(host);
		}
		return kfhosts;
	}
	
	public static int getStaticKafkaPort() {
		return LocalProps.KFK_SERV_PORT;
	}
	  
}
  