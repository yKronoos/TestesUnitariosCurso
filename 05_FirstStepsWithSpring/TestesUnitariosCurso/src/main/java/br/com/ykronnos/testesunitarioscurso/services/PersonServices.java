package br.com.ykronnos.testesunitarioscurso.services;

import br.com.ykronnos.testesunitarioscurso.exeptions.ResourceNotFoundException;
import br.com.ykronnos.testesunitarioscurso.model.Person;
import br.com.ykronnos.testesunitarioscurso.repositorys.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServices {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll(){
        return personRepository.findAll();
    }
    public Person findById(Long id){
        return personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records founds for id"));
    }

    public Person create(Person person){
//        Optional<Person> savedPerson = personRepository.findByAddress(person.getAddress());
//        if(!savedPerson.isEmpty()){
//            throw new ResourceNotFoundException("Email exists");
//        }

        return personRepository.save(person);
    }

    public Person update(Person person){
        Person personUpdate = personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records founds for id"));

        personUpdate.setFirstName(person.getFirstName());
        personUpdate.setLastName(person.getLastName());
        personUpdate.setAddress(person.getAddress());
        personUpdate.setGender(person.getGender());

        return personRepository.save(person);
    }

    public void delete(Long id){
        Person personUpdate = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records founds for id"));
        personRepository.deleteById(id);
    }
}
