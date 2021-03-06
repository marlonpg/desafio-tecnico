package com.sicredi.desafiotecnico.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicredi.desafiotecnico.dto.ScheduleDto;
import com.sicredi.desafiotecnico.dto.ScheduleResult;
import com.sicredi.desafiotecnico.exceptions.NotFoundException;
import com.sicredi.desafiotecnico.model.Schedule;
import com.sicredi.desafiotecnico.model.Session;
import com.sicredi.desafiotecnico.model.Vote;
import com.sicredi.desafiotecnico.repository.ScheduleRepository;
import com.sicredi.desafiotecnico.util.Constants;

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

	public Schedule getSchedule(Long id) throws NotFoundException {
		logger.trace(String.format(Constants.LOG_MESSAGE_1_PARAMS, CLASS_NAME, "getSchedule", id));

		Optional<Schedule> schedule = scheduleRepository.findById(id);
		if (!schedule.isPresent()) {
			throw new NotFoundException("No Schedule found for id:" + id);
		}
		return schedule.get();
	}

	public Schedule createSchedule(ScheduleDto scheduleDto) {
		logger.trace(String.format(Constants.LOG_MESSAGE_1_PARAMS, CLASS_NAME, "createSchedule", scheduleDto));

		return scheduleRepository.save(new Schedule(scheduleDto.getTopic()));
	}

	public ScheduleResult getScheduleResult(Long scheduleId) throws NotFoundException {
		logger.trace(String.format(Constants.LOG_MESSAGE_1_PARAMS, CLASS_NAME, "getScheduleResult", scheduleId));

		Session session = sessionService.getSession(scheduleId);
		List<Vote> votes = voteService.getVotes(scheduleId);
		long countSim = votes.stream().filter(vote -> vote.getUserVote()).count();
		long countNao = votes.size() - countSim;

		return new ScheduleResult(session, countSim, countNao, votes);
	}
}
