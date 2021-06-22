package com.example.mese_nunta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TableController {

    @Autowired
    TableRepository tableRepository;

    @GetMapping("/all")
    public List<AggregatedTableDto> findAll() {
        return getAggregatedTables().stream().sorted(Comparator.comparing(AggregatedTableDto::getId)).collect(Collectors.toList());
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
    public Table update(@RequestBody TableUpdateDto tableUpdateDto) {
        Optional<Table> table = tableRepository.findById(tableUpdateDto.getId());
        if (table.isPresent()) {
            Table t = table.get();
            t.setChecked(tableUpdateDto.isChecked());
            tableRepository.save(t);
        }
        return tableRepository.findById(tableUpdateDto.getId()).get();
    }

    public List<AggregatedTableDto> getAggregatedTables() {
        List<Table> tableList = tableRepository.findAll();
        Map<Long, List<Table>> tableMap = new HashMap<>();
        for (Table t: tableList) {
            if (tableMap.containsKey(t.getId())) {
                List<Table> tableList1 = tableMap.get(t.getId());
                tableList1.add(t);
                tableMap.put(t.getId(), tableList1);
            } else {
                List<Table> tableList1 = new ArrayList<>();
                tableList1.add(t);
                tableMap.put(t.getId(), tableList1);
            }
        }

        List<AggregatedTableDto> aggregatedTableDtos = new ArrayList<>();
        tableMap.forEach((k,v) -> {
            AggregatedTableDto aggregatedTableDto = new AggregatedTableDto();
            aggregatedTableDto.setId(k);
            aggregatedTableDto.setTableList(v);
            aggregatedTableDtos.add(aggregatedTableDto);
        });
        return aggregatedTableDtos;
    }

}
