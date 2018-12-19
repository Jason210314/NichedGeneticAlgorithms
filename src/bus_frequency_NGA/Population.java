package bus_frequency_NGA;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class Population {
	private final int MaxHamDis;//海明距离限制
	private final int popSize;
	private final int sPopSize;
	private final double pCross;
	private final double pMutate;
	private Individual[] Pop;
	
	//构造器
	public Population(int popSize, int sPopSize, double pCross, double pMutate, int MaxHamDis) {
		this.popSize = popSize;
		this.sPopSize = sPopSize;
		this.pCross = pCross;
		this.pMutate = pMutate;
		this.MaxHamDis = MaxHamDis;
		this.Pop = new Individual[popSize + sPopSize];//分配空间，后ｓPopSize个位置作为被选择种群位置
		
		//初始化种群，产生个体
		for(int i = 0; i < popSize + sPopSize; i++) {
			this.Pop[i] = new Individual();
		}
	}
	
	//初始选择
	public void PreSelect() {
		//排序
		Arrays.sort(Pop, 0, popSize);
		//复制前sPopSize个到小种群
		for(int i = 0; i < this.sPopSize; i++) {
			this.Pop[i + popSize].CopyFrom(this.Pop[i]);
		}
	}
	//交叉操作
	public void Cross() {
		Individual one = null, two = null;
		for(int i = 0; i < popSize; i++) {
			if(Math.random() < pCross) {
				if(one == null) {
					one = Pop[i];
				} else if(two == null) {
					two = Pop[i];
					//System.out.println("交叉");
					one.CrossWith(two);
					one = null;
					two = null;
				}
			}
		}
	}
	
	//变异操作
	public void Mutate() {
		for(int i = 0; i < popSize; i++) {
			if(Math.random() < pMutate) {
				this.Pop[i].Mutate();
			}
		}
	}
	
	//选择操作
	public void MySelect() {
		double sumFitness = 0;//总适应度
		double accPro = 0;
		int geneLen = Individual.geneLen;
		for(int i = 0; i < popSize; i++) {
			sumFitness += Pop[i].GetFitness();
		}
		//计算累计概率；
		for(int i = 0; i < popSize; i++) {
			accPro += (Pop[i].GetFitness() / sumFitness);
			Pop[i].SetaccProbability(accPro);
		}
		
		for(int i = 0; i < popSize; i++) {
			double pick = Math.random();
			for(int j = 0; j < popSize; j++) {
				if(pick <= Pop[j].GetaccProbability()) {
					Pop[i].CopyFrom(Pop[j]);
					break;
				}
			}
		}
		
	}
	
	//小生境淘汰操作
	public void NicheEliminate() {
		for(int i = 0; i < popSize + sPopSize - 1; i++) {
			for(int j = i + 1; j < popSize + sPopSize; j++) {
				int HamDis = Pop[i].HamDis(Pop[j]);
				if(HamDis < MaxHamDis) {
					if(Pop[i].GetFitness() < Pop[j].GetFitness()) {
						Pop[i].Punish();
					} else {
						Pop[j].Punish();
					}
				}
				
			}
		}
	}
	//选择下一代新种群
	public void RefreshPop() {
		Arrays.sort(Pop, 0, popSize + sPopSize);
		for(int i = 0; i < this.sPopSize; i++) {
			this.Pop[i + popSize].CopyFrom(this.Pop[i]);
		}
	}
	
	public void Reverse() {
		for(int i = 0; i < popSize; i++) {
			this.Pop[i].reverse();
		}
		
	}
	
	public Individual GetIndividualOfIndex(int Index) {
		return Pop[Index];
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
