package anhnh34.fpt.vn.entity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import anhnh34.com.vn.util.Constans;
import anhnh34.com.vn.util.Utility;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author anh-nguyen
 *
 */
public class Population {
	private final SimpleStringProperty id;
	private final SimpleStringProperty parentId;
	private final SimpleStringProperty houseHolderName;
	private final SimpleStringProperty fullName;
	private final SimpleStringProperty address;
	private final SimpleStringProperty locationId;
	private final SimpleStringProperty populationType;
	private final SimpleStringProperty addNew;
	private final SimpleStringProperty outSide;
	private final SimpleStringProperty reason;
	private final SimpleObjectProperty<LocalDate> birthday;
	private final SimpleStringProperty sex;
	private final SimpleStringProperty documentNumber;
	private final SimpleIntegerProperty caseNumber;
	private final ObjectProperty<LocalDate> dateCreated;
	private final SimpleStringProperty popStatus;
	private final SimpleObjectProperty<Double> externalFee;
	private final ObservableList<Population> populationList;
	private final SimpleBooleanProperty isOutSide;
	private final SimpleObjectProperty<Location> selectedLocation;
	private final SimpleObjectProperty<PopCase> selectedPopulationType;
	private final SimpleBooleanProperty isMan;
	private final SimpleBooleanProperty isAddNew;
	private final SimpleObjectProperty<Sex> sexP;

