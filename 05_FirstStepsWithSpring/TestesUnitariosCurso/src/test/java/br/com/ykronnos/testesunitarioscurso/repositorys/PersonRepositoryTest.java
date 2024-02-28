package br.com.ykronnos.testesunitarioscurso.repositorys;

import br.com.ykronnos.testesunitarioscurso.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.ykronnos.testesunitarioscurso.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private PersonRepository personRepository;

    private  Person person0;

    private Person person1;

    @BeforeEach
    public void setup(){
        person0 = new Person();
        person0.setFirstName("TestFistNameBDD");
        person0.setLastName("TestLastNAmeBDD");
        person0.setAddress("TestAddressBDD");
        person0.setGender("TestMaleBDD");

        person1 = new Person();

        person1.setFirstName("TestFistName1BDD");
        person1.setLastName("TestLastName1BDD");
        person1.setAddress("TestAddress1BDD");
        person1.setGender("TestMaleBDD");
    }

    @Test
    void testSave() {
        Person savedPerson = personRepository.save(person0);

        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId() > 0);
    }

    @Test
    void testListPerson() {

        personRepository.save(person0);
        personRepository.save(person1);

        List<Person> personList = personRepository.findAll();

        assertNotNull(personList);
        assertEquals(2, personList.size());
    }

    @Test
    void testGeyById() {

        personRepository.save(person0);

        Person person = personRepository.findById(person0.getId()).get();

        assertNotNull(person);
        assertEquals(person0.getId(), person.getId());
    }

    @Test
    void testGeyByEmail() {
        personRepository.save(person0);

        Person person = personRepository.findByAddress(person0.getAddress()).get();

        assertNotNull(person);
        assertEquals(person0.getAddress(), person.getAddress());
    }

    @Test
    void testUpdatePerson() {
        personRepository.save(person0);

        Person person = personRepository.findById(person0.getId()).get();
        person.setFirstName("Coco");
        person.setGender("Female");

        Person updatad = personRepository.save(person);

        assertNotNull(updatad);
        assertEquals("Coco", updatad.getFirstName());
    }

    @Test
    void testDelete() {

        personRepository.save(person0);

        Person person = personRepository.findById(person0.getId()).get();

        personRepository.delete(person);

        Optional<Person> optionalPerson = personRepository.findById(person0.getId());

        assertTrue(optionalPerson.isEmpty());
    }

    @Test
    void testJPQL() {
        personRepository.save(person0);

        Person person = personRepository.findByJPQL(person0.getFirstName(), person0.getLastName());

        assertNotNull(person);
        assertEquals("TestFistNameBDD", person.getFirstName());
        assertEquals("TestLastNAmeBDD", person.getLastName());
    }
}