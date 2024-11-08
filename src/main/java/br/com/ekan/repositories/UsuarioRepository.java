package br.com.ekan.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.ekan.domains.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,Long>{

}
