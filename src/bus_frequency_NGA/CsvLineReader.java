package bus_frequency_NGA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class CsvLineReader {
	
	BufferedReader reader;
	public CsvLineReader(String FileName) throws FileNotFoundException, UnsupportedEncodingException {
		FileInputStream fileInputStream = new FileInputStream(FileName);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
		this.reader = new BufferedReader(inputStreamReader);
	}
	public String readLine() throws IOException {
		return reader.readLine();
	}
	public void close() throws IOException {
		reader.close();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
