package com.desafioconsultavendas.controllers;

import com.desafioconsultavendas.dto.ReportMinDTO;
import com.desafioconsultavendas.projections.ReportMinProjection;
import com.desafioconsultavendas.projections.SummaryMinProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.desafioconsultavendas.dto.SaleMinDTO;
import com.desafioconsultavendas.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SummaryMinProjection>> getSummary(
			@RequestParam(name = "minDate", defaultValue = "") String minDate,
			@RequestParam(name = "maxDate", defaultValue = "") String maxDate
	) {
		List<SummaryMinProjection> dto = service.summary(minDate, maxDate);
		return ResponseEntity.ok(dto);
	}

	/*
	@GetMapping(value = "/report")
	public ResponseEntity<List<ReportMinProjection>> getReport(
			@RequestParam(name = "minDate", defaultValue = "") String minDate,
			@RequestParam(name = "maxDate", defaultValue = "") String maxDate,
			@RequestParam(name = "name", defaultValue = "") String name
	) {
		List<ReportMinProjection> dto = service.report(minDate, maxDate, name);
		return ResponseEntity.ok(dto);
	}
	*/

	@GetMapping(value = "/report")
	public ResponseEntity<Page<ReportMinDTO>> getReport(
			Pageable pageable,
			@RequestParam(name = "minDate", defaultValue = "") String minDate,
			@RequestParam(name = "maxDate", defaultValue = "") String maxDate,
			@RequestParam(name = "name", defaultValue = "") String name
	) {
		Page<ReportMinDTO> dto = service.report(minDate, maxDate, name, pageable);
		return ResponseEntity.ok(dto);
	}


}
