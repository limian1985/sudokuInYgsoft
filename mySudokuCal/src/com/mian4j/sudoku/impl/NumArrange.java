package com.mian4j.sudoku.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumArrange {
	
	private boolean f[];
	
	private List<String> results;
	
	private List<int[]> results2;
	
	public NumArrange( int n){
		this.f= new boolean[n];
    	Arrays.fill(f, true);
    	this.results = new ArrayList<String>();
    	this.results2 =  new ArrayList<int[]>();
	}
	
	public List<String> getResults(){
		return this.results;
	} 
	public List<int[]> getResults2(){
		return this.results2;
	} 
	
    public static void main(final String[] args) {
    	NumArrange ar = new NumArrange(3);
    	int[] num={1,2,3};
    	ar.arrangeNum(num);
    	List results = ar.getResults();
    	System.out.println(results);
    	List<int[]> results2 = ar.getResults2();
    	for(int[] a : results2){
//    		System.out.println(a);
    		for(int b : a){
    			System.out.print(b);
    		}
    		System.out.println();
    	}
    	
    	
    	
//    	String str="";
//    	count(num,str,num.length);
    }
    /**
     * 
     * @param num 表示要排列的数组
     * @param str 以排列好的字符串
     * @param nn  剩下需要排列的个数，如果需要全排列，则nn为数组长度
     */
//    private static void count(final int[] num, final String str, final int nn) {
//        if(nn==0){
//            System.out.println(str);
//            return;
//        }
//        for(int i=0;i<num.length;i++){
//            if(!f[i]){
//                continue;
//            }
//            f[i]=false;
//            count(num,str+num[i],nn-1);
//            f[i]=true;
//        }
//    }
    
    private void count2(final int[] num, final String str, final int nn) {
        if(nn==0){
            //System.out.println(str);
            this.results.add(str);
            return;
        }
        for(int i=0;i<num.length;i++){
            if(!f[i]){
                continue;
            }
            f[i]=false;
//            System.out.println(i+":"+str);
            count2(num,str+num[i]+",",nn-1);
            f[i]=true;
        }
    }
    
    private void count3(final int[] num, final int[] tarArr, final int nn) {
        if(nn==0){
            //System.out.println(str);
            this.results2.add(tarArr);
            return;
        }
        for(int i=0;i<num.length;i++){
            if(!f[i]){
                continue;
            }
            f[i]=false;
            int [] tarr = this.fillArr(tarArr, num[i]);
            count3(num,tarr,nn-1);
            f[i]=true;
        }
    }
    
    public void arrangeNum( final int[] sourceArr){
    	String str = "";
    	this.count2(sourceArr, str, sourceArr.length);
    	int [] arr = new  int [sourceArr.length];
    	this.count3(sourceArr, arr, sourceArr.length);
    }
    
    private int[] fillArr(int [] sour, int v){
    	int i=0;
    	for(i=0;i<sour.length; i++){
    		if(sour[i]==0){
    			sour[i] = v;
    			return sour;
    		}
    	}
    	
    	int[] nsour = new int[sour.length];
    	nsour[0] = v;
    	return nsour;
    }
    

}
