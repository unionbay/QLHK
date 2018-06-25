package anhnh34.fpt.vn.entity;

import javafx.beans.property.SimpleIntegerProperty;

public class OutSideOfLc {
	private final SimpleIntegerProperty totalNumber;
	private final SimpleIntegerProperty totalWomanNumber;
	private final SimpleIntegerProperty aboveFourteenYear;
	private final SimpleIntegerProperty womanAboveFourteenYear;
	private final SimpleIntegerProperty addNew;

	public SimpleIntegerProperty AddNewProperty(){
		return this.addNew;
	}
	
	public Integer getAddNew() {
		return addNew.getValue();
	}

	public void setAddNew(int addNew) {
		this.addNew.set(addNew);
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

	public SimpleIntegerProperty WomanAboveFourteenYear() {
		return this.womanAboveFourteenYear;
	}

	public Integer getWomanAboveFourteenYear() {
		return womanAboveFourteenYear.get();
	}

	public void setWomanAboveFourteenYear(int number) {
		this.womanAboveFourteenYear.set(number);
	}

	public void addTotalNumber(int number) {
		this.setTotalNumber(this.getTotalNumber() + number);
	}

	public void addAddNew(int number) {
		this.addNew.set(this.getAddNew() + number);
	}

	public void addTotalWomanNumber(int number) {
		this.totalWomanNumber.set(this.getTotalWomanNumber() + number);
	}

	public void addTotalAFNumber(int number) {
		this.aboveFourteenYear.set(this.getAboveFourteenYear() + number);
	}

	public void addWomanAFNumber(int number) {
		this.womanAboveFourteenYear.set(this.getWomanAboveFourteenYear() + number);
	}

	public OutSideOfLc() {
		this.totalNumber = new SimpleIntegerProperty();
		this.totalWomanNumber = new SimpleIntegerProperty();
		this.aboveFourteenYear = new SimpleIntegerProperty();
		this.womanAboveFourteenYear = new SimpleIntegerProperty();
		this.addNew = new SimpleIntegerProperty();
	}

	public OutSideOfLc(int totalNum, int totalAFNum, int totalWNum, int totalWAF, int addNew) {
		this.totalNumber = new SimpleIntegerProperty(totalNum);
		this.totalWomanNumber = new SimpleIntegerProperty(totalWNum);
		this.aboveFourteenYear = new SimpleIntegerProperty(totalAFNum);
		this.womanAboveFourteenYear = new SimpleIntegerProperty(totalWAF);
		this.addNew = new SimpleIntegerProperty(addNew);
	}
}
