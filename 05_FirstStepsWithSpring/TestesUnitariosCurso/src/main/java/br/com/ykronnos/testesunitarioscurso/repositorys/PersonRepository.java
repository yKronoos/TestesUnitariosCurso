package br.com.ykronnos.testesunitarioscurso.repositorys;

import br.com.ykronnos.testesunitarioscurso.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
    Optional<Person> findByAddress(String address);

    @Query("select p from Person p where p.firstName = ?1 and p.lastName = ?2")
    Person findByJPQL(String firstName, String lastName);
}
