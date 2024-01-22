package br.com.ykronnos.testesunitarioscurso.controllers;

import br.com.ykronnos.testesunitarioscurso.model.Person;
import br.com.ykronnos.testesunitarioscurso.services.PersonServices;
import br.com.ykronnos.testesunitarioscurso.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices personServices;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findById(@PathVariable Long id) throws Exception {
        return personServices.findById(id);
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) throws Exception {
        return personServices.create(person);
    }

    @PutMapping
    public Person updatePerson(@RequestBody Person person) throws Exception {
        return personServices.update(person);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePerson(@PathVariable Long id) throws Exception {
        personServices.delete(id);

        return ResponseEntity.noContent().build();
    }

}
