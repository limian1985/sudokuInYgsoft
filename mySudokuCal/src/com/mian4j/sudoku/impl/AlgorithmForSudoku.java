package com.mian4j.sudoku.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmForSudoku {
	
	private int log =0;
	private  BigDecimal DECIMAL_NIGHT = new BigDecimal(9);
	private  int INT_NIGHT = 9;
	
	private void printArray(final int[] array){
		for(int i =0;i<array.length;i++){
			int v = array[i];
			System.out.print(v);
			System.out.print(",");
			if((i+1)%9 == 0){
				System.out.println();
			}
		}
	}
	
	public static void main(final String[] args) {
		AlgorithmForSudoku sudoAl = new AlgorithmForSudoku();

		int[] sudoku = {8,0,0,0,0,0,0,0,0,
				0,0,3,6,0,0,0,0,0,
				0,7,0,0,9,0,2,0,0,
				0,5,0,0,0,7,0,0,0,
				0,0,0,0,4,5,7,0,0,
				0,0,0,1,0,0,0,3,0,
				0,0,1,0,0,0,0,6,8,
				0,0,8,5,0,0,0,1,0,
				0,9,0,0,0,0,4,0,0
		};
		
		long s = System.currentTimeMillis();
		List<int[]> rs = sudoAl.getResults(sudoku);
		for(int[] sudo : rs){
			sudoAl.printArray(sudo);
		}
		long e = System.currentTimeMillis();
		long time = (e-s);
		
		System.out.println("调用方法计算sudoAl.getResults_Sec");
		long s2 = System.currentTimeMillis();
		List<int[]> rs2 = sudoAl.getResults_Sec(sudoku);
		for(int[] sudo : rs2){
			sudoAl.printArray(sudo);
		}
		long e2 = System.currentTimeMillis();
		long time2 = (e2-s2);
		System.out.println("总耗时间为1："+time+" 豪秒");
		System.out.println("总耗时间为2："+time2+" 豪秒");
		
	}
	private Map<Integer,List<Integer>>rowIndexMap;
	private Map<Integer,List<Integer>>colIndexMap;
	private Map<Integer,List<Integer>>boxIndexMap;
	
	
	public Map<Integer, List<Integer>> getRowIndexMap() {
		return rowIndexMap;
	}
	public Map<Integer, List<Integer>> getColIndexMap() {
		return colIndexMap;
	}
	public Map<Integer, List<Integer>> getBoxIndexMap() {
		return boxIndexMap;
	}
	/**
	 * 构造函数，初始化行、列、块索引值.
	 */
	public AlgorithmForSudoku(){
		this.buildRowIndex();
		this.buildColIndex();
		this.buildBoxIndex();
	}
	/**
	 * map数据内容打印.
	 * @param m
	 */
	private void printMapObj(final String title,final Map<Integer,List<Integer>> m){
		if(log ==1){
			System.out.println(title);
			for(int i = 0;i<m.size();i++){
				System.out.println(m.get(i));
			}
		}

	}
	/**
	 * 构建数独行索引.
	 */
	private void buildRowIndex(){
		this.rowIndexMap = new HashMap<Integer,List<Integer>>();
		for(int i=0;i<9;i++){
			List<Integer>rowIndex = new ArrayList<Integer>(9);
			for(int j=0;j<9;j++){
				int value = 9*i+j;
				rowIndex.add(value);
			}
			this.rowIndexMap.put(i, rowIndex);
		}
		this.printMapObj("构建数独行索引",rowIndexMap);
	}
	/**
	 * 构建数独列维度索引.
	 */
	private void buildColIndex(){
		this.colIndexMap = new HashMap<Integer,List<Integer>>();
		for(int i=0;i<9;i++){
			List<Integer>colIndex = new ArrayList<Integer>(9);
			for(int j=0;j<9;j++){
				int value = j*9+i;
				colIndex.add(value);
			}
			colIndexMap.put(i, colIndex);
		}
		this.printMapObj("构建数独列维度索引",colIndexMap);
	}
	/**
	 * 构建数独块纬度索引.
	 */
	private void buildBoxIndex(){
		this.boxIndexMap = new HashMap<Integer,List<Integer>>();
		for(int i=0;i<3;i++){
			//大行
			for(int j=0;j<3;j++){
				//大列
				List<Integer>boxIndex = new ArrayList<Integer>(9);
				for(int m =0;m<3;m++){
					//块内行
					for(int n=0;n<3;n++){
						//块内列
						int value = i*9*3+(j*3)+m*9+n;
						boxIndex.add(value);
					}
				}
				boxIndexMap.put(i*3+j, boxIndex);
			}
		}
		this.printMapObj("构建数独块纬度索引",boxIndexMap);
	}
	
	/**
	 * 根据当前索引值，返回该数值所在行纬度的单元序号（第几行）.
	 */
	private int getRowPosOfIndex(final int index){
		final BigDecimal dataIndex = new BigDecimal(index);
		final BigDecimal rowPos = dataIndex.divide(this.DECIMAL_NIGHT, BigDecimal.ROUND_DOWN);
		return rowPos.intValue();
	}
	/**
	 * 根据当前索引值，返回该数值所在列纬度的位置（第几列）.
	 * @param index
	 * @return
	 */
	private int getColPosOfIndex(final int index){
		int colPos = index%this.INT_NIGHT;
		return colPos;
	}
	/**
	 * 根据当前索引值，返回该数值所在块纬度的位置（第几块）.
	 * @param index
	 * @return
	 */
	private int getBoxPosOfIndex(final int index){
		int boxPos =0;
		final BigDecimal dataIndex = new BigDecimal(index);
		final BigDecimal box_row = dataIndex.divide(new BigDecimal(27), BigDecimal.ROUND_DOWN);
		final BigDecimal col = new BigDecimal(index%this.INT_NIGHT);
		final BigDecimal box_col = col.divide(new BigDecimal(3), BigDecimal.ROUND_DOWN);
		boxPos = box_row.intValue()*3 + box_col.intValue();
		
		return boxPos;
	}
	/**
	 * 判断数独数组是否已经解决.
	 * @param sudoku
	 * @return true,已解决；false 未完成.
	 */
	private boolean isCompleted(final int[] sudoku){
		for(int n :sudoku){
			if(0 == n){
				return false;
			}
		}
		return true;
	}
	/**
	 * 判断单元数组中需要解决的元素个数.
	 * @param unionArray
	 * @return
	 */
	private int getNumOfZero(final int[] sudoku, final List<Integer> unitList){
		int num =0;
		for(int n : unitList){
			if (0 == sudoku[n]){
				num++;
			}
		}
		return num;
	}
	
	/**
	 * 判断每一个纬度单元的元素和是否等于45，即已经解析完成
	 * @param sudoku
	 * @param unitList
	 * @return
	 */
	public boolean isCompleteUnit(final int[] sudoku, final List<Integer> unitList){
		int sum =0;
		for(int n : unitList){
			sum += sudoku[n];
		}
		if(45 == sum){
			return true;
		}
		return false;
	}
	/**
	 * 判断数独数组是否已经被正确计算出来
	 * @param sudoku
	 * @return
	 */
	public boolean isCompleteSudoku(final int[] sudoku){
		for(int i=0; i<9; i++){
			List<Integer>rowUnits = this.getRowIndexMap().get(i);
			boolean right = this.isCompleteUnit(sudoku, rowUnits);
			if(!right){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * 拷贝数组.
	 * @param array
	 * @return
	 */
	private int[] copyArray(final int [] array){
		int length = array.length;
		int [] newArray = new int [length];
		for(int i=0;i<length;i++){
			newArray[i] = array[i];
		}
		return newArray;
	}
	
	/**
	 * 判断候选值在当前数独单元中是否合法.
	 * @param value 候选值.
	 * @param sudoku 当前数独数组.
	 * @param unit 当前维度单元.
	 * @return .
	 */
	private boolean isExistInSudokuUnit(final int value,final int[] sudoku,final List<Integer>unit){
		
		for(int i=0;i<unit.size();i++){
			int index = unit.get(i);
			int v = sudoku[index];
			if(value == v){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断当前数独位置的候选值在行维度是否合法.
	 * @param value 候选值.
	 * @param index 数独数组的位置索引.
	 * @return 是否合法 true 、false；
	 */
	private boolean isRightValueInRow(final int[] sudoku,final int value,final int index){
		int rowNum = this.getRowPosOfIndex(index);
		List<Integer> unit= this.rowIndexMap.get(rowNum); 
		boolean result  = this.isExistInSudokuUnit(value, sudoku, unit);
		return result;
	}
	/**
	 * 判断当前数独位置的候选值在列维度是否合法.
	 * @param value 候选值.
	 * @param index 数独数组的位置索引.
	 * @return 是否合法 true 、false；
	 */
	private boolean isRightValueInCol(final int[] sudoku,final int value,final int index){
		int rowNum = this.getColPosOfIndex(index);
		List<Integer> unit= this.getColIndexMap().get(rowNum); 
		boolean result  = this.isExistInSudokuUnit(value, sudoku, unit);
		return result;
	}
	/**
	 * 判断当前数独位置的候选值在块维度是否合法.
	 * @param value 候选值.
	 * @param index 数独数组的位置索引.
	 * @return 是否合法 true 、false；
	 */
	private boolean isRightValueInBox(final int[] sudoku,final int value,final int index){
		int rowNum = this.getBoxPosOfIndex(index);
		List<Integer> unit= this.getBoxIndexMap().get(rowNum); 
		boolean result  = this.isExistInSudokuUnit(value, sudoku, unit);
		return result;
	}
	/**
	 * 判断候选值在数独数组是否正确.
	 * @param type 当前计算的维度
	 * @param index 当前计算的数独数组的位置索引.
	 * @param value 待填入的值
	 * @return
	 */
	public boolean isRightValue(final int[] sudoku, final int type,final int index,final int value){
		//根据type，分别判断value 在另外两个维度的数据合法性
		boolean result = false;
		boolean rowResult = false;
		boolean colResult = false;
		boolean boxResult = false;
		switch(type){
			case 1:
				//判断value在列、块维度的合法性
				colResult = this.isRightValueInCol(sudoku, value, index);
				boxResult = this.isRightValueInBox(sudoku, value, index);
				if(colResult && boxResult){
					result = true;
				}
				break;
			case 2:
				// 判断value 在行、块维度的合法性
				rowResult = this.isRightValueInRow(sudoku, value, index);
				boxResult = this.isRightValueInBox(sudoku, value, index);
				if(rowResult && boxResult){
					result = true;
				}
				break;
			case 3:
				//判断value 在行、列维度的合法性
				rowResult = this.isRightValueInRow(sudoku, value, index);
				colResult = this.isRightValueInCol(sudoku, value, index);
				if(rowResult && colResult){
					result = true;
				}
				break;
			default :;
			
		}
		return result;
	}
	/**
	 * 获取开始计算的数独元素的索引信息.
	 * @param curEl
	 * @param sudoku
	 * @param unitList
	 */
	public void getStartIndex(final CurProcessElement curEl, final int[]sudoku){
		int first =0;
		int sudokuIndex = 0;
		
		int firstIndex =0;
		int lastIndex = 0;
		int sudokuIndex_first = 0;
		int sudokuIndex_last = 0;
		final List<Integer>unitList = curEl.getUnitList();
		final List<Integer>sudokuIndexList = new ArrayList<Integer>();
		
		for(int i=0;i<9;i++){
			sudokuIndex = unitList.get(i);
			
			if(sudoku[sudokuIndex] ==0){
				sudokuIndexList.add(sudokuIndex);
				if(0== first){
					firstIndex = i;
					sudokuIndex_first = sudokuIndex;
					first =1;
				}
				lastIndex = i;
				sudokuIndex_last = sudokuIndex;
			}
		}
		curEl.setFirstIndex(firstIndex);
		curEl.setSudokuIndex(sudokuIndex_first);
		curEl.setLastIndex(lastIndex);
		curEl.setSudokuIndexLast(sudokuIndex_last);
		curEl.setSudokuIndexList(sudokuIndexList);
		
	}
	/**
	 * 判断单元数组中缺少的候选值.
	 * @param sudoku
	 * @param unitList
	 */
	public void getPossibleValues(final int[]sudoku, final CurProcessElement curEl ){
		int sudokuIndex =0;
		int click =0;
		List<Integer>unitList = curEl.getUnitList();
		List<Integer>possibleValues = new ArrayList<Integer>();
		for(int i=1;i<=9; i++){
			click =0;
			for(int j=0; j<9; j++){
				sudokuIndex = unitList.get(j);
				int v = sudoku[sudokuIndex];
				if(i == v){
					click =1;
					break;
				}
			}
			if(click ==0){
				possibleValues.add(i);
			}
		}
		curEl.setValues(possibleValues);
	}
	/**
	 * 获取行维度最少需要处理的单元信息.
	 * @param sudoku
	 * @return
	 */
	private CurProcessElement getLestProcessNumOfRow(final int[]sudoku){
		final CurProcessElement curEl = new CurProcessElement();
		int xh = -1;
		int num =10;
		List<Integer>unitList = null;
		for(int i=0;i<9;i++){
			List<Integer> rowUnit = this.getRowIndexMap().get(i);
			int n_z = this.getNumOfZero(sudoku, rowUnit);
			if(n_z>0 && n_z<num){
				num = n_z;
				xh = i;
				unitList = rowUnit;
			}
		}
		curEl.setType(1);
		curEl.setXh(xh);
		curEl.setNum(num);
		curEl.setUnitList(unitList);
		
		return curEl;
	}
	/**
	 * 获取列维度最少需要处理的单元信息.
	 * @param sudoku
	 * @return.
	 */
	private CurProcessElement getLestProcessNumOfCol(final int[]sudoku){
		final CurProcessElement curEl = new CurProcessElement();
		int xh = -1;
		int num =10;
		List<Integer>unitList = null;
		for(int i=0;i<9;i++){
			List<Integer> colUnit = this.getColIndexMap().get(i);
			int n_z = this.getNumOfZero(sudoku, colUnit);
			if(n_z>0 && n_z<num){
				num = n_z;
				xh = i;
				unitList = colUnit;
			}
		}
		curEl.setType(2);
		curEl.setXh(xh);
		curEl.setNum(num);
		curEl.setUnitList(unitList);
		return curEl;
	}
	/**
	 * 获取块维度最少需要处理的单元信息.
	 * @param sudoku
	 * @return.
	 */
	private CurProcessElement getLestProcessNumOfBox(final int[]sudoku){
		final CurProcessElement curEl = new CurProcessElement();
		int xh = -1;
		int num =10;
		List<Integer>unitList = null;
		for(int i=0;i<9;i++){
			List<Integer> boxUnit = this.getBoxIndexMap().get(i);
			int n_z = this.getNumOfZero(sudoku, boxUnit);
			if(n_z>0 && n_z<num){
				num = n_z;
				xh = i;
				unitList = boxUnit;
			}
		}
		curEl.setType(3);
		curEl.setXh(xh);
		curEl.setNum(num);
		curEl.setUnitList(unitList);
		return curEl;
	}
	
	/**
	 * 获取需要计算元素最小的元素信息.
	 * @param curEl_Row
	 * @param curEl_Col
	 * @param curEl_Box
	 * @return
	 */
	private CurProcessElement getLeastNumOfEl(final CurProcessElement curEl_Row, 
			final CurProcessElement curEl_Col,final CurProcessElement curEl_Box){
		CurProcessElement lestNumEl;
		if(curEl_Row.getNum()<=curEl_Col.getNum()){
			lestNumEl = curEl_Row;
		}else{
			lestNumEl = curEl_Col;
		}
		if(lestNumEl.getNum() > curEl_Box.getNum() ){
			lestNumEl = curEl_Box;
		}
		return lestNumEl;
	}
	
	//获取最佳计算的数独元素位置和所在纬度、候选值
	public CurProcessElement getProcessEl(final int[]sudoku){
		
		//默认从行纬度开始
		final CurProcessElement curEl_Row = this.getLestProcessNumOfRow(sudoku);
		final CurProcessElement curEl_Col = this.getLestProcessNumOfCol(sudoku);
		final CurProcessElement curEl_Box = this.getLestProcessNumOfBox(sudoku);
	
		final CurProcessElement curEl = this.getLeastNumOfEl(curEl_Row, curEl_Col, curEl_Box);
		this.getPossibleValues(sudoku, curEl);
		this.getStartIndex(curEl, sudoku);
		
		return curEl;
	}
	
	private void printProcessInfo(final int [] sudoku,final CurProcessElement curEl){
		if( 1==log ){
			System.out.println("当前数独数组为：");
			this.printArray(sudoku);
			System.out.println("即将处理元素信息：" + curEl.toString());
		}
		
	}
	
	//1、获取当前行纬度、列维度、快纬度中缺少需要填充的数量，从当前需要填充数最小的维度开始
	//2、获取当前填充单元可以填充的数值集合，
	public List<int[]>getResults(final int[]sudoku){
		// 1、处理传入Sudoku，判断从哪一维度开始，需要获取CurProcessElement
		//具体维度类型、单元序号，位置索引
		final List<int[]>results = new ArrayList<int[]>();
		final List<int[]>nextProcessList = new ArrayList<int[]>();
		CurProcessElement curEl = this.getProcessEl(sudoku);
		this.printProcessInfo(sudoku, curEl);
		int type = curEl.getType();
		if( 2 == curEl.getNum()) {
			for(int i=1; i<=2; i++){
				Integer firstVal = curEl.getValues().get((i-1)%2);
				Integer	lastVal = curEl.getValues().get(i%2);
				int sudokuFirstIndex = curEl.getSudokuIndex();
				int sudokuLastIndex = curEl.getSudokuIndexLast();
				boolean firstEl = this.isRightValue(sudoku, type, sudokuFirstIndex, firstVal);
				boolean sedEl = this.isRightValue(sudoku, type, sudokuLastIndex, lastVal);
				if( firstEl && sedEl){
					int[] newSudoku = this.copyArray(sudoku);
					newSudoku[sudokuFirstIndex] = firstVal;
					newSudoku[sudokuLastIndex] = lastVal;
					boolean completed = this.isCompleted(newSudoku);
					if(completed){
						results.add(newSudoku);
					}else{
						nextProcessList.add(newSudoku);
					}
				}
			}
		} else {
			for(Integer value : curEl.getValues()) {
				// 2、判断当前value值是否符合规则，如果符合规则，
				//那么拷贝传入的sudoku，生成新的数独数组，填入该值,并判断该数独数组是否已经解完
				boolean result = this.isRightValue(sudoku, curEl.getType(), curEl.getSudokuIndex(), value);
//				System.out.println("计算结果"+ value +":" + result);
				if(result){
					int[] newSudoku = this.copyArray(sudoku);
					newSudoku[curEl.getSudokuIndex()] = value;
					boolean completed = this.isCompleted(newSudoku);
					if(completed){
						results.add(newSudoku);
					}else{
						nextProcessList.add(newSudoku);
					}
				}
			}
			
		}
		
		
		int num = nextProcessList.size();
		if(num > 0) {
			//递归计算
			for(int i=0;i<num; i++){
				int[] nextNewSudoku = nextProcessList.get(i);
				List<int[]>part_results = this.getResults(nextNewSudoku);
				if(part_results.size()>0){
					results.addAll(part_results);
				}
			}
		}
		return results;
	}
	
	/**
	 * 数独数组解法2
	 * @param sudoku
	 * @return
	 */
	public List<int[]>getResults_Sec(final int[]sudoku){
		// 1、处理传入Sudoku，判断从哪一维度开始，需要获取CurProcessElement
		//具体维度类型、单元序号，位置索引
		final List<int[]>results = new ArrayList<int[]>();
		final List<int[]>nextProcessList = new ArrayList<int[]>();
		CurProcessElement curEl = this.getProcessEl(sudoku);
		
		this.printProcessInfo(sudoku, curEl);
		
		//2、根据curEl中的 待计算的数独数组索引curEl.getSudokuIndexList() 和 侯选择值curEl.getValues()，组合出该单元数组的可能解
		int unitType = curEl.getType();
		List<Integer> values = curEl.getValues();
		int count = values.size();
		if(count == 1){
			int value = values.get(0);
			int sudokuIndex = curEl.getSudokuIndex();
			this.doProcessElValue (sudoku, unitType, sudokuIndex, value, results, nextProcessList);
		}else{
			final NumArrange numArrange = new NumArrange(count);
	    	List<int[]> valueArrayList = numArrange.arrangeNum(values);
	    	List<Integer>sudokuIndexs = curEl.getSudokuIndexList();
	    	int i =0;
	    	Map<Integer,Integer>wrongVmap = new HashMap<Integer,Integer>();
	    	for(int [] vArr : valueArrayList){
	    		boolean isRight = true;
	    		for(i=0; i<count; i++ ){
	    			int value = vArr[i];
	    			int sudokuIndex = sudokuIndexs.get(i);
	    			
	    			if(wrongVmap.get(sudokuIndex)!=null){
	    				int wrongV = wrongVmap.get(sudokuIndex);
	    				if(wrongV == value){
		    				isRight = false;
		    				break;
		    			}
	    			}
	    			
	    			boolean result = this.isRightValue(sudoku, unitType, sudokuIndex, value);
	    			if(result == false){
	    				isRight = false;
	    				wrongVmap.put(sudokuIndex, value);
	    				break;
	    			}
	    		}
	    		if(isRight == true){
	    			int[] newSudoku = this.copyArray(sudoku);
	    			for(i=0; i<count; i++ ){
	    				int value = vArr[i];
		    			int sudokuIndex = sudokuIndexs.get(i);
		    			newSudoku[sudokuIndex] = value;
	    			}
					boolean completed = this.isCompleted(newSudoku);
					if(completed){
						results.add(newSudoku);
					}else{
						nextProcessList.add(newSudoku);
					}
	    		}
	    	}
		}
		int num = nextProcessList.size();
		if(num > 0) {
			//递归计算
			for(int i=0;i<num; i++){
				int[] nextNewSudoku = nextProcessList.get(i);
				List<int[]>part_results = this.getResults(nextNewSudoku);
				if(part_results.size()>0){
					results.addAll(part_results);
				}
			}
		}
		return results;
	}
	
	private void doProcessElValue (final int[]sudoku, final int unitType, final int sudokuIndex, final int value,
			final List<int[]>results, final List<int[]>nextProcessList) {
		boolean result = this.isRightValue(sudoku, unitType, sudokuIndex, value);
		if(result){
			int[] newSudoku = this.copyArray(sudoku);
			newSudoku[sudokuIndex] = value;
			boolean completed = this.isCompleted(newSudoku);
			if(completed){
				results.add(newSudoku);
			}else{
				nextProcessList.add(newSudoku);
			}
		}
	}
	
}
