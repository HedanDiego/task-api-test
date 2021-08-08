package br.hd.pipeline.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
		.statusCode(200)
		;
		
	}

	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured.given()
			.body("{\"task\": \"Teste Data \", \"dueDate\": \"2021-08-07\" } ")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
			.log().all()
		;
		
	}
	
	@Test
	public void naodeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{\"task\": \"Teste via API\",\"dueDate\": \"2020-08-06\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
		.statusCode(400)
		.body("message", CoreMatchers.is("Due date must not be in past"))
		;		
	}	
}

