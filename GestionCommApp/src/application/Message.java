package application;

public class Message {
	private int id;
	private String alert;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public Message(int id, String alert) {
		super();
		this.id = id;
		this.alert = alert;
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", alert=" + alert + "]";
	}
	
	
}
