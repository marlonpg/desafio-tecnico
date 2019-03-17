package com.sicredi.desafiotecnico.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicredi.desafiotecnico.repository.CompositeVoteId;

@Entity
@IdClass(CompositeVoteId.class)
public class Vote {

	@Id
	@Column(length=11)
	private String userCPF;

	@Id
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scheduleId", insertable = false, updatable = false)
	private Schedule schedule;

	private Boolean vote;
	
	public Vote() {
	}

	public Vote(String userCPF, Boolean vote, Schedule schedule) {
		super();
		this.userCPF = userCPF;
		this.vote = vote;
		this.schedule = schedule;
	}

	public String getUserCPF() {
		return userCPF;
	}

	public void setUserCPF(String userCPF) {
		this.userCPF = userCPF;
	}

	public Boolean getVote() {
		return vote;
	}

	public void setVote(Boolean vote) {
		this.vote = vote;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	@Override
	public String toString() {
		return "Vote [userCPF=" + userCPF + ", schedule=" + schedule + ", vote=" + vote + "]";
	}

	
}
