package anhnh34.fpt.vn.entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Report {

	public SimpleStringProperty locationId() {
		return this.locationId;
	}

	public String getLocationId() {
		return locationId.getValue();
	}

	public void setLocationId(String id) {
		this.locationId.set(id);
	}

	public RegisterOfBirth getRegisterOfBirth() {
		return registerOfBirth.getValue();
	}

	public void setRegisterOfBirth(RegisterOfBirth registerOfBirth) {
		this.registerOfBirth.set(registerOfBirth);
	}

	public AddMore getAddMore() {
		return addMore.getValue();
	}

	public void setAddMore(AddMore addMore) {
		this.addMore.set(addMore);
	}

	public AddNew getAddNew() {
		return addNew.getValue();
	}

	public void setAddNew(AddNew addNew) {
		this.addNew.set(addNew);
	}

	public OutSideOfLc getOutOfLc() {
		return this.outOfLc.getValue();
	}

	public void setOutOfLc(OutSideOfLc outOfLc) {
		this.outOfLc.set(outOfLc);
	}

	public SimpleIntegerProperty ToDeny() {
		return this.toDeny;
	}

	public void setToDeny(Integer toDeny) {
		this.toDeny.set(toDeny);
	}

	public Integer getToDeny() {
		return toDeny.get();
	}

	public SimpleIntegerProperty Reallocate() {
		return this.reallocate;
	}

	public void setReallocate(Integer reallocate) {
		this.reallocate.set(reallocate);
	}

	public Integer getReallocate() {
		return reallocate.getValue();
	}

	public SimpleIntegerProperty SeperatePopulation() {
		return this.seperatePopulation;
	}

	public void setSeperatePopulation(Integer seperatePopulation) {
		this.seperatePopulation.set(seperatePopulation);
	}

	public Integer getSeperatePopulation() {
		return seperatePopulation.getValue();
	}

	public SimpleIntegerProperty DeathCertificate() {
		return this.deathCertificate;
	}

	public Integer getDeathCertificate() {
		return deathCertificate.getValue();
	}

	public void setDeathCertificate(Integer deathCertificate) {
		this.deathCertificate.set(deathCertificate);
	}

	public SimpleIntegerProperty ChangeDistrict() {
		return this.changeDistrict;
	}

	public Integer getChangeDistrict() {
		return changeDistrict.getValue();
	}

	public void setChangeDistrict(Integer changeDistrict) {
		this.changeDistrict.set(changeDistrict);
	}

	public SimpleIntegerProperty ChangePopDocument() {
		return this.changePopDocument;
	}

	public Integer getChangePopDocument() {
		return changePopDocument.getValue();
	}

	public void setChangePopDocument(Integer changePopDocument) {
		this.changePopDocument.set(changePopDocument);
	}

	public SimpleIntegerProperty ChangePopulation() {
		return this.changePopulation;
	}

	public Integer getChangePopulation() {
		return changePopulation.getValue();
	}

	public void setChangePopulation(Integer changePopulation) {
		this.changePopulation.set(changePopulation);
	}

	public SimpleIntegerProperty JoinPopDocDocument() {
		return this.joinPopDocDocument;
	}

	public Integer getJoinPopDocDocument() {
		return joinPopDocDocument.getValue();
	}

	public void setJoinPopDocDocument(Integer joinPopDocDocument) {
		this.joinPopDocDocument.setValue(joinPopDocDocument);
	}

	public Location getLocation() {
		return this.location.getValue();
	}

	public void Location(Location l) {
		this.location.set(l);
	}

	public void setLocation(Location location) {
		this.location.set(location);
	}
	
	public void setFee(double fee){
		this.fee.set(fee);
	}

	public Double getFee() {
		return this.fee.getValue();
	}

	/**
	 * @param locationId
	 * @param registerOfBirth
	 * @param addNew
	 * @param addMore
	 * @param outOfLc
	 * @param toDeny
	 * @param reallocate
	 * @param seperatePopulation
	 * @param deathCertificate
	 * @param changePopulation
	 * @param changeDistrict
	 * @param changePopDocument
	 * @param joinPopDocDocument
	 */
	public Report(String locationId, RegisterOfBirth registerOfBirth, AddNew addNew, AddMore addMore,
			OutSideOfLc outOfLc, int toDeny, int reallocate, int seperatePopulation, int deathCertificate,
			int changePopulation, int changeDistrict, int changePopDocument, int joinPopDocDocument) {

		this.locationId = new SimpleStringProperty(locationId);
		this.registerOfBirth = new SimpleObjectProperty<RegisterOfBirth>(registerOfBirth);
		this.addNew = new SimpleObjectProperty<AddNew>(addNew);
		this.addMore = new SimpleObjectProperty<AddMore>(addMore);
		this.outOfLc = new SimpleObjectProperty<OutSideOfLc>(outOfLc);
		this.toDeny = new SimpleIntegerProperty(toDeny);
		this.reallocate = new SimpleIntegerProperty(reallocate);
		this.seperatePopulation = new SimpleIntegerProperty(seperatePopulation);
		this.deathCertificate = new SimpleIntegerProperty(deathCertificate);
		this.changePopulation = new SimpleIntegerProperty(changePopulation);
		this.changeDistrict = new SimpleIntegerProperty(changeDistrict);
		this.changePopDocument = new SimpleIntegerProperty(changePopDocument);
		this.joinPopDocDocument = new SimpleIntegerProperty(joinPopDocDocument);
		this.location = new SimpleObjectProperty<Location>();
		this.fee = new SimpleDoubleProperty();
	}

	public Report() {
		this.locationId = new SimpleStringProperty();
		this.registerOfBirth = new SimpleObjectProperty<RegisterOfBirth>();
		this.addNew = new SimpleObjectProperty<AddNew>();
		this.addMore = new SimpleObjectProperty<AddMore>();
		this.outOfLc = new SimpleObjectProperty<OutSideOfLc>();
		this.toDeny = new SimpleIntegerProperty();
		this.reallocate = new SimpleIntegerProperty();
		this.seperatePopulation = new SimpleIntegerProperty();
		this.deathCertificate = new SimpleIntegerProperty();
		this.changePopulation = new SimpleIntegerProperty();
		this.changeDistrict = new SimpleIntegerProperty();
		this.changePopDocument = new SimpleIntegerProperty();
		this.joinPopDocDocument = new SimpleIntegerProperty();
		this.location = new SimpleObjectProperty<Location>();
		this.fee = new SimpleDoubleProperty();
	}

	private final SimpleStringProperty locationId;
	private final SimpleObjectProperty<RegisterOfBirth> registerOfBirth;
	private final SimpleObjectProperty<AddNew> addNew;
	private final SimpleObjectProperty<AddMore> addMore;
	private final SimpleObjectProperty<OutSideOfLc> outOfLc;
	private final SimpleIntegerProperty toDeny;
	private final SimpleIntegerProperty reallocate;
	private final SimpleIntegerProperty seperatePopulation;
	private final SimpleIntegerProperty deathCertificate;
	private final SimpleIntegerProperty changePopulation;
	private final SimpleIntegerProperty changeDistrict;
	private final SimpleIntegerProperty changePopDocument;
	private final SimpleIntegerProperty joinPopDocDocument;
	private final SimpleObjectProperty<Location> location;
	private final SimpleDoubleProperty fee;

}
