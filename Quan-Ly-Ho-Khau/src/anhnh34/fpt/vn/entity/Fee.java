package anhnh34.fpt.vn.entity;

public class Fee {
	private String id;
	private double fee;
	private String description;
	private String status;
	public String getId() {
		return id;
	}
	public double getFee() {
		return fee;
	}
	public String getDescription() {
		return description;
	}
	public String getStatus() {
		return status;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @param id
	 * @param fee
	 * @param description
	 * @param status
	 */
	public Fee(String id, double fee, String description, String status) {
		super();
		this.id = id;
		this.fee = fee;
		this.description = description;
		this.status = status;
	}
}
