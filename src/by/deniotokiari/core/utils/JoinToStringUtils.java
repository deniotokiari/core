package by.deniotokiari.core.utils;

import java.util.HashMap;
import java.util.List;

public class JoinToStringUtils {

	public static <T> String join(List<T> items, String joiner) {
		String result = "";
		for (int i = 0; i < items.size(); i++) {
			result += String.valueOf(items.get(i)) + joiner;
		}
		return substring(result);
	}

	public static String join(HashMap<?, ?> items, String joiner) {
		String result = "";
		String[] k = new String[items.size()];
		items.keySet().toArray(k);
		for (int i = 0; i < k.length; i++) {
			result += k[i] + joiner;
		}
		return substring(result);
	}

	public static <T> String join(T[] items, String joiner) {
		String result = "";
		for (int i = 0; i < items.length; i++) {
			result += String.valueOf(items[i]) + joiner;
		}
		return substring(result);
	}
	
	private static String substring(String str) {
		return str.substring(0, str.length() - 1);
	}

}
