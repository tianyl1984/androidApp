package com.tyl.commom;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtil {

	public static int[] stringToArray(String str) {
		List<Integer> ids = new ArrayList<Integer>();
		for (String id : str.split(":")) {
			if (StringUtil.isNotBlank(id)) {
				ids.add(Integer.parseInt(id));
			}
		}
		int[] result = new int[ids.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = ids.get(i);
		}
		return result;
	}
}
