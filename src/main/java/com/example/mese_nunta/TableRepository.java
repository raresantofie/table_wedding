package com.example.mese_nunta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {

    List<Table> findAllByNameContains(String contains);
    Table create(Table t);
    List<Table> findAllByTableNumber(Long tableNumber);



}
