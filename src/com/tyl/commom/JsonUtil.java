package com.tyl.commom;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtil {

	public static <T> T json2JavaPojo(String jsonString, Class<T> pojo) {
		try {
			List<Field> fields = getAllField(pojo);
			T result = pojo.newInstance();
			JSONObject jsonObj = new JSONObject(jsonString);
			for (Field f : fields) {
				setFieldValue(f, result, jsonObj);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static void setFieldValue(Field field, Object result, JSONObject jsonObj) throws Exception {
		if (jsonObj.isNull(field.getName())) {
			return;
		}
		if (Modifier.isFinal(field.getModifiers())) {
			return;
		}
		Object value = jsonObj.get(field.getName());
		setBaseTypeValue(field, result, value);
		JSONObject customTypeObj = jsonObj.optJSONObject(field.getName());
		if (customTypeObj != null) {
			setCustomType(field, result, customTypeObj);
		}
		JSONArray jsonArr = jsonObj.optJSONArray(field.getName());
		if (jsonArr != null) {
			Type genericType = field.getGenericType();
			if (genericType == null) {
				return;
			}
			Collection collectionResult = null;
			if (field.get(result) != null) {
				collectionResult = (Collection) field.get(result);
			}
			if (collectionResult == null) {
				if (field.getType().isAssignableFrom(List.class)) {
					collectionResult = new ArrayList();
				}
				if (field.getType().isAssignableFrom(Set.class)) {
					collectionResult = new HashSet();
				}
			}
			if (genericType instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) genericType;
				Class genericClazz = (Class) pt.getActualTypeArguments()[0];
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject obj = jsonArr.getJSONObject(i);
					Object eleObj = genericClazz.newInstance();
					List<Field> fields = getAllField(genericClazz);
					for (Field f : fields) {
						setFieldValue(f, eleObj, obj);
					}
					collectionResult.add(eleObj);
				}
				field.set(result, collectionResult);
			}
		}
	}

	private static void setCustomType(Field field, Object result, JSONObject jsonObj) throws Exception {
		if (jsonObj == null) {
			return;
		}
		Class class1 = field.getType();
		List<Field> fields = getAllField(class1);
		Object customTypeResult = class1.newInstance();
		for (Field f : fields) {
			setFieldValue(f, customTypeResult, jsonObj);
		}
		field.set(result, customTypeResult);
	}

	private static void setBaseTypeValue(Field f, Object result, Object value) throws Exception {
		String typeString = f.getType().toString();
		f.setAccessible(true);
		if (typeString.endsWith("Boolean") || typeString.endsWith("boolean")) {
			f.setBoolean(result, Boolean.getBoolean(value.toString()));
		}
		if (typeString.endsWith("Byte") || typeString.endsWith("byte")) {
			f.setByte(result, Byte.valueOf(value.toString()));
		}
		if (typeString.endsWith("Character") || typeString.endsWith("char")) {
			f.setChar(result, Character.valueOf(value.toString().charAt(0)));
		}
		if (typeString.endsWith("Double") || typeString.endsWith("double")) {
			f.setDouble(result, Double.valueOf(value.toString()));
		}
		if (typeString.endsWith("Float") || typeString.endsWith("float")) {
			f.setFloat(result, Float.valueOf(value.toString()));
		}
		if (typeString.endsWith("Integer") || typeString.endsWith("int")) {
			f.setInt(result, Integer.valueOf(value.toString()));
		}
		if (typeString.endsWith("Long") || typeString.endsWith("long")) {
			f.setLong(result, Long.valueOf(value.toString()));
		}
		if (typeString.endsWith("Short") || typeString.endsWith("short")) {
			f.setShort(result, Short.valueOf(value.toString()));
		}
		if (typeString.endsWith("String")) {
			f.set(result, value.toString());
		}
	}

	private static List<Field> getAllField(Class class1) {
		List<Field> fields = new ArrayList<Field>();
		List<String> names = new ArrayList<String>();
		Field[] fieldArr = class1.getDeclaredFields();
		for (Field f : fieldArr) {
			if (!names.contains(f.getName())) {
				fields.add(f);
				names.add(f.getName());
			}
		}
		Class<? extends Class> superClass = class1.getSuperclass();
		if (superClass != null) {
			List<Field> superFields = getAllField(superClass);
			for (Field f : superFields) {
				if (!names.contains(f.getName())) {
					fields.add(f);
					names.add(f.getName());
				}
			}
		}
		return fields;
	}

	public static <T> List<T> json2List(String jsonString, Class<T> pojo) {
		try {
			List<T> result = new ArrayList<T>();
			JSONArray jsonArr = new JSONArray(jsonString);
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject obj = jsonArr.getJSONObject(i);
				T t = pojo.newInstance();
				List<Field> fields = getAllField(pojo);
				for (Field f : fields) {
					setFieldValue(f, t, obj);
				}
				result.add(t);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
