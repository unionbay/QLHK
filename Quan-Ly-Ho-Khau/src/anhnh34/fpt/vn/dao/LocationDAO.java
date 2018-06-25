package anhnh34.fpt.vn.dao;

import java.sql.SQLException;
import java.util.List;

import anhnh34.fpt.vn.entity.Location;
import anhnh34.fpt.vn.sp.LocationProcedure;

public class LocationDAO {
	public List<Location> getLocations() throws SQLException{
		return LocationProcedure.getLocation();
	}
}