	private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Population.class);

	public SimpleObjectProperty<Sex> SexPProperty() {
		return this.sexP;
	}

	public Sex getSexP() {
		return sexP.get();
	}

	public void setSexP(Sex sex) {
		this.sexP.set(sex);
	}

	public SimpleBooleanProperty IsMan() {
		return isMan;
	}

	public Boolean getIsMan() {
		return isMan.getValue();
	}

	public void setIsMan(Boolean isMan) {
		this.isMan.set(isMan);
	}

	public SimpleBooleanProperty IsAddNew() {
		return isAddNew;
	}

	public boolean getIsAddNew() {
		return this.isAddNew.getValue();
	}

	public void setIsAddNew(boolean isAddNew) {
		this.isAddNew.setValue(isAddNew);
	}

	public SimpleBooleanProperty IsOutSide() {
		return isOutSide;
	}

	public void setIsOutSide(boolean isOutSide) {
		this.isOutSide.set(isOutSide);
	}

	public boolean getIsOutSide() {
		return this.isOutSide.getValue();
	}

	public SimpleObjectProperty<PopCase> SelectedPopulationType() {
		return selectedPopulationType;
	}

	public PopCase getSelectedPopulationType() {
		return selectedPopulationType.getValue();
	}

	public void setSelectedPopulationType(PopCase popType) {
		this.selectedPopulationType.set(popType);
	}

	public SimpleObjectProperty<Location> SelectedLocation() {
		return selectedLocation;
	}

	public Location getLocation() {
		return this.selectedLocation.getValue();
	}

	public void setLocation(Location location) {
		this.selectedLocation.set(location);
	}

	public Date getDateCreated() {
		return java.sql.Date.valueOf(this.dateCreated.get());
	}

	public SimpleStringProperty houseHolderName() {
		return houseHolderName;
	}

	public String getHouseHolderName() {
		return houseHolderName.getValue();
	}

	public void setHouseHolderName(String houseHolderName) {
		this.houseHolderName.set(houseHolderName);
	}

	public ObjectProperty<LocalDate> DateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date date) {
		Date input = new Date();
		LocalDate utilDate = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		this.dateCreated.set(utilDate);
	}

	public SimpleStringProperty OutSide() {
		return this.outSide;
	}

	public String getOutSide() {
		return outSide.get();
	}

	public void setOutSide(String os) {
		this.outSide.set(os);
	}

	public SimpleStringProperty AddNew() {
		return this.addNew;
	}

	public void setAddNew(String addNew) {
		this.addNew.set(addNew);
	}

	public String getAddNew() {
		return this.addNew.getValue();
	}

	public Double getExternalFee() {
		return externalFee.getValue();
	}

	public void setExternalFee(Double externalFee) {
		this.externalFee.set(externalFee);
	}

	public String getLocationId() {
		return locationId.getValue();
	}

	public void setLocationId(String id) {
		this.locationId.set(id);
	}

	public String getPopStatus() {
		return popStatus.getValue();
	}

	public void setPopStatus(String popStatus) {
		this.popStatus.setValue(popStatus);
	}

	public String getId() {
		return id.getValue();
	}

	public String getParentId() {
		return parentId.getValue();
	}

	public void setParentId(String parentId) {
		this.parentId.setValue(parentId);
	}

	public String getFullName() {
		return fullName.getValue();
	}

	public String getAddress() {
		return address.getValue();
	}

	public String getPopulationType() {
		return populationType.getValue();
	}

	public String getReason() {
		return reason.getValue();
	}

	public Date getBirthday() {
		return java.sql.Date.valueOf(this.birthday.getValue());
	}

	public String getSex() {
		if (isMan.getValue()) {
			return "Nam";
		}
		return "Nữ";
	}

	public SimpleStringProperty DocumentNumber() {
		return this.documentNumber;
	}

	public String getDocumentNumber() {
		return documentNumber.getValue();
	}

	public SimpleIntegerProperty CaseNumber() {
		return caseNumber;
	}

	public int getCaseNumber() {
		return caseNumber.getValue();
	}

	public void setId(String id) {
		this.id.setValue(id);
	}

	public void setFullName(String fullName) {
		this.fullName.set(fullName);
	}

	public SimpleStringProperty addressProperty() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address.setValue(address);
	}

	public void setPopulationType(String populationType) {
		this.populationType.setValue(populationType);
	}

	public SimpleStringProperty Reason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason.setValue(reason);
	}

	public void setBirthday(LocalDate date) {
		this.birthday.set(date);
	}

	public SimpleStringProperty fullNameProperty() {
		return this.fullName;
	}

	public ObjectProperty<LocalDate> birthdayProperty() {
		return this.birthday;
	}

	public SimpleStringProperty PopulationTypeProperty() {
		return this.populationType;
	}

	public SimpleStringProperty sexProperty() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex.setValue(sex);
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber.setValue(documentNumber);
	}

	public void setCaseNumber(int caseNumber) {
		this.caseNumber.setValue(caseNumber);
	}

	/**
	 * @param id
	 * @param fullName
	 * @param address
	 * @param populationType
	 * @param reason
	 * @param birthday
	 * @param sex
	 * @param documentNumber
	 * @param caseNumber
	 * @param feeType
	 * @param status
	 */
	public Population(String id, String parentId, String hName, String fullName, String locationId, String address,
			String populationType, String addNew, String outSide, String reason, LocalDate bithday, String sex,
			String documentNumber, int caseNumber, Double externalFee, LocalDate dateCreated, String status) {
		this.id = new SimpleStringProperty(id == null ? "" : id.trim());
		this.parentId = new SimpleStringProperty(parentId == null ? "" : parentId.trim());
		this.houseHolderName = new SimpleStringProperty(hName == null ? "" : hName.trim());
		this.fullName = new SimpleStringProperty(fullName == null ? "" : fullName.trim());
		this.locationId = new SimpleStringProperty(locationId == null ? "" : locationId.trim());
		this.address = new SimpleStringProperty(address == null ? "" : address.trim());
		this.populationType = new SimpleStringProperty(populationType == null ? "" : populationType.trim());

		if (outSide == null || outSide.trim().isEmpty()) {
			this.outSide = new SimpleStringProperty("");
			this.isOutSide = new SimpleBooleanProperty(false);
		} else {
			this.outSide = new SimpleStringProperty(Constans.OUTSIDE_LC);
			this.isOutSide = new SimpleBooleanProperty(true);
		}

		this.isOutSide.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true) {
					logger.info("Outside: " + true);
					setOutSide(Constans.OUTSIDE_LC);
				} else {
					logger.info("Outside: " + false);
					setOutSide("");
				}
			}
		});

		if (addNew == null || addNew.trim().isEmpty()) {
			this.addNew = new SimpleStringProperty("");
			this.isAddNew = new SimpleBooleanProperty(false);
		} else {
			this.addNew = new SimpleStringProperty(Constans.HO_MOI);
			this.isAddNew = new SimpleBooleanProperty(true);
		}

		this.isAddNew.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true) {
					setAddNew(Constans.HO_MOI);
				} else {
					setAddNew("");
				}
			}
		});

		this.isMan = new SimpleBooleanProperty();
		if (Sex.MAN_ID.compareTo(sex) == 0) {
			Sex sexObj = new Sex(Sex.MAN_ID, Sex.MAN_DESC);
			this.sexP = new SimpleObjectProperty<>(sexObj);
			this.setIsMan(true);
		} else {
			Sex sexObj = new Sex(Sex.WOMAN_ID, Sex.WOMAN_DESC);
			this.sexP = new SimpleObjectProperty<>(sexObj);
			this.setIsMan(false);
		}

		PopCase popType = Utility.getInstance().getPopulationType(populationType == null ? "" : populationType.trim());
		this.selectedPopulationType = new SimpleObjectProperty<PopCase>(popType);
		Location location = Utility.getInstance().getLocation(locationId == null ? "" : locationId.trim());
		this.selectedLocation = new SimpleObjectProperty<Location>(location);

		this.reason = new SimpleStringProperty(reason == null ? "" : reason.trim());
		this.birthday = new SimpleObjectProperty<LocalDate>(bithday);
		this.sex = new SimpleStringProperty(sex);
		this.documentNumber = new SimpleStringProperty(documentNumber == null ? "" : documentNumber.trim());
		this.caseNumber = new SimpleIntegerProperty(caseNumber);
		this.externalFee = new SimpleObjectProperty<Double>();
		this.dateCreated = new SimpleObjectProperty<LocalDate>(dateCreated);
		this.popStatus = new SimpleStringProperty(status.trim());
		this.populationList = FXCollections.observableArrayList(new ArrayList<Population>());

	}

	public Population() {
		this.id = new SimpleStringProperty("");
		this.parentId = new SimpleStringProperty("");
		this.locationId = new SimpleStringProperty("");
		this.fullName = new SimpleStringProperty("");
		this.houseHolderName = new SimpleStringProperty("");
		this.address = new SimpleStringProperty("");
		this.populationType = new SimpleStringProperty("");
		this.addNew = new SimpleStringProperty("");
		this.outSide = new SimpleStringProperty("");
		this.reason = new SimpleStringProperty("");
		this.birthday = new SimpleObjectProperty<LocalDate>(null);
		this.sex = new SimpleStringProperty("");
		this.documentNumber = new SimpleStringProperty("");
		this.caseNumber = new SimpleIntegerProperty(0);
		this.externalFee = new SimpleObjectProperty<Double>(0.0);
		this.dateCreated = new SimpleObjectProperty<LocalDate>();
		this.popStatus = new SimpleStringProperty("");
		this.populationList = FXCollections.observableArrayList(new ArrayList<Population>());
		this.selectedLocation = new SimpleObjectProperty<Location>(new Location("", "", ""));
		this.selectedPopulationType = new SimpleObjectProperty<PopCase>(null);
		this.isOutSide = new SimpleBooleanProperty();
		this.isAddNew = new SimpleBooleanProperty();
		this.isMan = new SimpleBooleanProperty();
		this.sexP = new SimpleObjectProperty<>(new Sex(Sex.WOMAN_ID, Sex.WOMAN_DESC));
	}

	public Population(Population pop) {
		this.id = new SimpleStringProperty(pop.getId());
		this.parentId = new SimpleStringProperty(pop.getParentId());
		this.locationId = new SimpleStringProperty(pop.getLocationId());
		this.fullName = new SimpleStringProperty(pop.getFullName());
		this.houseHolderName = new SimpleStringProperty(pop.getHouseHolderName());
		this.address = new SimpleStringProperty(pop.getAddress());
		this.populationType = new SimpleStringProperty(pop.getPopulationType());
		this.addNew = new SimpleStringProperty(pop.getAddNew());
		this.outSide = new SimpleStringProperty(pop.getOutSide());
		this.reason = new SimpleStringProperty(pop.getReason());
		this.birthday = new SimpleObjectProperty<LocalDate>(pop.birthday.get());
		this.sex = new SimpleStringProperty(pop.getSex());
		this.documentNumber = new SimpleStringProperty(pop.getDocumentNumber());
		this.caseNumber = new SimpleIntegerProperty(pop.getCaseNumber());
		this.externalFee = new SimpleObjectProperty<Double>(0.0);
		this.dateCreated = new SimpleObjectProperty<LocalDate>(pop.dateCreated.get());
		this.popStatus = new SimpleStringProperty(pop.getPopStatus());
		this.populationList = FXCollections.observableArrayList(new ArrayList<Population>());
		this.selectedLocation = new SimpleObjectProperty<Location>(pop.getLocation());
		this.selectedPopulationType = new SimpleObjectProperty<PopCase>(pop.getSelectedPopulationType());
		this.isOutSide = new SimpleBooleanProperty(pop.getIsOutSide());
		this.isAddNew = new SimpleBooleanProperty(pop.getIsAddNew());
		this.isMan = new SimpleBooleanProperty(pop.getIsMan());
		this.sexP = new SimpleObjectProperty<>(pop.getSexP());
	}

	@Override
	public boolean equals(Object obj) {
		Population popObj = (Population) obj;
		if (this.getId().compareTo(popObj.getId()) != 0) {
			return false;
		}

		if (this.getParentId() != null && this.getParentId().compareTo(popObj.getParentId()) != 0) {
			return false;
		}

		if (this.getHouseHolderName() != null
				&& this.getHouseHolderName().compareTo(popObj.getHouseHolderName()) != 0) {
			return false;
		}

		if (this.getFullName() != null && this.getFullName().compareTo(popObj.getFullName()) != 0) {
			return false;
		}

		if (this.getLocation() != null && this.getLocation().getId().compareTo(popObj.getLocation().getId()) != 0) {
			return false;
		}

		if (this.getAddress() != null && this.getAddress().compareTo(popObj.getAddress()) != 0) {
			return false;
		}

		if (this.getSelectedPopulationType() != null && this.getSelectedPopulationType().getTypeId()
				.compareTo(popObj.getSelectedPopulationType().getTypeId()) != 0) {
			return false;
		}

		if (this.getIsAddNew() != popObj.getIsAddNew()) {
			return false;
		}

		if (this.getIsOutSide() != popObj.getIsOutSide()) {
			return false;
		}

		if (this.getReason() != null && this.getReason().compareTo(popObj.getReason()) != 0) {
			return false;
		}

		if (this.getBirthday() != null && this.getBirthday().getTime() != popObj.getBirthday().getTime()) {
			return false;
		}

		if (this.getDocumentNumber() != null && this.getDocumentNumber().compareTo(popObj.getDocumentNumber()) != 0) {
			return false;
		}

		if (this.getCaseNumber() != popObj.getCaseNumber()) {
			return false;
		}

		if (this.getSexP().getId() != popObj.getSexP().getId()) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"Id:%s, parentId:%s, locationId:%s,house holder name: %s, fullName:%s, address:%s, "
						+ " popType:%s, reason:%s, birthday: %s, sex: %s, is outside %s, isAddNew: %s",
				this.getId(), this.getParentId(), this.getLocation().getId(), this.getHouseHolderName(),
				this.getFullName(), this.getAddress(),
				this.getSelectedPopulationType() == null ? ""
						: this.getSelectedPopulationType().getTypeId() + " : "
								+ this.getSelectedPopulationType().getValue(),
				this.getReason(), this.getBirthday().toString(), this.getSexP().getDesc(), this.getOutSide(),
				this.getAddNew());
	}
}
