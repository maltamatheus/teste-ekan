package br.com.ekan.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ekan.domains.Beneficiario;
import br.com.ekan.domains.BeneficiarioDto;
import br.com.ekan.domains.Documento;
import br.com.ekan.domains.enums.EnumStatusRemove;
import br.com.ekan.domains.enums.EnumTipoDocumento;
import br.com.ekan.services.BeneficiarioService;


@RestController
@RequestMapping("/teste-ekan/beneficiarios")
public class BeneficiarioController {
	
	@Autowired
	private BeneficiarioService beneficiarioService;
	
	@GetMapping
	public List<Beneficiario> listar(){
		return beneficiarioService.listar();
	}
	
	@GetMapping("/consultar")
	public ResponseEntity<Beneficiario> consultar(@RequestParam(name = "id") Long id){
		if (beneficiarioService.consultar(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
	
		return ResponseEntity.ok(beneficiarioService.consultar(id).get());
	}
		
	
	@PostMapping("/inserir")
	public ResponseEntity<Beneficiario> inserir(@RequestBody BeneficiarioDto beneficiarioDto){
		Beneficiario beneficiario = new Beneficiario(beneficiarioDto);
		return ResponseEntity.ok(beneficiarioService.salvar(beneficiario));
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Beneficiario> atualizarBeneficiario(@RequestBody BeneficiarioDto beneficiarioDto, @RequestParam(name="id") Long id){
		
		Beneficiario beneficiario = new Beneficiario(beneficiarioDto);
		
		beneficiario.setId(id);
		
		if(beneficiarioService.atualizar(beneficiario) == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(beneficiarioService.atualizar(beneficiario));
		
	}
	
	@DeleteMapping("/excluir")
	public ResponseEntity<EnumStatusRemove> excluir(@RequestParam(name = "id") Long id) {
		if(beneficiarioService.excluir(id).equals(EnumStatusRemove.BENEFICIARIO_NAO_ENCONTRADO)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(EnumStatusRemove.BENEFICIARIO_NAO_ENCONTRADO);
		}
		
		return ResponseEntity.ok(EnumStatusRemove.REMOVIDO_COM_SUCESSO);
	}
	
	
	@GetMapping("/teste")
	public ResponseEntity<List<Documento>> teste(@RequestParam(name="id") Long id){
		
		String retorno = null;
		
		List<Documento> documentos = new ArrayList<Documento>();
		
		List<String> palavras = new ArrayList<String>();
		
		HashMap<EnumTipoDocumento,String> hash = new HashMap<EnumTipoDocumento,String>();
		
		HashMap<EnumTipoDocumento,Documento> doctos = new HashMap<EnumTipoDocumento,Documento>();
		
		hash.put(EnumTipoDocumento.CNH, "1111111");
		hash.put(EnumTipoDocumento.CPF, "2222222");
		hash.put(EnumTipoDocumento.PASSAPORTE, "3333333");
		hash.put(EnumTipoDocumento.RG, "5555");
		
		beneficiarioService.consultar(id).get().getDocumentos().forEach(d -> {
			doctos.put(d.getTipoDocumento(), d);
		});
		
//		hash.forEach((chave,valor) ->{
//			palavras.add(chave + ":" + valor);
//			Documento d = new Documento();
//			d.setTipoDocumento(chave);
//			d.setDescricao(valor);
//			d.setDataAtualizacao(LocalDateTime.now());
//			
//			documentos.add(d);
//		});
		
		doctos.forEach((chave,valor) ->{
			palavras.add(chave + ":" + valor);
			Documento d = new Documento();
			d.setTipoDocumento(chave);
			d = valor;
			
			d.setDataAtualizacao(LocalDateTime.now());
			
			documentos.add(d);
		});
		
		retorno = palavras.toString();
		
		return ResponseEntity.ok(documentos);
	}

}
