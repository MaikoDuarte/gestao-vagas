package engineer.maiko.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.security.crypto.password.PasswordEncoder;

import engineer.maiko.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import engineer.maiko.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

  @Value("${security.token.secret}")
  private String secretKey;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

    public String execute(AuthCompanyDTO AuthCompanyDTO) throws AuthenticationException {
      var company = this.companyRepository.findByUsername(AuthCompanyDTO.getUsername()).orElseThrow(
        () -> {
          throw new UsernameNotFoundException("Username/password incorrect");
        }
      );

      //Verificar a senha são iguais
      var passwordMatches = this.passwordEncoder.matches(AuthCompanyDTO.getPassword(), company.getPassword());

      if(!passwordMatches) {
        throw new AuthenticationException();
      }

      Algorithm algorithm = Algorithm.HMAC256(secretKey);
      var token = JWT.create().withIssuer("javagas")
      .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
        .withSubject(company.getId().toString())
        .sign(algorithm);
      return token;



    }
  }

  

