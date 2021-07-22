package com.example.stockapp.repositories;

import com.example.stockapp.models.StockExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockExchangeRepository extends JpaRepository<StockExchange,Long> {
    StockExchange findByName(String s);
}
