package com.mian4j.sudoku.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumArrange {
	
	private boolean f[];
	
	private List<String> results;
	
	private List<int[]> results2;
	
	public NumArrange( final int n){
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
    	
    	List<Integer>ls = new ArrayList<Integer>();
    	int num =3;
    	for(int i=0; i<num; i++){
    		ls.add(i+1);
    	}
    	NumArrange ar = new NumArrange(num);
    	List<int[]> results = ar.arrangeNum(ls);
    	System.out.println(results);
    	for(int[] a : results){
    		for(int v : a){
    			System.out.print(v);
    		}
    		System.out.println();
    	}
    }
    
    public List<int []> arrangeNum( final List<Integer> sourceArr){
    	String str = "";
    	int num = sourceArr.size();
    	this.count1(sourceArr, str, num);
    	this.results2.clear();
    	
    	if(this.results.size()>0){
    		int i =0;
    		for(String s : results){
    			int[] arr = new int[num];
    			for(i=0; i<num; i++){
    				String v = s.substring(i, i+1);
    				arr[i] = Integer.parseInt(v);
    			}
    			results2.add(arr);
    		}
    	}
    	return this.results2;
    	
    }
    
    private void count1(final List<Integer> num, final String str, final int nn) {
        if(nn==0){
            //System.out.println(str);
            this.results.add(str);
            return;
        }
        for(int i=0;i<num.size();i++){
            if(!f[i]){
                continue;
            }
            f[i]=false;
//            System.out.println(i+":"+str);
            count1(num,str+num.get(i),nn-1);
            f[i]=true;
        }
    }
    
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
            count2(num,str+num[i],nn-1);
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
    
   
    
    private int[] fillArr(final int [] sour, final int v){
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
