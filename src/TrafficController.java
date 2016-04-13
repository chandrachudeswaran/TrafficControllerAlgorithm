import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TrafficController {

	int counter_a;
	int counter_b;
	int counter_c;
	int counter_d;
	LinkedHashSet<String> traffic_served_list;
	ArrayList<String> zeroList;
	String filename;

	public TrafficController() {
		counter_a = 0;
		counter_b = 0;
		counter_c = 0;
		counter_d = 0;
		traffic_served_list = new LinkedHashSet<String>();
		// filename= "//home//pi//project_workspace//socketserver//";
		filename = "C:\\Users\\chandra\\Desktop\\Sujay\\";
	}

	public void write(String content, String filename) {
		BufferedWriter bw = null;
		try {
			File file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			} else {
				PrintWriter writer = new PrintWriter(file);
				writer.print("");
				writer.close();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void writeRedValuesToAllFiles(String key) {
		for (int i = 1; i < 5; i++) {
			write("0", filename + "myresult" + i + ".txt");
		}
		writeSignalResult(key);
	}

	public void writeSignalResult(String key) {
		String name = "";
		switch (key) {
		case "A":
			name = filename + "myresult1.txt";
			break;
		case "B":
			name = filename + "myresult2.txt";
			break;
		case "C":
			name = filename + "myresult3.txt";
			break;
		case "D":
			name = filename + "myresult4.txt";
			break;
		}
		write("1", name);
		storeHistoryValues();
	}
	public void storeHistoryValues() {
		StringBuilder sb = new StringBuilder();
		sb.append(counter_a);
		sb.append(";");
		sb.append(counter_b);
		sb.append(";");
		sb.append(counter_c);
		sb.append(";");
		sb.append(counter_d);
		sb.append(";");
		sb.append(traffic_served_list.toString());
		write(sb.toString(), filename + "history.txt");
	}
	
	public String read(String filename){
		BufferedReader br = null;
		String content=null;
		try {
			String temp;
			br = new BufferedReader(new FileReader(filename));
			if ((temp = br.readLine()) != null) {
				content=temp;
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				System.out.println(ex.toString());
			}
		}
		return content;
	}

	public void readInput() {
		int[] array = new int[4];
			for (int i = 1; i < 5; i++) {
				array[i-1]=Integer.valueOf(read(filename + "mytext" + i+ ".txt"));
			}
			File f = new File(filename + "history.txt");
			if (f.exists() && !f.isDirectory()) {
				readHistoryValues();
			}
			doAnalysis(array[0], array[1], array[2], array[3]);
	}

	public void readHistoryValues() {
		String[] array =read(filename + "history.txt").split(";");
		counter_a = Integer.valueOf(array[0]);
		counter_b = Integer.valueOf(array[1]);
		counter_c = Integer.valueOf(array[2]);
		counter_d = Integer.valueOf(array[3]);
		String[] history_array = array[4].split(",");
		for (int i = 0; i < history_array.length; i++) {
			String temp = history_array[0].replace("[", "");
			temp = temp.replace("]", "");
			traffic_served_list.add(temp);
		}
	}

	public void populateInputs(HashMap<String, Integer> input_map,ArrayList<String> zeroList, int value, int position) {
		switch (position) {
		case 1:
			input_map.put("A", value);
			if (value <= 0)
				zeroList.add("A");
			break;

		case 2:
			input_map.put("B", value);
			if (value <= 0)
				zeroList.add("B");
			break;

		case 3:
			input_map.put("C", value);
			if (value <= 0)
				zeroList.add("C");
			break;

		case 4:
			input_map.put("D", value);
			if (value <= 0)
				zeroList.add("D");
			break;
		}
	}

	public String doAnalysis(int a, int b, int c, int d) {
		zeroList = new ArrayList<String>();
		HashMap<String, Integer> input_map = new HashMap<String, Integer>();
		populateInputs(input_map, zeroList, a, 1);
		populateInputs(input_map, zeroList, b, 2);
		populateInputs(input_map, zeroList, c, 3);
		populateInputs(input_map, zeroList, d, 4);

		LinkedHashMap<String, Integer> result_largest_map = sortByValueOut(input_map);
		String key = result_largest_map.keySet().iterator().next();
		if (traffic_served_list.isEmpty()) {
			traffic_served_list.add(key);
			incrementCounterForKey(key);
			storeHistoryValues();
			return key;
			//writeRedValuesToAllFiles(key);
		} else if (traffic_served_list.contains(key)) {
			String op = doAction(traffic_served_list, key);
			storeHistoryValues();
			return op;
			//writeRedValuesToAllFiles(doAction(traffic_served_list, key));
		} else {
			String op = doAction(traffic_served_list, key);
			storeHistoryValues();
			return op;
			//writeRedValuesToAllFiles(doAction(traffic_served_list, key));
		}

	}

	public void doDecrementonZeroList(ArrayList<String> zeroList) {
		if (!zeroList.isEmpty()) {
			for (String item : zeroList) {
				decrementCounterForNoTraffic(item);
			}
		}
	}

	public String doAction(LinkedHashSet<String> traffic_served_list, String key) {

		if (checkCounterForMaximum() == null) {
			if (traffic_served_list.size() == 3) {
				removeElement(traffic_served_list);
			}
			traffic_served_list.add(key);
			incrementCounterForKey(key);
			return key;
		} else {
			String urgent_key = checkCounterForMaximum();
			if (traffic_served_list.size() == 3) {
				removeElement(traffic_served_list);
			}
			traffic_served_list.add(urgent_key);
			incrementCounterForKey(urgent_key);
			return urgent_key;
		}
	}

	public void removeElement(LinkedHashSet<String> traffic_served_list) {
		Iterator<String> itr = traffic_served_list.iterator();
		while (itr.hasNext()) {
			traffic_served_list.remove(itr.next());
			break;
		}
	}

	public void decrementCounterForNoTraffic(String key) {
		switch (key) {
		case "A":
			counter_a = 0;
			break;
		case "B":
			counter_b = 0;
			break;
		case "C":
			counter_c = 0;
			break;
		case "D":
			counter_d = 0;
			break;
		}

	}

	public String checkCounterForMaximum() {
		HashMap<String, Integer> map_local = new HashMap<String, Integer>();
		map_local.put("A", counter_a);
		map_local.put("B", counter_b);
		map_local.put("C", counter_c);
		map_local.put("D", counter_d);
		LinkedHashMap<String, Integer> result_map_local = sortByValueOut(map_local);
		String key = result_map_local.keySet().iterator().next();
		int value = result_map_local.get(key);
		if (value >= 3) {
			return key;
		}
		return null;
	}

	public void incrementCounterForKey(String key) {

		switch (key) {
		case "A":
			counter_a = 0;
			counter_b++;
			counter_c++;
			counter_d++;
			break;
		case "B":
			counter_b = 0;
			counter_a++;
			counter_c++;
			counter_d++;
			break;
		case "C":
			counter_c = 0;
			counter_a++;
			counter_b++;
			counter_d++;
			break;
		case "D":
			counter_d = 0;
			counter_a++;
			counter_b++;
			counter_c++;
			break;
		}

		doDecrementonZeroList(zeroList);

	}

	
	public LinkedHashMap<String, Integer> sortByValueOut(HashMap<String, Integer> unsortMap) {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String,Integer>();
		for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	public static void main(String[] args) {
		TrafficController traffic = new TrafficController();
		traffic.readInput();
	}

}
