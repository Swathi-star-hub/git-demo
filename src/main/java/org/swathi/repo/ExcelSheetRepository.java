package org.swathi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.swathi.entity.ExcelSheet;


@Repository
public interface ExcelSheetRepository extends JpaRepository<ExcelSheet,Integer> {

    @Query(value = "select * from excel_sheet es2 where es2.customer_name like %:keyword% or es2.id like %:keyword%" ,nativeQuery = true)
    List<ExcelSheet> findByKeyword(@Param("keyword") String keyword);


}
