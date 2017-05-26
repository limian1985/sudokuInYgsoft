package com.mian4j.sudoku.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileDataOperate {

	public Map<String,int[]> ReadDataFromFilePath(final String filePath){
		Map<String,int[]> fileData = new LinkedHashMap<String,int[]>();
		final String fileDataStr = this.readFileData(filePath);
		
		String [] strArr = fileDataStr.toString().split("\r\n");
        int num = strArr.length%10;
        if(num!=0){
        	System.out.println("文件内容不规范，每组数独包括【X】应该为10行");
        }
        List<String> ls = new ArrayList<String>();
        StringBuffer sbf = new StringBuffer();
        for(int i=0;i<=strArr.length;i++)
        {
        	if(i%10 == 0 && sbf.length()>0){
        		
        		ls.add(sbf.toString());
        		sbf = new StringBuffer();
        	}
        	if(i!=strArr.length){
        		sbf.append(strArr[i]).append(",");
        	}
        	
        }
        for(int j=0;j<ls.size();j++){
        	String[] data = ls.get(j).split(",");
        	String key = data[0];
        	int [] arrInteger = new int[81];
        	try{
        		for(int m=0;m<81;m++){
            		arrInteger[m]=Integer.parseInt(data[m+1]);
            	}
        	}catch(NumberFormatException numEx){
        		arrInteger = new int[1];
        	}
        	
        	fileData.put(key, arrInteger);
        }
        return fileData;
	}
	/**
	 * 读取文件内容.
	 * @param filePath
	 * @return
	 */
	public String readFileData(final String filePath){
		InputStream in = null;
	    try{
	 	   	FileInputStream fileInputStream  = new FileInputStream(filePath);
	 	   	in = new BufferedInputStream(fileInputStream);

	        byte [] buf = new byte[1024];
	        int bytesRead = in.read(buf);
	        
	        final StringBuffer strb = new StringBuffer();
	        while(bytesRead != -1)
	        {
	            for(int i=0;i<bytesRead;i++){
	         	   char c = (char)buf[i];
	         	   strb.append(c);
	            }
	            bytesRead = in.read(buf);
	        }
//	        System.out.println(strb.toString());
	        return strb.toString();
	    }catch (IOException e){
	        e.printStackTrace();
	        return null;
	    }finally{
	        try{
	            if(in != null){
	                in.close();
	            }
	        }catch (IOException e){
	            e.printStackTrace();
	            return null;
	        }
	    }
	} 
	/**
	 * 将字符内容写到文件中.
	 * @param context
	 * @param fileName
	 */
	public void writeDataToFile(final String context,final String fileName){
		try {  
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件  
            FileWriter writer = new FileWriter(fileName, false);  
            writer.write(context);  
            writer.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
}
