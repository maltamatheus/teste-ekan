package br.com.ekan.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ekan.domains.Usuario;
import br.com.ekan.services.UsuarioService;

@RestController
@RequestMapping("/teste-ekan/usuarios")
public class UsuariosController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listar(){
		return ResponseEntity.ok(usuarioService.listar());
	}

}
