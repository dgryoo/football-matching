package api.service;


import api.common.exception.custom.SourceNotFoundException;
import api.dto.team.CreateTeamDto;
import api.dto.team.TeamDto;
import com.pfairplay.mysql.core.entity.Team;
import com.pfairplay.mysql.core.repository.team.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamDto createTeam(CreateTeamDto createTeamDto) {
        int a = 0;
        if (a ==0) throw new RuntimeException();
        Team savedTeam = teamRepository.save(createTeamDto.toEntity());
        teamRepository.flush();
        Optional<Team> foundTeam = teamRepository.findById(savedTeam.getId());
        return TeamDto.fromEntity(foundTeam.get());
    }

    public TeamDto findTeamById(Long id) {
        Team foundTeam = teamRepository.findById(id)
                .orElseThrow(SourceNotFoundException::new);

        return TeamDto.fromEntity(foundTeam);

    }
}

