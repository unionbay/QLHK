package anhnh34.com.vn.util;


import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import anhnh34.fpt.vn.entity.Population;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

public class DateEditingCell extends TableCell<Population, LocalDate> {
	
	private DatePicker datePicker;

	public DateEditingCell() {
	}
	
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(datePicker);
        }
    }
    
    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getDate().toString());
        setGraphic(null);
    }
    
    @Override
    public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getDate());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                setText(getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
                setGraphic(null);
            }
        }
    }

    private void createDatePicker() {
        datePicker = new DatePicker(getDate());
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setOnAction((e) -> {    
        	if(datePicker.getValue() != null){
        		
        		commitEdit(datePicker.getValue());
        	}            
        });
//        datePicker.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//            if (!newValue) {
//                commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
//            }
//        });
    }

    private LocalDate getDate() {
        return getItem() == null ? LocalDate.now() : getItem();
    }
}
