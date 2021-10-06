create table leagues
(
    id         serial not null primary key,
    name       text   not null unique
);

create table teams
(
    id          serial  not null primary key,
    name        text    not null,
    league_id  bigint not null references leagues
);

create table players
(
    id         serial  not null primary key,
    name       text   not null unique,
    team_id   bigint not null references teams
);

INSERT INTO leagues (name) VALUES ('league1');
INSERT INTO leagues (name) VALUES ('league2');
INSERT INTO leagues (name) VALUES ('league3');

INSERT INTO teams (name, league_id) VALUES ('team1', 1);
INSERT INTO teams (name, league_id) VALUES ('team2', 2);
INSERT INTO teams (name, league_id) VALUES ('team3', 2);
INSERT INTO teams (name, league_id) VALUES ('team4', 3);

INSERT INTO players (name, team_id) VALUES ('player1', 1);
INSERT INTO players (name, team_id) VALUES ('player2', 1);
INSERT INTO players (name, team_id) VALUES ('player3', 1);
INSERT INTO players (name, team_id) VALUES ('player4', 1);
INSERT INTO players (name, team_id) VALUES ('player5', 2);
INSERT INTO players (name, team_id) VALUES ('player6', 2);
INSERT INTO players (name, team_id) VALUES ('player7', 3);
INSERT INTO players (name, team_id) VALUES ('player8', 3);
INSERT INTO players (name, team_id) VALUES ('player9', 3);
INSERT INTO players (name, team_id) VALUES ('player10', 4);
INSERT INTO players (name, team_id) VALUES ('player11', 4);
INSERT INTO players (name, team_id) VALUES ('player12', 4);