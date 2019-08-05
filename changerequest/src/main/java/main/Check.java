package main;

import utilities.ExcelComparer;

public class Check {

	public static void main(String[] args) {
			
		ExcelComparer.getResult(excelComparer->{
				excelComparer.source("D:/Projects/sample.xls")
							 .sheet(0)
							 .column(1)
							 .compareAgainst("Central", "East")
							 .compare();							
		});
	}

}
