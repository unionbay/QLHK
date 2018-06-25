package anhnh34.fpt.vn.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class RegisterOfBirth {
	private final SimpleIntegerProperty totalNumber; //khẩu
	private final SimpleIntegerProperty totalWomanNumber; //nữ
	
	public SimpleIntegerProperty TotalNumber(){
		return this.totalNumber;
	}
	
	public SimpleIntegerProperty TotalWomanNumber(){
		return this.totalWomanNumber;
	}
	
	public Integer getTotalNumber() {
		return totalNumber.getValue();
	}

	public Integer getTotalWomanNumber() {
		return totalWomanNumber.getValue();
	}

	public void setTotalNumber(int number) {
		this.totalNumber.set(number);
	}

	public void setTotalWomanNumber(int number) {
		this.totalWomanNumber.set(number);
	}

	public RegisterOfBirth() {
		this.totalNumber = new SimpleIntegerProperty();
		this.totalWomanNumber = new SimpleIntegerProperty();
	}

	public RegisterOfBirth(int number, int totalNumber) {
		this.totalNumber = new SimpleIntegerProperty(totalNumber);
		this.totalWomanNumber = new SimpleIntegerProperty(totalNumber);
	}
	
	public void addTotalNumber(int number){
		this.setTotalNumber(this.getTotalNumber() + number);
	}
	
	public void addTotalWomanNumber(int number){
		this.setTotalWomanNumber(this.getTotalWomanNumber() + 1);
	}
}
