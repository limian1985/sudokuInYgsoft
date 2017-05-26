package com.mian4j.sudoku.impl;

import java.util.List;

public class CurProcessElement {
	/**
	 * 类型： 1、行；2、列；3、块
	 */
	int type;
	/**
	 * 单元序号：0~8 
	 */
	int xh;
	/**
	 * 需要计算元素个数，[0,9]
	 */
	int num;
	/**
	 * 当前单元数组的第一个需要计算的数组位置
	 */
	int firstIndex;
	/**
	 * 当前单元数组的最后个需要计算的数组位置
	 */
	int lastIndex;
	/**
	 * firstIndex所对应元素在数独数组的真正位置.
	 */
	int sudokuIndex;
	/**
	 * lastIndex 所对应元素在数独数组的真正位置.
	 */
	int sudokuIndexLast;
	/**
	 * 单元存储的索引数据.
	 */
	List<Integer>unitList;
	/**
	 * 待计算的数独数组索引值.
	 */
	List<Integer>sudokuIndexList;
	/** 
	 * 可能的值.
	 */
	List<Integer> values;
	

	public List<Integer> getSudokuIndexList() {
		return sudokuIndexList;
	}

	public void setSudokuIndexList(final List<Integer> sudokuIndexList) {
		this.sudokuIndexList = sudokuIndexList;
	}




	@Override
	public String toString(){
		StringBuffer sbf = new StringBuffer();
		sbf.append("当前节点类型为type：").append(this.getType()).append(",单元序号xh为：").append(this.getXh())
		.append(",单元起始位置firstIndex为：").append(this.getFirstIndex()).append("单元最后位置lastIndex为：")
		.append(this.getLastIndex()).append(",需要计算的元素个数num为：").append(this.getNum())
		.append(",\r\n 待计算的数独数组索引为").append(this.getSudokuIndexList())
		.append(",侯选择值为").append(this.getValues());
		return sbf.toString();
	}
	
	
	

	public List<Integer> getUnitList() {
		return unitList;
	}

	public void setUnitList(final List<Integer> newUnitList) {
		unitList = newUnitList;
	}

	public int getType() {
		return type;
	}

	public void setType(final int type) {
		this.type = type;
	}

	public int getXh() {
		return xh;
	}

	public void setXh(final int xh) {
		this.xh = xh;
	}

	public int getNum() {
		return num;
	}

	public void setNum(final int num) {
		this.num = num;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(final int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public List<Integer> getValues() {
		return values;
	}

	public void setValues(final List<Integer> values) {
		this.values = values;
	}

	public int getSudokuIndex() {
		return sudokuIndex;
	}

	public void setSudokuIndex(final int sudokuIndex) {
		this.sudokuIndex = sudokuIndex;
	}
	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(final int newLastIndex) {
		lastIndex = newLastIndex;
	}

	public int getSudokuIndexLast() {
		return sudokuIndexLast;
	}

	public void setSudokuIndexLast(final int newSudokuIndexLast) {
		sudokuIndexLast = newSudokuIndexLast;
	}
	
}
