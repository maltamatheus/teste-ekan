package br.com.ekan.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.ekan.domains.enums.EnumTipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tab_usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private Long id;
	
	private String nome;

	@Column(name="num_documento")
	private String numDocumento;
	
	@Column(name="tipo_documento")
	private EnumTipoDocumento tipoDocumento;
}
