package com.kafka.learning.basickafkaprograms.avro.extractor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kafka.learning.basickafkaprograms.model.AvroAttribute;

public class AvroExtractor {

	private static final String avroSchema = "{\r\n" + 
			"	\"namespace\": \"com.cloudurable.phonebook\",\r\n" + 
			"	\"type\": \"record\",\r\n" + 
			"	\"name\": \"Employee\",\r\n" + 
			"	\"doc\": \"Represents an Employee at a company\",\r\n" + 
			"	\"fields\": [{\r\n" + 
			"			\"name\": \"firstName\",\r\n" + 
			"			\"type\": [\r\n" + 
			"				\"string\",\r\n" + 
			"				\"avro.java.string\", \"String\"\r\n" + 
			"			],\r\n" + 
			"			\"doc\": \"The persons given name\"\r\n" + 
			"		},\r\n" + 
			"		{\r\n" + 
			"			\"name\": \"nickName\",\r\n" + 
			"			\"type\": [\"null\", \"string\"],\r\n" + 
			"			\"default\": null\r\n" + 
			"		},\r\n" + 
			"		{\r\n" + 
			"			\"name\": \"lastName\",\r\n" + 
			"			\"type\": [\r\n" + 
			"				\"string\",\r\n" + 
			"				\"avro.java.string\", \"String\"\r\n" + 
			"			]\r\n" + 
			"		},\r\n" + 
			"		{\r\n" + 
			"			\"name\": \"age\",\r\n" + 
			"			\"type\": [\r\n" + 
			"				\"int\",\r\n" + 
			"				\"avro.java.string\", \"String\"\r\n" + 
			"			],\r\n" + 
			"			\"default\": -1\r\n" + 
			"		},\r\n" + 
			"		{\r\n" + 
			"			\"name\": \"emails\",\r\n" + 
			"			\"default\": [],\r\n" + 
			"			\"type\": {\r\n" + 
			"				\"type\": \"array\",\r\n" + 
			"				\"items\": \"string\"\r\n" + 
			"			}\r\n" + 
			"		},\r\n" + 
			"		{\r\n" + 
			"			\"name\": \"phoneNumber\",\r\n" + 
			"			\"type\": [\"null\",\r\n" + 
			"				{\r\n" + 
			"					\"type\": \"record\",\r\n" + 
			"					\"name\": \"PhoneNumber\",\r\n" + 
			"					\"fields\": [{\r\n" + 
			"							\"name\": \"areaCode\",\r\n" + 
			"							\"type\": \"string\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"name\": \"countryCode\",\r\n" + 
			"							\"type\": \"string\",\r\n" + 
			"							\"default\": \"\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"name\": \"prefix\",\r\n" + 
			"							\"type\": \"string\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"name\": \"number\",\r\n" + 
			"							\"type\": \"string\"\r\n" + 
			"						}\r\n" + 
			"					]\r\n" + 
			"				}\r\n" + 
			"			]\r\n" + 
			"		}\r\n" + 
			"	]\r\n" + 
			"}";

	private static final String NAME = "name";
	private static final String DOC = "doc";
	private static final String TYPE = "type";
	private static final String FIELDS = "fields";
	private static final String RECORD = "record";

	public static void main(String[] args) {
		System.out.println(extractSchemaAttributes("", avroSchema));
	}

	private static Map<String, AvroAttribute> extractSchemaAttributes(String rootName, String avroSchema){

		Map<String, AvroAttribute> attributes = new TreeMap<>();

		JSONObject jsonObject = new JSONObject(avroSchema);

		String attributeName = rootName+ (jsonObject.has(NAME)? jsonObject.getString(NAME): "");
		String attributeDoc = jsonObject.has(DOC)? jsonObject.getString(DOC): "";
		List<String> attributeType = new LinkedList<>();

		if(jsonObject.has(TYPE)) {

			if(jsonObject.get(TYPE) instanceof JSONArray) {

				JSONArray jsonArray = jsonObject.getJSONArray(TYPE);

				for(int i = 0; i < jsonArray.length(); i++) {
					if(jsonArray.get(i) instanceof String) {
						attributeType.add(jsonArray.getString(i));
						attributes.put(attributeName, new AvroAttribute(attributeName, attributeType, attributeDoc));
					}else if(jsonArray.get(i) instanceof JSONObject) {
						attributes.putAll(extractTypesFromRecord(attributeName, attributeType, attributeDoc, jsonArray.getJSONObject(i)));
					}
				}

			}else if(jsonObject.get(TYPE) instanceof String) {

				attributeType.add(jsonObject.getString(TYPE));
				attributes.put(attributeName, new AvroAttribute(attributeName, attributeType, attributeDoc));

			}else if(jsonObject.get(TYPE) instanceof JSONObject) {
				attributes.putAll(extractTypesFromRecord(attributeName, attributeType, attributeDoc, jsonObject.getJSONObject(TYPE)));
			}
		}
		if(jsonObject.has(FIELDS)) {
			attributes.putAll(extractFieldsFromRecord( attributeName, jsonObject));
		}

		return attributes;

	}
	
	private static Map<String, AvroAttribute> extractTypesFromRecord(String attributeName, List<String> attributeType, String attributeDoc, JSONObject typeJSONObject){
		
		Map<String, AvroAttribute> attributes = new TreeMap<>();
		
		String type = typeJSONObject.has(TYPE)? typeJSONObject.getString(TYPE): RECORD;
		if(!type.matches(RECORD)) {
			attributeType.add(type);
			attributes.put(attributeName, new AvroAttribute(attributeName, attributeType, attributeDoc));
		}else {
			attributeType.add(type);
			attributes.put(attributeName, new AvroAttribute(attributeName, attributeType, attributeDoc));
			
			if(typeJSONObject.has(FIELDS)) {
				attributes.putAll(extractFieldsFromRecord( attributeName, typeJSONObject));
			}
		}
		
		return attributes;
	}
	
	private static Map<String, AvroAttribute> extractFieldsFromRecord(String attributeName, JSONObject jsonObject){
		Map<String, AvroAttribute> attributes = new TreeMap<>();

		if(jsonObject.get(FIELDS) instanceof JSONArray) {
			JSONArray jsonArray = jsonObject.getJSONArray(FIELDS);
			for(int i = 0; i < jsonArray.length(); i++) {
				attributes.putAll(extractSchemaAttributes(attributeName+":", jsonArray.getJSONObject(i).toString()));
			}
		}else {
			attributes.putAll(extractSchemaAttributes(attributeName+":", jsonObject.getJSONObject(FIELDS).toString()));
		}
		return attributes;
	}

}
