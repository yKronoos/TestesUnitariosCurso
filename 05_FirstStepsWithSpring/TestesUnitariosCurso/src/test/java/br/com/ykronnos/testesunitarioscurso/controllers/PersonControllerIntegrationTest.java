package br.com.ykronnos.testesunitarioscurso.controllers;

import br.com.ykronnos.testesunitarioscurso.config.TestConfigs;
import br.com.ykronnos.testesunitarioscurso.model.Person;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PersonControllerIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper mapper;
    private static Person person;

    @BeforeAll
    public static void setup(){

        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specification = new RequestSpecBuilder()
                .setBasePath("/person")
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();


        person = new Person();
        person.setFirstName("TestFistNameBDD");
        person.setLastName("TestLastNAmeBDD");
        person.setAddress("TestAddressBDD");
        person.setGender("TestMaleBDD");

    }

    @Test
    @Order(1)
    void testIntegrationCreate() throws IOException {

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();

        Person createdPerson = mapper.readValue(content, Person.class);
        person = createdPerson;

        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId()  > 0);
        assertEquals("TestFistNameBDD", createdPerson.getFirstName());
        assertEquals("TestLastNAmeBDD", createdPerson.getLastName());
        assertEquals("TestAddressBDD", createdPerson.getAddress());
        assertEquals("TestMaleBDD", createdPerson.getGender());

    }

    @Test
    @Order(2)
    void testIntegrationUpdate() throws IOException {

        person.setFirstName("TT");
        person.setLastName("NAni");

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person createdPerson = mapper.readValue(content, Person.class);
        person = createdPerson;

        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId()  > 0);
        assertEquals("TT", createdPerson.getFirstName());
        assertEquals("NAni", createdPerson.getLastName());
        assertEquals("TestAddressBDD", createdPerson.getAddress());
        assertEquals("TestMaleBDD", createdPerson.getGender());

    }

    @Test
    @Order(3)
    void testIntegrationFindById() throws IOException {

        var content = given().spec(specification)
                .pathParams("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person createdPerson = mapper.readValue(content, Person.class);

        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId()  > 0);
        assertEquals("TT", createdPerson.getFirstName());
        assertEquals("NAni", createdPerson.getLastName());
        assertEquals("TestAddressBDD", createdPerson.getAddress());
        assertEquals("TestMaleBDD", createdPerson.getGender());

    }

    @Test
    @Order(4)
    void testIntegrationFindAll() throws IOException {

        Person anotherPerson = new Person("Testinho3", "Nani", "nani@gmail.com", "NY");

        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(anotherPerson)
                .when()
                .post()
                .then()
                .statusCode(200);

        var content = given().spec(specification)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        List<Person> peoples = Arrays.asList(mapper.readValue(content, Person[].class));

        assertNotNull(peoples);
        assertTrue(peoples.size()  > 0);
    }

    @Test
    @Order(5)
    void testIntegrationDelete() throws IOException {

        given().spec(specification)
                .pathParams("id", person.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

}