package br.com.ykronnos.testesunitarioscurso.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import br.com.ykronnos.testesunitarioscurso.exeptions.ResourceNotFoundException;
import br.com.ykronnos.testesunitarioscurso.model.Person;
import br.com.ykronnos.testesunitarioscurso.repositorys.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonServices services;

    private Person person0;

    @BeforeEach
    public void setup(){
        person0 = new Person();
        person0.setFirstName("TestFistNameBDD");
        person0.setLastName("TestLastNAmeBDD");
        person0.setAddress("AWDAW@gas");
        person0.setGender("TestMaleBDD");
    }

    @Test
    void testSaveGood() {
        when(repository.findByAddress(anyString())).thenReturn(Optional.empty());
        when(repository.save(person0)).thenReturn(person0);

        Person savPerson = services.create(person0);

        assertNotNull(savPerson);
        assertEquals("TestFistNameBDD", savPerson.getFirstName());
        //assertTrue(savPerson.getId() > 0);
    }

    @Test
    void testSaveBad() {
        when(repository.findByAddress(anyString())).thenReturn(Optional.of(person0));

        assertThrows(ResourceNotFoundException.class, () -> {
            services.create(person0);
        });

        verify(repository, never()).save(any(Person.class));
    }

    @Test
    void testGetAllGood() {
        Person person1 = new Person();

        person1.setFirstName("TestFistName1BDD");
        person1.setLastName("TestLastName1BDD");
        person1.setAddress("TestAddress1BDD");
        person1.setGender("TestMaleBDD");

        when(repository.findAll()).thenReturn(List.of(person0, person1));

        List<Person> personList = services.findAll();

        assertNotNull(personList);
        assertEquals(2, personList.size());
    }

    @Test
    void testGetAllBad() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Person> personList = services.findAll();

        assertTrue(personList.isEmpty());
        assertEquals(0, personList.size());
    }

    @Test
    void testGetId() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(person0));

        Person savPerson = services.findById(1L);

        assertNotNull(savPerson);
        assertEquals("TestFistNameBDD", savPerson.getFirstName());
        //assertTrue(savPerson.getId() > 0);
    }

    @Test
    void testUpdate() {

        person0.setId(1l);

        when(repository.findById(anyLong())).thenReturn(Optional.of(person0));

        person0.setAddress("Nabni3");
        person0.setFirstName("Testinho24");

        when(repository.save(person0)).thenReturn(person0);

        Person updatadPerson = services.update(person0);

        assertNotNull(updatadPerson);
        assertEquals("Testinho24", updatadPerson.getFirstName());
        assertEquals("Nabni3", updatadPerson.getAddress());
        //g
    }

    @Test
    void testDelete() {

        person0.setId(1l);

        when(repository.findById(anyLong())).thenReturn(Optional.of(person0));
        doNothing().when(repository).deleteById(person0.getId());

        services.delete(person0.getId());

        verify(repository, times(1)).deleteById(person0.getId());
    }
}