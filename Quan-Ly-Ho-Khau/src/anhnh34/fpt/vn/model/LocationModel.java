package anhnh34.fpt.vn.model;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import anhnh34.com.vn.util.Constans;
import anhnh34.fpt.vn.dao.LocationDAO;
import anhnh34.fpt.vn.entity.Location;

public class LocationModel {

	private final Logger logger = Logger.getLogger(LocationModel.class);

	public List<Location> getLocations() throws SQLException {
		LocationDAO locationDAO = new LocationDAO();
		return locationDAO.getLocations();
	}

	public List<Location> getDistricts() throws SQLException {
		LocationDAO locationDAO = new LocationDAO();
		List<Location> locations = locationDAO.getLocations();
		int index = -1;
		for (Location lc : locations) {
			if (Constans.LC_LAOCAI_CITY.compareToIgnoreCase(lc.getId()) == 0) {
				logger.info("locationId: " + index);
				index = locations.indexOf(lc);
				break;
			}
		}

		if (index != -1) {
			locations.remove(index);
		}

		return locations;
	}
}
