package com.cap.reader;

import org.apache.commons.collections4.Trie;

public interface InputReader<K, V> {
	
	Trie<K, V> read();

}
