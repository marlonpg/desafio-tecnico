package com.sicredi.desafiotecnico.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Session {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDateTime sessionStartTime;
	private long sessionDuration = 60;

	@OneToOne
	private Schedule schedule;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getSessionStartTime() {
		return sessionStartTime;
	}

	public void setSessionStartTime(LocalDateTime sessionStartTime) {
		this.sessionStartTime = sessionStartTime;
	}

	public long getSessionDuration() {
		return sessionDuration;
	}

	public void setSessionDuration(long sessionDuration) {
		this.sessionDuration = sessionDuration;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	@Override
	public String toString() {
		return "Session [id=" + id + ", schedule=" + schedule + ", sessionStartTime=" + sessionStartTime
				+ ", sessionDuration=" + sessionDuration + "]";
	}

}
