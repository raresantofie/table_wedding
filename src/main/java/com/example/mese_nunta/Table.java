package com.example.mese_nunta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "table_distribution")
public class Table {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    private Long tableNumber;

    private boolean checked;

    @Column
    private LocalDateTime localDateTime;

    public Table() {
    }

    public Table(Long id, String name, Long tableNumber, boolean checked) {
        this.id = id;
        this.name = name;
        this.tableNumber = tableNumber;
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Long tableNumber) {
        this.tableNumber = tableNumber;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
