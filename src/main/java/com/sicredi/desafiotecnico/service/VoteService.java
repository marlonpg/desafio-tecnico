package com.sicredi.desafiotecnico.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicredi.desafiotecnico.dto.VoteDto;
import com.sicredi.desafiotecnico.model.Schedule;
import com.sicredi.desafiotecnico.model.Vote;
import com.sicredi.desafiotecnico.repository.VoteRepository;

import javassist.NotFoundException;

@Service
public class VoteService {
	private static final Logger logger = LogManager.getLogger(VoteService.class);
	private static final String CLASS_NAME = logger.getName();
	private static final String VOTE_SIM = "SIM";
	private static final String VOTE_NAO = "NÃO";

	@Autowired
	private VoteRepository voteRepository;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private SessionService sessionService;

	public void vote(Long scheduleId, VoteDto voteDto) throws NotFoundException {
		logger.info(String.format("[%s.%s] - [%s] - [%s]", CLASS_NAME, "vote", scheduleId, voteDto));

		if (sessionService.isSessionAvailable(scheduleId) && isUserAbleToVote(scheduleId, voteDto.getUserCPF())) {

			Schedule schedule = scheduleService.getSchedule(scheduleId);
			Vote vote = new Vote(voteDto.getUserCPF(), convert(voteDto.getVote()), schedule);
			voteRepository.save(vote);
		} else {
			throw new NotFoundException("Session closed or the user have already voted.");
		}
	}

	public List<Vote> getVotes(Long scheduleId) throws NotFoundException {
		logger.info(String.format("[%s.%s] - [%s]", CLASS_NAME, "getVotes", scheduleId));

		List<Vote> votes = voteRepository.findByScheduleId(scheduleId);
		if (votes == null) {
			throw new NotFoundException("No Votes found by this ScheduleId: " + scheduleId);
		}
		return votes;
	}

	private Boolean convert(String vote) {
		Boolean result = null;
		if (VOTE_SIM.contains(vote.toUpperCase())) {
			result = true;
		} else if (VOTE_NAO.contains(vote.toUpperCase())) {
			result = false;
		}
		return result;
	}

	private boolean isUserAbleToVote(Long scheduleId, String userCPF) {
		logger.info(String.format("[%s.%s] - [%s] - [%s]", CLASS_NAME, "isUserAbleToVote", scheduleId, userCPF));

		Vote vote = voteRepository.findByScheduleIdAndUserCPF(scheduleId, userCPF);
		logger.info(String.format("[%s.%s] - [%s]", CLASS_NAME, "isUserAbleToVote", vote));

		return vote == null;
	}

}
