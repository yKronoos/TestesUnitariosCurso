package br.com.ykronnos.testesunitarioscurso.controllers;

import static org.mockito.BDDMockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.MockMvc.*;

import br.com.ykronnos.testesunitarioscurso.exeptions.ResourceNotFoundException;
import br.com.ykronnos.testesunitarioscurso.model.Person;
import br.com.ykronnos.testesunitarioscurso.services.PersonServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonServices personServices;

    @Autowired
    private ObjectMapper objectMapper;

    private Person person;

    @BeforeEach
    public void setup(){
        person = new Person();
        person.setFirstName("TestFistNameBDD");
        person.setLastName("TestLastNAmeBDD");
        person.setAddress("AWDAW@gas");
        person.setGender("TestMaleBDD");
    }

    @Test
    void testCreate() throws Exception {
        given(personServices.create(any(Person.class))).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person)));

        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.gender", is(person.getGender())));
    }

    @Test
    void testFindAll() throws Exception {

        List<Person> personList = new ArrayList<>();

        personList.add(person);
        personList.add(new Person("Lala", "email@email.com", "Cidade", "Male"));

        given(personServices.findAll()).willReturn(personList);

        ResultActions response = mockMvc.perform(get("/person"));

        response.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.size()", is(personList.size())));
    }

    @Test
    void testFindById() throws Exception {

        long personId = 1L;

        given(personServices.findById(personId)).willReturn(person);

        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        response.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.gender", is(person.getGender())));
    }

    @Test
    void testFindByIdBad() throws Exception {

        long personId = 2L;

        given(personServices.findById(personId)).willThrow(ResourceNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        response.andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    void testUpdate() throws Exception {

        long personId = 1L;

        given(personServices.findById(personId)).willReturn(person);
        given(personServices.update(any(Person.class))).willAnswer((invocation) -> invocation.getArgument(0));

        Person updated = new Person("Lala", "email@email.com", "Cidade", "Male");

        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)));

        response.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.firstName", is(updated.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updated.getLastName())))
                .andExpect(jsonPath("$.gender", is(updated.getGender())));
    }

    @Test
    void testDelete() throws Exception {

        long personId = 1L;

        given(personServices.findById(personId)).willReturn(person);
        willDoNothing().given(personServices).delete(personId);

        ResultActions response = mockMvc.perform(delete("/person/{id}", personId));

        response.andExpect(status().isNoContent()).andDo(print());
    }
}