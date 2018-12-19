package bus_frequency_NGA;

import java.util.Arrays;

public class student implements Comparable<student> {
	private int age;
	public student(int age) {
		this.age = age;
	}
	public int get() {
		return this.age;
	}
	
	public int compareTo(student d) {
		return d.get() - this.age;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		student[] y = new student[5];
		for(int i = 0; i < 5; i++) {
			y[i] = new student(i);
		}
		Arrays.parallelSort(y, 0, 4);
		for(int i = 0; i < 5; i++) {
			System.out.println(y[i].get());
		}
	}

}
