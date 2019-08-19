package gameLogic;


import java.io.Serializable;

public class MyLog implements Serializable {
	private String log;
	public MyLog() {
		log="event log:";
	}
	public void addLine(String line) {
		log+="\n"+line;
	}
	public void clear() {
		log="";
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	
}
