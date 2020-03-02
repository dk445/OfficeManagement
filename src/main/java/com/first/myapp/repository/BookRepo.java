package com.first.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.myapp.model.Book;

public interface BookRepo extends JpaRepository<Book, Integer> {
	
}
