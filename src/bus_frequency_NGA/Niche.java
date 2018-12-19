package bus_frequency_NGA;

import java.io.IOException;



public class Niche {
	private final int MaxHamDis;//海明距离限制
	private final int MaxIterationCnt;
	private final int popSize;
	private final int sPopSize;
	private final double pCross;
	private final double pMutate;
	private int stopCnt;
	private char key;
	private final int upStopCnt = 21;
	private final int downStopCnt = 19;
	private Population population;
	
	//构造器
	public Niche(int popSize, int sPopSize, double pCross, double pMutate, int MaxHamDis, int MaxIterationCnt, String Direction, String OpenFileName, String OutFileName) throws IOException {
		this.popSize = popSize;
		this.sPopSize = sPopSize;
		this.pCross = pCross;
		this.pMutate = pMutate;
		this.MaxHamDis = MaxHamDis;
		this.MaxIterationCnt = MaxIterationCnt;
		if(Direction.equals("up")) {
			key = ',';
			this.stopCnt = upStopCnt;
		} else if(Direction.equals("down")){
			this.stopCnt = downStopCnt;
			key = '-';
		}
		Individual.stopCnt = this.stopCnt;
		
		this.ReadData(OpenFileName, OutFileName);
		
		population = new Population(this.popSize, this.sPopSize, this.pCross, this.pMutate, this.MaxHamDis);
	}
	
	//读取数据
	private void ReadData(String OpenFileName, String OutFileName) throws IOException {
		CsvLineReader read = new CsvLineReader(OpenFileName);
		CsvLineWriter write = new CsvLineWriter(OutFileName);
		int[][] passengerData = Individual.PassengerData;
		for(int i = 0; i < 22; i++) {
			for(int j = 0; j < 35; j++) {
				passengerData[i][j] = 0;
			}
		}
		
		String str;
		str = read.readLine();
		write.writeLine(str);
		while ((str = read.readLine()) != null) {
			char key1 = ',', key2 = ' ';
			int cnt = 0;
			for(int i = 0; i < str.length(); i++) {
				if(str.charAt(i) == key1) {
					cnt++;
				}
				if (cnt == 5) {
					String s = str.substring(i + 3, i + 6);
					int num = Integer.parseInt(s);
					if(num == 425) {
						if(str.charAt(str.length() - 2) == key) {
							write.writeLine(str);
							int cnt1 = 0, cnt2 = 0;
							int stop = 0, time = 0;
							boolean flag = true;
							for(int j = 0; j < str.length(); j++) {
								if(str.charAt(j) == key1) {
									cnt1++;
								} else if(str.charAt(j) == key2) {
									cnt2++;
								}
								if(cnt1 == 3) {
									stop = Integer.parseInt(str.substring(j + 1, j + 2));
									if(Character.isDigit(str.charAt(j + 2))) {
										stop = stop * 10 + Integer.parseInt(str.substring(j + 2, j + 3));
									}
									break;
								}
								if(cnt2 == 2 && flag) {
									String a = str.substring(j + 1, j + 3);
									String b = str.substring(j + 4, j + 6);
									time = (Integer.parseInt(a) * 60 + Integer.parseInt(b)) / 30 - 11;
									flag = false;
								}
							}
							if(time >= 0 && time <= 34)
								passengerData[stop][time]++;
						} 
						break;
					} else {
						break;
					}
				}
			}
		}
		int sum = 0;
		for(int i = 1; i <= Individual.stopCnt; i++) {
			for(int j = 0; j < Individual.geneLen; j++) {
				passengerData[i][j] = (passengerData[i][j] / 64) + 1; //计算每天客流量
			}
		}
		write.close();
		read.close();
	}

	public void Run() {
		long startTim = System.currentTimeMillis();
		population.PreSelect();
		int cnt = 0;
		for(int i = 0; i < MaxIterationCnt; i++) {
			population.MySelect();
			population.Cross();
			population.Mutate();
			population.Reverse();
			population.NicheEliminate();
			population.RefreshPop();
			cnt++;
			if(cnt % 2 == 0) {
				long endTime = System.currentTimeMillis();
				System.out.printf("%-4d,%-6.2f\n", (endTime - startTim), population.GetIndividualOfIndex(0).GetFitness());
			}
		}
		this.ShowTheResult();
	}
	
	public void ShowTheResult() {
		for(int i = 0; i < 50; i++) {
			System.out.printf("%5.2f\n", population.GetIndividualOfIndex(i).GetFitness());
//			for(int j = 0; j < Individual.geneLen; j++) {
//				System.out.printf("%d ", population.GetIndividualOfIndex(i).GetGeneOfIndex(j));
//			}
//			System.out.println("");
		}
		
		System.out.println("");
	}
	public static void main(String[] args) throws IOException {
		
	}

}
