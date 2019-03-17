package com.sicredi.desafiotecnico.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sicredi.desafiotecnico.model.Session;
import com.sicredi.desafiotecnico.service.SessionService;

import javassist.NotFoundException;

@RestController
@SuppressWarnings("rawtypes")
@RequestMapping("/schedule/{scheduleId}/session")
public class SessionController {
	private static final Logger logger = LogManager.getLogger(SessionController.class);
	private static final String CLASS_NAME = logger.getName();

	@Autowired
	private SessionService sessionService;

	@GetMapping
	public ResponseEntity getSession(@PathVariable Long scheduleId) {
		try {
			return ResponseEntity.ok(sessionService.getSession(scheduleId));

		} catch (NotFoundException e) {
			logger.warn(String.format("[%s.%s] - [%s]", CLASS_NAME, "createSession", e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			logger.error(String.format("[%s.%s] - [%s]", CLASS_NAME, "createSession", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/{durationInSeconds}")
	public ResponseEntity createSession(@PathVariable Long scheduleId, @PathVariable Long durationInSeconds) {
		try {
			Session scheduleSession = sessionService.createSession(scheduleId, durationInSeconds);
			return ResponseEntity.ok(scheduleSession);
		} catch (NotFoundException e) {
			logger.warn(String.format("[%s.%s] - [%s]", CLASS_NAME, "createSession", e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.warn(String.format("[%s.%s] - [%s]", CLASS_NAME, "createSession", e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			logger.error(String.format("[%s.%s] - [%s]", CLASS_NAME, "createSession", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
