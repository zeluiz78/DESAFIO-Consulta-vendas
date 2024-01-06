package com.desafioconsultavendas.services;

import com.desafioconsultavendas.dto.ReportMinDTO;
import com.desafioconsultavendas.dto.SaleMinDTO;
import com.desafioconsultavendas.entities.Sale;
import com.desafioconsultavendas.projections.ReportMinProjection;
import com.desafioconsultavendas.projections.SummaryMinProjection;
import com.desafioconsultavendas.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SummaryMinProjection> summary(String minDate, String maxDate){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate maxLocalDate;
		LocalDate minLocalDate = null;

		if (Objects.equals(minDate, "") && Objects.equals(maxDate, "")){
			maxLocalDate = today;
			minLocalDate = maxLocalDate.minusYears(1L);
			minDate = minLocalDate.format(formatter);
			maxDate = maxLocalDate.format(formatter);
		} else if (Objects.equals(maxDate, "")) {
			maxLocalDate = today;
			maxDate = maxLocalDate.format(formatter);
		} else if (Objects.equals(minDate, "")){
			maxLocalDate = LocalDate.parse(maxDate, formatter);
			minLocalDate = maxLocalDate.minusYears(1L);
			minDate = minLocalDate.format(formatter);
		}
		return repository.searchSummary(minDate, maxDate);

	}

	public Page<ReportMinDTO> report(String minDate, String maxDate, String name, Pageable pageable){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate maxLocalDate = null;
		LocalDate minLocalDate = null;

		if (Objects.equals(minDate, "") && Objects.equals(maxDate, "")){
			maxLocalDate = today;
			minLocalDate = maxLocalDate.minusYears(1L);

		} else if (Objects.equals(maxDate, "")) {
			maxLocalDate = today;
		} else if (Objects.equals(minDate, "")){
			maxLocalDate = LocalDate.parse(maxDate, formatter);
			minLocalDate = maxLocalDate.minusYears(1L);

		} else {
			maxLocalDate = LocalDate.parse(maxDate, formatter);
			minLocalDate = LocalDate.parse(minDate, formatter);
		}
		return repository.searchReport(minLocalDate, maxLocalDate, name, pageable);

	}


}
