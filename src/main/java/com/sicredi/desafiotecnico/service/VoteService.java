package com.sicredi.desafiotecnico.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sicredi.desafiotecnico.dto.VoteDto;
import com.sicredi.desafiotecnico.model.Schedule;
import com.sicredi.desafiotecnico.model.Vote;
import com.sicredi.desafiotecnico.repository.VoteRepository;
import com.sicredi.desafiotecnico.util.Constants;
import com.sicredi.desafiotecnico.exceptions.NotFoundException;

@Service
public class VoteService {
	private static final Logger logger = LogManager.getLogger(VoteService.class);
	private static final String CLASS_NAME = logger.getName();

	@Autowired
	private VoteRepository voteRepository;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private SessionService sessionService;

	public Vote vote(Long scheduleId, VoteDto voteDto) throws NotFoundException {
		logger.trace(String.format(Constants.LOG_MESSAGE_2_PARAMS, CLASS_NAME, "vote", scheduleId, voteDto));

		validateUserCPF(voteDto.getUserCPF());

		if (sessionService.isSessionAvailable(scheduleId) && isUserAbleToVote(scheduleId, voteDto.getUserCPF())) {
			Schedule schedule = scheduleService.getSchedule(scheduleId);
			Vote vote = new Vote(voteDto.getUserCPF(), convert(voteDto.getUserVote()), schedule);
			return voteRepository.save(vote);
		} else {
			throw new NotFoundException("Session closed or the user have already voted.");
		}
	}

	public List<Vote> getVotes(Long scheduleId) throws NotFoundException {
		logger.trace(String.format(Constants.LOG_MESSAGE_1_PARAMS, CLASS_NAME, "getVotes", scheduleId));

		List<Vote> votes = voteRepository.findByScheduleId(scheduleId);
		if (CollectionUtils.isEmpty(votes)) {
			throw new NotFoundException("No Votes found by this ScheduleId: " + scheduleId);
		}
		return votes;
	}

	private boolean convert(String vote) {
		logger.trace(String.format(Constants.LOG_MESSAGE_1_PARAMS, CLASS_NAME, "convert", vote));

		boolean result;
		if (Constants.VOTE_SIM.equalsIgnoreCase(vote)) {
			result = true;
		} else if (Constants.VOTE_NAO.equalsIgnoreCase(vote)) {
			result = false;
		} else {
			throw new IllegalArgumentException("Invalid vote string [" + vote + "], the vote must be 'SIM' or 'NÃO.");
		}
		return result;
	}

	private boolean isUserAbleToVote(Long scheduleId, String userCPF) {
		logger.trace(
				String.format(Constants.LOG_MESSAGE_2_PARAMS, CLASS_NAME, "isUserAbleToVote", scheduleId, userCPF));

		Vote vote = voteRepository.findByScheduleIdAndUserCPF(scheduleId, userCPF);

		return vote == null;
	}

	private void validateUserCPF(String userCPF) {
		logger.trace(String.format(Constants.LOG_MESSAGE_1_PARAMS, CLASS_NAME, "validateUserCPF", userCPF));

		if (StringUtils.isBlank(userCPF) || userCPF.length() != Constants.CPF_LENGTH) {
			throw new IllegalArgumentException("Invalid CPF [" + userCPF + "], the CPF must have 11 character.");
		}
	}
}
