package bus_frequency_NGA;

import java.util.Random;

public class Individual implements Comparable<Individual> {
	public static int Count = 0;
	private static final double cMax = 10200;
	public static final int geneLen = 35; /*基因长度*/
	private static double alpha = 0.4;		//公交公司加权系数
	private static double beta  = 0.6;		//乘客加权系数\
	private static double C_1 = 4;			//公交车单位距离运营成本
	private static double C_2 = 0.1;		//乘客单位时间成本
	private static final double ticPrice = 1.3;
	private static double lineLen = 19.3;	//公交线路长度
	private static int timeGapLen = 30;			//每个时间段长度30min
	private static final double punishQuo = 0.7;//惩罚系数 
	
	public static  int[][] PassengerData = new int[22][geneLen];
	
	public static int stopCnt;
	private static final int[] gap = {3, 5, 6, 10, 15, 30}; //发车间隔
	
	
	private double accProbability = 0;
	private int[] gene;	//基因序列
	private double Fitness;//个体适应度
	
	
	Random rand = new Random();
	//构造器
	public Individual() {
		//初始化基因长度，基因
		this.gene = new int[geneLen];
		//便利基因数组，赋值
		for(int i = 0; i < geneLen; i++) {
			int rad = rand.nextInt(gap.length);//随机取间隔数组中一个数
			this.gene[i] = gap[rad];
		}
		this.CalFitness();
	}
	
	
	//惩罚操作
	public void Punish() {
		this.Fitness *= punishQuo;
	}
	//返回累计概率
	public double GetaccProbability() {
		return this.accProbability;
	}
	//设置累计概率
	public void SetaccProbability(double accProbability) {
		this.accProbability = accProbability;
	}
	//返回适应度值
	public double GetFitness() {
		return this.Fitness;
	}
	
	//返回指定下标处基因编码值
	public int GetGeneOfIndex(int Index) {
		return this.gene[Index];
	}
	//设置指定下标处基因编码值
	public void SetGeneOfIndex(int Index, int Value) {
		this.gene[Index] = Value;
	}
	//变异
	public void Mutate() {
		int pos = rand.nextInt(geneLen);	//选中基因序列中一个点做变异点
		int rad = rand.nextInt(gap.length);//随机取间隔数组中一个数
		while(this.gene[pos] == gap[rad]){
			rad = rand.nextInt(gap.length);
		}
		this.gene[pos] = gap[rad];
		
		this.CalFitness();
	}
	//进化逆转
	public void reverse() {
		double preFit = this.Fitness;
		int pos = rand.nextInt(geneLen);
		int preCode = this.gene[pos];
		int index = rand.nextInt(gap.length);
		while(this.gene[pos] == gap[index]) {
			index = rand.nextInt(gap.length);
		}
		this.gene[pos] = gap[index];
		this.CalFitness();
		double newFit = this.Fitness;
		if(newFit < preFit) {
			this.gene[pos] = preCode;
			this.Fitness = preFit;
		}
	}
	//交叉
	public void CrossWith(Individual ano) {
		int pos = rand.nextInt(geneLen);
		for(int i = pos; i < geneLen; i++) {
			int temp = this.gene[i];
			this.gene[i] = ano.GetGeneOfIndex(i);
			ano.SetGeneOfIndex(i, temp);
		}
		this.CalFitness();
		ano.CalFitness();
	}
	//计算适应度
	public void CalFitness() {
		double companyCost = 1;
		double passengerCost = 1;
		double profit;
		
		//计算公司
		companyCost *= (C_1 * lineLen);
		double busCnt = 0;
		for(int i = 0; i < geneLen; i++) {
			busCnt += timeGapLen / this.gene[i];
		}
		companyCost *= busCnt;
		//计算乘客
		passengerCost *= C_2;
		int sum = 0;
		int sumPassenger = 0;
		for(int i = 0; i < geneLen; i++) {
			for(int j = 1; j <= stopCnt; j++) {
				sumPassenger += Individual.PassengerData[j][i];
				sum += Individual.PassengerData[j][i] * this.gene[i];
			}
		}
		passengerCost *= sum;
		profit = (ticPrice * sumPassenger);
		if(companyCost > profit) {
			this.Fitness = 0;
		} else {
			double f = alpha * companyCost + beta * passengerCost;
			this.Fitness = cMax - f;
			if(this.Fitness < 0) {
				this.Fitness = 0;
			}
		}
		
		//Individual.Count++;
	}
	
	//从另一个个体复制数据的方法
	public void CopyFrom(Individual ano) {
		for(int i = 0; i < geneLen; i++){
			this.gene[i] = ano.GetGeneOfIndex(i);
		}
		this.Fitness = ano.GetFitness();
	}
	
	//计算和另一个个体的海明距离
	public int HamDis(Individual ano) {
		int ret = 0;
		for(int i = 0; i < geneLen; i++) {
			if(this.gene[i] != ano.GetGeneOfIndex(i)) {
				ret++;
			}
		}
		return ret;
	}
	//重载compare
	@Override
	public int compareTo(Individual ano) {
		if(this.Fitness > ano.GetFitness()) {
			return -1;
		} else if(this.Fitness < ano.GetFitness()) {
			return 1;
		}else {
			return 0;
		}
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
