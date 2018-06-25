package anhnh34.fpt.vn.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import anhnh34.com.vn.util.Constans;
import anhnh34.com.vn.util.Utility;
import anhnh34.fpt.vn.dao.LocationDAO;
import anhnh34.fpt.vn.dao.PopulationDAO;
import anhnh34.fpt.vn.entity.AddMore;
import anhnh34.fpt.vn.entity.AddNew;
import anhnh34.fpt.vn.entity.Location;
import anhnh34.fpt.vn.entity.OutSideOfLc;
import anhnh34.fpt.vn.entity.PopCase;
import anhnh34.fpt.vn.entity.Population;
import anhnh34.fpt.vn.entity.RegisterOfBirth;
import anhnh34.fpt.vn.entity.Report;
import anhnh34.fpt.vn.sp.PopulationProcedures;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author anh-nguyen
 *
 */
/**
 * @author anh-nguyen
 *
 */
/**
 * @author anh-nguyen
 *
 */
public class ReportModel {
	final static Logger logger = Logger.getLogger(ReportModel.class);

	public ReportModel() throws SQLException {
		this.reportList = FXCollections.observableArrayList(new ArrayList<Population>());
		this.reports = new ArrayList<Report>();
		this.report = new SimpleObjectProperty<Report>(new Report());
		this.regisDateOfBirth = new SimpleObjectProperty<RegisterOfBirth>(new RegisterOfBirth());
		this.addMore = new SimpleObjectProperty<AddMore>(new AddMore());
		this.addNew = new SimpleObjectProperty<AddNew>(new AddNew());
		this.outSideLc = new SimpleObjectProperty<OutSideOfLc>(new OutSideOfLc());
		this.popTypeList = FXCollections.observableArrayList(new PopulationDAO().getAllPopCase());
		this.locationList = FXCollections.observableArrayList(new LocationDAO().getLocations());
		this.popList = new ReadOnlyObjectWrapper<ObservableList<Population>>(
				FXCollections.observableArrayList(new ArrayList<Population>()));
		this.report.getValue().setRegisterOfBirth(this.regisDateOfBirth.getValue());
		this.report.getValue().setAddMore(this.addMore.getValue());
		this.report.getValue().setAddNew(this.addNew.getValue());
		this.report.getValue().setOutOfLc(this.outSideLc.getValue());
	}

	public ObservableList<Location> getLocationList() {
		return this.locationList;
	}

	public void initModel() throws SQLException {
		PopulationDAO popDAO = new PopulationDAO();
		LocationDAO locationDao = new LocationDAO();

		if (this.popTypeList.isEmpty()) {
			this.popTypeList.setAll(popDAO.getAllPopCase());
		}

		if (this.locationList.isEmpty()) {
			this.locationList.setAll(locationDao.getLocations());
		}
	}

	public ObjectProperty<ObservableList<Population>> PopList() {
		return popList;
	}

	public ObservableList<Population> getPopList() {
		return popList.getValue();
	}

	public void setPopList(List<Population> arrPopulation) {
		this.popList.set(FXCollections.observableArrayList(arrPopulation));
	}

	public Report getReport() {
		return this.report.getValue();
	}

	public void setReport(Report report) {
		this.report.set(report);
	}

	public List<Population> getPopulationList() {
		return populationList;
	}

	public void getReport(String locationId, java.sql.Date fromDate, java.sql.Date toDate) throws SQLException {
		this.reset();
		this.report.get().setLocationId(locationId);
		this.getReport().setLocation(selectedLocation);
		PopulationDAO populationDao = new PopulationDAO();
		this.populationList = populationDao.getPopulations(locationId, fromDate, toDate);
		this.setPopList(this.populationList);
		
		if (Constans.LC_LOCATION.equalsIgnoreCase(locationId)) {
			loadDistrictReports(populationList);
		}		
		
		this.setReport(this.caculateReports(this.report.getValue(), this.populationList,
				Utility.getInstance().getLocation(locationId)));
		this.getReport().setFee(this.calculateTotalFee(this.getReport()));
		this.reports.add(this.getReport());
	}

