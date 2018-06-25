package anhnh34.fpt.vn.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import anhnh34.com.vn.util.Constans;

public class DatabaseFactory {
	private static Logger logger = Logger.getLogger(DatabaseFactory.class);
	private static DatabaseFactory instance;
	private String driver;
	private String url;
	private String username;
	private String password;
	private String databaseLocation;

	private DatabaseFactory() throws IOException {
		Properties properties = new Properties();
		InputStream inStream = null;
		try {			
			inStream = getClass().getResourceAsStream(Constans.RS_PROPERY_PATH);
			//inStream = new FileInputStream(Constans.RS_PROPERY_PATH.trim());			
			properties.load(inStream);			
			this.url = properties.getProperty("url");			
			this.driver = properties.getProperty("dirver");
			this.databaseLocation = properties.getProperty("database_location");
			this.username = properties.getProperty("username");
			this.password = properties.getProperty("password");	
			

		} finally {
			if (inStream != null) {
				inStream.close();
			}
		}

	}

	public static DatabaseFactory getInstance() {
		if (instance == null) {
			try {
				instance = new DatabaseFactory();
			} catch (IOException e) {				 
				logger.log(Priority.FATAL, "Exceptions happen!", e); //this will put all the details in log file configured earlier
			}
		}
		return instance;
	}

	public Connection getDatabaseConnection() {
		Connection conn = null;
		try {
			// System.setProperty("derby.database.forceDatabaseLock", "true");
			Properties p = System.getProperties();
			p.put("derby.system.home", this.databaseLocation);
			p.put("derby.ui.codeset", "UTF8");
			p.put("user", this.username);
			p.put("password", this.password);			
			String driver = this.driver;			
			String url = this.url;
			System.out.println("driver" + driver);
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url,p);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				return conn;
			}
		}
		return conn;
	}
}
