package bus_frequency_NGA;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

	public static void main(String[] args) throws IOException{
		System.out.println("time,fitness");
		long start = System.currentTimeMillis();
		String modelUp = "up";
		String modelDown = "down";
		Niche niche = new Niche(100, 40, 0.8, 0.2, 2, 300, modelDown, "/home/wmc/文档/425_data/line_425_aly.csv", "/home/wmc/文档/425_data/line_425_" + modelDown + ".csv");
		niche.Run();
		long end = System.currentTimeMillis();
		System.out.println("Total time is: " + (end - start) + "ms");
	}
}
