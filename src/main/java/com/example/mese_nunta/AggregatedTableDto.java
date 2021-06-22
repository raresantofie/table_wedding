package com.example.mese_nunta;

import java.util.List;

public class AggregatedTableDto {
    private Long id;
    private List<Table> tableList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public void setTableList(List<Table> tableList) {
        this.tableList = tableList;
    }
}
