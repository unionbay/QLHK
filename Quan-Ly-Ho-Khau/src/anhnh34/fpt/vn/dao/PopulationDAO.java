package anhnh34.fpt.vn.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;

import anhnh34.fpt.vn.entity.PopCase;
import anhnh34.fpt.vn.entity.Population;
import anhnh34.fpt.vn.model.PopulationModel;
import anhnh34.fpt.vn.sp.PopulationCaseProcedures;
import anhnh34.fpt.vn.sp.PopulationProcedures;

public class PopulationDAO {
	
	final static Logger logger = Logger.getLogger(PopulationDAO.class);
	
	public String addPopulation(Population population) throws SQLException, ParseException {
		return PopulationProcedures.insertPopulation(population);
	}
	
	public void insertPopType(String popId, List<PopCase> types) throws SQLException {
		 PopulationCaseProcedures.insertPopTypes(popId, types);
	}

	public List<PopCase> getAllPopCase() throws SQLException {
		return PopulationCaseProcedures.getPopulationTypes();
	}

	public List<Population> getPopulationsByLocationId(String id) throws SQLException {
		return PopulationProcedures.getPopulationById(id);
	}

	public int countPopulation() {
		return PopulationProcedures.countNumberOfPopulation();
	}

	public int countSubPopulation(String parentId) {
		return PopulationProcedures.countSubPopulationNumber(parentId);
	}

	public List<Population> getPopulations(String locationId, Date fromDate, Date toDate) throws SQLException {
		return PopulationProcedures.getPopulations(locationId, fromDate, toDate);
	}

	public int deletePopulation(String populationId) throws SQLException {
		return PopulationProcedures.deletePopulation(populationId);
	}
	
	public int updatePopulationStatus(String populationId, String statusId) throws SQLException{
		return PopulationProcedures.updatePopulationStatus(populationId, statusId);
	}

	public List<Population> getSubPopulation(String parentId) throws SQLException {
		return PopulationProcedures.getSubPopulationById(parentId);	
	}
	
	public int updatePopulation(Population pop) throws SQLException{
		return PopulationProcedures.updatePopulation(pop);
	} 

}
