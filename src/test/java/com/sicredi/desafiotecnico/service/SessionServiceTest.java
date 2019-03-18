package com.sicredi.desafiotecnico.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.sicredi.desafiotecnico.exceptions.NotFoundException;
import com.sicredi.desafiotecnico.model.Schedule;
import com.sicredi.desafiotecnico.model.Session;
import com.sicredi.desafiotecnico.repository.SessionRepository;

@RunWith(MockitoJUnitRunner.class)
public class SessionServiceTest {

	@InjectMocks
	private SessionService sessionService;

	@Mock
	private SessionRepository sessionRepository;

	@Mock
	private ScheduleService scheduleService;

	private Session session;

	@Before
	public void setUp() {
		session = new Session(LocalDateTime.now(), 10L, new Schedule());
	}

	@Test(expected = NotFoundException.class)
	public void getSession_throwException_whenSessionIsNull() throws NotFoundException {
		when(sessionRepository.findByScheduleId(Mockito.anyLong())).thenReturn(null);

		sessionService.getSession(Mockito.anyLong());
	}

	@Test(expected = IllegalArgumentException.class)
	public void createSession_throwException_whenSessionWasAlreadyCreated() throws NotFoundException {
		when(sessionRepository.findByScheduleId(Mockito.anyLong())).thenReturn(session);

		sessionService.createSession(1000L, 10L);
	}

	@Test(expected = NotFoundException.class)
	public void createSession_throwException_whenScheduleDoesntExist() throws NotFoundException {
		when(sessionRepository.findByScheduleId(Mockito.anyLong())).thenReturn(null);
		when(scheduleService.getSchedule(Mockito.anyLong())).thenThrow(NotFoundException.class);

		Session session = sessionService.createSession(1000L, 10L);
	}

	@Test
	public void isSessionAvailable_shouldBeFalse_whenSessionNotFound() {
		when(sessionRepository.findByScheduleId(Mockito.anyLong())).thenReturn(null);

		boolean result = sessionService.isSessionAvailable(Mockito.anyLong());
		assertFalse("Session should not be available: ", result);
	}

	@Test
	public void isSessionAvailable_shouldBeFalse_whenSessionIsDone() {
		session.setSessionStartTime(LocalDateTime.MIN);
		when(sessionRepository.findByScheduleId(Mockito.anyLong())).thenReturn(session);

		boolean result = sessionService.isSessionAvailable(Mockito.anyLong());
		assertFalse("Session should not be available: ", result);
	}

	@Test
	public void isSessionAvailable_shouldBeTrue_whenSessionIsOpen() {
		session.setSessionStartTime(LocalDateTime.now());
		when(sessionRepository.findByScheduleId(Mockito.anyLong())).thenReturn(session);

		boolean result = sessionService.isSessionAvailable(Mockito.anyLong());
		assertTrue("Session should be available: ", result);
	}
}
