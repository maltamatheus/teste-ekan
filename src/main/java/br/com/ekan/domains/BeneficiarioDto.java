package br.com.ekan.domains;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BeneficiarioDto {
	
	private String nome;
	private String telefone;
	private List<DocumentoDto> documentos;
	private LocalDate dataNascimento;
	
	public List<Documento> getDoctos(List<DocumentoDto> documentosDto) {
		
		List<Documento> documentos = new ArrayList<Documento>();
		
		if(documentosDto == null) {
			return documentos;
		} else {
			documentosDto.forEach(dto -> {
				Documento d = new Documento();
				d.setTipoDocumento(dto.getTipoDocumento());
				d.setDescricao(dto.getDescricao());
				
				documentos.add(d);
			});
		}
		
		
		return documentos;
	}

}
