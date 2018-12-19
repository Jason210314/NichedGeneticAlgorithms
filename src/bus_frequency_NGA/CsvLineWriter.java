package bus_frequency_NGA;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class CsvLineWriter {

	BufferedWriter writer;
	public CsvLineWriter(String FileName) throws FileNotFoundException, UnsupportedEncodingException {
		FileOutputStream fileOutputStream = new FileOutputStream(FileName);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
		this.writer = new BufferedWriter(outputStreamWriter);
	}
	public void writeLine(String str) throws IOException {
		writer.write(str);
		writer.newLine();
	}
	public void close() throws IOException {
		writer.flush();
		writer.close();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
