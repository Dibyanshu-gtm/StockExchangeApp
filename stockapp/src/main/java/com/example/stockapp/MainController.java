package com.example.stockapp;

import com.example.stockapp.models.Company;
import com.example.stockapp.models.CompanyStockexchangemap;
import com.example.stockapp.models.StockExchange;
import com.example.stockapp.repositories.CompanyRepository;
import com.example.stockapp.repositories.CompanyStockexchangemapRepository;
import com.example.stockapp.repositories.StockExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
public class MainController {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    StockExchangeRepository stockExchangeRepository;
    @Autowired
    CompanyStockexchangemapRepository companyStockexchangemapRepository;
    @Autowired
    EntityManager em;

    @RequestMapping(value = "/company1", method = RequestMethod.POST)
    public ResponseEntity<Object>createCompany(@RequestBody Company company){
        companyRepository.save(company);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(company.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @RequestMapping(value = "/mapcompanycode", method = RequestMethod.POST)
    public String mapcode(@RequestBody Map<String, String> text){
        System.out.println(text.get("companyName"));
        Query query = em.createNamedQuery("Company.findByName");
        query.setParameter("companyName",text.get("companyName"));
        Company c= (Company) query.getSingleResult();

        StockExchange e = stockExchangeRepository.findByName(text.get("exchangename"));
        CompanyStockexchangemap cse = new CompanyStockexchangemap();
        cse.setCompany(c);
        cse.setStockExchange(e);
        companyStockexchangemapRepository.save(cse);
        return "Test";

    }

    public String listit(){
        String x= "";
        List<CompanyStockexchangemap> csem = companyStockexchangemapRepository.findAll();
        for(CompanyStockexchangemap c:csem){
            Optional<StockExchange> s =stockExchangeRepository.findById(c.getStockExchange().getId());
            Optional<Company> cc = companyRepository.findById(c.getCompany().getId());
            x= x+" "+cc.get().getCompanyName()+" "+s.get().getName();
        }
        return x;
    }

}
