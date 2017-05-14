/*
 * Copyright 2000-2020 YGSoft.Inc All Rights Reserved.
 */
package com.mian4j.sudoku.impl;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * TODO �ڴ�д��������˵��.<br>
 * @author limian <br>
 * @version 1.0.0 2017-5-2<br>
 * @see 
 * @since JDK 1.5.0
 */
public class MysudokuRun {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		System.out.println("�������ļ�·����");
		Scanner scan = new Scanner(System.in);
		String filePath = scan.nextLine();
		System.out.println("�ļ�·��: "+ filePath);
		
		FileDataOperate fileOp = new FileDataOperate();
		Map<String,int[]> fileData = fileOp.ReadDataFromFilePath(filePath);
		Object[] keyData = fileData.keySet().toArray();
		StringBuffer outPutData = new StringBuffer();
		
		AlgorithmForSudoku sudoAl = new AlgorithmForSudoku();
		
		int ringthNum,wrongNum;
		
		for(int i=0;i<keyData.length;i++){
			ringthNum =0;
			wrongNum = 0;
			long stime = System.currentTimeMillis();
			final String key = keyData[i].toString();
			String numKey = "";
			int s = key.indexOf("[");
    		int e = key.indexOf("]");
    		if (s>=0 && e>0){
    			numKey = key.substring(s+1,e);
    		}else{
    			numKey = key;
    		}
			int[] sourceData = fileData.get(key); 
			if(sourceData.length<81){
				outPutData.append("[").append(numKey).append(",-1]").append("\r\n");
//				continue;
			}else{
				List<int[]> rs = sudoAl.getResults(sourceData);
				int n = rs.size();
				if(n <= 0){
					outPutData.append("[").append(numKey).append(",0]").append("\r\n");
				}else{
					for(int m =0; m < n; m++){
						outPutData.append("[").append(numKey).append(",").append((m+1)).append("]").append("\r\n");
						int [] result = rs.get(m);
						boolean isRight = sudoAl.isCompleteSudoku(result);
						if(isRight){
							ringthNum++;
						}else{
							wrongNum++;
						}
					    for(int j=0;j<result.length;j++){
								if((j+1)%9==0){
									outPutData.append(result[j]).append("\r\n");
								}else{
									outPutData.append(result[j]).append(",");
								}
						}
					}
				}
				
			}
			long etime = System.currentTimeMillis();
			long exeTime = etime - stime;
			System.out.println("��" + key + "�����ʱ��Ϊ: " + exeTime + "����");
			System.out.println("��" + key + "�����������(" + ringthNum 
					+ ")����ȷ��("+ wrongNum + ")������");
		}
		final String outPutFile = "SU_DOKU_AN.txt";
		fileOp.writeDataToFile(outPutData.toString(), outPutFile);
		
		
	}
	
	

}
