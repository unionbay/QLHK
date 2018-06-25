package anhnh34.fpt.vn.entity;

import javafx.beans.property.SimpleStringProperty;

public class PopCase {
	private final SimpleStringProperty typeId;
	private final SimpleStringProperty parentId;
	private final SimpleStringProperty value;
	private final SimpleStringProperty status;

	public String getTypeId() {
		return typeId.getValue();
	}

	public void setTypeId(String typeId) {
		this.typeId.set(typeId);
	}

	public String getParentId() {
		return parentId.getValue();
	}

	public void setParentId(String parentId) {
		this.parentId.set(parentId);
	}

	public String getValue() {
		return value.getValue();
	}

	public SimpleStringProperty ValueProperty() {
		return this.value;
	}

	public void setValue(String value) {
		this.value.set(value);
		;
	}

	public void setStatus(String status) {
		this.status.set(status);
	}

	public String getStatus() {
		return status.get();
	}

	/**
	 * @param typeId
	 * @param tKey
	 * @param value
	 * @param status
	 */
	public PopCase(String typeId, String parentId, String value, String status) {
		this.typeId = new SimpleStringProperty(typeId);
		this.parentId = new SimpleStringProperty(parentId);
		this.value = new SimpleStringProperty(value);
		this.status = new SimpleStringProperty(status);
	}
}
