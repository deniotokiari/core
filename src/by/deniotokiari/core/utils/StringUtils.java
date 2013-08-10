package by.deniotokiari.core.utils;

import java.util.HashMap;
import java.util.List;

public class StringUtils {

	public static <T> String join(List<T> items, String joiner) {
		String result = "";
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == null) {
				continue;
			}
			result += String.valueOf(items.get(i)) + joiner;
		}
		return substring(result, joiner.length());
	}

	public static String join(HashMap<?, ?> items, String joiner) {
		String result = "";
		String[] k = new String[items.size()];
		items.keySet().toArray(k);
		for (int i = 0; i < k.length; i++) {
			result += k[i] + joiner;
		}
		return substring(result, joiner.length());
	}

	public static <T> String join(T[] items, String joiner) {
		String result = "";
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				continue;
			}
			result += String.valueOf(items[i]) + joiner;
		}
		return substring(result, joiner.length());
	}
	
	private static String substring(String str, int n) {
		return str.substring(0, str.length() - n);
	}

}
