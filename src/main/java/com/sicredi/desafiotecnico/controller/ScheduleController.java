package com.sicredi.desafiotecnico.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sicredi.desafiotecnico.model.Schedule;
import com.sicredi.desafiotecnico.service.ScheduleService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
	private static final Logger logger = LogManager.getLogger(SessionController.class);
	private static final String CLASS_NAME = logger.getName();

	@Autowired
	private ScheduleService scheduleService;

	@GetMapping
	public ResponseEntity<List<Schedule>> getAllSchedules() {
		return ResponseEntity.ok(scheduleService.getAllSchedules());
	}

	@GetMapping("/{scheduleId}")
	public ResponseEntity<Schedule> getSchedule(@PathVariable Long scheduleId) {
		try {
			return ResponseEntity.ok(scheduleService.getSchedule(scheduleId));
		} catch (NotFoundException e) {
			logger.warn(String.format("[%s.%s] - [%s]", CLASS_NAME, "getSchedule", e.getMessage()));
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			logger.error(String.format("[%s.%s] - [%s]", CLASS_NAME, "getSchedule", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/result")
	public ResponseEntity<String> getScheduleResult(@RequestBody Schedule schedule) {
		// TODO: Compile the result with all votes.
		return ResponseEntity.ok(Strings.EMPTY);
	}

	@PostMapping
	public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
		Schedule createdSchedule = scheduleService.createSchedule(schedule);
		return ResponseEntity.ok(createdSchedule);
	}

}
