package anhnh34.fpt.vn.entity;

import javafx.beans.property.SimpleStringProperty;

public class Location {
	private final SimpleStringProperty id;
	private final SimpleStringProperty description;
	private final SimpleStringProperty parentId;

	public void setId(String locationId) {
		this.id.setValue(locationId);
	}

	public String getId() {
		return id.getValue();
	}

	public SimpleStringProperty DescriptionProperty() {
		return this.description;
	}

	public String getDescription() {
		return description.getValue();
	}

	public void setDescription(String description) {
		this.description.setValue(description);
	}

	public String getParentId() {
		return parentId.getValue();
	}

	public void setParentId(String parentId) {
		this.parentId.setValue(parentId);
	}

	public Location(String id, String description, String parentId) {		
		this.id = new SimpleStringProperty(id);
		this.description = new SimpleStringProperty(description);
		this.parentId = new SimpleStringProperty(parentId);
	}

}
