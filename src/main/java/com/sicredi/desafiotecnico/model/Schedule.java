package com.sicredi.desafiotecnico.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String topic;

	public Schedule() {}
	
	public Schedule(String topic) {
		super();
		this.topic = topic;
	}

	public Schedule(Long id, String topic) {
		super();
		this.id = id;
		this.topic = topic;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", topic=" + topic + "]";
	}

}
