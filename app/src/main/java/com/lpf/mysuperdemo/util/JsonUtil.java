package com.lpf.mysuperdemo.util;

// Need pagkage json-2.2.4.jar

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {
	private static GsonBuilder builder = new GsonBuilder(); 
	private static Gson gson = builder.create(); 
	
	public static String bean2Json(Object bean){
		return gson.toJson(bean);
	}
	
	public static <T> T json2Bean(String jsonStr, Class<T> beanClass){
		try {
			return gson.fromJson(jsonStr, beanClass);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public static <T> String list2Json(List<T> list){
		Type type = new TypeToken<List<T>>(){}.getType(); 
		return gson.toJson(list, type);
	}
	
	public static List<String> json2List(String jsonString){
		return json2List(jsonString, new TypeToken<List<String>>(){});
	}
	
	public static <T> List<T> json2List(String jsonString, TypeToken<List<T>> typeToken){
		Type type = typeToken.getType(); 
		return gson.fromJson(jsonString, type); 
	}
	
	public static <K, V> String map2Json(Map<K, V> map){
		Type type = new TypeToken<Map<K, V>>(){}.getType(); 
		return gson.toJson(map, type);
	}
	
	public static <K, V> Map<K, V> json2Map(String jsonString, Class<K> keyClass, Class<V> valueClass) {
        return json2Map(jsonString, new TypeToken<Map<K, V>>(){});
    }
	
	
	public static <K, V> Map<K, V> json2Map(String jsonString, TypeToken<Map<K, V>> typeToken) {
		Type type = typeToken.getType(); 
        return gson.fromJson(jsonString, type);
    }

}