	private Report caculateReports(Report report, List<Population> popList, Location location) {
		int toDeny = 0; // Đính chính
		int reallocate = 0; // Cấp lại
		int seperatePopulation = 0; // Tách hộ
		int deathCertificate = 0; // chứng tử
		int changePopulation = 0; // Chuyển khẩu
		int changeDistrict = 0; // Chuyển phường
		int changePopDocument = 0; // Đổi sổ
		int joinPopulation = 0; // Hợp hộ

		if (location != null) {
			report.setLocation(location);
		}

		for (Population pop : popList) {
			switch (pop.getPopulationType()) {
			case Constans.DINH_CHINH:
				toDeny++;
				break;
			case Constans.CAP_LAI:
				reallocate++;
				break;
			case Constans.TACH_HO:
				seperatePopulation++;
				break;
			case Constans.CHUNG_TU:
				deathCertificate++;
				break;
			case Constans.CHUYEN_kHAU:
				changePopulation += 1;
				break;
			case Constans.CHUYEN_PHUONG:
				changeDistrict += 1;
				break;
			case Constans.DOI_SO:
				changePopDocument += 1;
				break;
			case Constans.HOP_HO:
				joinPopulation += 1;
				break;
			case Constans.KHAI_SINH:
				this.caculateRegisterOfBirth(report, pop);
				break;
			case Constans.NHAP_THEM:
				this.caculateAddMore(report, pop);
				break;
			}

			this.calculateAddNew(report, pop);
			this.calculateOutSideOfLc(report, pop);
		}

		report.setToDeny(toDeny);
		report.setReallocate(reallocate);
		report.setSeperatePopulation(seperatePopulation);
		report.setDeathCertificate(deathCertificate);
		report.setChangePopulation(changePopulation);
		report.setChangeDistrict(changeDistrict);
		report.setChangeDistrict(changeDistrict);
		report.setChangePopDocument(changePopDocument);
		report.setJoinPopDocDocument(joinPopulation);

		return report;
	}

	List<Population> subPopulationList = new ArrayList<Population>();

	private void loadDistrictReports(List<Population> popList) {
		List<Location> locationList = Utility.getInstance().getLocationList();

		for (Location location : locationList) {
			if (Constans.LC_LOCATION.equalsIgnoreCase(location.getId().trim())) {
				continue;
			}
			subPopulationList.clear();

			for (Population pop : popList) {
				if (pop.getLocation().getId().equalsIgnoreCase(location.getId())) {
					subPopulationList.add(pop);
				}
			}

			// create report and calculate
			Report report = new Report();
			report.setLocation(location);
			report.setLocationId(location.getId());
			RegisterOfBirth regisOfBirth = new RegisterOfBirth();
			AddMore addMore = new AddMore();
			AddNew addNew = new AddNew();
			OutSideOfLc outOfLc = new OutSideOfLc();

			report.setRegisterOfBirth(regisOfBirth);
			report.setAddMore(addMore);
			report.setAddNew(addNew);
			report.setOutOfLc(outOfLc);

			report = this.caculateReports(report, subPopulationList, location);
			report.setFee(this.calculateTotalFee(report));
			this.reports.add(report);			
		}
	}

	/**
	 * Công thức khai sinh
	 */
	// thêm khẩu mới.
	private void handleRegisterOfBirth(Population pop) {
		this.regisDateOfBirth.getValue().addTotalNumber(1);
		// Nữ
		if (Constans.SEX_WOMAN.compareToIgnoreCase(pop.getSexP().getId().trim()) == 0) {
			this.regisDateOfBirth.getValue().addTotalWomanNumber(1);
		}
	}

