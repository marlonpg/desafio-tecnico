package com.sicredi.desafiotecnico.service;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicredi.desafiotecnico.model.Schedule;
import com.sicredi.desafiotecnico.model.Session;
import com.sicredi.desafiotecnico.repository.SessionRepository;

import javassist.NotFoundException;

@Service
public class SessionService {
	private static final Logger logger = LogManager.getLogger(SessionService.class);
	private static final String CLASS_NAME = logger.getName();

	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	private ScheduleService scheduleService;

	public Session getSession(Long scheduleId) throws NotFoundException {
		logger.info(String.format("[%s.%s] - [%s]", CLASS_NAME, "getSession", scheduleId));

		Session session = sessionRepository.findByScheduleId(scheduleId);
		if (session == null) {
			throw new NotFoundException("No Session found by this ScheduleId: " + scheduleId);
		}
		return session;
	}

	public Session createSession(Long scheduleId, Long durationInSeconds) throws NotFoundException {
		logger.info(String.format("[%s.%s] - [%s] - [%s]", CLASS_NAME, "createSession", scheduleId, durationInSeconds));

		Session session = sessionRepository.findByScheduleId(scheduleId);
		if (session != null && session.getSessionStartTime() != null) {
			throw new IllegalArgumentException("The session was already created for this schedule: " + scheduleId);
		} else {
			Schedule schedule = scheduleService.getSchedule(scheduleId);
			session = new Session(LocalDateTime.now(), durationInSeconds, schedule);
			sessionRepository.save(session);
		}

		return session;
	}

	public boolean isSessionAvailable(Long scheduleId) {
		logger.info(String.format("[%s.%s] - [%s]", CLASS_NAME, "isSessionAvailable", scheduleId));

		boolean isSessionAvailable = false;
		try {
			Session session = getSession(scheduleId);
			LocalDateTime localDateTime = session.getSessionStartTime();
			LocalDateTime finishAt = localDateTime.plusSeconds(session.getSessionDuration());
			isSessionAvailable = LocalDateTime.now().isBefore(finishAt);
		} catch (NotFoundException e) {
			logger.warn(String.format("[%s.%s] - [%s]", CLASS_NAME, "isSessionAvailable", e.getMessage()));
		}
		return isSessionAvailable;
	}

}
