package stracture.football.controller;

import stracture.football.dto.TeamCreateRequestDto;
import stracture.football.dto.TeamResponseDto;
import stracture.football.dto.TeamUpdateRequestDto;
import stracture.football.dto.mapper.TeamMapper;
import stracture.football.model.Team;
import stracture.football.service.TeamService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamMapper teamMapper;
    private final TeamService teamService;

    public TeamController(TeamMapper teamMapper, TeamService teamService) {
        this.teamMapper = teamMapper;
        this.teamService = teamService;
    }

    @PostMapping
    public TeamResponseDto save(@RequestBody TeamCreateRequestDto dto) {
        Team team = teamMapper.toModelWhenCreate(dto);
        return teamMapper.toDto(teamService.save(team));
    }

    @PutMapping("/{id}")
    public TeamResponseDto update(@RequestBody TeamUpdateRequestDto dto,
                                  @PathVariable Long id) {
        Team team = teamMapper.toModelWhenUpdate(dto);
        team.setId(id);
        return teamMapper.toDtoWhenUpdate(teamService.update(team));
    }

    @GetMapping("/get/{id}")
    public TeamResponseDto get(@PathVariable Long id) {
        return teamMapper.toDto(teamService.get(id));
    }

    @GetMapping
    public List<TeamResponseDto> getAll() {
        return teamService.getAll().stream()
                .map(teamMapper::toDto)
                .toList();
    }

    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable Long id) {
        return teamService.delete(id);
    }
}
