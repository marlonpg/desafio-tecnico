package com.sicredi.desafiotecnico.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.sicredi.desafiotecnico.dto.VoteDto;
import com.sicredi.desafiotecnico.exceptions.NotFoundException;
import com.sicredi.desafiotecnico.model.Schedule;
import com.sicredi.desafiotecnico.model.Vote;
import com.sicredi.desafiotecnico.repository.VoteRepository;

@RunWith(MockitoJUnitRunner.class)
public class VoteServiceTest {
	private static final String VOTE_SIM = "SIM";
	private static final String VOTE_NAO = "NÃO";

	@InjectMocks
	private VoteService voteService;
	
	@Mock
	private VoteRepository voteRepository;

	@Mock
	private ScheduleService scheduleService;

	@Mock
	private SessionService sessionService;

	private VoteDto invalidVote;
	private VoteDto validVoteSIM;
	private VoteDto validVoteNAO;
	
	@Before
	public void setUp() {
		invalidVote = new VoteDto("02132154355", "YES");
		validVoteSIM = new VoteDto("02132154355", "SIM");
		validVoteNAO = new VoteDto("02132154355", "NÃO");
	}
	
	@Test(expected = NotFoundException.class)
	public void vote_throwException_whenSessionIsNotAvailable() throws NotFoundException {
		when(sessionService.isSessionAvailable(Mockito.anyLong())).thenReturn(false);

		voteService.vote(Mockito.anyLong(), new VoteDto());
	}

	@Test(expected = NotFoundException.class)
	public void vote_throwException_whenUserIsNotAbleToVote() throws NotFoundException {
		when(sessionService.isSessionAvailable(Mockito.anyLong())).thenReturn(true);
		when(voteRepository.findByScheduleIdAndUserCPF(Mockito.anyLong(), Mockito.anyString())).thenReturn(new Vote());
		
		voteService.vote(Mockito.anyLong(), validVoteSIM);
	}

	@Test(expected = IllegalArgumentException.class)
	public void vote_throwException_whenVoteIsDifferentFromSIMorNAO() throws NotFoundException {
		when(sessionService.isSessionAvailable(Mockito.anyLong())).thenReturn(true);
		when(scheduleService.getSchedule(Mockito.anyLong())).thenReturn(new Schedule());
		when(voteRepository.findByScheduleIdAndUserCPF(Mockito.anyLong(), Mockito.anyString())).thenReturn(null);
		
		voteService.vote(Mockito.anyLong(), invalidVote);
	}
	
	@Test
	public void vote_happyPath_whenVoteIsSIM() throws NotFoundException {
		when(sessionService.isSessionAvailable(Mockito.anyLong())).thenReturn(true);
		when(voteRepository.findByScheduleIdAndUserCPF(Mockito.anyLong(), Mockito.anyString())).thenReturn(null);
		
		voteService.vote(Mockito.anyLong(), validVoteSIM);
		
		Mockito.verify(voteRepository, times(1)).save(Mockito.anyObject());
	}
	
	@Test
	public void vote_happyPath_whenVoteIsNAO() throws NotFoundException {
		when(sessionService.isSessionAvailable(Mockito.anyLong())).thenReturn(true);
		when(voteRepository.findByScheduleIdAndUserCPF(Mockito.anyLong(), Mockito.anyString())).thenReturn(null);
		
		voteService.vote(Mockito.anyLong(), validVoteNAO);
		
		Mockito.verify(voteRepository, times(1)).save(Mockito.anyObject());
	}
	
	@Test(expected = NotFoundException.class)
	public void getVotes_throwException_whenNoVotesFound() throws NotFoundException{
		when(voteRepository.findByScheduleId(Mockito.anyLong())).thenReturn(null);
		
		voteService.getVotes(Mockito.anyLong());
	}
}
