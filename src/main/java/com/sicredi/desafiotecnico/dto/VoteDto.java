package com.sicredi.desafiotecnico.dto;

public class VoteDto {
	private String userCPF;
	private String userVote;
	
	public VoteDto() {}

	public VoteDto(String userCPF, String userVote) {
		super();
		this.userCPF = userCPF;
		this.userVote = userVote;
	}

	public String getUserCPF() {
		return userCPF;
	}

	public void setUserCPF(String userCPF) {
		this.userCPF = userCPF;
	}

	public String getUserVote() {
		return userVote;
	}

	public void setUserVote(String userVote) {
		this.userVote = userVote;
	}
}