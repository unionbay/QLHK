package anhnh34.fpt.vn.dao;

import java.sql.SQLException;
import java.util.List;

import anhnh34.fpt.vn.entity.Fee;
import anhnh34.fpt.vn.entity.PopCase;
import anhnh34.fpt.vn.sp.FeeProcedures;

public class FeeDAO {
	public List<Fee> getAllFee() throws SQLException{		
		return FeeProcedures.getFeeList();
	}
}
