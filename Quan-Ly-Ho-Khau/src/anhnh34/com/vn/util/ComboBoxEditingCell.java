package anhnh34.com.vn.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import anhnh34.fpt.vn.entity.PopCase;
import anhnh34.fpt.vn.entity.Population;
import anhnh34.fpt.vn.model.PopulationModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;

public class ComboBoxEditingCell extends TableCell<Population, PopCase>{
	
	private ComboBox<PopCase> comboBox;
	
	public ComboBoxEditingCell(){		
	}
	
	
	@Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createComboBox();
            setText(getTyp() == null ? "" : getTyp().getValue());
            setGraphic(comboBox);
        }
    }
	
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getTyp() == null ? "" : getTyp().getValue());
        setGraphic(null);
    }
    
    
    @Override
    public void updateItem(PopCase item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (comboBox != null) {
                    comboBox.setValue(item);                    
                }
                setText(getTyp() == null ? "" : getTyp().getValue());
                setGraphic(comboBox);
            } else {
                setText(getTyp() == null ? "" : getTyp().getValue());
                setGraphic(null);
            }
        }
    }
    
    private void createComboBox() {
    	PopulationModel populationModel = null;
		try {
			populationModel = new PopulationModel();
		} catch (SQLException e2) {			
			e2.printStackTrace();
		}
		List<PopCase> popCaseList = new ArrayList<PopCase>();		
		List<String> stringList = new ArrayList<>();
		try {
			popCaseList = populationModel.getPopulationCase();
			
			for(PopCase pc : popCaseList){
				stringList.add(pc.getValue());
			}
			
		} catch (SQLException e1) {			
			e1.printStackTrace();
		}
	    ObservableList<PopCase> typData
        = FXCollections.observableArrayList(popCaseList);            
		
        comboBox = new ComboBox<>(typData);
        comboBoxConverter(comboBox);
        comboBox.valueProperty().set(getTyp());
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        comboBox.setOnAction((e) -> {            
            commitEdit(comboBox.getSelectionModel().getSelectedItem());
        });
//        comboBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//            if (!newValue) {
//                commitEdit(comboBox.getSelectionModel().getSelectedItem());
//            }
//        });
    }
    
    private void comboBoxConverter(ComboBox<PopCase> comboBox) {
        // Define rendering of the list of values in ComboBox drop down. 
        comboBox.setCellFactory((c) -> {
            return new ListCell<PopCase>() {
                @Override
                protected void updateItem(PopCase item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getValue());
                    }
                }
            };
        });
    }
    
    
   
    
    private PopCase getTyp() {
        return getItem() == null ? null : getItem();
    }
    
}
