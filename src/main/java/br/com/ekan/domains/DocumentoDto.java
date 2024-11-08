package br.com.ekan.domains;

import br.com.ekan.domains.enums.EnumTipoDocumento;
import lombok.Data;

@Data
public class DocumentoDto {
	private EnumTipoDocumento tipoDocumento;
	private String descricao;
}
