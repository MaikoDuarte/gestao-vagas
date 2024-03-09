package engineer.maiko.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import engineer.maiko.gestao_vagas.exceptions.JobNotFoundException;
import engineer.maiko.gestao_vagas.exceptions.UserNotFoundException;
import engineer.maiko.gestao_vagas.modules.candidate.CandidateRepository;
import engineer.maiko.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import engineer.maiko.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import engineer.maiko.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private ApplyJobRepository applyJobRepository;

  

  // ID do Candidato
  // ID da 
  @SuppressWarnings("null")
  public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
    //Validar se candidao existe

    this.candidateRepository.findById(idCandidate)
    .orElseThrow(() -> {
      throw new UserNotFoundException();

    });

    //Validar se a vaga existe

    this.jobRepository.findById(idJob)
    .orElseThrow(() -> {
      throw new JobNotFoundException();
    });

    var applyJob = ApplyJobEntity.builder()
    .candidateId(idCandidate)
    .jobId(idJob).build();

     applyJob = applyJobRepository.save(applyJob);
     return applyJob;


    //Candidato se inscrever na vaga


  }

  
}
