package com.school.api.security;

import com.school.api.security.dto.DadosLogin;
import com.school.api.security.dto.DadosTokenJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(
        name = "Autenticação",
        description = "Endpoints responsáveis pela autenticação dos usuários"
)
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginController(AuthenticationManager authenticationManager, TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Operation(
            summary = "Realizar login",
            description = """
                    Autentica um usuário utilizando login e senha.
                    
                    Em caso de sucesso retorna um token JWT.
                    """
    )
    @PostMapping
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody DadosLogin dados){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        var authentication = authenticationManager.authenticate(authenticationToken);

        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenService.gerarToken(usuario);

        return ResponseEntity.ok(new DadosTokenJWT(token));
    }
}
