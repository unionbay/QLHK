package anhnh34.com.vn.util;

import java.sql.SQLException;
import org.apache.log4j.Logger;

import anhnh34.fpt.vn.entity.Population;
import anhnh34.fpt.vn.entity.Sex;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;

public class comboboxSexEditingCell extends TableCell<Population, Sex> {

	private static final Logger logger = Logger.getLogger(comboboxSexEditingCell.class);
	private ComboBox<Sex> comboBox;

	public comboboxSexEditingCell() {
	}

	@Override
	public void startEdit() {
		if (!isEmpty()) {
			super.startEdit();
			try {
				createComboBox();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setText(null);
			setGraphic(comboBox);
		}
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText(getTyp() == null ? "" : getTyp().getDesc());
		setGraphic(null);
	}

	@Override
	public void updateItem(Sex item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				if (comboBox != null) {
					//logger.info("Updating sex: " + item.getDesc());
					comboBox.setValue(item);
				}
				setText(getTyp() == null ? "" : getTyp().getDesc());
				setGraphic(comboBox);
			} else {
				setText(getTyp() == null ? "" : getTyp().getDesc());
				setGraphic(null);
			}
		}
	}

	private void createComboBox() throws SQLException {		
		ObservableList<Sex> typData = FXCollections.observableArrayList(new Sex(Sex.MAN_ID, Sex.MAN_DESC),
				new Sex(Sex.WOMAN_ID, Sex.WOMAN_DESC));

		comboBox = new ComboBox<>(typData);
		comboBoxConverter(comboBox);
		comboBox.valueProperty().set(getTyp());
		comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		comboBox.setOnAction((e) -> {
			//logger.info("commited item" + comboBox.getSelectionModel().getSelectedItem().getDesc());
			commitEdit(comboBox.getSelectionModel().getSelectedItem());
		});

		comboBox.valueProperty().addListener(new ChangeListener<Sex>() {
			@Override
			public void changed(ObservableValue<? extends Sex> observable, Sex oldValue, Sex newValue) {
				//logger.info("commited item" + newValue.getDesc());
				commitEdit(newValue);
			}
		});

		// comboBox.focusedProperty().addListener((ObservableValue<? extends
		// Boolean> observable, Boolean oldValue, Boolean newValue) -> {
		// logger.info("newvalue: ");
		// });
	}

	private void comboBoxConverter(ComboBox<Sex> comboBox) {
		// Define rendering of the list of values in ComboBox drop down.
		comboBox.setCellFactory((c) -> {
			return new ListCell<Sex>() {
				@Override
				protected void updateItem(Sex item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.getDesc());
					}
				}
			};
		});
	}

	private Sex getTyp() {
		return getItem() == null ? null : getItem();
	}
}
