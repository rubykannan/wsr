package utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelComparer {

	private Trie<String, List<Row>> trie = new PatriciaTrie<List<Row>>();
	
	private Workbook workbook;
	
	private Sheet sheet;
	
	private String[] comparingText;
	
	public ExcelComparer source(String path) {
		try {
			this.workbook = WorkbookFactory.create(new File(path));
		} catch (EncryptedDocumentException | IOException | InvalidFormatException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public ExcelComparer sheet(int index) {
		this.sheet = this.workbook.getSheetAt(index);
		return this;
	}
	
	public ExcelComparer column(int value) {
		this.sheet.forEach(row->{
			Cell cell = row.getCell(value);
			DataFormatter dataFormatter = new DataFormatter();
			String key = dataFormatter.formatCellValue(cell);
			if(trie.get(key) == null) {
				List<Row> rowList = new ArrayList<>();
				rowList.add(row);
				trie.put(key, rowList);
			}
			else {
				trie.get(key).add(row);
			}			
		});
		return this;
	}
	
	public ExcelComparer compareAgainst(String... values) {
			this.comparingText = values;
			return this;
	}
	
	public ExcelComparer compare() {
		DataFormatter dataFormatter = new DataFormatter();
		for(String value: this.comparingText) {			
				List<Row> rows = this.trie.get(value);
				for(Row row: rows) {
					System.out.println(dataFormatter.formatCellValue(row.getCell(0)));					
				}
				
		}
		return this;
	}
	
	public static void getResult(Consumer<ExcelComparer> consumer) {
		ExcelComparer excelComparer = new ExcelComparer();
		consumer.accept(excelComparer);
	}
	
}
