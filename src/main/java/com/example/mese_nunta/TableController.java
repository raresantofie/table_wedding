package com.example.mese_nunta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

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

    @PutMapping()
    public Table update(@RequestBody Long id) {
        Optional<Table> table = tableRepository.findById(id);
        if (table.isPresent()) {
            Table t = table.get();
            t.setChecked(true);
            tableRepository.save(t);
        }
        return tableRepository.findById(id).get();
    }

}
