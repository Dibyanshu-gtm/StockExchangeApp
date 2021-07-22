package com.example.stockapp.repositories;

import com.example.stockapp.models.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorRepository extends JpaRepository<Sector,Long> {
    Sector findBySectorName(String sector);
}
