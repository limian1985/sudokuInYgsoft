package com.mian4j.sudoku.impl;

import java.util.List;

public class CurProcessElement {
	/**
	 * ���ͣ� 1���У�2���У�3����
	 */
	int type;
	/**
	 * ��Ԫ��ţ�0~8 
	 */
	int xh;
	/**
	 * ��Ҫ����Ԫ�ظ�����[0,9]
	 */
	int num;
	/**
	 * ��ǰ��Ԫ����ĵ�һ����Ҫ���������λ��
	 */
	int firstIndex;
	/**
	 * ��ǰ��Ԫ�����������Ҫ���������λ��
	 */
	int lastIndex;
	/**
	 * firstIndex����ӦԪ�����������������λ��.
	 */
	int sudokuIndex;
	/**
	 * lastIndex ����ӦԪ�����������������λ��.
	 */
	int sudokuIndexLast;
	/**
	 * ��Ԫ�洢����������.
	 */
	List<Integer>unitList;
	/**
	 * �������������������ֵ.
	 */
	List<Integer>sudokuIndexList;
	/** 
	 * ���ܵ�ֵ.
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
		sbf.append("��ǰ�ڵ�����Ϊtype��").append(this.getType()).append(",��Ԫ���xhΪ��").append(this.getXh())
		.append(",��Ԫ��ʼλ��firstIndexΪ��").append(this.getFirstIndex()).append("��Ԫ���λ��lastIndexΪ��")
		.append(this.getLastIndex()).append(",��Ҫ�����Ԫ�ظ���numΪ��").append(this.getNum())
		.append(",\r\n �������������������Ϊ").append(this.getSudokuIndexList())
		.append(",��ѡ��ֵΪ").append(this.getValues());
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
