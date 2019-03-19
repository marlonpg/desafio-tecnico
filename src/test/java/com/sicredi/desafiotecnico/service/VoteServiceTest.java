package com.sicredi.desafiotecnico.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.sicredi.desafiotecnico.dto.VoteDto;
import com.sicredi.desafiotecnico.exceptions.NotFoundException;
import com.sicredi.desafiotecnico.facades.UserVotePermissionFacade;
import com.sicredi.desafiotecnico.model.Schedule;
import com.sicredi.desafiotecnico.model.Vote;
import com.sicredi.desafiotecnico.repository.VoteRepository;
import com.sicredi.desafiotecnico.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class VoteServiceTest {

	@InjectMocks
	private VoteService voteService;
	
	@Mock
	private VoteRepository voteRepository;

	@Mock
	private ScheduleService scheduleService;

	@Mock
	private SessionService sessionService;
	
	@Mock
	private UserVotePermissionFacade userVotePermissionFacade;

	private VoteDto invalidVote;
	private VoteDto validVoteSIM;
	private VoteDto validVoteNAO;
	
	@Before
	public void setUp() {
		invalidVote = new VoteDto("02132154355", "YES");
		validVoteSIM = new VoteDto("02132154355", Constants.VOTE_SIM);
		validVoteNAO = new VoteDto("02132154355", Constants.VOTE_NAO);
	}
	
	@Test(expected = NotFoundException.class)
	public void vote_throwException_whenSessionIsNotAvailable() throws NotFoundException {
		when(sessionService.isSessionAvailable(Mockito.anyLong())).thenReturn(false);
		when(userVotePermissionFacade.isUserAbleToVote(Mockito.anyString())).thenReturn(true);
		voteService.vote(Mockito.anyLong(), validVoteSIM);
	}

	@Test(expected = NotFoundException.class)
	public void vote_throwException_whenUserIsNotAbleToVote() throws NotFoundException {
		when(sessionService.isSessionAvailable(Mockito.anyLong())).thenReturn(true);
		when(voteRepository.findByScheduleIdAndUserCPF(Mockito.anyLong(), Mockito.anyString())).thenReturn(new Vote());
		when(userVotePermissionFacade.isUserAbleToVote(Mockito.anyString())).thenReturn(true);
		
		voteService.vote(Mockito.anyLong(), validVoteSIM);
	}

	@Test(expected = IllegalArgumentException.class)
	public void vote_throwException_whenVoteIsDifferentFromSIMorNAO() throws NotFoundException {
		when(sessionService.isSessionAvailable(Mockito.anyLong())).thenReturn(true);
		when(scheduleService.getSchedule(Mockito.anyLong())).thenReturn(new Schedule());
		when(voteRepository.findByScheduleIdAndUserCPF(Mockito.anyLong(), Mockito.anyString())).thenReturn(null);
		when(userVotePermissionFacade.isUserAbleToVote(Mockito.anyString())).thenReturn(true);
		
		voteService.vote(Mockito.anyLong(), invalidVote);
	}
	
	@Test
	public void vote_happyPath_whenVoteIsSIM() throws NotFoundException {
		when(sessionService.isSessionAvailable(Mockito.anyLong())).thenReturn(true);
		when(voteRepository.findByScheduleIdAndUserCPF(Mockito.anyLong(), Mockito.anyString())).thenReturn(null);
		when(userVotePermissionFacade.isUserAbleToVote(Mockito.anyString())).thenReturn(true);
		
		voteService.vote(Mockito.anyLong(), validVoteSIM);
		
		Mockito.verify(voteRepository, times(1)).save(Mockito.anyObject());
	}
	
	@Test
	public void vote_happyPath_whenVoteIsNAO() throws NotFoundException {
		when(sessionService.isSessionAvailable(Mockito.anyLong())).thenReturn(true);
		when(voteRepository.findByScheduleIdAndUserCPF(Mockito.anyLong(), Mockito.anyString())).thenReturn(null);
		when(userVotePermissionFacade.isUserAbleToVote(Mockito.anyString())).thenReturn(true);
		
		voteService.vote(Mockito.anyLong(), validVoteNAO);
		
		Mockito.verify(voteRepository, times(1)).save(Mockito.anyObject());
	}
	
	@Test(expected = NotFoundException.class)
	public void getVotes_throwException_whenNoVotesFound() throws NotFoundException{
		when(voteRepository.findByScheduleId(Mockito.anyLong())).thenReturn(null);
		
		voteService.getVotes(Mockito.anyLong());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void vote_throwException_whenCPFBlank() throws NotFoundException {
		voteService.vote(1000L, new VoteDto());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void vote_throwException_whenCPFLengthIsLessThan11Characters() throws NotFoundException {
		VoteDto invalidCPFLessThan11 = new VoteDto("123456789", Constants.VOTE_SIM);
		voteService.vote(1000L, invalidCPFLessThan11);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void vote_throwException_whenCPFLengthIsGreaterThan11Characters() throws NotFoundException {
		VoteDto invalidCPFGreaterThan11 = new VoteDto("123456789101112", Constants.VOTE_SIM);
		voteService.vote(1000L, invalidCPFGreaterThan11);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void vote_throwException_whenUserVotePermissionFacadeReturnFalse() throws NotFoundException {
		when(userVotePermissionFacade.isUserAbleToVote(Mockito.anyString())).thenReturn(false);
		VoteDto invalidCPFGreaterThan11 = new VoteDto("123456789101112", Constants.VOTE_SIM);
		
		voteService.vote(1000L, validVoteSIM);
	}
}
