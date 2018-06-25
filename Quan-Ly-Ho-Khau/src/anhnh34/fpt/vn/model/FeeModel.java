package anhnh34.fpt.vn.model;

import java.sql.SQLException;
import java.util.List;

import anhnh34.fpt.vn.dao.FeeDAO;
import anhnh34.fpt.vn.entity.Fee;

public class FeeModel {
	public List<Fee> getAllFee() throws SQLException{
		FeeDAO feeDao = new FeeDAO();
		return feeDao.getAllFee();	
	}
}
