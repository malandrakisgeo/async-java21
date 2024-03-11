package com.gmalandrakis.async.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ModelList {

    private List<MyModel> modelList = new ArrayList<MyModel>();

}
