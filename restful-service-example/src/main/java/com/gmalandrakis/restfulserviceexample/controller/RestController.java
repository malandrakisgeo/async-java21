package com.gmalandrakis.restfulserviceexample.controller;

import com.gmalandrakis.restfulserviceexample.model.MyModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@Controller
public class RestController {


    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/model/{uuid}"},
            produces = {"application/json"}
    )
    ResponseEntity<MyModel> model(
            @PathVariable("uuid")
            UUID uuid) {

        MyModel model = new MyModel();
        if (uuid.toString().equals("03472000-1f2d-436d-ad90-143a602b975a")) {
            model.setMyString("model 1");
            var intlist = new ArrayList<Integer>();
            intlist.add(100);
            intlist.add(200);
            model.setMyList(Collections.singletonList(intlist));
        }
        if (uuid.toString().equals("03472000-1f2d-436d-ad90-143a602b975c")) {
            model.setMyString("model 2");
            var intlist = new ArrayList<String>();
            intlist.add("ALL YOU HAD TO DO WAS FOLLOW THE DAMN TRAIN, SJ");
            model.setMyList(Collections.singletonList(intlist));
        }

        return ResponseEntity.ok(model);
    }

}
