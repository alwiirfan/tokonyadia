package com.enima.tokonyadia.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping(value = "/hello")
    public String hello(){
        return "<h1>Hello World !</h1>";
    }

    @GetMapping(value = "/hobbies")
    public String[] getHobbies(){
        return new String[]{"Tidur","Makan"};
    }

    @GetMapping( "/request-param/{id}{key}")
    public String getRequestParam(@RequestParam String key , @PathVariable String id){
        return key + " " + id;
    }

    @GetMapping("/person/{id}")
    public String getPersonById(@PathVariable String id){
        return "Person" + id;
    }

    @GetMapping("/person")
    public Map<String, Object> getPerson() {
        Map<String, Object> person = new HashMap<>();

        person.put("name", "Edy");
        person.put("age", 17);
        person.put("is_married", false);

        String[] hobbies = new String[]{"Tidur", "Ibadah"};
        person.put("hobbies", hobbies);

        Map<String, String> address = new HashMap<>();
        address.put("rt", "08");
        address.put("rw", "04");
        address.put("street", "Jl. H. Dahlan");
        person.put("address", address);

        return person;
    }
}
