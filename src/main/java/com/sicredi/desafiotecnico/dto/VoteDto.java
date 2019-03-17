package com.sicredi.desafiotecnico.dto;

public class VoteDto {
	private String userCPF;
	private String vote;
	
	public String getUserCPF() {
		return userCPF;
	}
	public void setUserCPF(String userCPF) {
		this.userCPF = userCPF;
	}
	public String getVote() {
		return vote;
	}
	public void setVote(String vote) {
		this.vote = vote;
	}
}
