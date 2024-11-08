package br.com.ekan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.ekan.domains.Beneficiario;

@Repository
public interface BeneficiarioRepository extends CrudRepository<Beneficiario, Long> {

}
