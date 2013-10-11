/*
 *
 *FatFileReader - Reads big file and returns the content of given line number
 *Copyright (C) 2013, DCS.
 *
 *This program is free software: you can redistribute it and/or modify
 *it under the terms of the GNU General Public License as published by
 *the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.dcs.system.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class FatFileReader {

	private Map<Long, Long> lineMap;
	private String fileName;
	//private String tempFileName = new Date().hashCode()+".temp";
	//private FileReader tempFileReader;
	//private FileWriter tempFileWriter;
	private long lines;
	private RandomAccessFile randomAccessFile;
	
	
	public FatFileReader(String fileName){
		this.fileName = fileName;
		this.lines = 0;
		this.lineMap = new HashMap<Long, Long>();
		/*
		try {
			tempFileWriter = new FileWriter(tempFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
	
	public String getLine(long lineNumber){
		if (lineNumber > 0) {
		String readLine = "";
		long bytesToSkip = lineMap.get(lineNumber);
		//System.out.println("Bytes to Skip -->"+ bytesToSkip);
		try {
			randomAccessFile.seek(bytesToSkip);
			readLine = randomAccessFile.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return readLine;
		}
		return null;
	}
	
	public long getLinesCount(){
		return this.lines;
	}
	
	public void load(){
		readFile(fileName);
		randInst();
	}
	
	private void readFile(String fileName){
		try {
			FileReader originalFileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(originalFileReader);
			String origLine;
			long lineLength = 0;
			System.out.println("Reading File-->");			
			while ((origLine = bufferedReader.readLine()) != null) {
				lines++;
				lineMap.put(lines, lineLength);
				//tempFileWriter.write(String.valueOf(lineLength)+'\n');
				lineLength = lineLength + origLine.getBytes().length + 1;				
			}
			//tempFileWriter.close();
			System.out.println("Done Reading File<--");
			//tempFileReader = new FileReader(tempFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void randInst(){
		try {
			randomAccessFile = new RandomAccessFile(fileName, "r");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
