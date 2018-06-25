package anhnh34.com.vn.test;

import org.junit.Test;

import anhnh34.com.vn.util.Constans;
import anhnh34.fpt.vn.entity.Location;
import anhnh34.fpt.vn.entity.PopCase;
import anhnh34.fpt.vn.entity.Population;
import anhnh34.fpt.vn.model.ExcelModel;
import anhnh34.fpt.vn.model.ReportModel;
import anhnh34.fpt.vn.sp.PopulationCaseProcedures;
import jxl.write.WriteException;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class PopulationUnitTest {

	@Test
	public void testConcatenate() {
		PopulationCaseProcedures propcedure = new PopulationCaseProcedures();
		String result = propcedure.concatenate("one", "two");
		assertEquals("onetwo", result);

	}

	@Test
	public void testPopulationCase() {
		try {
			List<PopCase> populationTypes = PopulationCaseProcedures.getPopulationTypes();
			assertEquals(16, populationTypes.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testCountPopulationNumber() {
		try {
			// int number = PopulationCaseProcedures.countNumberOfPopulation();
			// assertEquals(1, number);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInsertPopulation() {
		try {
			Population population = new Population();
			// population.setAddNew(addNew);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private Location getLocation() {
		return null;
	}

	@Test
	public void exportExcelFile() {
		try {
			
			String locationId = Constans.LC_LAOCAI_CITY;
			//locationId = "LC009";
			
			ReportModel reportModel = new ReportModel();			
			reportModel.getReport(locationId, null, null);
			ExcelModel excelModel = new ExcelModel();
			try {							
				excelModel.exportExcel(reportModel.getReports());
			} catch (WriteException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getLog4jPropertyFile() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("./resources/log4j.properties");
			boolean isValid = input == null ? false : true;
			prop.load(input);

			System.out.println(prop.getProperty("log4j.appender.file"));
			assertEquals(true, isValid);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
