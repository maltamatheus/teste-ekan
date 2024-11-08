package br.com.ekan.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ekan.domains.Beneficiario;
import br.com.ekan.domains.Documento;
import br.com.ekan.domains.enums.EnumStatusRemove;
import br.com.ekan.domains.enums.EnumTipoDocumento;
import br.com.ekan.repositories.BeneficiarioRepository;

@Service
public class BeneficiarioService {
	
	@Autowired
	private BeneficiarioRepository beneficiarioRepository;

	public List<Beneficiario> listar() {
		return (List<Beneficiario>) beneficiarioRepository.findAll();
	}
	
	public Optional<Beneficiario> consultar(Long id) {
		return beneficiarioRepository.findById(id);
	}
	
	public Beneficiario salvar(Beneficiario novoBeneficiario) {
		if(novoBeneficiario.getDataInclusao() == null) {
			novoBeneficiario.setDataInclusao(LocalDateTime.now());
		}
		novoBeneficiario.setDataAtualizacao(LocalDateTime.now());
		
		if(!novoBeneficiario.getDocumentos().isEmpty()) {
			novoBeneficiario.getDocumentos().forEach(d->{
				if (d.getDataInclusao() == null) {
					d.setDataInclusao(LocalDateTime.now());
				}
				
				d.setDataAtualizacao(LocalDateTime.now());
			});
		}
		
		return beneficiarioRepository.save(novoBeneficiario);
	}
	
	public Beneficiario atualizar(Beneficiario beneficiario) {
		
		Optional<Beneficiario> beneficiarioAtual = consultar(beneficiario.getId());
		
		if (!beneficiarioAtual.isPresent()) {
			return null;
		}
		
		if(beneficiario.getNome() != null) {
			beneficiarioAtual.get().setNome(beneficiario.getNome());
		}
		
		if(beneficiario.getTelefone() != null) {
			beneficiarioAtual.get().setTelefone(beneficiario.getTelefone());
		}
		
		atualizaListaDocumentos(beneficiarioAtual.get(),beneficiario);
		
		if(beneficiario.getDataNascimento() != null) {
			beneficiarioAtual.get().setDataNascimento(beneficiario.getDataNascimento());
		}
		
		if(beneficiarioAtual.get().getDataInclusao() == null) {
			beneficiarioAtual.get().setDataInclusao(LocalDateTime.now());
		}
		
		return salvar(beneficiarioAtual.get());
	}
	
	public EnumStatusRemove excluir(Long id) {
		if(consultar(id).isEmpty()) {
			return EnumStatusRemove.BENEFICIARIO_NAO_ENCONTRADO;
		}
		
		beneficiarioRepository.delete(consultar(id).get());
		
		return EnumStatusRemove.REMOVIDO_COM_SUCESSO;
	}
	
	//Método criado para atualizar os documentos que existem com os que estão entrando
	private void atualizaListaDocumentos(Beneficiario beneficiarioAtual, Beneficiario beneficiario) {
		List<Documento> documentosUpdt = new ArrayList<Documento>();

		//Se não houverem documentos na base, os novos são atribuídos
		if(!beneficiario.getDocumentos().isEmpty()) {
			
			if (beneficiarioAtual.getDocumentos().isEmpty()) {
				beneficiarioAtual.setDocumentos(beneficiario.getDocumentos());
				
			} else {
				//Separamos os documentos que estão entrando dos documentos atuais
				HashMap<EnumTipoDocumento,Documento> docto = converteArrayListToHashMap(beneficiario.getDocumentos());
				
				HashMap<EnumTipoDocumento,Documento> doctoAtual = converteArrayListToHashMap(beneficiarioAtual.getDocumentos());
				
				//Atualiza a lista dos documentos que já existem com os documentos que estão sendo informados.
				docto.forEach((tipo,documento) -> {
					doctoAtual.put(tipo, documento);
				});
				
				//Gera uma nova lista de documentos
				doctoAtual.forEach((tipoNovo,novoDocto) -> {
					Documento d = novoDocto;
					
					d.setDataAtualizacao(LocalDateTime.now());
					
					d.setTipoDocumento(tipoNovo);
					
					documentosUpdt.add(d);
				});
				
				//Atualiza os documentos do beneficiário encontrado na base
				beneficiarioAtual.setDocumentos(documentosUpdt);
			}
		}	
	}
	
    private HashMap<EnumTipoDocumento, Documento> converteArrayListToHashMap(List<Documento> documentos) 
    { 
  
        HashMap<EnumTipoDocumento, Documento> hashMap = new HashMap<>(); 
  
        documentos.forEach(d->{
        	hashMap.put(d.getTipoDocumento(), d);
        });
  
        return hashMap; 
    } 
}