	private void caculateRegisterOfBirth(Report report, Population pop) {
		report.getRegisterOfBirth().addTotalNumber(1);
		// Nữ
		if (Constans.SEX_WOMAN.compareToIgnoreCase(pop.getSexP().getId()) == 0) {
			report.getRegisterOfBirth().addTotalWomanNumber(1);
		}
	}

	private void caculateAddMore(Report report, Population pop) {
		// Khẩu
		report.getAddMore().addTotalNumber(1);
		if (Constans.SEX_WOMAN.compareToIgnoreCase(pop.getSexP().getId()) == 0) {
			report.getAddMore().addTotalWomanNumber(1);
		}

		// Trên 14 tuổi
		if (this.aboveFourteenYears(pop.getBirthday())) {
			report.getAddMore().addAboveFourteenYear(1);
			// Nữ trên 14 tuổi
			if (Constans.SEX_WOMAN.compareToIgnoreCase(pop.getSexP().getId()) == 0) {
				report.getAddMore().addWomanAboveFourteenYear(1);
			}
		}
	}

	private void calculateAddNew(Report report, Population pop) {
		// Hộ
		if (pop.getAddNew() != null && "X".compareToIgnoreCase(pop.getAddNew().trim()) == 0) {
			report.getAddNew().addAddNewNumber(1);
		}

		// Khẩu. ;
		PopCase popCase = this.popTypeList.stream().filter(pt -> pt.getTypeId().equals(pop.getPopulationType().trim()))
				.findFirst().orElse(null);

		if (popCase != null && "T002".compareToIgnoreCase(popCase.getParentId()) == 0) {
			report.getAddNew().addTotalNumber(1);

			// Nữ
			if (Constans.SEX_WOMAN.compareToIgnoreCase(pop.getSexP().getId()) == 0) {
				report.getAddNew().addTotalWomanNumber(1);
			}
			// Trên 14 tuổi
			if (this.aboveFourteenYears(pop.getBirthday())) {
				report.getAddNew().addAboveFtNumber(1);

				// Nữ trên 14 tuổi.
				if (Constans.SEX_WOMAN.compareToIgnoreCase(pop.getSexP().getId()) == 0) {
					report.getAddNew().addWomanAboveFtNumber(1);
				}
			}
		}

	}

	private void calculateOutSideOfLc(Report report, Population pop) {
		if (pop.IsOutSide().getValue() && pop.IsAddNew().getValue()) {
			// Hộ
			report.getOutOfLc().addAddNew(1);
		}

		if (pop.IsOutSide().getValue()) {
			// Khẩu
			report.getOutOfLc().addTotalNumber(1);
			if (this.aboveFourteenYears(pop.getBirthday())) {
				// Trên 14 tuổi
				report.getOutOfLc().addTotalAFNumber(1);
				if (Constans.SEX_WOMAN.compareToIgnoreCase(pop.getSexP().getId()) == 0) {
					// Nữ trên 14 tuổi
					report.getOutOfLc().addWomanAFNumber(1);
				}
			}
			if (Constans.SEX_WOMAN.compareToIgnoreCase(pop.getSexP().getId()) == 0) {
				// Nữ
				this.outSideLc.getValue().addTotalWomanNumber(1);
			}
		}

	}

	private void getSelectedLocation(String locationId) {
		for (Location lc : locationList) {
			String id = lc.getId().trim();
			if (locationId.trim().compareTo(id) == 0) {
				System.out.println("Location finded: " + locationId);
				ReportModel.selectedLocation = lc;
				break;
			}
		}
	}

