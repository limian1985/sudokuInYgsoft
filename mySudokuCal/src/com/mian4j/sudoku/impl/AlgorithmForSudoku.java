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
		
		System.out.println("���÷�������sudoAl.getResults_Sec");
		long s2 = System.currentTimeMillis();
		List<int[]> rs2 = sudoAl.getResults_Sec(sudoku);
		for(int[] sudo : rs2){
			sudoAl.printArray(sudo);
		}
		long e2 = System.currentTimeMillis();
		long time2 = (e2-s2);
		System.out.println("�ܺ�ʱ��Ϊ1��"+time+" ����");
		System.out.println("�ܺ�ʱ��Ϊ2��"+time2+" ����");
		
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
	 * ���캯������ʼ���С��С�������ֵ.
	 */
	public AlgorithmForSudoku(){
		this.buildRowIndex();
		this.buildColIndex();
		this.buildBoxIndex();
	}
	/**
	 * map�������ݴ�ӡ.
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
	 * ��������������.
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
		this.printMapObj("��������������",rowIndexMap);
	}
	/**
	 * ����������ά������.
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
		this.printMapObj("����������ά������",colIndexMap);
	}
	/**
	 * ����������γ������.
	 */
	private void buildBoxIndex(){
		this.boxIndexMap = new HashMap<Integer,List<Integer>>();
		for(int i=0;i<3;i++){
			//����
			for(int j=0;j<3;j++){
				//����
				List<Integer>boxIndex = new ArrayList<Integer>(9);
				for(int m =0;m<3;m++){
					//������
					for(int n=0;n<3;n++){
						//������
						int value = i*9*3+(j*3)+m*9+n;
						boxIndex.add(value);
					}
				}
				boxIndexMap.put(i*3+j, boxIndex);
			}
		}
		this.printMapObj("����������γ������",boxIndexMap);
	}
	
	/**
	 * ���ݵ�ǰ����ֵ�����ظ���ֵ������γ�ȵĵ�Ԫ��ţ��ڼ��У�.
	 */
	private int getRowPosOfIndex(final int index){
		final BigDecimal dataIndex = new BigDecimal(index);
		final BigDecimal rowPos = dataIndex.divide(this.DECIMAL_NIGHT, BigDecimal.ROUND_DOWN);
		return rowPos.intValue();
	}
	/**
	 * ���ݵ�ǰ����ֵ�����ظ���ֵ������γ�ȵ�λ�ã��ڼ��У�.
	 * @param index
	 * @return
	 */
	private int getColPosOfIndex(final int index){
		int colPos = index%this.INT_NIGHT;
		return colPos;
	}
	/**
	 * ���ݵ�ǰ����ֵ�����ظ���ֵ���ڿ�γ�ȵ�λ�ã��ڼ��飩.
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
	 * �ж����������Ƿ��Ѿ����.
	 * @param sudoku
	 * @return true,�ѽ����false δ���.
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
	 * �жϵ�Ԫ��������Ҫ�����Ԫ�ظ���.
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
	 * �ж�ÿһ��γ�ȵ�Ԫ��Ԫ�غ��Ƿ����45�����Ѿ��������
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
	 * �ж����������Ƿ��Ѿ�����ȷ�������
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
	 * ��������.
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
	 * �жϺ�ѡֵ�ڵ�ǰ������Ԫ���Ƿ�Ϸ�.
	 * @param value ��ѡֵ.
	 * @param sudoku ��ǰ��������.
	 * @param unit ��ǰά�ȵ�Ԫ.
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
	 * �жϵ�ǰ����λ�õĺ�ѡֵ����ά���Ƿ�Ϸ�.
	 * @param value ��ѡֵ.
	 * @param index ���������λ������.
	 * @return �Ƿ�Ϸ� true ��false��
	 */
	private boolean isRightValueInRow(final int[] sudoku,final int value,final int index){
		int rowNum = this.getRowPosOfIndex(index);
		List<Integer> unit= this.rowIndexMap.get(rowNum); 
		boolean result  = this.isExistInSudokuUnit(value, sudoku, unit);
		return result;
	}
	/**
	 * �жϵ�ǰ����λ�õĺ�ѡֵ����ά���Ƿ�Ϸ�.
	 * @param value ��ѡֵ.
	 * @param index ���������λ������.
	 * @return �Ƿ�Ϸ� true ��false��
	 */
	private boolean isRightValueInCol(final int[] sudoku,final int value,final int index){
		int rowNum = this.getColPosOfIndex(index);
		List<Integer> unit= this.getColIndexMap().get(rowNum); 
		boolean result  = this.isExistInSudokuUnit(value, sudoku, unit);
		return result;
	}
	/**
	 * �жϵ�ǰ����λ�õĺ�ѡֵ�ڿ�ά���Ƿ�Ϸ�.
	 * @param value ��ѡֵ.
	 * @param index ���������λ������.
	 * @return �Ƿ�Ϸ� true ��false��
	 */
	private boolean isRightValueInBox(final int[] sudoku,final int value,final int index){
		int rowNum = this.getBoxPosOfIndex(index);
		List<Integer> unit= this.getBoxIndexMap().get(rowNum); 
		boolean result  = this.isExistInSudokuUnit(value, sudoku, unit);
		return result;
	}
	/**
	 * �жϺ�ѡֵ�����������Ƿ���ȷ.
	 * @param type ��ǰ�����ά��
	 * @param index ��ǰ��������������λ������.
	 * @param value �������ֵ
	 * @return
	 */
	public boolean isRightValue(final int[] sudoku, final int type,final int index,final int value){
		//����type���ֱ��ж�value ����������ά�ȵ����ݺϷ���
		boolean result = false;
		boolean rowResult = false;
		boolean colResult = false;
		boolean boxResult = false;
		switch(type){
			case 1:
				//�ж�value���С���ά�ȵĺϷ���
				colResult = this.isRightValueInCol(sudoku, value, index);
				boxResult = this.isRightValueInBox(sudoku, value, index);
				if(colResult && boxResult){
					result = true;
				}
				break;
			case 2:
				// �ж�value ���С���ά�ȵĺϷ���
				rowResult = this.isRightValueInRow(sudoku, value, index);
				boxResult = this.isRightValueInBox(sudoku, value, index);
				if(rowResult && boxResult){
					result = true;
				}
				break;
			case 3:
				//�ж�value ���С���ά�ȵĺϷ���
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
	 * ��ȡ��ʼ���������Ԫ�ص�������Ϣ.
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
	 * �жϵ�Ԫ������ȱ�ٵĺ�ѡֵ.
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
	 * ��ȡ��ά��������Ҫ����ĵ�Ԫ��Ϣ.
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
	 * ��ȡ��ά��������Ҫ����ĵ�Ԫ��Ϣ.
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
	 * ��ȡ��ά��������Ҫ����ĵ�Ԫ��Ϣ.
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
	 * ��ȡ��Ҫ����Ԫ����С��Ԫ����Ϣ.
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
	
	//��ȡ��Ѽ��������Ԫ��λ�ú�����γ�ȡ���ѡֵ
	public CurProcessElement getProcessEl(final int[]sudoku){
		
		//Ĭ�ϴ���γ�ȿ�ʼ
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
			System.out.println("��ǰ��������Ϊ��");
			this.printArray(sudoku);
			System.out.println("��������Ԫ����Ϣ��" + curEl.toString());
		}
		
	}
	
	//1����ȡ��ǰ��γ�ȡ���ά�ȡ���γ����ȱ����Ҫ�����������ӵ�ǰ��Ҫ�������С��ά�ȿ�ʼ
	//2����ȡ��ǰ��䵥Ԫ����������ֵ���ϣ�
	public List<int[]>getResults(final int[]sudoku){
		// 1��������Sudoku���жϴ���һά�ȿ�ʼ����Ҫ��ȡCurProcessElement
		//����ά�����͡���Ԫ��ţ�λ������
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
				// 2���жϵ�ǰvalueֵ�Ƿ���Ϲ���������Ϲ���
				//��ô���������sudoku�������µ��������飬�����ֵ,���жϸ����������Ƿ��Ѿ�����
				boolean result = this.isRightValue(sudoku, curEl.getType(), curEl.getSudokuIndex(), value);
//				System.out.println("������"+ value +":" + result);
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
			//�ݹ����
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
	 * ��������ⷨ2
	 * @param sudoku
	 * @return
	 */
	public List<int[]>getResults_Sec(final int[]sudoku){
		// 1��������Sudoku���жϴ���һά�ȿ�ʼ����Ҫ��ȡCurProcessElement
		//����ά�����͡���Ԫ��ţ�λ������
		final List<int[]>results = new ArrayList<int[]>();
		final List<int[]>nextProcessList = new ArrayList<int[]>();
		CurProcessElement curEl = this.getProcessEl(sudoku);
		
		this.printProcessInfo(sudoku, curEl);
		
		//2������curEl�е� �������������������curEl.getSudokuIndexList() �� ��ѡ��ֵcurEl.getValues()����ϳ��õ�Ԫ����Ŀ��ܽ�
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
			//�ݹ����
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
