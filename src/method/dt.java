package method;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.classification;
import model.dataset;
import model.TreeNode;
import model.max;
import model.subclassification;
import model.Tree;

/*
 * decision tree
 * 	age income student credit_rating buys_computer
 */
public class dt {
	//private String[] charaname = {"Age","Income","Student","Credit_rating","Buys_computer"}; // 所有变量名
	String[] charaname = {"intface","area","label","local_address","neighbor","peer_as","peer","protocols"}; 
	private ArrayList<String> name = new ArrayList<String>();
	public ArrayList<String> getName(){
		return this.name;
	}
	public ArrayList<dataset> init(){// 将数据集录入arraylist
		ArrayList<dataset> list = new ArrayList<dataset>();
		dataset data;
		String line = "";
		String[] temp;
		int i,j;
		File file = new File("E://work//newdata.txt");
		FileReader reader;
		try {
			reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			while ((line=br.readLine())!=null){
				data = new dataset();
				temp = line.split(" ");
				for (j=0;j<temp.length;j++){
					if (j==0){
						data.setIntface(temp[j]);
					}
					else if (j==1){
						data.setArea(temp[j]);
					}
					else if (j==2){
						data.setLabel(temp[j]);
					}
					else if (j==3){
						data.setLocal_address(temp[j]);
					}
					else if (j==4){
						data.setNeighbor(temp[j]);
					}
					else if (j==5){
						data.setPeer_as(temp[j]);
					}
					else if (j==6){
						data.setPeer(temp[j]);
					}
					else if (j==7){
						data.setProtocols(temp[j]);
					}
				}
				list.add(data);
			}
			br.close();
			//arraylist赋值操作
			for (i=0;i<charaname.length-1;i++){
				name.add(charaname[i]);
			}
			//
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		return list;
	}
	public double getEntropy(ArrayList<classification> list){//将某一种分类方式的结果计算成概率, 存到arraylist中
		classification cla ; 
		int totalnum = 0; //总数计算
		double entropy = 0,temp;
		int i;
		for (i=0;i<list.size();i++){
			cla = (classification)list.get(i);
			totalnum += cla.getNum();
		}
		for (i=0;i<list.size();i++){
			temp = (double)((classification)list.get(i)).getNum()/(double)totalnum;
			entropy -= temp*(Math.log(temp)/Math.log(2));
		}
		return entropy;
	}
	public double getSubEntropy(ArrayList<subclassification> list, int S){//计算子属性的熵, 数据存储到arraylist中
		subclassification subcla;
		classification cla;
		int i,j;
		double subentropy = 0, temp1,tempresult = 0;
		for (i=0;i<list.size();i++){
			subcla = (subclassification)list.get(i);
			for (j=0;j<subcla.getSub().size();j++){
				cla = (classification)subcla.getSub().get(j);
				temp1 = (double)cla.getNum()/(double)subcla.getSubnum();
				if (temp1 == 0)
					continue;
				tempresult -= temp1*(Math.log(temp1)/Math.log(2));
			}
			subentropy += tempresult*subcla.getSubnum()/S;
			tempresult = 0;
		}
		return subentropy;
	}
	public double getRatio(double entropy, double subentropy){ //计算C4.5的信息增益率
		double ratio;
		double gain = entropy-subentropy;
		ratio = gain/subentropy;
		return ratio;
	}
	public String getObject(String character, dataset data){//根据类变量名输出get结果，需要完全改接口
		String result = "";
		if (character.equals("intface")){
			result = data.getIntface();
		}else if (character.equals("area")){
			result = data.getArea();
		}else if (character.equals("label")){
			result = data.getLabel();
		}else if (character.equals("local_address")){
			result = data.getLocal_address();
		}else if (character.equals("neighbor")){ 
			result = data.getNeighbor();
		}else if (character.equals("peer_as")){
			result = data.getPeer_as();
		}else if (character.equals("peer")){
			result = data.getPeer();
		}else{
			result = data.getProtocols();
		}
		/*if (character.equals("Age")){//age
			result = data.getAge();
		}else if (character.equals("Income")){
			result = data.getIncome();
		}else if (character.equals("Student")){
			result = data.getStudent();
		}else if (character.equals("Credit_rating")){
			result = data.getCredit_rating();
		}else{// buys_computer
			result = data.getBuys_computer();
		}*/
		return result;
	}
	public ArrayList<ArrayList<dataset>> divide(ArrayList<dataset> datasets, String character){//将数据集按照属性分成n个子数组
		ArrayList<ArrayList<dataset>> result = new ArrayList<ArrayList<dataset>>();
		dataset data;
		int i,count=0,j;
		boolean flag;
		for (i=0;i<datasets.size();i++){
			j=0;
			flag = false;
			data = (dataset)datasets.get(i);
			if (i==0){
				result.add(0,new ArrayList<dataset>());
				result.get(0).add(data);
				count++;
			}
			else{
				while (j<count){
					if (getObject(character, data).equals(getObject(character, result.get(j).get(0)))){
						result.get(j).add(data);
						flag = true;
						break;
					}			
					j++;
				}
				if (flag == false){
					result.add(j,new ArrayList<dataset>());
					result.get(j).add(data);
					count++;
				}
			}
		}
		return result;
	}
	public ArrayList<subclassification> subclassify(ArrayList<dataset> datasets, String character){//按照枝属性进行分类
		ArrayList<subclassification> list = new ArrayList<subclassification>();// return list
		ArrayList<classification> cla;
		ArrayList<String> StrList,StrList1;
		ArrayList<ArrayList<dataset>> temp = new ArrayList<ArrayList<dataset>>(); // 存子属性的叶子属性
		dataset data;
		subclassification[] subcla;
		boolean flag;
		int i,j,k;
		//algorithm
		StrList = getNum(datasets,character);// 按照属性的分类数组
		StrList1 = getNum(datasets,"protocols");//叶节点综属性个数
		subcla = new subclassification[StrList.size()];
		System.out.println("the size of cluster is: "+StrList.size());
		for (i=0;i<subcla.length;i++)// 初始化
			subcla[i] = new subclassification();
		for (i=0;i<datasets.size();i++){
			flag = false;
			data = (dataset)datasets.get(i);
			if (i==0){// initially
				temp.add(0,new ArrayList<dataset>());
				temp.get(0).add(data);
				subcla[0].setSubclassicifyID(getObject(character, data));
				subcla[0].addSubnum();
			}
			else{
				j = 0;
				while (subcla[j].getSubnum()>0&&j<StrList.size()){
					if (getObject(character, data).equals(subcla[j].getSubclassicifyID())){//重复
						temp.get(j).add(data);
						subcla[j].addSubnum();
						flag = true;
						break;
					}
					j++;
				}
				if (flag == false){
					temp.add(j,new ArrayList<dataset>());
					temp.get(j).add(data);
					subcla[j].setSubclassicifyID(getObject(character, data));
					subcla[j].addSubnum();
				}
			}	
		}
		//添加子属性分类的叶节点分类结果
	    System.out.println(temp.size()+" "+character);
		for (i=0;i<temp.size();i++){
			cla = classify(temp.get(i),StrList1);
			subcla[i].setSub(cla);
			for (k=0;k<cla.size();k++){
				System.out.println(cla.get(k).getClassifyID()+" "+cla.get(k).getNum());
				System.out.println("");
			}
			//System.out.println(cla.get(0).getClassifyID()+" "+cla.get(0).getNum());
		}
		for (i=0;i<StrList.size();i++)//将对象放入list
			list.add(subcla[i]);
		return list;
	}
	public ArrayList<classification> classify(ArrayList<dataset> datasets, ArrayList<String> StrList1){ //只按照叶子属性进行分类
		ArrayList<classification> list;
		ArrayList<String> StrList;
		dataset data;
		classification[] cla;
		int i,j;
		StrList = getNum(datasets,"protocols");// 属性下的分类字符串
		cla = new classification[StrList1.size()];
		for (i=0;i<cla.length;i++){// 初始化
			cla[i] = new classification();
			cla[i].setClassicifyID((String)(StrList1.get(i)));
		}
		list = new ArrayList<classification>();
		for (i=0;i<datasets.size();i++){
			data = (dataset)datasets.get(i);
			for (j=0;j<cla.length;j++){
				if (getObject("protocols", data).equals(cla[j].getClassifyID()))//这需要改动get接口
					cla[j].addNum();
			}
		}
		for (i=0;i<StrList1.size();i++){
				list.add(cla[i]);
		}
		return list;
	}
	public ArrayList<String> getNum(ArrayList<dataset> datasets, String character){ // 返回某一属性在数据集中的分类数
		int i,j;
		boolean flag;
		ArrayList<String> list = new ArrayList<String>();
		dataset ds;
		for (i=0;i<datasets.size();i++){
			flag = false;
			ds = (dataset)datasets.get(i);
			if (list.size()==0){
				list.add(getObject(character, ds));
			}
			else{
				for (j=0;j<list.size();j++){
					if (getObject(character, ds).equals((String)list.get(j))){
						flag = true;
						break;
					}
				}
				if (flag == false){// 未出现在当前的输出arraylist中
					list.add(getObject(character, ds));
				}
			}
		}
		return list;
	}
	public void MakeTree(ArrayList<dataset> datasets,TreeNode root,ArrayList<String> name, double entropy){//根据测试数据集构造决策树-递归
		ArrayList<String> StrList = getNum(datasets, "protocols");
		ArrayList<String> temp;
		//ArrayList<String> name = new ArrayList<String>(); // 存所有的属性名字
		ArrayList<classification> cla = classify(datasets,StrList);
		ArrayList<subclassification> subcla = null;
		ArrayList<ArrayList<dataset>> data;
		TreeNode childNode;
		max max1 = new max();
		int i,j;
		double subentropy,ratio;
		if (entropy == -1)
			entropy = getEntropy(cla); // 叶子节点的信息熵,root节点需要计算
		for (i=0;i<name.size();i++){
			subcla = subclassify(datasets, ((String)name.get(i)));//这个是最后一个的数据
			subentropy = getSubEntropy(subcla, datasets.size());
			if (subentropy !=0){//当前不可以完美分类
				ratio = getRatio(entropy, subentropy);
				if(ratio> max1.getRatio()){
					max1.setRatio(ratio);
					max1.setSubID(((String)name.get(i)));
					max1.setIndex(i);
					max1.setSubentropy(subentropy);
				}
			}
			else{ 
				ratio = 0;//分类成功
				max1.setRatio(ratio);
				max1.setSubID(((String)name.get(i)));
				max1.setIndex(i);
				max1.setSubentropy(subentropy);
				break;
			}
		}
		System.out.println("the current character is: "+max1.getSubID());		
		//name.remove(max1.getIndex());//去掉已经添加的节点
		System.out.println("the size of name is: "+name.size());
		root.setCharacter(max1.getSubID());
		subcla = subclassify(datasets, max1.getSubID());
		data = divide(datasets,root.getCharacter());//分类数组
		temp = getNum(datasets,max1.getSubID());
		//实例化n个子节点
		for (i=0;i<subcla.size();i++){//循环生成n个子节点
			childNode = new TreeNode();
			childNode.setID((String)temp.get(i));
			childNode.setParenet(root);//加入父节点
			//System.out.println("the parent");
			root.setChildren(childNode);
			ArrayList<String> type = getNum(data.get(i),"protocols");
			if (type.size()!=1)//还需要继续分类
				MakeTree(data.get(i),childNode,name,max1.getSubentropy());
			else{
				childNode.setCharacter(type.get(0).toString());
				continue;
			}
		}	
	}
	public void RouteQuery(String character, ArrayList<TreeNode> route, TreeNode root){//根据某一目标属性返回相应的通路-recursion 
		TreeNode temp;
		int i;
		ArrayList<TreeNode> childNodes = root.getChildren();//返回当前父节点的所有子节点
		for (i=0;i<childNodes.size();i++){
			if (childNodes.get(i).getCharacter().equals(character)){//找到目标
				route.add(childNodes.get(i));
				temp = childNodes.get(i);
				while (!temp.getID().equals("root")){
					temp = temp.getParenet();
					route.add(temp);
				}
			}
			else{//未找到目标,继续递归
				RouteQuery(character,route,childNodes.get(i));	
			}
		}
		//return route;
	}
    public static void main(String[] args){
    	String[] charaname = {"intface","area","label","local_address","neighbor","peer_as","peer","protocols"}; 
    	ArrayList<String> name = new ArrayList<String>();
    	TreeNode temp;
    	for (int i=0;i<charaname.length-1;i++)
    		name.add(charaname[i]);
    	dt decisiontree = new dt();
    	ArrayList<dataset> data = decisiontree.init();
    	ArrayList<String> StrList = decisiontree.getNum(data, "protocols");
    	ArrayList<TreeNode> TreeList = new ArrayList<TreeNode>();
    	//ArrayList<ArrayList<dataset>> datasets = decisiontree.divide(data, "buys_computer");
    	//decisiontree.subclassify(data, "intface");
    	Tree tree = new Tree();
    	decisiontree.MakeTree(data, tree.getRoot(),name,-1);
    	decisiontree.RouteQuery("ldp", TreeList, tree.getRoot());
    	System.out.println(TreeList.size());
    	for (int j=0;j<TreeList.size();j++){
    		temp = TreeList.get(TreeList.size()-1-j);
    		System.out.print(temp.getCharacter()+":"+temp.getID()+"->");
    	}
	}
}