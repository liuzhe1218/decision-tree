package model;

import java.util.ArrayList;

/*
 * 所有决策树节点的抽象类
 * 根据该节点的ID和父节点的属性组成一条路径串,到达叶节点，确定分类结果
 */
public class TreeNode {
	private String Character; // 属性
	private String ID; // root节点为-1,其余为上层character的取值
	//private data dataset; //所有的判断数据对象
	private ArrayList<TreeNode> children = new ArrayList<TreeNode>(); // 子节点的数组
	private TreeNode parenet; // 父节点
	public TreeNode(){
		this.ID = "";
		this.parenet = null;
		this.Character = "";
	}
	public ArrayList<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(TreeNode children) {// 加入一个子节点
		this.children.add(children);
	}
	public TreeNode getParenet() {
		return parenet;
	}
	public void setParenet(TreeNode parenet) {
		this.parenet = parenet;
	}
	public String getCharacter() {
		return Character;
	}
	public void setCharacter(String character) {
		Character = character;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
}
