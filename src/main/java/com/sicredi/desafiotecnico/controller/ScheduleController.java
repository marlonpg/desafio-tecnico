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

import com.sicredi.desafiotecnico.dto.ScheduleResult;
import com.sicredi.desafiotecnico.exceptions.NotFoundException;
import com.sicredi.desafiotecnico.model.Schedule;
import com.sicredi.desafiotecnico.service.ScheduleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@SuppressWarnings("rawtypes")
@Api(value = "/schedule", produces = "application/json", description = "This API is responsible to retrieve/create/listResults of the Schedules")
@RequestMapping("/schedule")
public class ScheduleController {
	private static final Logger logger = LogManager.getLogger(ScheduleController.class);
	private static final String CLASS_NAME = logger.getName();

	@Autowired
	private ScheduleService scheduleService;

	@GetMapping("/{scheduleId}")
	@ApiOperation(value = "Get Schedule", notes = "Service is used to get an existent schedule by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrieve a schedule", response = Schedule.class),
			@ApiResponse(code = 404, message = "No Schedule found for id: XYZ"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity getSchedule(
			@ApiParam(name = "scheduleId", value = "The id of the schedule", example = "1", required = true) @PathVariable Long scheduleId) {
		long startedTime = System.currentTimeMillis();
		try {
			return ResponseEntity.ok(scheduleService.getSchedule(scheduleId));
		} catch (NotFoundException e) {
			logger.warn(String.format("[%s.%s] - [%s]", CLASS_NAME, "getSchedule", e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			logger.error(String.format("[%s.%s] - [%s]", CLASS_NAME, "getSchedule", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} finally {
			logger.info(String.format("[%s.%s] - [%s]", CLASS_NAME, "getSchedule", "Service took [" + (System.currentTimeMillis() - startedTime)/1000.00 + "] seconds"));
		}
	}

	@GetMapping("/{scheduleId}/result")
	@ApiOperation(value = "Get Schedule Results", notes = "Service is used to get the compiled votes result")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retrieve a schedule results", response = ScheduleResult.class),
			@ApiResponse(code = 404, message = "No Schedule found for id: XYZ"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity getScheduleResult(
			@ApiParam(name = "scheduleId", value = "The id of the schedule", example = "1", required = true) @PathVariable Long scheduleId) {
		long startedTime = System.currentTimeMillis();
		try {
			return ResponseEntity.ok(scheduleService.getScheduleResult(scheduleId));
		} catch (NotFoundException e) {
			logger.warn(String.format("[%s.%s] - [%s]", CLASS_NAME, "getSchedule", e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			logger.error(String.format("[%s.%s] - [%s]", CLASS_NAME, "getSchedule", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} finally {
			logger.info(String.format("[%s.%s] - [%s]", CLASS_NAME, "getSchedule", "Service took [" + (System.currentTimeMillis() - startedTime)/1000.00 + "] seconds"));
		}
	}

	@PostMapping
	@ApiOperation(value = "Create Schedule", notes = "Service is used to create a schedule")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Schedule has been created", response = Schedule.class),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Schedule> createSchedule(
			@ApiParam(name = "schedule", value = "The schedule object", required = true) @RequestBody Schedule schedule) {
		long startedTime = System.currentTimeMillis();
		try {
			Schedule createdSchedule = scheduleService.createSchedule(schedule);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
		} catch (Exception e) {
			logger.error(String.format("[%s.%s] - [%s]", CLASS_NAME, "getSchedule", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} finally {
			logger.info(String.format("[%s.%s] - [%s]", CLASS_NAME, "getSchedule", "Service took [" + (System.currentTimeMillis() - startedTime)/1000.00 + "] seconds"));
		}
	}
}
