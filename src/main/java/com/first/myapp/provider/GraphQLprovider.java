package com.first.myapp.provider;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;


@Component
public class GraphQLprovider {
	private GraphQL graphql;
	
	@Autowired
    GraphQLDataFetchers graphQLDataFetchers;
	
	@Bean
	public GraphQL graphQL() {
		return graphql;
	}
	
	@PostConstruct
	public void init() throws IOException {
		URL url = Resources.getResource("schema.graphqls");
		String sdl = Resources.toString(url,Charsets.UTF_8);
		GraphQLSchema graphqlschema = buildSchema(sdl);
		this.graphql=GraphQL.newGraphQL(graphqlschema).build();	
	}
	private GraphQLSchema buildSchema(String sdl) {
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
		RuntimeWiring runtimewiring = buildWiring();
		SchemaGenerator schemaGenerator = new SchemaGenerator();
		return schemaGenerator.makeExecutableSchema(typeRegistry, runtimewiring);	
	}
	private RuntimeWiring buildWiring() {
		return RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("bookById",  graphQLDataFetchers.getBookByIdDataFetcher()))      
                .type(TypeRuntimeWiring.newTypeWiring("Mutation")
                		.dataFetcher("addBook", graphQLDataFetchers.addNewBook()))
                .build();
	}
}
