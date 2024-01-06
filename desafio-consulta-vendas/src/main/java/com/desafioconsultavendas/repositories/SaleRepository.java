package com.desafioconsultavendas.repositories;

import com.desafioconsultavendas.dto.ReportMinDTO;
import com.desafioconsultavendas.entities.Sale;
import com.desafioconsultavendas.projections.SummaryMinProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value = "SELECT " +
            "se.name AS sellerName, " +
            "SUM(s.amount) AS total " +
            "FROM " +
            "tb_sales s " +
            "JOIN " +
            "tb_seller se ON s.seller_id = se.id " +
            "WHERE " +
            "s.date >= :minDate AND s.date <= :maxDate " +
            "GROUP BY " +
            "s.seller_id, se.name " +
            "ORDER BY se.name ASC;")
    List<SummaryMinProjection> searchSummary(String minDate, String maxDate);

    @Query("SELECT new com.desafioconsultavendas.dto.ReportMinDTO(s.id, s.date, s.amount, se.name AS sellerName)  " +
            "FROM Sale s " +
            "JOIN s.seller se " +
            "WHERE s.date >= :minDate AND s.date <= :maxDate " +
            "AND (UPPER(se.name) LIKE UPPER(CONCAT('%', :name, '%')) OR :name IS NULL) " +
            "ORDER BY s.amount")
    Page<ReportMinDTO> searchReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);


}
