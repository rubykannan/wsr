package com.cap.reader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections4.Trie;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException, IOException
    {
        InputReader<String, Row> inputReader = ExcelInput.builder("D:\\Projects\\sample.xlsx")
        												 .setSheetIndex(0)
        												 .setColumnIndex(1)
        												 .build();
        Trie<String, Row> trie = inputReader.read();
        System.out.println(trie.get("Central").getRowNum());
 
        
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sample");
        XSSFRow row = sheet.createRow(0); 
        AtomicInteger atomicInteger = new AtomicInteger(0);
        trie.get("Central").forEach(cell->{
        	DataFormatter dataFormatter = new DataFormatter();
        	Cell outCell = row.createCell(atomicInteger.getAndIncrement());
        	outCell.setCellValue(dataFormatter.formatCellValue(cell));        	
        	
        });
        
        try(FileOutputStream outputStream = new FileOutputStream("sample.xlsx")){
        	workbook.write(outputStream);
        }
        workbook.close();
    }
}
