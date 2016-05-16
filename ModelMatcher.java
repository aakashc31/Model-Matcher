import java.util.*;
import java.io.*;

public class ModelMatcher{
	
	public static Vector<String> allFiles; //will contain the address of all the files for each model
	public Vector<Model> models; //each of the files is loaded as an object of type Model, and stores in this array
	
	public static void main(String args[]){
		String path = args[0];
		allFiles = new Vector<String>();
		getAllFileNamesInFolder(path);
		
		ModelMatcher as = new ModelMatcher();
		as.readFiles();
		as.compareModels();
	}

	//read the directory path, and store address of each of the models in allFiles array
	public static void getAllFileNamesInFolder(String path){
		Boolean __IS_UBUNTU__ = false;
		String concat = "\\";
		if(__IS_UBUNTU__)
			concat = "/";
		File directory = new File(path);
		File[] list = directory.listFiles();
		for(int i=0; i<list.length; i++){
			allFiles.add(path + concat + list[i].getName());
		}
	}

	// read each file and create a model for each of them
	public void readFiles(){
		models = new Vector<Model>();
		for(int i=0; i<allFiles.size(); i++){
			Model m = new Model();
			m.readModelFromFile(allFiles.get(i));
			models.add(m);
			// m.print();
			m.merge();
			m.computeDihedralAngles();
			// m.print();
		}
	}

	public void compareModels(){
		SortedMap<String, Vector<String>> M = new TreeMap<String, Vector<String>>();
		for(int i=0; i<models.size(); i++){
			for(int j=i+1; j<models.size(); j++){
				if(models.get(i).isSimilar(models.get(j))){
					String n1 = models.get(i).modelName, n2 = models.get(j).modelName;
					if(M.containsKey(n1)){
						Vector<String> temp = M.get(n1);
						temp.add(n2);
					}
					else{
						Vector<String> temp = new Vector<String>();
						temp.add(n2);
						M.put(n1, temp);
					}
					if(M.containsKey(n2)){
						Vector<String> temp = M.get(n2);
						temp.add(n1);
					}
					else{
						Vector<String> temp = new Vector<String>();
						temp.add(n1);
						M.put(n2, temp);
					}
				}
			}
		}
		Set<String> s = M.keySet();
		Iterator<String> it = s.iterator();
		while(it.hasNext()){
			String name = it.next();
			Vector<String> val = M.get(name);
			Collections.sort(val);
			System.out.print(name + " ");
			for(int i=0; i<val.size(); i++){
				System.out.print(val.get(i));
				if(i < val.size() - 1)
					System.out.print(" ");
			}
			System.out.println("");
		}
	}
}