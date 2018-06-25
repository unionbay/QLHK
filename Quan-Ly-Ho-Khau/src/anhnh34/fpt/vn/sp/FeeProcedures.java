package anhnh34.fpt.vn.sp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import anhnh34.fpt.vn.dao.DatabaseFactory;
import anhnh34.fpt.vn.entity.Fee;


public class FeeProcedures {
	public static List<Fee> getFeeList() throws SQLException{
		Connection connection = DatabaseFactory.getInstance().getDatabaseConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Fee> feeList = new ArrayList<>();
		
		try {
			String sql = "Select * from POPULATION.FEE where Status = 'STA001'";
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				String id = rs.getString(1);
				double fee = rs.getDouble(2);
				String description = rs.getString(3);
				String status = rs.getString(4);
				
				Fee feeObj = new Fee(id, fee, description, status);
				feeList.add(feeObj);
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
		return feeList;
	}
}
