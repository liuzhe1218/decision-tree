package model;

import java.util.ArrayList;

public class Tree {
	private TreeNode root; //¸ù½Úµã
	public Tree(){
		//root.setParenet(null);
		root = new TreeNode();
		root.setID("root");
	}
	public TreeNode getRoot() {
		return root;
	}
	public void setRoot(TreeNode root) {
		this.root = root;
	}
	/*public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}*/
}
