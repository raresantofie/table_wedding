package com.example.mese_nunta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TableController {

    @Autowired
    TableRepository tableRepository;

    @GetMapping("/all")
    public List<Table> findAll() {
        return tableRepository.findAll();
    }


    @GetMapping("/search")
    public List<Table> findAllByName(@RequestParam("name") String name){
        return tableRepository.findAllByNameContainsIgnoreCase(name);
    }

    @PostMapping()
    public Table create(@RequestBody Table t) {
        return tableRepository.save(t);
    }

}
