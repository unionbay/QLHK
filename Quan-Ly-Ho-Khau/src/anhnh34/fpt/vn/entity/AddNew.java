package anhnh34.fpt.vn.entity;

import javafx.beans.property.SimpleIntegerProperty;

public class AddNew {
	private SimpleIntegerProperty totalNumber;
	private SimpleIntegerProperty totalWomanNumber;
	private SimpleIntegerProperty aboveFourteenYear;
	private SimpleIntegerProperty womanAboveFourteenYear;
	private SimpleIntegerProperty isAddNew;
	
	public SimpleIntegerProperty isAddNew(){
		return this.isAddNew;
	}
	
	public Integer getIsAddNew() {
		return isAddNew.getValue();
	}

	public void setIsAddNew(Integer addNew) {
		this.isAddNew.set(addNew);
	}

	public SimpleIntegerProperty AboveFourteenYear() {
		return this.aboveFourteenYear;
	}

	public Integer getAboveFourteenYear() {
		return aboveFourteenYear.getValue();
	}

	public void setAboveFourteenYear(int number) {
		this.aboveFourteenYear.set(number);
	}

	public SimpleIntegerProperty TotalNumber() {
		return this.totalNumber;
	}

	public Integer getTotalNumber() {
		return this.totalNumber.get();
	}

	public void setTotalNumber(int number) {
		this.totalNumber.set(number);
	}

	public SimpleIntegerProperty TotalWomanNumber() {
		return this.totalWomanNumber;
	}

	public void setTotalWomanNumber(int totalWomanNumber) {
		this.totalWomanNumber.set(totalWomanNumber);
	}

	public Integer getTotalWomanNumber() {
		return this.totalWomanNumber.get();
	}

	public Integer getWomanAboveFourteenYear() {
		return womanAboveFourteenYear.get();
	}

	public SimpleIntegerProperty WomanAboveFourteenYear() {
		return this.womanAboveFourteenYear;
	}

	public void setWomanAboveFourteenYear(int number) {
		this.womanAboveFourteenYear.set(number);
	}

	public void addAddNewNumber(int number) {
		this.isAddNew.setValue(this.getIsAddNew() + number);
	}

	public void addTotalNumber(int number) {
		this.totalNumber.setValue(this.getTotalNumber() + number);
	}

	public void addTotalWomanNumber(int number) {
		this.totalWomanNumber.setValue(this.getTotalWomanNumber() + number);
	}

	public void addAboveFtNumber(int number) {
		this.aboveFourteenYear.setValue(this.getAboveFourteenYear() + number);
	}

	public void addWomanAboveFtNumber(int number) {
		this.womanAboveFourteenYear.setValue(this.getWomanAboveFourteenYear() + number);
	}

	public AddNew() {
		this.isAddNew = new SimpleIntegerProperty();
		this.totalNumber = new SimpleIntegerProperty();
		this.totalWomanNumber = new SimpleIntegerProperty();
		this.aboveFourteenYear = new SimpleIntegerProperty();
		this.womanAboveFourteenYear = new SimpleIntegerProperty();
	}

	public AddNew(int addNew, int totalNum, int totalAFNum, int totalWNum, int totalWAF) {
		this.isAddNew = new SimpleIntegerProperty(addNew);
		this.totalNumber = new SimpleIntegerProperty(totalNum);
		this.totalWomanNumber = new SimpleIntegerProperty(totalWNum);
		this.aboveFourteenYear = new SimpleIntegerProperty(totalAFNum);
		this.womanAboveFourteenYear = new SimpleIntegerProperty(totalWAF);
	}
}
