package org.swathi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.swathi.entity.ExcelSheet;
import org.swathi.repo.ExcelSheetRepository;


@Service
public class ExcelSheetService {

    @Autowired
    private ExcelSheetRepository repo;

    public Page<ExcelSheet> getCustomerByPaginate(int size, int pageno){
        PageRequest of = PageRequest.of( size,pageno);
        return repo.findAll(of);
    }

    public List<ExcelSheet> getAllExcels(){
        List<ExcelSheet> list =  (List<ExcelSheet>)repo.findAll();
        return list;
    }

    public List<ExcelSheet> getByKeyword(String keyword){



        return  repo.findByKeyword(keyword);

    }


    public ExcelSheet save(ExcelSheet xl)
    {
        return repo.save(xl);
    }

    public ExcelSheet getExcelById(Integer sNO) {
        Optional<ExcelSheet> optional = repo.findById(sNO);
        ExcelSheet xl =null;
        if(optional.isPresent()) {
            xl = optional.get();
        }else {
            throw new RuntimeException("customer not found for id ::"+ sNO);
        }
        return xl;
    }

    public void deleteExcelById(Integer sNO) {
        repo.deleteById(sNO);

    }
}
