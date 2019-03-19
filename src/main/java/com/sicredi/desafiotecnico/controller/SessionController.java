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

import com.sicredi.desafiotecnico.exceptions.NotFoundException;
import com.sicredi.desafiotecnico.model.Session;
import com.sicredi.desafiotecnico.service.SessionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/schedule/{scheduleId}/session")
@Api(value = "/schedule/{scheduleId}/session", produces = "application/json", description = "This API is responsible to retrieve/create of the Voting Session")
public class SessionController {
	private static final Logger logger = LogManager.getLogger(SessionController.class);
	private static final String CLASS_NAME = logger.getName();

	@Autowired
	private SessionService sessionService;

	@GetMapping
	@ApiOperation(value = "Get Session", notes = "Service is used to get an existent session by scheduleId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrieve a session", response = Session.class),
			@ApiResponse(code = 404, message = "No Session found by this ScheduleId: XYZ"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity getSession(
			@ApiParam(name = "scheduleId", value = "The id of the schedule", example = "1", required = true) @PathVariable Long scheduleId) {
		long startedTime = System.currentTimeMillis();
		try {
			return ResponseEntity.ok(sessionService.getSession(scheduleId));

		} catch (NotFoundException e) {
			logger.warn(String.format("[%s.%s] - [%s]", CLASS_NAME, "createSession", e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			logger.error(String.format("[%s.%s] - [%s]", CLASS_NAME, "createSession", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} finally {
			logger.info(String.format("[%s.%s] - [%s]", CLASS_NAME, "getSchedule", "Service took [" + (System.currentTimeMillis() - startedTime)/1000.00 + "] seconds"));
		}
	}

	@PostMapping("/{durationInSeconds}")
	@ApiOperation(value = "Create Session", notes = "Service is used to create a session for the schedule")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Session has been created", response = Session.class),
			@ApiResponse(code = 404, message = "No Schedule found for id: XYZ"),
			@ApiResponse(code = 400, message = "The session was already created for this schedule: XYZ"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity createSession(
			@ApiParam(name = "scheduleId", value = "The id of the schedule", example = "1", required = true) @PathVariable Long scheduleId,
			@ApiParam(name = "durationInSeconds", value = "The duration of the session in seconds", example = "60", required = true) @PathVariable Long durationInSeconds) {
		long startedTime = System.currentTimeMillis();
		try {
			Session scheduleSession = sessionService.createSession(scheduleId, durationInSeconds);
			return ResponseEntity.status(HttpStatus.CREATED).body(scheduleSession);
		} catch (NotFoundException e) {
			logger.warn(String.format("[%s.%s] - [%s]", CLASS_NAME, "createSession", e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.warn(String.format("[%s.%s] - [%s]", CLASS_NAME, "createSession", e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			logger.error(String.format("[%s.%s] - [%s]", CLASS_NAME, "createSession", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} finally {
			logger.info(String.format("[%s.%s] - [%s]", CLASS_NAME, "getSchedule", "Service took [" + (System.currentTimeMillis() - startedTime)/1000.00 + "] seconds"));
		}

	}
}
