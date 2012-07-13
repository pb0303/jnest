package com.lo9ic.research.nest;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TemperatureModeEnumSerializer implements JsonSerializer<TemperatureModeEnum> {
	public JsonElement serialize(TemperatureModeEnum src, Type typeOfSrc, JsonSerializationContext context) {
	    return new JsonPrimitive(src.toString());
	  }
}