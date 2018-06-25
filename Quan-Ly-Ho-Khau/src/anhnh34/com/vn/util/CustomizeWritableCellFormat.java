package anhnh34.com.vn.util;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.Pattern;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

public class CustomizeWritableCellFormat extends WritableCellFormat {

	private String cellType;
	public static final String HEAD_CELL_TYPE = "head";
	public static final String SUB_HEAD_CELL_TYPE = "sub_head";
	public static final String CONTENT_CELL_TYPE = "content";

	public String getCellType() {
		return cellType;
	}

	public void setCellType(String cellType) {
		this.cellType = cellType;
	}

	@Override
	public void setAlignment(Alignment a) throws WriteException {
		// TODO Auto-generated method stub
		super.setAlignment(a);
	}

	@Override
	public void setBackground(Colour c, Pattern p) throws WriteException {
		// TODO Auto-generated method stub
		super.setBackground(c, p);
	}

	@Override
	public void setBackground(Colour c) throws WriteException {
		// TODO Auto-generated method stub
		super.setBackground(c);
	}

	@Override
	public void setBorder(Border b, BorderLineStyle ls, Colour c) throws WriteException {
		// TODO Auto-generated method stub
		super.setBorder(b, ls, c);
	}

	@Override
	public void setBorder(Border b, BorderLineStyle ls) throws WriteException {
		// TODO Auto-generated method stub
		super.setBorder(b, ls);
	}

	@Override
	public void setIndentation(int i) throws WriteException {
		// TODO Auto-generated method stub
		super.setIndentation(i);
	}

	@Override
	public void setLocked(boolean l) throws WriteException {
		super.setLocked(l);
	}

	@Override
	public void setOrientation(Orientation o) throws WriteException {
		super.setOrientation(o);
	}

	@Override
	public void setShrinkToFit(boolean s) throws WriteException {
		// TODO Auto-generated method stub
		super.setShrinkToFit(s);
	}

	@Override
	public void setVerticalAlignment(VerticalAlignment va) throws WriteException {
		// TODO Auto-generated method stub
		super.setVerticalAlignment(va);
	}

	@Override
	public void setWrap(boolean w) throws WriteException {
		// TODO Auto-generated method stub
		super.setWrap(w);
	}

	public CustomizeWritableCellFormat(WritableFont font, String cellType) {
		super(font);
		setCellType(cellType);
	}

}
