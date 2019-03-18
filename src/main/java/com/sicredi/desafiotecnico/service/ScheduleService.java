package com.sicredi.desafiotecnico.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicredi.desafiotecnico.dto.ScheduleResult;
import com.sicredi.desafiotecnico.model.Schedule;
import com.sicredi.desafiotecnico.model.Session;
import com.sicredi.desafiotecnico.model.Vote;
import com.sicredi.desafiotecnico.repository.ScheduleRepository;

import com.sicredi.desafiotecnico.exceptions.NotFoundException;

@Service
public class ScheduleService {
	private static final Logger logger = LogManager.getLogger(ScheduleService.class);
	private static final String CLASS_NAME = logger.getName();

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private VoteService voteService;

	public List<Schedule> getAllSchedules() {
		logger.info(String.format("[%s.%s]", CLASS_NAME, "getAllSchedules"));

		return (List<Schedule>) scheduleRepository.findAll();
	}

	public Schedule getSchedule(Long id) throws NotFoundException {
		logger.info(String.format("[%s.%s] - [%s]", CLASS_NAME, "getSchedule", id));

		Optional<Schedule> schedule = scheduleRepository.findById(id);
		if (!schedule.isPresent()) {
			throw new NotFoundException("No Schedule found for id:" + id);
		}
		return schedule.get();
	}

	public Schedule createSchedule(Schedule schedule) {
		logger.info(String.format("[%s.%s] - [%s]", CLASS_NAME, "createSchedule", schedule));

		return scheduleRepository.save(schedule);
	}

	public ScheduleResult getScheduleResult(Long scheduleId) throws NotFoundException {
		logger.info(String.format("[%s.%s] - [%s]", CLASS_NAME, "getScheduleResult", scheduleId));

		Session session = sessionService.getSession(scheduleId);
		List<Vote> votes = voteService.getVotes(scheduleId);
		long countSim = votes.stream().filter(vote -> vote.getVote()).count();
		long countNao = votes.size() - countSim;

		return new ScheduleResult(session, countSim, countNao, votes);
	}
}
