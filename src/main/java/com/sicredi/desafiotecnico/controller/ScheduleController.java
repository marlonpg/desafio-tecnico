package com.sicredi.desafiotecnico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sicredi.desafiotecnico.model.Schedule;
import com.sicredi.desafiotecnico.service.ScheduleService;

@RestController
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@GetMapping("/schedule")
	public ResponseEntity<List<Schedule>> getAllSchedules(){
		return ResponseEntity.ok(scheduleService.getAllSchedules());
	}
	
	@PostMapping("/schedule")
	public void createSchedule(@RequestBody Schedule schedule){
		scheduleService.createSchedule(schedule);
	}
}
