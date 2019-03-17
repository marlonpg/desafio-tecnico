package com.sicredi.desafiotecnico.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.sicredi.desafiotecnico.model.Vote;

public interface VoteRepository extends CrudRepository<Vote, Serializable> {

	public List<Vote> findByScheduleId(Long scheduleId);
	
	public Vote findByScheduleIdAndUserCPF(Long scheduleId, String userCPF);
}
