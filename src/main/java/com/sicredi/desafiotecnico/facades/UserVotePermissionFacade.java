package com.sicredi.desafiotecnico.facades;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.sicredi.desafiotecnico.dto.UserVotePermissionResponse;
import com.sicredi.desafiotecnico.util.Constants;

@Component
public class UserVotePermissionFacade {
	private static final Logger logger = LogManager.getLogger(UserVotePermissionFacade.class);
	private static final String CLASS_NAME = logger.getName();

	private static final String URI = "https://user-info.herokuapp.com/users/";
	private static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
	
	public boolean isUserAbleToVote(String userCPF) {
		logger.trace(String.format(Constants.LOG_MESSAGE_1_PARAMS, CLASS_NAME, "getVotePermission", userCPF));
		long startedTime = System.currentTimeMillis();
		RestTemplate restTemplate = new RestTemplate();
		try {
			UserVotePermissionResponse votePermission = restTemplate.getForObject(URI + userCPF,
					UserVotePermissionResponse.class);

			logger.info(String.format(Constants.LOG_MESSAGE_2_PARAMS, CLASS_NAME, "isUserAbleToVote", votePermission,
					String.format(Constants.LOG_RESPONSE_TIME, (System.currentTimeMillis() - startedTime) / 1000.00)));
			
			return ABLE_TO_VOTE.equalsIgnoreCase(votePermission.getStatus());
		} catch (RestClientException e) {
			logger.error(String.format(Constants.LOG_MESSAGE_1_PARAMS, CLASS_NAME, "isUserAbleToVote", e.getMessage()));
			throw new IllegalArgumentException("This CPF is NOT valid: " + userCPF);
		}
	}
}