	/**
	 * Công thức tính nhập mới.
	 **/
	private void calculateAddNew(Population pop) {
		logger.info(pop.getAddNew().trim());
		// Hộ
		if (pop.getAddNew() != null && "X".compareToIgnoreCase(pop.getAddNew().trim()) == 0) {
			this.addNew.getValue().addAddNewNumber(1);
		}

		// Khẩu. ;
		PopCase popCase = this.popTypeList.stream().filter(pt -> pt.getTypeId().equals(pop.getPopulationType().trim()))
				.findFirst().orElse(null);

		if (popCase != null && "T002".compareToIgnoreCase(popCase.getParentId()) == 0) {
			this.addNew.getValue().addTotalNumber(1);

			// Nữ
			if (Constans.SEX_WOMAN.compareToIgnoreCase(pop.getSexP().getId()) == 0) {
				this.addNew.getValue().addTotalWomanNumber(1);
			}
			// Trên 14 tuổi
			if (this.aboveFourteenYears(pop.getBirthday())) {
				this.addNew.getValue().addAboveFtNumber(1);

				// Nữ trên 14 tuổi.
				if (Constans.SEX_WOMAN.compareToIgnoreCase(pop.getSexP().getId()) == 0) {
					this.addNew.getValue().addWomanAboveFtNumber(1);
				}
			}
		}
	}

	/**
	 * Công thức tính nhập thêm.
	 */
	private void calculateAddMore(Population pop) {
		// Khẩu
		this.addMore.getValue().addTotalNumber(1);
		logger.info(pop.getSex() + " " + pop.getSexP().getId());
		if (Constans.SEX_WOMAN.compareToIgnoreCase(pop.getSexP().getId()) == 0) {
			this.addMore.getValue().addTotalWomanNumber(1);
		}

		// Trên 14 tuổi
		if (this.aboveFourteenYears(pop.getBirthday())) {
			this.addMore.getValue().addAboveFourteenYear(1);
			// Nữ trên 14 tuổi
			if (Constans.SEX_WOMAN.compareToIgnoreCase(pop.getSexP().getId()) == 0) {
				this.addMore.getValue().addWomanAboveFourteenYear(1);
			}
		}
	}

	/**
	 * Công thức tính ngoài tỉnh
	 */
	private void calculateOutSideLc(Population pop) {

		if (pop.IsOutSide().getValue() && pop.IsAddNew().getValue()) {
			// Hộ
			this.outSideLc.getValue().addAddNew(1);
		}

		if (pop.IsOutSide().getValue()) {
			// Khẩu
			this.outSideLc.getValue().addTotalNumber(1);
			if (this.aboveFourteenYears(pop.getBirthday())) {
				// Trên 14 tuổi
				this.outSideLc.getValue().addTotalAFNumber(1);
				if (Constans.SEX_WOMAN.compareToIgnoreCase(pop.getSexP().getId()) == 0) {
					// Nữ trên 14 tuổi
					this.outSideLc.getValue().addWomanAFNumber(1);
				}
			}
			if (Constans.SEX_WOMAN.compareToIgnoreCase(pop.getSexP().getId()) == 0) {
				// Nữ
				this.outSideLc.getValue().addTotalWomanNumber(1);
			}
		}

	}

	/**
	 * Fomular check if age greater than 14.
	 */
	private boolean aboveFourteenYears(Date birthday) {
		Calendar now = Calendar.getInstance();
		int currentYear = now.get(Calendar.YEAR);
		now.setTime(birthday);
		int birthdayYear = now.get(Calendar.YEAR);
		if (currentYear - birthdayYear > 14) {
			return true;
		}
		return false;
	}

	// public void initialize() throws SQLException{
	// locationList =
	// FXCollections.observableArrayList(LocationProcedure.getLocation());
	// }

	public void removeItem(Population pop) {
		this.getPopList().remove(pop);
	}

	private double calculateTotalFee(Report report) {
		double totalFee = 0;
		double transactionFee = 20000;
		totalFee += report.getToDeny() * transactionFee;
		totalFee += report.getReallocate() * transactionFee;
		totalFee += report.getSeperatePopulation() * transactionFee;
		totalFee += report.getChangePopulation() * transactionFee;
		totalFee += report.getChangeDistrict() * transactionFee;
		totalFee += report.getChangePopDocument() * transactionFee;

		// Nhap moi
		totalFee += report.getAddNew().getTotalNumber() * transactionFee;
		totalFee += report.getAddMore().getTotalNumber() * transactionFee;
		totalFee += report.getOutOfLc().getTotalNumber() * transactionFee;
		return totalFee;
	}

