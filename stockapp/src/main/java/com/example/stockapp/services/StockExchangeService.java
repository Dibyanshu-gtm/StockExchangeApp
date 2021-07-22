package com.example.stockapp.services;

import com.example.stockapp.models.Company;
import com.example.stockapp.models.StockExchange;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StockExchangeService {
    public String addExchange(StockExchange stockExchange);
    public List<StockExchange> getExchange();
    public List<Company>getCompanies(String exchangename);
    public String getipodetails(String name);
}
