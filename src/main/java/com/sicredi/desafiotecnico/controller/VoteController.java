package com.sicredi.desafiotecnico.controller;

import java.util.List;

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
import com.sicredi.desafiotecnico.model.Vote;
import com.sicredi.desafiotecnico.service.VoteService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/schedule/{scheduleId}/vote")
public class VoteController {
	private static final Logger logger = LogManager.getLogger(VoteController.class);
	private static final String CLASS_NAME = logger.getName();

	@Autowired
	private VoteService voteService;

	@PostMapping
	public ResponseEntity<String> vote(@PathVariable Long scheduleId, @RequestBody VoteDto voteDto) {
		try {
			voteService.vote(scheduleId, voteDto);
			return ResponseEntity.ok().build();
		} catch (NotFoundException e) {
			logger.warn(String.format("[%s.%s] - [%s]", CLASS_NAME, "vote", e.getMessage()));
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error(String.format("[%s.%s] - [%s]", CLASS_NAME, "vote", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping
	public ResponseEntity<List<Vote>> getVotes(@PathVariable Long scheduleId) {
		try {
			List<Vote> computedVotes = voteService.getVotes(scheduleId);
			return ResponseEntity.ok(computedVotes);
		} catch (NotFoundException e) {
			logger.warn(String.format("[%s.%s] - [%s]", CLASS_NAME, "getVotes", e.getMessage()));
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			logger.error(String.format("[%s.%s] - [%s]", CLASS_NAME, "getVotes", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
