import java.util.Properties;

import kafka.javaapi.producer.ProducerData;
import kafka.producer.ProducerConfig;
import   java.text.SimpleDateFormat;     
import java.util.Date;

public class KafkaBean {
	private kafka.javaapi.producer.Producer<String, String> producer=null;
	private String topic="";
	private Properties props = new Properties();

	public KafkaBean(String topic){
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("zk.connect", "h252201:2181,h252202:2181,h252203:2181");
		producer = new kafka.javaapi.producer.Producer<String, String>(
							new ProducerConfig(props));
		this.topic = topic;
	}
	
	public  void send(String data){
		producer.send(new ProducerData<String, String>(topic, data));
	} 

    public String current_time(){
        SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd HH:mm:ss");     
        Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间     
        String   str   =   formatter.format(curDate);
        Long x = System.currentTimeMillis();
        String y =  String.valueOf(x);
        return str + "\t" + y;
    }
    public static void main(String[] args){
        int a = 5;
        KafkaBean kafka_bean = new KafkaBean("test_kafka_bean");
        for(int i=0; i< 100000; i++){
           kafka_bean.send( a + "2222" + i + "::" + kafka_bean.current_time());
        }
    }
}
