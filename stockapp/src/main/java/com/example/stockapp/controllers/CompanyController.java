package com.example.stockapp.controllers;

import com.example.stockapp.models.Company;
import com.example.stockapp.models.IPODetail;
import com.example.stockapp.models.PriceResponse;
import com.example.stockapp.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
public class CompanyController {

    @Autowired
    CompanyService companyService;
    @RequestMapping(value = "/company",method = RequestMethod.POST)
    public String addCompany(@RequestBody Map<String,String> text) throws ParseException {

        return companyService.addCompany(text.get("companyName").trim(),Double.parseDouble(text.get("turnover")),
                text.get("ceo").trim(),text.get("boardOfDirectors").trim(),text.get("companyBrief").trim(),text.get("openDateTime"),
                Float.parseFloat(text.get("pricePerShare")),Long.parseLong(text.get("totalNumberOfShares")),text.get("CompanyCode").trim(),
                text.get("sector").trim(),text.get("exchangeName").trim()
                );

    }
    @RequestMapping(value = "/company",method = RequestMethod.GET)
    public ResponseEntity<List<Company>> getCompanies(){
        List<Company>gr =companyService.listAll();
        if(gr.isEmpty())
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Company>>(gr,HttpStatus.OK);
    }
    @RequestMapping(value = "/company/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getCompaniesById(@PathVariable Long id){
        Company c =companyService.findById(id);

        if(Objects.isNull(c))
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return  ResponseEntity.ok().body(c);
    }
    @PutMapping( "/company/{id}")
    public ResponseEntity<Company>updateCompany(@RequestBody Company company){

        Company c= companyService.editCompany(company.getId(),company.getCompanyName(),company.getTurnover(),
                company.getCeo(),company.getBoardOfDirectors(),company.getCompanyBrief());
        if(Objects.isNull(c))
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok().body(c);
    }
    @RequestMapping(value = "/companyipo/{name}",method = RequestMethod.GET)
    public ResponseEntity<List<IPODetail>>getIPOCompany(@PathVariable String name){
        List<IPODetail>info= companyService.getIPODetails(name);
        if(info.isEmpty())
        {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<IPODetail>>(info,HttpStatus.OK);
    }
    @RequestMapping(value = "/getipo/{name}",method =RequestMethod.GET)
    public String getDetails(@PathVariable String name)
    {
        return companyService.getipo(name);
    }

    @CrossOrigin(origins ={"http://127.0.0.1:3000","http://localhost:3000/"})
    @RequestMapping(value="/getpricedate/{name}",method = RequestMethod.GET)
    public ResponseEntity<List<PriceResponse>>getStockPrice(@PathVariable String name,@RequestParam(name = "from")String from, @RequestParam(name = "todate")String todate,@RequestParam(name="exchangename")String exchangename) throws ParseException {
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        List<PriceResponse>resp= companyService.getCompanyStockPriceDate(name,dateFormat.parse(from),dateFormat.parse(todate),exchangename);
        if(resp.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<PriceResponse>>(resp,HttpStatus.OK);
    }
    @CrossOrigin(origins ={"http://127.0.0.1:3000","http://localhost:3000/"})
    @RequestMapping(value="/getpriceyear/{name}",method = RequestMethod.GET)
    public ResponseEntity<List<PriceResponse>>getStockPriceyear(@PathVariable String name,@RequestParam(name = "from")String from, @RequestParam(name = "todate")String todate,@RequestParam(name="exchangename")String exchangename) throws ParseException {
        DateFormat dateFormat= new SimpleDateFormat("yyyy");
        List<PriceResponse>resp= companyService.getCompanyStockPriceYear(name,dateFormat.parse(from),dateFormat.parse(todate),exchangename);
        if(resp.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<PriceResponse>>(resp,HttpStatus.OK);
    }
    @CrossOrigin(origins ={"http://127.0.0.1:3000","http://localhost:3000/"})
    @RequestMapping(value="/getpricetime/{name}",method = RequestMethod.GET)
    public ResponseEntity<List<PriceResponse>>getStockPriceyear(@PathVariable String name,@RequestParam(name = "from")String from,@RequestParam(name="exchangename")String exchangename) throws ParseException {
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        List<PriceResponse>resp= companyService.getCompanyStockPriceTime(name,dateFormat.parse(from),exchangename);
        if(resp.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<PriceResponse>>(resp,HttpStatus.OK);
    }


}
