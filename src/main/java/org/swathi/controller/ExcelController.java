package org.swathi.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import jakarta.servlet.http.HttpSession;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.swathi.entity.ExcelSheet;
import org.swathi.repo.ExcelSheetRepository;
import org.swathi.service.ExcelSheetService;

import javax.validation.Valid;


@Controller
public class ExcelController {

    @Autowired
    private ExcelSheetRepository xlRepo;

    @Autowired
    private ExcelSheetService service;

    @GetMapping("/")
    public String index(Model m) {

        m.addAttribute("list", service.getAllExcels());
        return findPaginated(0,m);
    }
    @RequestMapping(path = {"/","/@search"})
    public String home(ExcelSheet xl, Model model, String  keyword, HttpSession session) {
        String string = keyword.replaceAll("[^a-zA-Z0-9]", "");
        if(string!=null) {
            List<ExcelSheet> list = service.getByKeyword(string);
            Integer count = list.size();

            model.addAttribute("list", list);
            model.addAttribute("string",string);
            model.addAttribute("count",count);
            if (count == 0 ) {
                String str = "No records found";
                model.addAttribute(str);

                session.setAttribute("message","No Records found");
            }
            else {
                List<ExcelSheet> list1 = service. getAllExcels();

                model.addAttribute("list1", list1);
            }
        }

        return "index";
    }

    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam MultipartFile file,HttpSession session) {
        try {
           /* File saveFile = new ClassPathResource("static/file").getFile();
            Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
            System.out.println(path);
            Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);*/
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheet("Customers");
            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();
            while(iterator.hasNext()){
                Row row = iterator.next();
                if(rowNumber == 0){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cid = 0;
                ExcelSheet xl = new ExcelSheet();
                while (cells.hasNext()){
                    Cell cell = cells.next();
                    // CellType cellType = cell.getCellType();
                    switch (cid){
                        case 0:
                            xl.setId((int)cell.getNumericCellValue());
                            break;
                        case 1:
                            xl.setCustomerName(cell.getStringCellValue());
                            break;
                        case 2:
                            xl.setQualification(cell.getStringCellValue());
                            break;
                        case 3:
                            xl.setGender(cell.getStringCellValue());
                            break;
                        case 4:
                            Date date = cell.getDateCellValue();
                            long millis = date.getTime();
                            //Date time = new Date(millis);
                            xl.setDateOfBirth(millis);
                            break;
                        case 5:
                            xl.setAddressLine1(cell.getStringCellValue());
                            break;
                        case 6:
                            xl.setAddressLine2(cell.getStringCellValue());
                            break;
                        case 7:
                            xl.setCity(cell.getStringCellValue());
                            break;
                        case 8:
                            xl.setState(cell.getStringCellValue());
                            break;
                        case 9:
                            xl.setPincode(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case 10:
                            xl.setMaritalStatus(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cid++;
                }
                ExcelSheet save = xlRepo.save(xl);

            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        if(file != null ) {
            session.setAttribute("msg","file uploaded successfully");
        }
        return "redirect:/";
    }
    @GetMapping("/addnew")
    public String addNewCustomer(Model model) {
        ExcelSheet xl = new  ExcelSheet();
        model.addAttribute("xl", xl);
        return "newcustomer";
    }

    @PostMapping("/save")
    public String saveCustomer(Model model, @Valid @ModelAttribute("xl") ExcelSheet xl, Errors errors) {

        if (errors.hasErrors()) {
            return "newcustomer";
        }
        model.addAttribute("xl", xl);
        service.save(xl);
        return "redirect:/";
    }


    @GetMapping("/update/{sNO}")
    public String update(@PathVariable(value ="sNO") Integer sNO,Model model) {
        ExcelSheet xl = service.getExcelById(sNO);
        model.addAttribute("xl",xl);
        return "editCustomer";
    }

    @GetMapping("/delete/{sNO}")
    public String deleteEmployee(@PathVariable(value ="sNO") Integer sNO) {
        this.service.deleteExcelById(sNO);
        return "redirect:/";
    }


    @GetMapping("/page/{pageno}")
    public String findPaginated(@PathVariable int pageno,Model m) {
        int size = 10;
        Page<ExcelSheet> list = service.getCustomerByPaginate(pageno,size);
        m.addAttribute("list",list);
        m.addAttribute("currentPage",pageno);
        m.addAttribute("totalPages",list.getTotalPages());
        m.addAttribute("totalItem",list.getTotalElements());
        m.addAttribute("itemsPerPage",list.getNumberOfElements());
        m.addAttribute("pageNo",list.getNumber());
        m.addAttribute("count",list.getSize());
        //m.addAttribute("count",list.getPageable().getPageSize());
        //double items = Math.ceil(list.getTotalElements()/list.getSize());
        //m.addAttribute(items);
        return "index";
    }
}
