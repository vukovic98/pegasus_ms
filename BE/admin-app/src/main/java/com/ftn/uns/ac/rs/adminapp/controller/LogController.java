package com.ftn.uns.ac.rs.adminapp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.uns.ac.rs.adminapp.beans.Log;
import com.ftn.uns.ac.rs.adminapp.dto.LogFilterDTO;
import com.ftn.uns.ac.rs.adminapp.util.PageImplMapper;
import com.ftn.uns.ac.rs.adminapp.util.PageImplementation;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping(path = "/logs")
public class LogController {

	@Autowired
	private MongoTemplate mongoRepository;

	@GetMapping(path = "by-page/{pageNum}")
	public ResponseEntity<PageImplementation<Log>> findAll(@PathVariable("pageNum") int pageNum) {
		Pageable pageable = PageRequest.of(pageNum, 13);

		Query query = new Query();
		long count = this.mongoRepository.count(query, Log.class);

		query.with(pageable);
		ArrayList<Log> logs = (ArrayList<Log>) mongoRepository.find(query, Log.class);

		PageImplMapper<Log> mapper = new PageImplMapper<>();

		PageImplementation<Log> logPage = mapper
				.toPageImpl(PageableExecutionUtils.getPage(logs, pageable, () -> count));

		return new ResponseEntity<>(logPage, HttpStatus.OK);
	}

	@PostMapping(path = "filter-by-page/{pageNum}")
	public ResponseEntity<PageImplementation<Log>> filterByPage(@PathVariable("pageNum") int pageNum,
			@RequestBody LogFilterDTO dto) {
		Pageable pageable = PageRequest.of(pageNum, 13);

		Query query = new Query();

		if (dto.getStatus() != null)
			query.addCriteria(Criteria.where("status").is(dto.getStatus()));

		if (dto.getRegex() != null) {
			query.addCriteria(new Criteria().orOperator(Criteria.where("date").regex(dto.getRegex()),
					Criteria.where("status").regex(dto.getRegex()), Criteria.where("user").regex(dto.getRegex()),
					Criteria.where("message").regex(dto.getRegex()), Criteria.where("id").regex(dto.getRegex())));
		}

		long count = this.mongoRepository.count(query, Log.class);

		query.with(pageable);
		ArrayList<Log> logs = (ArrayList<Log>) mongoRepository.find(query, Log.class);

		PageImplMapper<Log> mapper = new PageImplMapper<>();

		PageImplementation<Log> logPage = mapper
				.toPageImpl(PageableExecutionUtils.getPage(logs, pageable, () -> count));

		return new ResponseEntity<>(logPage, HttpStatus.OK);
	}
}
