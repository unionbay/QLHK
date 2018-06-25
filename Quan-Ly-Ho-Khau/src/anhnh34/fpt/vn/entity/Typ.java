package anhnh34.fpt.vn.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Typ {
	private final SimpleStringProperty typ;
	
	public Typ(String typ){
		this.typ = new SimpleStringProperty(typ);
	}
	
	public String getTyp() {
		return typ.getValue();
	}
	
	
	 public StringProperty typProperty() {
         return this.typ;
     }
	 
	 public void setTyp(String typ) {
         this.typ.set(typ);
     }

     @Override
     public String toString() {
         return typ.get();
     }	 
     
     public Typ(){
    	 typ = new SimpleStringProperty();
     }
}
