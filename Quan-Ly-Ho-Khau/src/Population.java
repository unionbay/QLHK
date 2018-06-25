import java.sql.Date;

public class Population {
	private String populationId;
	private String populationName;
	private String address;
	private String popType;
	private String contents;
	private Date dateOfBirth;
	private int sex;
	private String documentNumber;
	private String numberOfCase;
	private double fee;

	public String getPopulationId() {
		return populationId;
	}

	public String getPopulationName() {
		return populationName;
	}

	public String getAddress() {
		return address;
	}

	public String getPopType() {
		return popType;
	}

	public String getContents() {
		return contents;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public int getSex() {
		return sex;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public String getNumberOfCase() {
		return numberOfCase;
	}

	public double getFee() {
		return fee;
	}

	public void setPopulationId(String populationId) {
		this.populationId = populationId;
	}

	public void setPopulationName(String populationName) {
		this.populationName = populationName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPopType(String popType) {
		this.popType = popType;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public void setNumberOfCase(String numberOfCase) {
		this.numberOfCase = numberOfCase;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public Population() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param populationId
	 * @param populationName
	 * @param address
	 * @param popType
	 * @param contents
	 * @param dateOfBirth
	 * @param sex
	 * @param documentNumber
	 * @param numberOfCase
	 * @param fee
	 */
	public Population(String populationId, String populationName, String address, String popType, String contents,
			Date dateOfBirth, int sex, String documentNumber, String numberOfCase, double fee) {
		super();
		this.populationId = populationId;
		this.populationName = populationName;
		this.address = address;
		this.popType = popType;
		this.contents = contents;
		this.dateOfBirth = dateOfBirth;
		this.sex = sex;
		this.documentNumber = documentNumber;
		this.numberOfCase = numberOfCase;
		this.fee = fee;
	}
}
