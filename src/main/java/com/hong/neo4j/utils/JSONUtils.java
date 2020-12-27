package com.hong.neo4j.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : KongJHong
 * @Date : 2020-12-22 14:59
 * @Version : 1.0
 * Description     : objectMapper工具类
 */
@Component
@SuppressWarnings({"unchecked","rawtypes"})
public class JSONUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		// 全部字段序列化
		//对象的所有字段全部列入
		objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //取消默认转换timestamps形式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		//忽略空Bean转json的错误
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
	}

	public static <T> String obj2String(T obj) {
		if (obj == null)return null;
		try {
			return obj instanceof String?(String) obj: objectMapper.writeValueAsString(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> byte[] obj2Bytes(T obj) {
		if (obj == null)return null;
		try {
			return obj instanceof String?((String)obj).getBytes(StandardCharsets.UTF_8): objectMapper.writeValueAsBytes(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> T string2Obj(String str, Class<T> clazz) {
		if (StringUtils.isEmpty(str) || clazz == null){
			return null;
		}

		try {
			return clazz.equals(String.class)?(T)str: objectMapper.readValue(str, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> T string2Obj(String str, TypeReference typeReference) {
		if (StringUtils.isEmpty(str) || typeReference == null){
			return null;
		}

		try {
			return (T)(typeReference.getType().equals(String.class)?str:objectMapper.readValue(str,typeReference));
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("a","111");
		map.put("b","222");
		String mapStr = obj2String(map);
		System.out.println(mapStr);

		Map<String, String> map2 = string2Obj(mapStr, Map.class);
		assert map2 != null;
		for (String key: map2.keySet()) {
			System.out.println(map2.get(key));
		}
	}
}
