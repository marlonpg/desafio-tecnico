package com.sicredi.desafiotecnico.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sicredi.desafiotecnico.dto.VoteDto;
import com.sicredi.desafiotecnico.service.VoteService;

import com.sicredi.desafiotecnico.exceptions.NotFoundException;

@RestController
@SuppressWarnings("rawtypes")
@RequestMapping("/schedule/{scheduleId}/vote")
public class VoteController {
	private static final Logger logger = LogManager.getLogger(VoteController.class);
	private static final String CLASS_NAME = logger.getName();

	@Autowired
	private VoteService voteService;

	@PostMapping
	public ResponseEntity vote(@PathVariable Long scheduleId, @RequestBody VoteDto voteDto) {
		try {
			return ResponseEntity.ok(voteService.vote(scheduleId, voteDto));
		} catch (NotFoundException e) {
			logger.warn(String.format("[%s.%s] - [%s]", CLASS_NAME, "vote", e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.warn(String.format("[%s.%s] - [%s]", CLASS_NAME, "vote", e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			logger.error(String.format("[%s.%s] - [%s]", CLASS_NAME, "vote", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping
	public ResponseEntity getVotes(@PathVariable Long scheduleId) {
		try {
			return ResponseEntity.ok(voteService.getVotes(scheduleId));
		} catch (NotFoundException e) {
			logger.warn(String.format("[%s.%s] - [%s]", CLASS_NAME, "getVotes", e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			logger.error(String.format("[%s.%s] - [%s]", CLASS_NAME, "getVotes", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
