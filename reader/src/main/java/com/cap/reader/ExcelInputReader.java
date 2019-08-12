package com.cap.reader;

import org.apache.commons.collections4.Trie;
import org.apache.poi.ss.usermodel.Sheet;

public interface ExcelInputReader<K, V> extends InputReader<K, V> {
	
	Trie<K, V> read();
	
	Trie<K, V> read(Sheet sheet, int column);

}
