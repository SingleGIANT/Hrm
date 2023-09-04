package com.example.hrm_new.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class LongListConverter implements AttributeConverter<List<Long>, String> {

	private static final String DELIMITER = ",";

	@Override
	public String convertToDatabaseColumn(List<Long> attribute) {
		if (attribute == null || attribute.isEmpty()) {
			return "";
		}
		return attribute.stream().map(Object::toString).collect(Collectors.joining(DELIMITER));
	}

	@Override
	public List<Long> convertToEntityAttribute(String dbData) {
		if (dbData == null || dbData.isEmpty()) {
			return new ArrayList<>();
		}
		return Arrays.stream(dbData.split(DELIMITER)).map(Long::parseLong).collect(Collectors.toList());
	}
}
