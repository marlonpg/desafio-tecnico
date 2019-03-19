package com.sicredi.desafiotecnico.dto;

public class ScheduleDto {
	private String topic;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "ScheduleDto [topic=" + topic + "]";
	}
}
