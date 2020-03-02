package com.first.myapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private int pageCount;
	
	public Book(Object title, Object pageCount) {
		super();
		this.title = (String) title;
		this.pageCount = (int) pageCount;
	}

	public Book() {
		// TODO Auto-generated constructor stub
	}
	
	
//	private String isbn;
}
