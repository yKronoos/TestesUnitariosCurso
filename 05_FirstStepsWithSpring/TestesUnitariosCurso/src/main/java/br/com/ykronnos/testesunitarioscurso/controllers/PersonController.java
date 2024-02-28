package br.com.ykronnos.testesunitarioscurso.controllers;

import br.com.ykronnos.testesunitarioscurso.model.Person;
import br.com.ykronnos.testesunitarioscurso.services.PersonServices;
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
    public ResponseEntity<Person> findById(@PathVariable Long id) throws Exception {
        try {
            return ResponseEntity.ok(personServices.findById(id));
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll() throws Exception {
        return personServices.findAll();
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) throws Exception {
        return personServices.create(person);
    }

    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) throws Exception {
        try {
            return ResponseEntity.ok(personServices.update(person));
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePerson(@PathVariable Long id) throws Exception {
        personServices.delete(id);

        return ResponseEntity.noContent().build();
    }

}
