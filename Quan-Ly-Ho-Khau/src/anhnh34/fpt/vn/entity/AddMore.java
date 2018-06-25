package anhnh34.fpt.vn.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class AddMore {
	private SimpleIntegerProperty totalNumber; //khẩu.
	private SimpleIntegerProperty totalWomanNumber;
	private SimpleIntegerProperty aboveFourteenYear;
	private SimpleIntegerProperty womanAboveFourteenYear;

	public SimpleIntegerProperty AboveFourteenYear(){
		return this.aboveFourteenYear;
	}
	public Integer getAboveFourteenYear() {
		return aboveFourteenYear.getValue();
	}

	public void setAboveFourteenYear(int number) {
		this.aboveFourteenYear.set(number);
	}

	public SimpleIntegerProperty TotalNumber(){
		return this.totalNumber;
	}
	
	public Integer getTotalNumber() {
		return this.totalNumber.get();
	}

	public void setTotalNumber(int number) {
		this.totalNumber.set(number);
	}
	
	public SimpleIntegerProperty TotalWomanNumber(){
		return this.totalWomanNumber;
	}
	
	public void setTotalWomanNumber(int totalWomanNumber) {
		this.totalWomanNumber.set(totalWomanNumber);
	}
	
	public Integer getTotalWomanNumber() {
		return this.totalWomanNumber.get();
	}
	
	public SimpleIntegerProperty WomanAboveFourteenYear(){
		return this.womanAboveFourteenYear;
	}
	
	public Integer getWomanAboveFourteenYear() {
		return womanAboveFourteenYear.get();
	}
	
	public void setWomanAboveFourteenYear(int number) {
		this.womanAboveFourteenYear.set(number);
	}
	
	public void addTotalNumber(int number){
		this.totalNumber.set(this.totalNumber.getValue() + number);
	}
	
	public void addTotalWomanNumber(int number){
		this.totalWomanNumber.set(this.totalWomanNumber.getValue() + number);
	}
	
	public void addAboveFourteenYear(int number){
		this.aboveFourteenYear.set(this.aboveFourteenYear.getValue() + number);
	}
	
	public void addWomanAboveFourteenYear(int number){
		this.womanAboveFourteenYear.set(this.womanAboveFourteenYear.get() + number);
	}
	 
	public AddMore(){
		this.totalNumber = new SimpleIntegerProperty(0);
		this.totalWomanNumber = new SimpleIntegerProperty(0);
		this.aboveFourteenYear = new SimpleIntegerProperty(0);
		this.womanAboveFourteenYear = new SimpleIntegerProperty(0);
	}
	
	public AddMore(int totalNum, int totalAFNum, int totalWNum, int totalWAF){
		this.totalNumber = new SimpleIntegerProperty(totalNum);
		this.totalWomanNumber = new SimpleIntegerProperty(totalWNum);
		this.aboveFourteenYear = new SimpleIntegerProperty(totalAFNum);
		this.womanAboveFourteenYear = new SimpleIntegerProperty(totalWAF);
	}
	

}
