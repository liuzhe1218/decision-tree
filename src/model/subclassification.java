package model;

import java.util.ArrayList;

/*
 * 子类的分类存储
 */
public class subclassification {
	private String subclassifyID; // dataset某属性的取值
	private int subnum = 0; //拥有该取值的对象个数
	private ArrayList<classification> sub; //该子分类中的关于叶节点的分类
	public String getSubclassicifyID() {
		return subclassifyID;
	}
	public void setSubclassicifyID(String subclassifyID) {
		this.subclassifyID = subclassifyID;
	}
	public int getSubnum() {
		return subnum;
	}
	public void addSubnum() {
		this.subnum ++;
	}
	public ArrayList<classification> getSub() {
		return sub;
	}
	public void setSub(ArrayList<classification> sub) {
		this.sub = sub;
	}
}
