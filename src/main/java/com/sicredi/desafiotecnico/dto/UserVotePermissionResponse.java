package com.sicredi.desafiotecnico.dto;

public class UserVotePermissionResponse {
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserVotePermissionResponse [status=" + status + "]";
	}
	
}
