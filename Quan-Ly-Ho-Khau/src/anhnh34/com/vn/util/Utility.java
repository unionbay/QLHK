package anhnh34.com.vn.util;

import java.beans.IntrospectionException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import anhnh34.fpt.vn.dao.LocationDAO;
import anhnh34.fpt.vn.dao.PopulationDAO;
import anhnh34.fpt.vn.entity.Location;
import anhnh34.fpt.vn.entity.PopCase;

public class Utility {
	private PopulationDAO populationDao;
	private LocationDAO locationDao;
	private List<PopCase> popTypeList;
	private List<Location> locationList;
	private Properties properties;
	private static Utility instance;
	private String url;
	private String username;
	private String password;
	private String driver;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	private Utility() {
		populationDao = new PopulationDAO();
		locationDao = new LocationDAO();
		try {
			// this.loadProperties();
			this.popTypeList = populationDao.getAllPopCase();
			this.locationList = locationDao.getLocations();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// private void loadProperties() throws IOException {
	// InputStream inStream = null;
	// try {
	// inStream = new FileInputStream(Constans.RS_PROPERY_PATH);
	// Properties prop = new Properties();
	// prop.load(inStream);
	//
	// this.setUrl(prop.getProperty("url"));
	// this.setUsername(prop.getProperty("username"));
	// this.setPassword(prop.getProperty("password"));
	// this.setDriver(prop.getProperty("driver"));
	// } finally {
	// if(inStream != null){
	// inStream.close();
	// }
	// }
	// }

	public static Utility getInstance() {
		if (instance == null) {
			instance = new Utility();
		}
		return instance;
	}

	public String getPopTypeText(String id) {
		switch (id.trim()) {
		case Constans.KHAI_SINH:
			return "Khai Sinh";
		case Constans.NHAP_MOI:
			return "Nhập mới";
		case Constans.NHAP_THEM:
			return "Nhập thêm";
		case Constans.THUE_NHA:
			return "Thuê nhà";
		case Constans.NHAP:
			return "Nhập";
		case Constans.BIA_DO:
			return "Bìa đỏ";
		case Constans.CHUNG_CU:
			return "Chung cư";
		case Constans.DINH_CHINH:
			return "Đính chính";
		case Constans.CAP_LAI:
			return "Cấp lại";
		case Constans.TACH_HO:
			return "Tách hộ";
		case Constans.CHUNG_TU:
			return "Chứng tử";
		case Constans.CHUYEN_kHAU:
			return "Chuyển kh";
		case Constans.CHUYEN_PHUONG:
			return "Khai Sinh";
		case Constans.DOI_SO:
			return "Đổi sổ";
		default:
			return "";
		}
	}

	public Location getLocation(String locationId) {
		for (Location lc : locationList) {
			if (lc.getId().trim().compareTo(locationId) == 0) {
				return lc;
			}
		}
		return null;
	}

	public PopCase getPopulationType(String id) {
		for (PopCase pt : popTypeList) {
			if (pt.getTypeId().trim().compareToIgnoreCase(id.trim()) == 0) {
				return pt;
			}
		}
		return null;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Location> getLocationList() {
		return locationList;
	}

	public List<PopCase> getPopTypeList() {
		return popTypeList;
	}
}
