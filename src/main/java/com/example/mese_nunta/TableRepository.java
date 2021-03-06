package com.example.mese_nunta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {

    List<Table> findAllByNameContainsIgnoreCase(String contains);
    List<Table> findAllByTableNumber(Long tableNumber);



}
