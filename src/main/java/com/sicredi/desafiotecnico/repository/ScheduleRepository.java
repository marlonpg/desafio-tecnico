package com.sicredi.desafiotecnico.repository;

import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;

import com.sicredi.desafiotecnico.model.Schedule;

public interface ScheduleRepository extends CrudRepository<Schedule, Serializable> {

}
