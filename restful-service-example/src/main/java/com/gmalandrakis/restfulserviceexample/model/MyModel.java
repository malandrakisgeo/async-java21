package com.gmalandrakis.restfulserviceexample.model;

import lombok.Data;

import java.util.List;

@Data
public class MyModel {

    private String myString;
    private List<Object> myList;
}
