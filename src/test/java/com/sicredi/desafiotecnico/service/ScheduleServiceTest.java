package com.sicredi.desafiotecnico.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.sicredi.desafiotecnico.dto.ScheduleResult;
import com.sicredi.desafiotecnico.exceptions.NotFoundException;
import com.sicredi.desafiotecnico.model.Schedule;
import com.sicredi.desafiotecnico.model.Session;
import com.sicredi.desafiotecnico.model.Vote;
import com.sicredi.desafiotecnico.repository.ScheduleRepository;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceTest {

	@InjectMocks
	private ScheduleService scheduleService;

	@Mock
	private ScheduleRepository scheduleRepository;

	@Mock
	private SessionService sessionService;

	@Mock
	private VoteService voteService;

	@Test(expected = NotFoundException.class)
	public void getSchedule_throwException_whenScheduleDoesntExist() throws NotFoundException {
		when(scheduleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		scheduleService.getSchedule(Mockito.anyLong());
	}

	@Test(expected = NotFoundException.class)
	public void getScheduleResult_throwException_whenSessionDoesntExist() throws NotFoundException {
		when(sessionService.getSession(Mockito.anyLong())).thenThrow(NotFoundException.class);

		scheduleService.getScheduleResult(Mockito.anyLong());
	}

	@Test(expected = NotFoundException.class)
	public void getScheduleResult_throwException_whenNoVotesFound() throws NotFoundException {
		when(sessionService.getSession(Mockito.anyLong())).thenReturn(new Session());
		when(voteService.getVotes(Mockito.anyLong())).thenThrow(NotFoundException.class);

		scheduleService.getScheduleResult(Mockito.anyLong());
	}

	@Test
	public void getScheduleResult_calculateVotes_whenValid() throws NotFoundException {
		List<Vote> votes = Arrays.asList(new Vote("", true, new Schedule()), new Vote("", true, new Schedule()),
				new Vote("", true, new Schedule()), new Vote("", false, new Schedule()));

		when(sessionService.getSession(Mockito.anyLong())).thenReturn(new Session());
		when(voteService.getVotes(Mockito.anyLong())).thenReturn(votes);

		ScheduleResult scheduleResult = scheduleService.getScheduleResult(Mockito.anyLong());

		assertEquals("Result must have 3 SIM ", 3L, scheduleResult.getTotalVotesSim());
		assertEquals("Result must have 1 NÃO ", 1L, scheduleResult.getTotalVotesNao());
	}
}

