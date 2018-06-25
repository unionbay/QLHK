package anhnh34.fpt.vn.entity;

import javafx.beans.property.SimpleStringProperty;

public class Sex {
	public static final String WOMAN_ID = "Y";
	public static final String MAN_ID = "X";
	public static final String WOMAN_DESC = "Nữ";
	public static final String MAN_DESC = "Nam"; 
	private final SimpleStringProperty id;
	private final SimpleStringProperty desc;
	
	public String getId() {
		return id.getValue();
	}
	
	public void setId(String id) {
		this.id.set(id);
	}
	
	public SimpleStringProperty DescProperty(){
		return this.desc;
	}
	
	public String getDesc() {
		return desc.getValue();
	}
	
	public void setDesc(String desc) {
		this.desc.setValue(desc);;
	}
	
	public Sex(){
		this.id = new SimpleStringProperty();
		this.desc = new SimpleStringProperty();
	}
	
	public Sex(String id, String desc){
		this.id = new SimpleStringProperty(id);
		this.desc = new SimpleStringProperty(desc);
	}
}
