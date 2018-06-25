package anhnh34.fpt.vn.sp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import anhnh34.fpt.vn.dao.DatabaseFactory;
import anhnh34.fpt.vn.entity.PopCase;

public class PopulationCaseProcedures {
	private static final Logger logger = Logger.getLogger(PopulationCaseProcedures.class);

	public static List<PopCase> getPopulationTypes() throws SQLException {
		Connection connection = DatabaseFactory.getInstance().getDatabaseConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<PopCase> popCases = new ArrayList<PopCase>();
		try {
			String sql = "Select * from POPULATION.type where status = 'STA001'";
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String typeId = rs.getString(1) == null ? "" : rs.getString(1).trim();
				String parentId = rs.getString(2) == null ? "" : rs.getString(2).trim();
				String value = rs.getString(3) == null ? "" : rs.getString(3).trim();
				String status = rs.getString(4) == null ? "" : rs.getString(4).trim();

				PopCase popCase = new PopCase(typeId, parentId, value, status);
				popCases.add(popCase);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (stmt != null) {
				stmt.close();
			}

			if (connection != null) {
				connection.close();
			}
		}

		logger.info("Types size: " + popCases.size());
		return popCases;
	}

	public static void insertPopTypes(String popId, List<PopCase> types) throws SQLException {
		Connection conn = DatabaseFactory.getInstance().getDatabaseConnection();
		PreparedStatement stmt = null;	
		String sql = "Insert into Population.PopulationType (TypeId, PopulationId, Status) values (?, ?, ?)";
		try {
			for (PopCase t : types) {
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, t.getTypeId());
				stmt.setString(2, popId);
				stmt.setString(3, t.getStatus());
				stmt.executeUpdate();
			}
			
		} finally {
			stmt.close();
			conn.close();
		}		
	}

	public String concatenate(String one, String two) {
		return one + two;
	}
}
