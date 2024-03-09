package engineer.maiko.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import engineer.maiko.gestao_vagas.exceptions.UserNotFoundException;
import engineer.maiko.gestao_vagas.modules.candidate.CandidateRepository;
import engineer.maiko.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  public ProfileCandidateResponseDTO execute(UUID idCandidate) {
    @SuppressWarnings("null")
    var candidate = this.candidateRepository.findById(idCandidate)
    .orElseThrow(() -> {
      throw new UserNotFoundException();

    });
    var candidateDTO = ProfileCandidateResponseDTO.builder()
    .description(candidate.getDescription())
    .username(candidate.getUsername())
    .email(candidate.getEmail())
    .name(candidate.getName())
    .id(candidate.getId())
    .build();
    
    return candidateDTO;

  }
   
}
