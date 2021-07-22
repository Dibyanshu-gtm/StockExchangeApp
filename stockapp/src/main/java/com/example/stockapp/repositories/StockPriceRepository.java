package com.example.stockapp.repositories;

import com.example.stockapp.models.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPriceRepository extends JpaRepository<StockPrice,Long> {
}
