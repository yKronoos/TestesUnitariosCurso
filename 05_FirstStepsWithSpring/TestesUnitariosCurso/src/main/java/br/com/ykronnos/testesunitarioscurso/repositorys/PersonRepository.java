package br.com.ykronnos.testesunitarioscurso.repositorys;

import br.com.ykronnos.testesunitarioscurso.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
}
