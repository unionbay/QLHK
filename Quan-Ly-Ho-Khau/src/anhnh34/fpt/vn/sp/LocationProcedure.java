package anhnh34.fpt.vn.sp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import anhnh34.fpt.vn.dao.DatabaseFactory;
import anhnh34.fpt.vn.entity.Location;

public class LocationProcedure {
	public static List<Location> getLocation() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Location> locations = new ArrayList<Location>();
		String query = "Select * from POPULATION.Location where parentId = 'LC001' or locationId = 'LC001'";
		try {
			conn = DatabaseFactory.getInstance().getDatabaseConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString(1) == null ? "" : rs.getString(1).trim();
				String description = rs.getString(2) == null ? "" : rs.getString(2).trim();
				String parentId = rs.getString(3) == null ? "" : rs.getString(3).trim();

				Location location = new Location(id, description, parentId);
				locations.add(location);
			}
			return locations;
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
	}
}
