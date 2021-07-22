package com.example.stockapp.services;


import com.example.stockapp.models.Company;
import com.example.stockapp.models.CompanyStockexchangemap;
import com.example.stockapp.models.IPODetail;
import com.example.stockapp.models.StockExchange;
import com.example.stockapp.repositories.StockExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockExchangeServiceImpl implements StockExchangeService{
    @Autowired
    StockExchangeRepository stockExchangeRepository;
    @Override
    public String addExchange(StockExchange stockExchange) {
       stockExchangeRepository.save(stockExchange);
       return "Done "+ stockExchange.getName()+" is Added";
    }

    @Override
    public List<StockExchange> getExchange() {
        List<StockExchange>exchange= stockExchangeRepository.findAll();
        return exchange;
    }

    @Override
    public List<Company> getCompanies(String exchangename) {
        StockExchange stockExchange= stockExchangeRepository.findByName(exchangename);
        List<CompanyStockexchangemap> compstockmap= stockExchange.getCompstockmap();
        List<Company>comp = new ArrayList<>();
        for(CompanyStockexchangemap c: compstockmap)
        {
            Company company= new Company(c.getCompany().getCompanyName(),c.getCompany().getTurnover(),c.getCompany().getCeo(),
                    c.getCompany().getBoardOfDirectors(),c.getCompany().getCompanyBrief());
            company.setId(c.getCompany().getId());
            comp.add(company);
        }
        return  comp;
    }

    @Override
    public String getipodetails(String name) {
        StockExchange exchange= stockExchangeRepository.findByName(name);
        List<IPODetail>ipolist=exchange.getIpoDetail();
        String x= "";
        for(IPODetail i : ipolist)
        {
            x=x+i.getCompany().getCompanyName();
        }
        return x;
    }
}
