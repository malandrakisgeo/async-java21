package com.gmalandrakis.async.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MyModel {
    @JsonProperty("myString")
    protected String myString;
    @JsonProperty("myList")
    private List<Object> myList;
}
