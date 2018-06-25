package anhnh34.fpt.vn.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import anhnh34.com.vn.util.CustomizeWritableCellFormat;
import anhnh34.fpt.vn.entity.Report;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelModel {

	public ExcelModel() {
		this.reportList = new ArrayList<Report>();
	}

	public void setReportList(List<Report> reportList) {
		this.reportList = reportList;
	}

	public List<Report> getReportList() {
		return reportList;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public void exportExcel(List reports) throws IOException, WriteException {
		this.reportList = reports;
		File file = new File("/home/anh-nguyen/Downloads/AAA/QLHK/bao_cao.xls");
		WorkbookSettings wbSetttings = new WorkbookSettings();
		wbSetttings.setLocale(new Locale("en", "EN"));
		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSetttings);
		WritableSheet excelSheet = workbook.createSheet("Report", 0);
		this.initFont();
		this.initCellContent();
		this.createLabel(excelSheet);
		this.createContent(excelSheet);

		workbook.write();
		workbook.close();
	}

	private void initFont() throws WriteException {
		headerFont = new WritableFont(WritableFont.TIMES, 12);
		headerFont.setColour(Colour.WHITE);
		headerFont.setBoldStyle(WritableFont.BOLD);

		subHeadFont = new WritableFont(WritableFont.TIMES, 11);
		subHeadFont.setColour(Colour.BLACK);
		subHeadFont.setBoldStyle(WritableFont.BOLD);

		contentFont = new WritableFont(WritableFont.TIMES, 10);
		contentFont.setColour(Colour.BLACK);
	}

	private void initCellContent() throws WriteException {
		headCellFormat = new CustomizeWritableCellFormat(headerFont, CustomizeWritableCellFormat.HEAD_CELL_TYPE);
		headCellFormat.setAlignment(Alignment.CENTRE);
		headCellFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);
		headCellFormat.setBackground(Colour.OCEAN_BLUE);
		headCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		headCellFormat.setWrap(true);

		subHeadCellFormat = new CustomizeWritableCellFormat(subHeadFont,
				CustomizeWritableCellFormat.SUB_HEAD_CELL_TYPE);
		subHeadCellFormat.setAlignment(Alignment.CENTRE);
		subHeadCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		subHeadCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		subHeadCellFormat.setWrap(true);

		contentCellFormat = new CustomizeWritableCellFormat(contentFont, CustomizeWritableCellFormat.CONTENT_CELL_TYPE);
		contentCellFormat.setAlignment(Alignment.CENTRE);
		contentCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		contentCellFormat.setWrap(true);
	}

	private void addCaption(WritableSheet sheet, int sRowIndex, int eRowIndex, int sColIndex, int eColIndex,
			String title, CustomizeWritableCellFormat cellFormat) throws RowsExceededException, WriteException {

		for (int i = sRowIndex; i <= eRowIndex; i++) {
			sheet.setRowView(i, HEIGHT_IN_POINT);
		}

		for (int i = sColIndex; i <= eColIndex; i++) {
			if (sColIndex == 0) {
				sheet.setColumnView(i, 25);
			} else {
				sheet.setColumnView(i, SUB_HEAD_WIDTH);
			}
		}

		if (eColIndex > sColIndex) {
			if (eRowIndex > sRowIndex) {
				sheet.mergeCells(sColIndex, sRowIndex, eColIndex, eRowIndex);
			} else {
				sheet.mergeCells(sColIndex, sRowIndex, eColIndex, sRowIndex);
			}
		} else {
			sheet.mergeCells(sColIndex, sRowIndex, sColIndex, eRowIndex);
		}
		Label label = new Label(sColIndex, sRowIndex, title, cellFormat);
		sheet.addCell(label);
	}

	private void createLabel(WritableSheet sheet) throws RowsExceededException, WriteException {
		this.addCaption(sheet, 0, 0, 0, 0, "Phường", headCellFormat);
		this.addCaption(sheet, 1, 1, 0, 0, "", subHeadCellFormat);

		this.addCaption(sheet, 0, 0, 1, 2, "Khai Sinh", headCellFormat);
		this.addCaption(sheet, 1, 1, 1, 1, "Khẩu", subHeadCellFormat);
		this.addCaption(sheet, 1, 1, 2, 2, "Nữ", subHeadCellFormat);

		this.addCaption(sheet, 0, 0, 3, 6, "Nhập thêm", headCellFormat);
		this.addCaption(sheet, 1, 1, 3, 3, "Khẩu", subHeadCellFormat);
		this.addCaption(sheet, 1, 1, 4, 4, "Nữ", subHeadCellFormat);
		this.addCaption(sheet, 1, 1, 5, 5, "Trên 14 tuổi", subHeadCellFormat);
		this.addCaption(sheet, 1, 1, 6, 6, "Nữ trên 14 tuổi", subHeadCellFormat);

		this.addCaption(sheet, 0, 0, 7, 11, "Nhập mới", headCellFormat);
		this.addCaption(sheet, 1, 1, 7, 7, "Hộ", subHeadCellFormat);
		this.addCaption(sheet, 1, 1, 8, 8, "Khẩu", subHeadCellFormat);
		this.addCaption(sheet, 1, 1, 9, 9, "Nữ", subHeadCellFormat);
		this.addCaption(sheet, 1, 1, 10, 10, "Trên 14 tuổi", subHeadCellFormat);
		this.addCaption(sheet, 1, 1, 11, 11, "Nữ trên 14 tuổi", subHeadCellFormat);

		this.addCaption(sheet, 0, 0, 12, 16, "Ngoài tỉnh", headCellFormat);
		this.addCaption(sheet, 1, 1, 12, 12, "Hộ", subHeadCellFormat);
		this.addCaption(sheet, 1, 1, 13, 13, "Khẩu", subHeadCellFormat);
		this.addCaption(sheet, 1, 1, 14, 14, "Nữ", subHeadCellFormat);
		this.addCaption(sheet, 1, 1, 15, 15, "Trên 14 tuổi", subHeadCellFormat);
		this.addCaption(sheet, 1, 1, 16, 16, "Nữ trên 14 tuổi", subHeadCellFormat);

		this.addCaption(sheet, 0, 0, 17, 17, "Đính chính", headCellFormat);
		this.addCaption(sheet, 0, 0, 18, 18, "Cấp lại", headCellFormat);
		this.addCaption(sheet, 0, 0, 19, 19, "Tách hộ", headCellFormat);
		this.addCaption(sheet, 0, 0, 20, 20, "Chứng tử", headCellFormat);
		this.addCaption(sheet, 0, 0, 21, 21, "Chuyển khẩu", headCellFormat);
		this.addCaption(sheet, 0, 0, 22, 22, "Chuyển Phường", headCellFormat);
		this.addCaption(sheet, 0, 0, 23, 23, "Đổi sổ", headCellFormat);
		this.addCaption(sheet, 0, 0, 24, 24, "Hợp hộ", headCellFormat);
		this.addCaption(sheet, 0, 0, 25, 25, "Tiền phí", headCellFormat);
	}

	private void createContent(WritableSheet sheet) throws RowsExceededException, WriteException {
		for (int i = 0; i < reportList.size(); i++) {
			int rowIndex = i + 2;
			Report report = reportList.get(i);

			// District name
			addCaption(sheet, rowIndex, rowIndex, 0, 0, report.getLocation().getDescription(), contentCellFormat);

			// Register of birth
			addCaption(sheet, rowIndex, rowIndex, 1, 1, report.getRegisterOfBirth().getTotalNumber().toString(),
					contentCellFormat);

			addCaption(sheet, rowIndex, rowIndex, 2, 2, report.getRegisterOfBirth().getTotalWomanNumber().toString(),
					contentCellFormat);

			// AddMore
			addCaption(sheet, rowIndex, rowIndex, 3, 3, report.getAddMore().getTotalNumber().toString(),
					contentCellFormat);

			addCaption(sheet, rowIndex, rowIndex, 4, 4, report.getAddMore().getTotalWomanNumber().toString(),
					contentCellFormat);

			addCaption(sheet, rowIndex, rowIndex, 5, 5, report.getAddMore().getAboveFourteenYear().toString(),
					contentCellFormat);

			addCaption(sheet, rowIndex, rowIndex, 6, 6, report.getAddMore().getWomanAboveFourteenYear().toString(),
					contentCellFormat);

			// AddNew
			addCaption(sheet, rowIndex, rowIndex, 7, 7, report.getAddNew().getIsAddNew().toString(), contentCellFormat);
			addCaption(sheet, rowIndex, rowIndex, 8, 8, report.getAddNew().getTotalNumber().toString(),
					contentCellFormat);
			addCaption(sheet, rowIndex, rowIndex, 9, 9, report.getAddNew().getTotalWomanNumber().toString(),
					contentCellFormat);
			addCaption(sheet, rowIndex, rowIndex, 10, 10, report.getAddNew().getAboveFourteenYear().toString(),
					contentCellFormat);
			addCaption(sheet, rowIndex, rowIndex, 11, 11, report.getAddNew().getWomanAboveFourteenYear().toString(),
					contentCellFormat);

			// Outside LaoCai
			addCaption(sheet, rowIndex, rowIndex, 12, 12, report.getOutOfLc().getAddNew().toString(),
					contentCellFormat);
			addCaption(sheet, rowIndex, rowIndex, 13, 13, report.getOutOfLc().getTotalNumber().toString(),
					contentCellFormat);
			addCaption(sheet, rowIndex, rowIndex, 14, 14, report.getOutOfLc().getTotalWomanNumber().toString(),
					contentCellFormat);
			addCaption(sheet, rowIndex, rowIndex, 15, 15, report.getOutOfLc().getWomanAboveFourteenYear().toString(),
					contentCellFormat);
			addCaption(sheet, rowIndex, rowIndex, 16, 16, report.getOutOfLc().getWomanAboveFourteenYear().toString(),
					contentCellFormat);

			// Other

			// Dinh chinh
			addCaption(sheet, rowIndex, rowIndex, 17, 17, report.getToDeny().toString(), contentCellFormat);
			// Cap lai
			addCaption(sheet, rowIndex, rowIndex, 18, 18, report.getReallocate().toString(), contentCellFormat);
			// Tach ho
			addCaption(sheet, rowIndex, rowIndex, 19, 19, report.getSeperatePopulation().toString(), contentCellFormat);
			// Chung tu
			addCaption(sheet, rowIndex, rowIndex, 20, 20, report.getDeathCertificate().toString(), contentCellFormat);
			// Chuyen khau
			addCaption(sheet, rowIndex, rowIndex, 21, 21, report.getChangePopulation().toString(), contentCellFormat);
			// Chuyen phuong
			addCaption(sheet, rowIndex, rowIndex, 22, 22, report.getChangeDistrict().toString(), contentCellFormat);
			// Doi so
			addCaption(sheet, rowIndex, rowIndex, 23, 23, report.getChangePopDocument().toString(), contentCellFormat);
			// Hop ho
			addCaption(sheet, rowIndex, rowIndex, 24, 24, report.getJoinPopDocDocument().toString(), contentCellFormat);
			// Tien phi
			addCaption(sheet, rowIndex, rowIndex, 25, 25, report.getFee().toString(), contentCellFormat);
		}
	}

	private Report report;
	private List<Report> reportList;
	private WritableFont headerFont;
	private WritableFont contentFont;
	private WritableFont subHeadFont;
	private CustomizeWritableCellFormat headCellFormat;
	private CustomizeWritableCellFormat subHeadCellFormat;
	private CustomizeWritableCellFormat contentCellFormat;

	private static final int WIDTH_IN_CHAR = 15;
	private static final int SUB_HEAD_WIDTH = 10;
	private static final int HEIGHT_IN_POINT = 520;

}
