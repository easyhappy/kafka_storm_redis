package realtime;

import java.util.List;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import contrib.KafkaBolt;
import contrib.KafkaSpout;
import contrib.SpoutConfig;
import contrib.StringScheme;

public class MainTopology {
	public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        List<String> hosts = RTDPUtils.getStaticKafkaHosts();
        
        SpoutConfig spoutConf = SpoutConfig
        		.fromHostStrings(hosts,               // kafka broker hosts  
        				LocalProps.PART_NUMS,                            // partition per
        				args[1],                  		// topic
        				LocalProps.ZK_ROOT,            // the root path in zk you want to manage your offsets
        				LocalProps.GROUP);                      // an id for this consumers' offsets storing in zk follwed the root path.

        spoutConf.scheme = new StringScheme();
        spoutConf.forceStartOffsetTime(LocalProps.START_OFFSET);     
        
        builder.setSpout("from_kafka", new KafkaSpout(spoutConf), LocalProps.SPOUT_PARALLELISM);
        
        builder.setBolt("to_redis", new KafkaBolt(), LocalProps.BOLT_PARALLELISM).shuffleGrouping("from_kafka");
        Config conf = new Config();
        
        List<String> zkHosts = RTDPUtils.getStaticZkHosts();

        spoutConf.zkServers = zkHosts;
        spoutConf.zkPort = RTDPUtils.getStaticZkPort();
        
        try { 
        	conf.setNumWorkers(5);
			StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
			//StormSubmitter.submitTopology("firstClick", conf, firstClickBuilder.createTopology());
		} catch (AlreadyAliveException e) {
			e.printStackTrace();
		} catch (InvalidTopologyException e) {
			e.printStackTrace();
		}
    }
}
