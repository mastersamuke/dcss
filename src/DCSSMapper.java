import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DCSSMapper {
	private HashMap<String, DCSSObject> hmMap;
	private HashMap<String, Boolean> hmUsed;
	private ArrayList<String> alSorted;

	public DCSSMapper() {
		hmMap = new HashMap<>();
		hmUsed = new HashMap<>();
		alSorted = new ArrayList<>();
	}

	public String FindPath(String start, String end, boolean exclusives) {
		if (start.equals(end)) {
			return start;
		}

		Queue<Stack<String>> qPaths = new LinkedList<>();
		Stack<String> stack = new Stack<>();
		stack.push(start);
		hmUsed.put(start, true);
		qPaths.add(stack);
		String strPath = FindPath(qPaths, end, exclusives);

		ClearUsed(); // We're done finding the path, regardless.

		return strPath;

	}

	private String FindPath(Queue<Stack<String>> queue, String end, boolean exclusives) {
		while (!queue.isEmpty()) {
			Stack<String> stack = queue.poll();
			LinkedList<String> llDVs = hmMap.get(stack.peek()).GetDVs();
			for (String s : llDVs) {
				System.out.println(s);
				if (!hmUsed.get(s) && ((!hmMap.get(s).GetExclusive()) || exclusives)) {
					Stack<String> newStack = new Stack<>();
					newStack.addAll(stack);
					newStack.add(s);
					if (s.equals(end)) {
						return BuildPathFromStack(newStack);
					} else {
						hmUsed.put(s, true);
						queue.add(newStack);
					}
				}
			}
		}
		return "No paths found.";
	}

	private String BuildPathFromStack(Stack<String> stack) {
		String path = new String("");

		path = stack.pop();
		while (!stack.empty()) {
			path = stack.pop() + " >> " + path;
		}

		return path;

	}

	private void ClearUsed() {
		for (String key : hmUsed.keySet()) {
			if (hmUsed.get(key)) {
				hmUsed.put(key, false);
			}
		}
	}

	public void MapName(String s) {
		String[] sa = s.split(",");
		DCSSObject dcssNew;

		alSorted.add(sa[0]);
		Collections.sort(alSorted);

		if (hmMap.containsKey(sa[0])) {
			dcssNew = hmMap.get(sa[0]);
		} else {
			dcssNew = new DCSSObject(sa[0]);
		}

		dcssNew.SetExclusive(sa[1]);
		for (int i = 2; i < sa.length; i++) {
			if (!sa[i].isEmpty()) {
				dcssNew.AddDV(sa[i]);
				if (!hmMap.containsKey(sa[i])) {
					hmMap.put(sa[i], new DCSSObject(sa[i]));
				}
				hmMap.get(sa[i]).AddDV(sa[0]);
			}
		}
		hmMap.put(sa[0], dcssNew);
		hmUsed.put(sa[0], false);
	}

	public ArrayList<String> SortedNames() {
		return alSorted;
	}

}
