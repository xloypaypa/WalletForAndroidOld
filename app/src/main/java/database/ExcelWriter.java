package database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;

public class ExcelWriter {
	String path;
	WritableWorkbook workbook;
	FileOutputStream fos;
	Vector <WritableSheet> pages;
	public ExcelWriter(String path) throws IOException{
		this.path=path;
		File file=new File(path);
		fos=new FileOutputStream(file);
		workbook=Workbook.createWorkbook(fos);
		
		pages=new Vector<WritableSheet>();
	}
	
	public void writeEnd() throws WriteException, IOException{
		workbook.write();
		workbook.close();
		fos.close();
	}
	
	public int createPage(String pageName){
		int ret=pages.size();
		WritableSheet now=workbook.createSheet(pageName, pages.size());
		pages.addElement(now);
		return ret;
	}
	
	public void setMergeCells(int page,int left,int top,int right,int bottom) throws RowsExceededException, WriteException{
		WritableSheet now=pages.get(page);
		now.mergeCells(left, top, right, bottom);
	}
	
	public void setRowHeight(int page,int row,int height) throws RowsExceededException{
		WritableSheet now=pages.get(page);
		now.setRowView(row, height, false);
	}
	
	public void addCell(int page,int x,int y, double value) throws RowsExceededException, WriteException{
		WritableSheet now=pages.get(page);
		Number number = new Number(x,y,value);
		now.addCell(number);
	}
	
	public void addCell(int page,int x,int y, String message) throws RowsExceededException, WriteException{
		WritableSheet now=pages.get(page);
		Label formate = new Label(x,y, message);
        now.addCell(formate);
	}
	
	public void addCell(int page,int x,int y, Date date) throws RowsExceededException, WriteException{
		WritableSheet now=pages.get(page);
		DateTime dt = new DateTime(x,y,date);
		now.addCell(dt);
	}
	
	public void addCell(int page,int x,int y, double value,WritableCellFormat formate) throws RowsExceededException, WriteException{
		WritableSheet now=pages.get(page);
		Number number = new Number(x,y,value,formate);
		now.addCell(number);
	}
	
	public void addCell(int page,int x,int y, String message,WritableCellFormat formate) throws RowsExceededException, WriteException{
		WritableSheet now=pages.get(page);
		Label word = new Label(x,y, message,formate);
        now.addCell(word);
	}
	
	public void addCell(int page,int x,int y, Date date,WritableCellFormat formate) throws RowsExceededException, WriteException{
		WritableSheet now=pages.get(page);
		DateTime dt = new DateTime(x,y,date,formate);
		now.addCell(dt);
	}
	
	public void setColumnWidth(int page,int column,int width){
		WritableSheet now=pages.get(page);
		now.setColumnView(column, width);
	}
}
