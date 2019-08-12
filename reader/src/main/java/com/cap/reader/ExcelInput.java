package com.cap.reader;

import java.io.File;
import java.io.IOException;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelInput implements ExcelInputReader<String, Row>{
		
	private int columnIndex;
	
	private int sheetIndex;
	
	private Workbook workbook;

	private ExcelInput(String path, int sheetIndex, int columnIndex) {
		
		try {
			this.workbook = WorkbookFactory.create(new File(path));
			this.sheetIndex = sheetIndex;
			this.columnIndex = columnIndex;
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		} 
	}

	public Trie<String, Row> read() {
		return read(this.workbook.getSheetAt(this.sheetIndex), this.columnIndex);
	}

	public Trie<String, Row> read(Sheet sheet, final int column) {		
		Trie<String, Row> trie = new PatriciaTrie<>();		
		sheet.forEach(row->{
			DataFormatter dataFormatter = new DataFormatter();
			String key = dataFormatter.formatCellValue(row.getCell(column));
			trie.put(key, row);			
		});		
		return trie;
	}
	
	public static Builder builder(String path) {
		return new Builder(path);
	}
	
	public static class Builder{
		
		private int columnIndex = 0;
		
		private int sheetIndex = 0;		
		
		private String path;
		
		public Builder(String path) {
			this.path = path;
		}

		public Builder setColumnIndex(int columnIndex) {
			this.columnIndex = columnIndex;
			return this;
		}
		
		public Builder setSheetIndex(int sheetIndex) {
			this.sheetIndex = sheetIndex;
			return this;
		}
		
		public ExcelInputReader<String, Row> build() {
			return new ExcelInput(path, sheetIndex, columnIndex);
		}	
	}	
}
