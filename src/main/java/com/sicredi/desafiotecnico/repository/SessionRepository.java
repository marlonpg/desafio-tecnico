package com.sicredi.desafiotecnico.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import com.sicredi.desafiotecnico.model.Session;

public interface SessionRepository extends CrudRepository<Session, Serializable> {

	public Session findByScheduleId(Long scheduleId);
}
