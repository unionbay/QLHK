package anhnh34.fpt.vn.model;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import anhnh34.com.vn.util.Constans;
import anhnh34.fpt.vn.dao.FeeDAO;
import anhnh34.fpt.vn.dao.LocationDAO;
import anhnh34.fpt.vn.dao.PopulationDAO;
import anhnh34.fpt.vn.entity.Fee;
import anhnh34.fpt.vn.entity.Location;
import anhnh34.fpt.vn.entity.PopCase;
import anhnh34.fpt.vn.entity.Population;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PopulationModel {

	final static Logger logger = Logger.getLogger(PopulationModel.class);

	public PopulationModel() throws SQLException {
		this.population = new SimpleObjectProperty<Population>(new Population());
		this.subPopulations = FXCollections.observableArrayList(new ArrayList<Population>());
		this.originPopulationList = new ArrayList<Population>();
		this.selectedTypes = new ArrayList<PopCase>();

		PopulationDAO popDao = new PopulationDAO();			
		this.populationCases = popDao.getAllPopCase();		
	}

	public String getParentId() {
		return parentId;
	}

	public void setOriginPop(Population originPop) {
		this.originPop = originPop;
	}

	public List<PopCase> getPopulationCases() {
		return populationCases;
	}

	public void setPopulationCases(List<PopCase> populationCases) {
		this.populationCases = populationCases;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Population getPopulation() {
		return this.population.getValue();
	}

	public void setPopulation(Population population) {
		logger.info("set population: " + population.toString());
		this.population.set(population);
		this.setOriginPopulation(population);
	}

	public List<PopCase> getPopulationCase() throws SQLException {
		PopulationDAO popDao = new PopulationDAO();
		return popDao.getAllPopCase();
	}

	public void addNewPopulation() throws SQLException, ParseException {
		PopulationDAO popDao = new PopulationDAO();
		int numOfPopulation = popDao.countPopulation();
		numOfPopulation = numOfPopulation + 1;
		String newPopId = "P".concat(String.valueOf(numOfPopulation));
		this.getPopulation().setId(newPopId);
		this.getPopulation().setPopStatus(Constans.ACTIVE);
		String popId = popDao.addPopulation(this.getPopulation());
		popDao.insertPopType(popId, this.getSelectedTypes());						
	}

	public void updateForm() throws SQLException, ParseException {
		logger.info("Orgin pop: " + this.originPop.toString());
		logger.info("Pop: " + this.population.toString());
		// if (!this.originPop.equals(this.population.get())) {
		if (Constans.IN_ACTIVE.equals(this.population.get().getPopStatus())) {
			// this.deletePopulation(this.population.get());
		} else {
			if (this.updatePopulation(this.population.get()) == 1) {
				this.setOriginPopulation(this.population.get());
			}
		}
		// }

		for (int i = 0; i < this.subPopulations.size(); i++) {			
			Population pop = this.subPopulations.get(i);
			logger.info(pop.toString());
			if (pop.getId() == null || pop.getId().isEmpty()) {
				this.addNewSubPopulation(pop);
			} else if (Constans.IN_ACTIVE.equals(this.population.get().getPopStatus())) {
				this.deletePopulation(pop);
			} else {
				this.updatePopulation(pop);
			}

			// Population originPop = this.originPopulationList.get(i);
			// logger.info("origin sub pop: " + originPop.toString());
			// logger.info("sub pop: " + pop.toString());
			// if (pop != originPop) {
			// if
			// (Constans.IN_ACTIVE.equals(this.population.get().getPopStatus()))
			// {
			// this.deletePopulation(pop);
			// } else {
			// this.updatePopulation(pop);
			// }
			// }
		}
	}

	private void addNewSubPopulation(Population pop) throws SQLException, ParseException {
		PopulationDAO popDao = new PopulationDAO();
		pop.setParentId(getPopulation().getId());
		pop.setLocationId(getPopulation().getLocationId());
		pop.setLocation(getPopulation().getLocation());
		pop.setHouseHolderName(getPopulation().getHouseHolderName());
		int numberOfPopulation = popDao.countPopulation();
		String newId = "P".concat(String.valueOf(numberOfPopulation + 1));
		// String popTypeText = pop.getPopulationType();
		// PopCase selectedPopType = findPopulationTypeId(popTypeText.trim());
		// logger.info("Selected population type: " + popTypeText.trim());
		// pop.setPopulationType(selectedPopType.getTypeId());
		// pop.setSelectedPopulationType(selectedPopType);
		pop.setPopStatus(Constans.ACTIVE);
		pop.setId(newId);
		pop.setDateCreated(getPopulation().getDateCreated());
		popDao.addPopulation(pop);
	}

	public int deletePopulation(Population pop) throws SQLException {
		PopulationDAO popDao = new PopulationDAO();
		if (pop.getParentId() == null || pop.getParentId().isEmpty()) {
			popDao = new PopulationDAO();
			List<Population> subpopulation = popDao.getSubPopulation(pop.getParentId());
			for (Population subPop : subpopulation) {
				popDao.deletePopulation(subPop.getId());
			}
			return popDao.deletePopulation(pop.getId());
		}

		return popDao.deletePopulation(pop.getId());
	}

	private int updatePopulation(Population pop) throws SQLException {
		PopulationDAO popDAO = new PopulationDAO();
		return popDAO.updatePopulation(pop);
	}

	public void addSubPopulation() throws SQLException, ParseException {
		PopulationDAO popDao = new PopulationDAO();

		for (Population pop : getArrSubPopulations()) {
			pop.setParentId(getPopulation().getId());
			pop.setLocationId(getPopulation().getLocationId());
			pop.setLocation(getPopulation().getLocation());
			pop.setHouseHolderName(getPopulation().getHouseHolderName());
			int numberOfPopulation = popDao.countPopulation();
			String newId = "P".concat(String.valueOf(numberOfPopulation + 1));					
			pop.setPopStatus(Constans.ACTIVE);
			pop.setId(newId);
			pop.setDateCreated(getPopulation().getDateCreated());
			String popId = popDao.addPopulation(pop);
			popDao.insertPopType(popId, this.selectedTypes);
		}
	}

	public void setSubPopulations(List<Population> subPopulations) {
		if (!this.subPopulations.isEmpty()) {
			this.subPopulations.clear();
		}
		this.subPopulations.addAll(subPopulations);
	}

	public void addPopulation(Population pop) {
		this.subPopulations.add(pop);
	}

	public ObservableList<Population> getSubPopulations() {
		return this.subPopulations;
	}

	public List<Population> getArrSubPopulations() {
		return subPopulations.stream().collect(Collectors.toList());
	}

	public void updateSubOriginPopulations() {		
		this.setSubPopulationList(this.subPopulations);
	}

	private void setSubPopulationList(List<Population> populationList) {
		if (!this.originPopulationList.isEmpty()) {
			this.originPopulationList.clear();
		}

		for (Population pop : populationList) {
			Population population = new Population(pop);
			this.originPopulationList.add(population);
		}
	}
	
	public List<PopCase> getSelectedTypes() {
		return selectedTypes;
	}
	
	public void setSelectedTypes(List<PopCase> selectedTypes) {
		this.selectedTypes = selectedTypes;
	}

	public void loadSubpopulations() throws SQLException {
		PopulationDAO popDao = new PopulationDAO();
		List<Population> populationList = popDao.getSubPopulation(this.population.get().getId().trim());
		if (!populationList.isEmpty()) {
			this.setSubPopulations(populationList);
			this.setSubPopulationList(populationList);
		}
	}
	public void reset() {
		this.getPopulation().setHouseHolderName("");
		this.getPopulation().setLocation(new Location("LC009", "Phường Bắc Cường", "LC001"));
		this.getPopulation().setAddress("");
		this.getPopulation().setIsOutSide(false);
		this.getPopulation().setIsAddNew(false);
		this.getPopulation().setSelectedPopulationType(null);
		this.getPopulation().setReason("");
		this.getPopulation().setBirthday(null);
		this.getPopulation().setIsMan(false);
		this.getPopulation().setDocumentNumber("");
		this.getPopulation().setCaseNumber(0);
		this.getPopulation().setDateCreated(new Date());
		this.subPopulations.clear();
	}

	private void setOriginPopulation(Population pop) {
		this.originPop = new Population(pop);
	}

	private List<PopCase> populationCases;	
	private Population originPop;
	private List<PopCase> selectedTypes;
	private List<Population> originPopulationList;
	private final ObservableList<Population> subPopulations;
	private final SimpleObjectProperty<Population> population;
	private String parentId;
}
