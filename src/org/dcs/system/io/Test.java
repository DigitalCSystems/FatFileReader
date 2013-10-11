package org.dcs.system.io;

import java.io.IOException;

public class Test {
	public static void main(String[] args) throws IOException {

		FatFileReader fatFileReader = new FatFileReader("SampleFile.csv");
		fatFileReader.load();
		for (int i = 1; i <= fatFileReader.getLinesCount(); i++) {
			System.out.println(fatFileReader.getLine(i));
		}
	}
}
