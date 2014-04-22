package model;

import java.lang.reflect.Method;
/*
 * java 反射
 * 使用dataset类
 */
public class Reflect {
	public Method reflect(String ReflectName,String character,String methods){
		Class<?> demo = null;
		Method returnMethod = null;
		try {
			demo = Class.forName(ReflectName);
			Method[] method = demo.getDeclaredMethods();
			for (int i=0;i<method.length;i++){
				//System.out.println(method[i]);
				if (method[i].toString().contains(methods)&&method[i].toString().contains(character)){
					returnMethod = method[i];
					break;
				}
			}
			//System.out.println(cons.length+" "+cons[1]);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnMethod;
	}
	/*public static void main(String[] args){
		Reflect ref = new Reflect();
		Method method = ref.reflect("model.dataset","Buys_computer","get");
		System.out.println(method);
	}*/
}