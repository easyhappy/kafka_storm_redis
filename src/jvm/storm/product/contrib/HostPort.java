package contrib;

import java.io.Serializable;

import realtime.LocalProps;

@SuppressWarnings("serial")
public class HostPort implements Serializable {
    public String host; 
    public int port;
    
    public HostPort(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public HostPort(String host) {
        this(host, LocalProps.KFK_SERV_PORT);
    }
}
