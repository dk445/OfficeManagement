package com.first.myapp.provider;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.first.myapp.model.Book;
import com.first.myapp.repository.BookRepo;

import graphql.schema.DataFetchingEnvironment;

@Component
public class GraphQLDataFetchers {
	
	@Autowired
	BookRepo bookRepo;
	public graphql.schema.DataFetcher getBookByIdDataFetcher() {
		return dataFetchingEnvironment -> {
			String bookId = dataFetchingEnvironment.getArgument("id");
			int id = Integer.parseInt(bookId);
			return bookRepo.findById(id);
		};
	}
	
	public graphql.schema.DataFetcher addNewBook(){
		return dataFetchingEnvironment -> {
			Map<String, Object> inputData = dataFetchingEnvironment.getArgument("input");
			return bookRepo.save(new Book(inputData.get("title"),inputData.get("pageCount")));
		};
	}
}
