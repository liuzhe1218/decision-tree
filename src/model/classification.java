package model;

public class classification {
	private String classifyID = " ";//叶节点的取值
	private int num = 0;//该取值下的对象数量
	public String getClassifyID() {
		return classifyID;
	}
	public void setClassicifyID(String classifyID) {
		this.classifyID = classifyID;
	}
	public int getNum() {
		return num;
	}
	public void addNum() {
		this.num++;
	}
}
