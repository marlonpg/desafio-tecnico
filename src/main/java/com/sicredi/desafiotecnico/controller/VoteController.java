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

import com.sicredi.desafiotecnico.dto.VoteDto;
import com.sicredi.desafiotecnico.exceptions.NotFoundException;
import com.sicredi.desafiotecnico.model.Session;
import com.sicredi.desafiotecnico.model.Vote;
import com.sicredi.desafiotecnico.service.VoteService;
import com.sicredi.desafiotecnico.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/schedule/{scheduleId}/vote")
@Api(value = "/schedule/{scheduleId}/vote", produces = "application/json", description = "This API is responsible to retrieve/create Votes for a specific Session")
public class VoteController {
	private static final Logger logger = LogManager.getLogger(VoteController.class);
	private static final String CLASS_NAME = logger.getName();

	@Autowired
	private VoteService voteService;

	@PostMapping
	@ApiOperation(value = "Create Vote", notes = "Service is used to create a vote in the schedule")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Vote has been created", response = Vote.class),
			@ApiResponse(code = 404, message = "Session closed or the user have already voted. \n No Schedule found for id: XYZ"),
			@ApiResponse(code = 400, message = "Invalid vote string [XYZ], the vote must be 'SIM' or 'NÃO. \n Invalid CPF, it can't be blank/null. \n Invalid CPF, it must have 11 character [XYZ]. \n This CPF [XYZ] is unable to vote."),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity vote(
			@ApiParam(name = "scheduleId", value = "The id of the schedule", example = "1", required = true) @PathVariable Long scheduleId,
			@ApiParam(name = "voteDto", value = "The Vote object", required = true) @RequestBody VoteDto voteDto) {
		long startedTime = System.currentTimeMillis();
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(voteService.vote(scheduleId, voteDto));
		} catch (NotFoundException e) {
			logger.warn(String.format(Constants.LOG_MESSAGE_1_PARAMS, CLASS_NAME, "vote", e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.warn(String.format(Constants.LOG_MESSAGE_1_PARAMS, CLASS_NAME, "vote", e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			logger.error(String.format(Constants.LOG_MESSAGE_1_PARAMS, CLASS_NAME, "vote", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} finally {
			logger.info(String.format(Constants.LOG_MESSAGE_1_PARAMS, CLASS_NAME, "vote", String.format(Constants.LOG_RESPONSE_TIME, (System.currentTimeMillis() - startedTime)/1000.00)));
		}

	}

	@GetMapping
	@ApiOperation(value = "Get Votes", notes = "Service is used to get all votes from the scheduleId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retrieve a session", response = Session.class),
			@ApiResponse(code = 404, message = "No Session found by this ScheduleId: XYZ"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity getVotes(
			@ApiParam(name = "scheduleId", value = "The id of the schedule", example = "1", required = true) @PathVariable Long scheduleId) {
		long startedTime = System.currentTimeMillis();
		try {
			return ResponseEntity.ok(voteService.getVotes(scheduleId));
		} catch (NotFoundException e) {
			logger.warn(String.format(Constants.LOG_MESSAGE_1_PARAMS, CLASS_NAME, "getVotes", e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			logger.error(String.format(Constants.LOG_MESSAGE_1_PARAMS, CLASS_NAME, "getVotes", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} finally {
			logger.info(String.format(Constants.LOG_MESSAGE_1_PARAMS, CLASS_NAME, "getVotes", String.format(Constants.LOG_RESPONSE_TIME, (System.currentTimeMillis() - startedTime)/1000.00)));
		}

	}
}
