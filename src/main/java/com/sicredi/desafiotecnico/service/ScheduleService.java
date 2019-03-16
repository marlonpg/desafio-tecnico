package com.sicredi.desafiotecnico.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicredi.desafiotecnico.model.Schedule;
import com.sicredi.desafiotecnico.repository.ScheduleRepository;

@Service
public class ScheduleService {
	private static final Logger logger = LogManager.getLogger(ScheduleService.class);
	private static final String CLASS_NAME = logger.getName();
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	public List<Schedule> getAllSchedules() {
		logger.info(String.format("[%s.%s]", CLASS_NAME, "getAllSchedules"));
		
		return (List<Schedule>) scheduleRepository.findAll();
	}
	
	public void createSchedule(Schedule schedule) {
		logger.info(String.format("[%s.%s] - [%s]", CLASS_NAME, "createSchedule", schedule));
		
		scheduleRepository.save(schedule);
	}

}
