package realtime;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class ServLogic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2761730624784647492L;
	private static final long MAX_TIME_DELAY_MS = 300;
	private String regexString;
	private Pattern p;
	public String regexStringProperties = "^http://searchb;" + "^http://product;"
			+ "^http://category;" + "^http://search";
	public static final Logger LOG = Logger.getLogger(ServLogic.class);
	private String originData = "";
	
	private static final long MSG_LEN = 23;
	private static final long TRAC_LEN = 22;
	private static final long PERM_LEN = 35;

	public ServLogic() {
		setConfig();
	}

	private void setConfig() {
		String[] rsp = this.regexStringProperties.split(";");
		for (String item : rsp) {
			regexString += "(" + item + ")" + "|";
		}
		regexString = regexString.substring(0, regexString.length() - 1);
		p = Pattern.compile(regexString);
	}

	public boolean isFieldsExist(String target) {
		Matcher m = p.matcher(target);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isPassed(String[] msg_split) {
		if (msg_split.length != MSG_LEN){
			LOG.error("data length not enouth: " + getOriginData());
			return false;
		}
		if (msg_split[22].length() != TRAC_LEN || msg_split[16].length() != PERM_LEN) {
			LOG.error("perm_id or trace_id with wrong length: " + msg_split.length + "\t" + getOriginData().replace("\t", "$"));
			return false;
		}
		try{
			return (isFieldsExist(msg_split[3]) && 
				("1".equals(msg_split[7]) || "2".equals(msg_split[7])));
		}catch(Exception e) {
			LOG.error(e);
			return false;
		}
	}

	public String getPermId(String[] data_arr) {
		return data_arr[16];
	}

	public String getValue(String[] msg_split) {
		long etime = System.currentTimeMillis();
		String type = msg_split[7];
		String time = msg_split[9];
		String url = msg_split[3];
		String region_id = msg_split[2];
		String ctr_id = msg_split[1];
		String to_url = msg_split[5];
		String trace_id = msg_split[22];
		long stime = new Long(trace_id.substring(0, 13));

		return type + "\t" + time + "\t" + url + "\t" + region_id + "\t"
				+ ctr_id + "\t" + to_url + "\t" + trace_id + "\t"
				+ (etime - stime);

	}

	public String getUrl(String[] msg_split) {
		return msg_split[3];
	}

	public void getTimeDelay(String[] msg_split) {
		long etime = System.currentTimeMillis();
		try {
			String trace_id = msg_split[22];
			long stime = new Long(trace_id.substring(0, 13));
			long time_min = etime - stime;
			if ((time_min) >= MAX_TIME_DELAY_MS) {
				// get producer unix_timestamp
				LOG.info("[Slow data: " + getOriginData() + "]");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage() + "[Error data:" + getOriginData() + "]");
		}
	}

	public String[] getData(String data) {
		setOriginData(data);
		return data.split("\t");
	}
	
	public void setOriginData(String data) {
		originData = data;
	}
	
	public String getOriginData() {
		return originData;
	}
}
