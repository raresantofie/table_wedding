package com.example.mese_nunta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class TableController {

    @Autowired
    TableRepository tableRepository;

    @GetMapping("/count")
    public Metrics getCount() {
        List<Table> tableList = tableRepository.findAll();
        double checkedListCount = tableList.stream().filter(Table::isChecked).count();
        Metrics metrics = new Metrics();
        metrics.setTotalInvites(tableList.size());
        metrics.setTotalArrived(Double.valueOf(checkedListCount).intValue());
        metrics.setToArrive(tableList.size() - Double.valueOf(checkedListCount).intValue());
        return metrics;
    }

    @GetMapping("/all")
    public List<AggregatedTableDto> findAll() {

        return getAggregatedTables(x -> true).stream().sorted(Comparator.comparing(AggregatedTableDto::getId)).collect(Collectors.toList());
    }

    @GetMapping("/not-arrived")
    public List<AggregatedTableDto> findAllToArrive() {
        return getAggregatedTables(x -> !x.isChecked()).stream().sorted(Comparator.comparing(AggregatedTableDto::getId)).collect(Collectors.toList());
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
            if (tableUpdateDto.isChecked()) {
                t.setLocalDateTime(LocalDateTime.now());
            } else {
                t.setLocalDateTime(null);
            }
            t.setChecked(tableUpdateDto.isChecked());
            tableRepository.save(t);
        }
        return tableRepository.findById(tableUpdateDto.getId()).get();
    }

    public List<AggregatedTableDto> getAggregatedTables(Predicate<Table> predicate) {
        List<Table> tableList =
                tableRepository
                        .findAll()
                        .stream()
                        .filter(predicate)
                        .collect(Collectors.toList());

        Map<Long, List<Table>> tableMap = new HashMap<>();
        for (Table t: tableList) {
            if (tableMap.containsKey(t.getTableNumber())) {
                List<Table> tableList1 = tableMap.get(t.getTableNumber());
                tableList1.add(t);
                tableMap.put(t.getTableNumber(), tableList1);
            } else {
                List<Table> tableList1 = new ArrayList<>();
                tableList1.add(t);
                tableMap.put(t.getTableNumber(), tableList1);
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
