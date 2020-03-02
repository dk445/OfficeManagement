package com.first.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;

import graphql.schema.idl.TypeDefinitionRegistry;
import static graphql.schema.GraphQLObjectType.newObject;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLObjectType;

@SpringBootApplication
public class OfficeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfficeManagementApplication.class, args);
		
//		GraphQLObjectType MyType = newObject()
//				.name("Person")
//				.field(newFieldDefinition()
//						.name("Name")
//						.type(GraphQLString))
//				.build();
		//System.out.println(MyType);
		
		String schema = "type Query{hello: String,name:String}";

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
                .type("Query", builder -> builder.dataFetcher("name", new StaticDataFetcher("xkja")))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{hello,name}");

        System.out.println(executionResult.getData().toString());
		
	}

}