	private void reset() {

		this.getReport().setToDeny(0);
		this.getReport().setReallocate(0);
		this.getReport().setSeperatePopulation(0);
		this.getReport().setDeathCertificate(0);
		this.getReport().setChangePopulation(0);
		this.getReport().setChangeDistrict(0);
		this.getReport().setChangePopDocument(0);

		this.getReport().getRegisterOfBirth().setTotalNumber(0);
		this.getReport().getRegisterOfBirth().setTotalWomanNumber(0);

		this.getReport().getAddNew().setTotalNumber(0);
		this.getReport().getAddNew().setAboveFourteenYear(0);
		this.getReport().getAddNew().setIsAddNew(0);
		this.getReport().getAddNew().setTotalWomanNumber(0);
		this.getReport().getAddNew().setWomanAboveFourteenYear(0);

		this.getReport().getAddMore().setTotalNumber(0);
		this.getReport().getAddMore().setAboveFourteenYear(0);
		this.getReport().getAddMore().setTotalWomanNumber(0);
		this.getReport().getAddMore().setWomanAboveFourteenYear(0);

		this.getReport().getOutOfLc().setTotalNumber(0);
		this.getReport().getOutOfLc().setAddNew(0);
		this.getReport().getOutOfLc().setAboveFourteenYear(0);
		this.getReport().getOutOfLc().setTotalWomanNumber(0);
		this.getReport().getOutOfLc().setWomanAboveFourteenYear(0);

		this.getReports().clear();
	}

	// private void updatePopInformation() {
	// for (int i = 0; i < this.popList.get().size(); i++) {
	// this.popList.get().get(i).setLocation(selectedLocation);
	// this.populationList.get(i).setLocation(selectedLocation);
	// this.populationList.get(i)
	// .setSelectedPopulationType(getPopCase(this.populationList.get(i).getPopulationType()));
	// this.popList.get().get(i)
	// .setSelectedPopulationType(getPopCase(this.popList.get().get(i).getPopulationType()));
	//
	// if
	// (Constans.SEX_WOMAN.compareToIgnoreCase(this.populationList.get(i).getSex())
	// == 0) {
	// this.populationList.get(i).setIsMan(false);
	// this.popList.get().get(i).setIsMan(false);
	// } else {
	// this.populationList.get(i).setIsMan(true);
	// this.popList.get().get(i).setIsMan(true);
	// this.popList.get().get(i).setSex("Nam");
	// this.populationList.get(i).setSex("Nam");
	// }
	//
	// logger.info(this.popList.get().get(i).toString());
	// }
	// }

	private PopCase getPopCase(String id) {
		for (PopCase popType : popTypeList) {
			if (popType.getTypeId().trim().compareToIgnoreCase(id.trim()) == 0) {
				return popType;
			}
		}
		return null;
	}

	public List<Report> getReports() {
		return this.reports;
	}

	private final String TACH_HO = "T008";
	private final SimpleObjectProperty<Report> report;
	private final SimpleObjectProperty<AddMore> addMore;
	private final SimpleObjectProperty<RegisterOfBirth> regisDateOfBirth;
	private final SimpleObjectProperty<AddNew> addNew;
	private final SimpleObjectProperty<OutSideOfLc> outSideLc;
	private final ObservableList<PopCase> popTypeList;
	private final ObservableList<Location> locationList;
	private final ObservableList<Population> reportList;
	private static Location selectedLocation;
	private final ObjectProperty<ObservableList<Population>> popList;
	private List<Population> populationList;
	private List<Report> reports;

}
