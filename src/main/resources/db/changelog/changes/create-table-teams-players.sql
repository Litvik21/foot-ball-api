--liquibase formatted sql
--changeset <litvik>:<create-table-teams-players>
CREATE TABLE teams_players (
                               team_id BIGINT NOT NULL,
                               player_id BIGINT NOT NULL,
                               CONSTRAINT PK_teams_players PRIMARY KEY (team_id, player_id),
                               CONSTRAINT FK_teams_players_teams FOREIGN KEY (team_id) REFERENCES teams(id),
                               CONSTRAINT FK_teams_players_players FOREIGN KEY (player_id) REFERENCES players(id)
);
--rollback DROP TABLE teams-players;