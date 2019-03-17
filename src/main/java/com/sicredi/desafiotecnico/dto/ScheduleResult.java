package com.sicredi.desafiotecnico.dto;

import java.util.List;

import com.sicredi.desafiotecnico.model.Session;
import com.sicredi.desafiotecnico.model.Vote;

public class ScheduleResult {

	private Session session;
	private long totalVotesSim;
	private long totalVotesNao;
	private List<Vote> votes;

	public ScheduleResult() {
	}
	
	public ScheduleResult(Session session, long totalVotesSim, long totalVotesNao, List<Vote> votes) {
		super();
		this.session = session;
		this.totalVotesSim = totalVotesSim;
		this.totalVotesNao = totalVotesNao;
		this.votes = votes;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public long getTotalVotesSim() {
		return totalVotesSim;
	}

	public void setTotalVotesSim(long totalVotesSim) {
		this.totalVotesSim = totalVotesSim;
	}

	public long getTotalVotesNao() {
		return totalVotesNao;
	}

	public void setTotalVotesNao(long totalVotesNao) {
		this.totalVotesNao = totalVotesNao;
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}

	@Override
	public String toString() {
		return "ScheduleResult [session=" + session + ", totalVotesSim=" + totalVotesSim + ", totalVotesNao="
				+ totalVotesNao + ", votes=" + votes + "]";
	}
}
