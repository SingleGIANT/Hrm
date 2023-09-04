package com.example.hrm_new.entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class converter implements AttributeConverter<List<Long>, String> {
 
   
   private static final String SPLIT_CHAR = ",";
//
//    @Override
//    public String convertToDatabaseColumn(List<Long> attribute) {
//        if (attribute == null || attribute.isEmpty()) {
//            return null;
//        }
//        return attribute.stream()
//                        .map(Object::toString)
//                        .collect(Collectors.joining(DELIMITER));
//    }
//
//    @Override
//    public List<Long> convertToEntityAttribute(String dbData) {
//        if (dbData == null || dbData.isEmpty()) {
//            return new ArrayList<>();
//        }
//        return Arrays.stream(dbData.split(DELIMITER))
//                     .map(Long::parseLong)
//                     .collect(Collectors.toList());
//    }
	
	   @Override
	    public String convertToDatabaseColumn(List<Long> integerList) {
	        return integerList != null ? integerList.stream()
	                .map(String::valueOf)
	                .collect(Collectors.joining(SPLIT_CHAR))
	                : "";
	    }

	    @Override
	    public List<Long> convertToEntityAttribute(String string) {
	        return (string != null && !string.isEmpty()) ? Arrays.stream(string.split(SPLIT_CHAR))
	                .map(Long::valueOf)
	                .collect(Collectors.toList())
	                : Collections.emptyList();
	    }}