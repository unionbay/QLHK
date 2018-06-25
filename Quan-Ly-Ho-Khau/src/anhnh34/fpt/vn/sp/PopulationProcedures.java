package anhnh34.fpt.vn.sp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import anhnh34.com.vn.util.Constans;
import anhnh34.fpt.vn.dao.DatabaseFactory;
import anhnh34.fpt.vn.entity.Population;

public class PopulationProcedures {

	final static Logger logger = Logger.getLogger(PopulationProcedures.class);

	private static PreparedStatement pstmt;

	public static String insertPopulation(Population population) throws SQLException, ParseException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = "Insert into POPULATION.POPULATIONFORM " + "(PopulationId, " + " ParentId, "
				+ " AddNew, " + " OutSide, " + "HouseHolderName, " + "FullName, " + " Address," + " Reason,"
				+ " BirthDay," + " Sex," + " LocationId," + " DocumentNumber," + "CaseNumber," + " DateCreated,"
				+ " Status)" + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			System.out.println(population.toString());
			conn = DatabaseFactory.getInstance().getDatabaseConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, population.getId());
			pstmt.setString(2, population.getParentId());			
			pstmt.setString(3, population.getAddNew());
			pstmt.setString(4, population.getOutSide());
			pstmt.setString(5, population.getHouseHolderName());
			pstmt.setString(6, population.getFullName());
			pstmt.setString(7, population.getAddress());
			pstmt.setString(8, population.getReason());
			pstmt.setDate(9, new java.sql.Date(population.getBirthday().getTime()));
			pstmt.setString(10, population.getSexP().getId());
			pstmt.setString(11, population.getLocation().getId());
			pstmt.setString(12, population.getDocumentNumber());
			pstmt.setInt(13, population.getCaseNumber());
			pstmt.setDate(14, new java.sql.Date(population.getDateCreated().getTime()));
			pstmt.setString(15, population.getPopStatus());
			pstmt.execute();
			return population.getId();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			if (conn != null) {
				conn.close();
			}
		}
	}

	public static int countNumberOfPopulation() {

		Connection conn = null;
		PreparedStatement stmt;
		ResultSet rs;
		int numberOfPopulation = -1;

		try {
			conn = DatabaseFactory.getInstance().getDatabaseConnection();
			stmt = conn.prepareStatement("SELECT COUNT(*) from POPULATION.PopulationForm");
			rs = stmt.executeQuery();
			while (rs.next()) {
				numberOfPopulation = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return numberOfPopulation;
	}

	public static List<Population> getPopulationById(String id) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Population> populationList = new ArrayList<Population>();
		String query = "Select* from POPULATION.PopulationForm where locationId = ?";
		try {
			conn = DatabaseFactory.getInstance().getDatabaseConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, id);
			rs = stmt.executeQuery();

			while (rs.next()) {
				String populationId = rs.getString(1);
				String parentId = rs.getString(2);
				String popTypeId = rs.getString(3);
				String addNew = rs.getString(4);
				String outSide = rs.getString(5);
				String hHName = rs.getString(6);
				String fullName = rs.getString(7);
				String address = rs.getString(8);
				String reason = rs.getString(9);
				Date birthday = rs.getDate(10);
				String sex = rs.getString(11);
				Double externalFee = rs.getDouble(12);
				String documentNumber = rs.getString(13);
				int caseNumber = rs.getInt(14);
				String locationId = rs.getString(15);
				Date dateCreated = rs.getDate(16);
				String status = rs.getString(17);
				Population pop = new Population(populationId, parentId, hHName, fullName, locationId, address,
						popTypeId, addNew, outSide, reason, birthday.toLocalDate(), sex, documentNumber, caseNumber,
						externalFee, dateCreated.toLocalDate(), status);

				populationList.add(pop);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (stmt != null) {
				stmt.close();
			}

			if (conn != null) {
				conn.close();
			}
		}

		return populationList;
	}

	public static List<Population> getPopulations(String loId, Date fromDate, Date toDate) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		List<Population> populationList = new ArrayList<Population>();
		try {
			conn = DatabaseFactory.getInstance().getDatabaseConnection();
			
			if (fromDate == null && toDate == null) {
				if(Constans.LC_LOCATION.compareTo(loId.trim()) == 0){
					query = "Select * from POPULATION.PopulationForm Where Status = ?";					
				}else{
					query = "Select * from POPULATION.PopulationForm where locationId = ? AND Status = ?";
				}				
			} else {
				if(Constans.LC_LOCATION.compareTo(loId.trim()) == 0){
					query = "Select * from POPULATION.PopulationForm Where DateCreated Between ? And ? And Status = ?";
				} else{
					query = "Select* from POPULATION.PopulationForm Where locationId = ? AND DateCreated Between ? And ? And Status = ?";
				}				
			}
			
			logger.info("Query: " + query);

			pstmt = conn.prepareStatement(query);
			
			if (fromDate != null && toDate != null) {
				if(Constans.LC_LOCATION.compareTo(loId) == 0){
					pstmt.setDate(1, fromDate);
					pstmt.setDate(2, toDate);
					pstmt.setString(3, Constans.ACTIVE);
				}else{
					pstmt.setString(1, loId);
					pstmt.setDate(2, fromDate);
					pstmt.setDate(3, toDate);
					pstmt.setString(4, Constans.ACTIVE);
				}												
			} else {
				if(Constans.LC_LOCATION.compareTo(loId.trim()) == 0){
					pstmt.setString(1, Constans.ACTIVE);
				}else{
					pstmt.setString(1,loId);
					pstmt.setString(2, Constans.ACTIVE);
				}				
			}

			rs = pstmt.executeQuery();
			while (rs.next()) {
				String populationId = rs.getString(1) == null ? "" : rs.getString(1).trim();
				String parentId = rs.getString(2) == null ? "" : rs.getString(2).trim();
				String popTypeId = rs.getString(3) == null ? "": rs.getString(3).trim();
				String addNew = rs.getString(4);
				String outSide = rs.getString(5);
				String hHName = rs.getString(6);
				String fullName = rs.getString(7);
				String address = rs.getString(8);
				String reason = rs.getString(9);
				Date birthday = rs.getDate(10);
				String sex = rs.getString(11);
				Double externalFee = rs.getDouble(12);
				String documentNumber = rs.getString(13);
				int caseNumber = rs.getInt(14);
				String locationId = rs.getString(15);
				Date dateCreated = rs.getDate(16);
				String status = rs.getString(17);
				Population pop = new Population(populationId, parentId, hHName, fullName, locationId, address,
						popTypeId, addNew, outSide, reason, birthday.toLocalDate(), sex, documentNumber, caseNumber,
						externalFee, dateCreated.toLocalDate(), status);
				logger.info(pop.toString());
				populationList.add(pop);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			if (conn != null) {
				conn.close();
			}
		}
		return populationList;
	}

	public static int countSubPopulationNumber(String parentId) {
		Connection conn = null;
		PreparedStatement stmt;
		String query = "Select COUNT(*) From POPULATION.PopulationForm where parentId = ?";
		ResultSet rs;
		int numberOfPopulation = 0;

		try {
			conn = DatabaseFactory.getInstance().getDatabaseConnection();
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				numberOfPopulation = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return numberOfPopulation;
	}

	public static int deletePopulation(String id) throws SQLException {
		Connection conn = null;
		pstmt = null;
		String query = "Delete From POPULATION.PopulationForm where PopulationId = ?";
		int rs = -1;

		conn = DatabaseFactory.getInstance().getDatabaseConnection();
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, id);
		rs = pstmt.executeUpdate();

		return rs;
	}

	public static int updatePopulationStatus(String id, String status) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "Update POPULATION.PopulationForm Set Status = ? where PopulationId = ?";

		conn = DatabaseFactory.getInstance().getDatabaseConnection();
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, status);
		pstmt.setString(2, id);
		result = pstmt.executeUpdate();
		return result;

	}

	public static int updatePopulation(Population pop) throws SQLException {
		logger.info(pop.toString());
		Connection conn = null;
		PreparedStatement pstmt = null;		
		String query = "Update POPULATION.PopulationForm Set TypeId = ? " + ", AddNew = ?" + ", OutSide = ?"
				+ ", HouseHolderName = ?" + ", FullName = ?" + ", Address = ?" + ", Reason = ?" + ", Birthday = ?"
				+ ", Sex = ?" + ", DocumentNumber = ?" + ", CaseNumber = ?" + ", LocationId = ?"
				+ " Where PopulationId = ?";

		conn = DatabaseFactory.getInstance().getDatabaseConnection();
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, pop.getSelectedPopulationType().getTypeId());
		pstmt.setString(2, pop.getAddNew());
		pstmt.setString(3, pop.getOutSide());
		pstmt.setString(4, pop.getHouseHolderName());
		pstmt.setString(5, pop.getFullName());
		pstmt.setString(6, pop.getAddress());
		pstmt.setString(7, pop.getReason());
		pstmt.setDate(8, new Date(pop.getBirthday().getTime()));		
		pstmt.setString(9, pop.getSexP().getId().trim());
		pstmt.setString(10, pop.getDocumentNumber());
		pstmt.setInt(11, pop.getCaseNumber());
		pstmt.setString(12, pop.getLocation().getId());
		pstmt.setString(13, pop.getId());
		int returnId = pstmt.executeUpdate();
		logger.info("return id: " + returnId);
		return returnId;

	}

	public static List<Population> getSubPopulationById(String pId) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "Select* from POPULATION.PopulationForm Where ParentId = ?" + "And Status = ?";
		List<Population> populationList = new ArrayList<Population>();
		try {
			conn = DatabaseFactory.getInstance().getDatabaseConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, pId);
			pstmt.setString(2, Constans.ACTIVE);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String populationId = rs.getString(1);
				String parentId = rs.getString(2);
				String popTypeId = rs.getString(3);
				String addNew = rs.getString(4);
				String outSide = rs.getString(5);
				String hHName = rs.getString(6);
				String fullName = rs.getString(7);
				String address = rs.getString(8);
				String reason = rs.getString(9);
				Date birthday = rs.getDate(10);
				String sex = rs.getString(11);
				Double externalFee = rs.getDouble(12);
				String documentNumber = rs.getString(13);
				int caseNumber = rs.getInt(14);
				String locationId = rs.getString(15);
				Date dateCreated = rs.getDate(16);
				String status = rs.getString(17);
				Population pop = new Population(populationId, parentId, hHName, fullName, locationId, address,
						popTypeId, addNew, outSide, reason, birthday.toLocalDate(), sex, documentNumber, caseNumber,
						externalFee, dateCreated.toLocalDate(), status);
				populationList.add(pop);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			if (conn != null) {
				conn.close();
			}
		}
		return populationList;

	}

}
