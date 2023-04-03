package com.cts.springboot.firstrestapi.survey;

import java.util.List;

public class Question {

	public Question() {
		// no argument constructor
	}

	public Question(String id, String descripton, List<String> options, String correctAnswer) {
		super();
		this.id = id;
		this.descripton = descripton;
		this.options = options;
		this.correctAnswer = correctAnswer;
	}

	private String id;
	private String descripton;
	private List<String> options;
	private String correctAnswer;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getDescripton() {
		return descripton;
	}

	public List<String> getOptions() {
		return options;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", descripton=" + descripton + ", options=" + options + ", correctAnswer="
				+ correctAnswer + "]";
	}

}
