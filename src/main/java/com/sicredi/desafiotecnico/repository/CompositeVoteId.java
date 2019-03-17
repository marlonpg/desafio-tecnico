package com.sicredi.desafiotecnico.repository;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CompositeVoteId implements Serializable {

	private static final long serialVersionUID = -3951944394458267854L;
	
	private String userCPF;
	private Long schedule;

	public CompositeVoteId() {

	}

	public CompositeVoteId(String userCPF, Long scheduleId) {
		super();
		this.userCPF = userCPF;
		this.schedule = scheduleId;
	}

	public String getUserCPF() {
		return userCPF;
	}

	public void setUserCPF(String userCPF) {
		this.userCPF = userCPF;
	}

	public Long getScheduleId() {
		return schedule;
	}

	public void setScheduleId(Long scheduleId) {
		this.schedule = scheduleId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((schedule == null) ? 0 : schedule.hashCode());
		result = prime * result + ((userCPF == null) ? 0 : userCPF.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeVoteId other = (CompositeVoteId) obj;
		if (schedule == null) {
			if (other.schedule != null)
				return false;
		} else if (!schedule.equals(other.schedule))
			return false;
		if (userCPF == null) {
			if (other.userCPF != null)
				return false;
		} else if (!userCPF.equals(other.userCPF))
			return false;
		return true;
	}
}
