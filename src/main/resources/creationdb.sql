--        Script Postgresql
------------------------------------------------------------
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;



------------------------------------------------------------
-- Table: Utilisateur
------------------------------------------------------------
CREATE TABLE public.Utilisateur(
                                   user_id             SERIAL NOT NULL ,
                                   pseudo              VARCHAR (20) NOT NULL ,
                                   mail                VARCHAR (50) NOT NULL ,
                                   passwdhash          VARCHAR (100) NOT NULL ,
                                   nom                 VARCHAR (50) NOT NULL ,
                                   section             VARCHAR (50) NOT NULL  ,
                                   token               VARCHAR (200) NOT NULL  ,
                                   role                VARCHAR (50) NOT NULL  ,
                                   CONSTRAINT Utilisateur_PK PRIMARY KEY (user_id)
)WITHOUT OIDS;

CREATE TABLE public.RefreshToken(
				id             SERIAL NOT NULL ,
                                token              VARCHAR (200) NOT NULL ,
                                expiryDate         TIMESTAMP NOT NULL,
                                user_id            INT NOT NULL ,

                                   CONSTRAINT RefreshToken_PK PRIMARY KEY (id), CONSTRAINT RefreshToken_Utilisateur_FK FOREIGN KEY (user_id) REFERENCES public.Utilisateur(user_id)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: Jeux
------------------------------------------------------------
CREATE TABLE public.Jeux(
                            id_game   SERIAL NOT NULL ,
                            nom       VARCHAR (50) NOT NULL ,
                            image     VARCHAR (50) NOT NULL ,
                            regles    VARCHAR (50) NOT NULL  ,
                            CONSTRAINT Jeux_PK PRIMARY KEY (Id_game)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: Partie en cours
------------------------------------------------------------
CREATE TABLE public.CurrentGames(
                                    id_current_game        SERIAL NOT NULL ,
                                    id_game                INT  NOT NULL,
    /* tableau des positions sont relié à la table par l'id current game*/
                                    CONSTRAINT Partie_en_cours_PK PRIMARY KEY (id_current_game)

    ,CONSTRAINT CurrentGames_Game_FK FOREIGN KEY (id_game) REFERENCES public.Jeux(Id_game)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: accountplayertimeposition
------------------------------------------------------------
CREATE TABLE public.AccountPlayerTimePositions(
                                     id    SERIAL  NOT NULL ,
                                     player_id    INT,
/*position relié par l'id accountplayer dans la table positions*/
                                     time   INT,
                                     current_game_id INT,
                                     CONSTRAINT AccountPlayerTimePositions_PK PRIMARY KEY (id)

                                     ,CONSTRAINT AccountPlayerTimePositions_Player_FK FOREIGN KEY (player_id) REFERENCES public.Utilisateur(user_id)
                                    ,CONSTRAINT AccountPlayerTimePositions_CurrentGame_FK FOREIGN KEY (current_game_id) REFERENCES public.CurrentGames(id_current_game)

                                     )WITHOUT OIDS;





CREATE TABLE public.Positions
(
    id       SERIAL      NOT NULL,
    piece    VARCHAR(50) NOT NULL,
    position VARCHAR(50) NOT NULL,
    id_account_player_time_position INT NOT NULL,
    CONSTRAINT Positions_PK PRIMARY KEY (id),
    CONSTRAINT Positions_AccountPlayerTimePosition_FK FOREIGN KEY (id_account_player_time_position) REFERENCES public.AccountPlayerTimePositions(id)
)WITHOUT OIDS;
/* INSERTION */
INSERT INTO jeux (nom, image, regles) VALUES ('tictactoe', 'tictactoe', '3 symboles identiques et le jeu est gagné');
INSERT INTO Utilisateur(pseudo, mail, passwdhash, nom, section, token, role) VALUES ('clement2','clement.jaminion@isen-ouest.yncrea.fr','e4811e01d087d1e9cbefe34d46713cf157def8baf37128ea739c611597f054d5','"Clément Jaminio,"','M1','e01ffe7da3d2d9845355dc0b944f461c17083daf144473feb3b4ad7deeb55e5b','ROLE_USER');
INSERT INTO Utilisateur(pseudo, mail, passwdhash, nom, section, token, role) VALUES ('clement','clement.jaminion@gmail.com','e4811e01d087d1e9cbefe34d46713cf157def8baf37128ea739c611597f054d5','"test"','M1','e01ffe7da3d2d9845355dc0b944f461c17083daf144473feb3b4ad7deeb55e5c','ROLE_USER');

/*INSERTION of a current game of tictactoe */
INSERT INTO CurrentGames(id_game) VALUES (1);
INSERT INTO AccountPlayerTimePositions(player_id, time, current_game_id) VALUES (1, 28, 1);
INSERT INTO AccountPlayerTimePositions(player_id, time, current_game_id) VALUES (2, 25, 1);
INSERT INTO Positions(piece, position, id_account_player_time_position) VALUES ('cross', 'a1', 1);
INSERT INTO Positions(piece, position, id_account_player_time_position) VALUES ('cross', 'b2', 1);
INSERT INTO Positions(piece, position, id_account_player_time_position) VALUES ('cross', 'a2', 1);
INSERT INTO Positions(piece, position, id_account_player_time_position) VALUES ('round', 'c1', 2);
INSERT INTO Positions(piece, position, id_account_player_time_position) VALUES ('round', 'b3', 2);
INSERT INTO Positions(piece, position, id_account_player_time_position) VALUES ('round', 'a3', 2);
