package realtime;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class RTDProperty {

	private Properties props = new Properties(); 
	
	public RTDProperty(String filepath){
		readPropertyFile(filepath);
	}
	
	private void readPropertyFile(String filepath) {
		InputStream in = null;
		try {
			in = new FileInputStream(filepath);
		}catch (Exception e){
			in = RTDProperty.class.getClassLoader().getResourceAsStream(filepath);
		}
		
		try {
			props.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getProperty(String key){
		return props.getProperty(key);
	}
	
	public HashMap<String, String> getProperties() {
		HashMap<String, String> propMap = new HashMap<String, String>();
		@SuppressWarnings("rawtypes")
		Enumeration en = props.propertyNames();
		while (en.hasMoreElements()) {
			String key = (String)en.nextElement();
			propMap.put(key, props.getProperty(key));
		}
		return propMap;
	}
}
