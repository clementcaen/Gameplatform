package fr.isencaen.Gameplatform.service;
import fr.isencaen.Gameplatform.exceptions.AccountFunctionalException;
import fr.isencaen.Gameplatform.exceptions.GamesFunctionalException;
import fr.isencaen.Gameplatform.models.*;
import fr.isencaen.Gameplatform.models.dto.*;
import fr.isencaen.Gameplatform.repositories.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final CurrentGameRepository currentGameRepository;
    private final AccountRepository accountRepository;
    private final AccountPlayerPositionRepository accountPlayerPositionRepository;
    //private final DroolsService droolsService;

    public GameService(GameRepository gameRepository, CurrentGameRepository currentGameRepository, AccountRepository accountRepository, AccountPlayerPositionRepository accountPlayerPositionRepository) {
        this.gameRepository = gameRepository;
        this.currentGameRepository = currentGameRepository;
        this.accountRepository = accountRepository;
        this.accountPlayerPositionRepository = accountPlayerPositionRepository;

    }

    public CurrentGameDto invite(InviteDto inviteDto, int id_current_game) throws AccountFunctionalException, GamesFunctionalException {
        return this.addPlayer(id_current_game, inviteDto.pseudo());
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll().stream().toList();
    }
    public CurrentGameDto createGame(String game_name, String account_token) throws GamesFunctionalException {
        //Vérifie que je jeu existe
        return this.gameRepository.findByName(game_name).stream().findFirst().map(game -> {
            //Récupération du joueur
            Account account_p1 = accountRepository.findByToken(account_token);//récupère le compte du joueur
            System.out.println(account_p1);
            //Création de la partie
            CurrentGame currentGame = new CurrentGame(game, new ArrayList<>());
            currentGame = this.currentGameRepository.save(currentGame);
            //Rajout du joueur/temps de jeu/coups à la partie
            AccountPosition accountPosition = new AccountPosition(account_p1, new ArrayList<>(), 0, currentGame);
            accountPosition = this.accountPlayerPositionRepository.save(accountPosition);
            currentGame.getAccount_players_positions().add(accountPosition);
            accountPosition = this.accountPlayerPositionRepository.save(accountPosition);
            //List<AccountPosition> accountPositions = List.of(accountPosition);
            //List<PositionsObject> positionsObjects = List.of(new PositionsObject(, ,));//liste vide au début : pas de liaison

            System.out.println(accountPosition);


            CurrentGame currentGamecreated = currentGameRepository.save(currentGame);//créer le jeu dans la base de données
            return getGameInfos(currentGamecreated);
        })
        .orElseThrow(() -> new GamesFunctionalException("Le jeu n'existe pas", "GAME_NOT_FOUND"));
    }
    public CurrentGameDto addPlayer(int id_current_game, String player_pseudo) throws GamesFunctionalException {
        return this.currentGameRepository.findById(id_current_game).map(current_game -> {
            try {
                if (!accountRepository.existsByPseudo(player_pseudo)) {
                    throw new AccountFunctionalException("Le joueur n'existe pas", "PLAYER_NOT_FOUND");
                }else {
                    System.out.println(accountRepository.findByPseudo(player_pseudo));
                    Account account_p2 = accountRepository.findByPseudo(player_pseudo);
                    List<AccountPosition> positionsPlayers = current_game.getAccount_players_positions();
                    positionsPlayers.add(new AccountPosition(account_p2, new ArrayList<>(), 0, current_game));//ajoute le joueur à la partie

                    CurrentGame gameModified = currentGameRepository.save(current_game);
                    return getGameInfos(gameModified);
                }
            } catch (AccountFunctionalException e) {
                throw new RuntimeException(e);
            }
        }).orElseThrow(() -> new GamesFunctionalException("La partie n'existe pas", "GAME_NOT_FOUND"));
    }
    public CurrentGameDto getGameInfos(String id) throws GamesFunctionalException {
        Optional<CurrentGame> currentGame = currentGameRepository.findById(Integer.parseInt(id));
        if (currentGame.isPresent()) {
            return new CurrentGameDto(currentGame.get());
        } else {
            throw new GamesFunctionalException("La partie n'existe pas", "GAME_NOT_FOUND");
        }
    }
    public CurrentGameDto getGameInfos(CurrentGame currentGamecreated) {
        List<AccountPositionDto> accountspositionsdto = new ArrayList<AccountPositionDto>();
        for (AccountPosition accountPosition : currentGamecreated.getAccount_players_positions()) {
            AccountPositionDto accountpositiondto = new AccountPositionDto(accountPosition);
            accountspositionsdto.add(accountpositiondto);
        }
        return new CurrentGameDto(currentGamecreated.getId_current_game(), accountspositionsdto);
    }

    public List<myGameInfoDto> getMyGames(String idUser) throws AccountFunctionalException {
        Account account = accountRepository.findById(Integer.parseInt(idUser))
                .orElseThrow( () -> new AccountFunctionalException("Le joueur n'existe pas", "PLAYER_NOT_FOUND"));
        return currentGameRepository.findByAccount(account)
                .stream()
                .map(myGameInfoDto::of)
                .toList();
    }

    public Map<Integer, Map<String, CellInfoDto>> getCellsInRow(Integer idCurrentGame, int player_id) {
        CurrentGame currentGame = currentGameRepository.getReferenceById(idCurrentGame);
        List<AccountPosition> accountPositions = currentGame.getAccount_players_positions();
        //Récupération des cases dans l'ordre dans
        Map<Integer, Map<String, CellInfoDto>> rowInfoDtos = new HashMap<>();


        accountPositions.forEach(accountPosition -> {
            System.out.println("Id accountPosition : " + accountPosition.getId() + " Id player : " + accountPosition.getPlayer().getId());
                    accountPosition.getPositionByPieces().forEach(positionPiece -> {
                        System.out.println("id_positon object" + positionPiece.getId() + " position : " + positionPiece.getPosition() + " piece : " + positionPiece.getPiece());
                        if (!rowInfoDtos.containsKey(Integer.parseInt(positionPiece.getPosition().substring(1, 2)))) {
                            rowInfoDtos.put(Integer.parseInt(positionPiece.getPosition().substring(1, 2)), new HashMap<>());
                        }
                    });
        });
        accountPositions.forEach(accountPosition -> {
            //Récupération des cases avec leur pièce sous la forme, les cases vides n'existe pas
            /*"{ 1:
                    { "a":
                        {   String path_img
                            int id_user
                            String status_case
                        }
                    }
                }
             */

            //Map<String, CellInfoDto> cellsInfosDto = new HashMap<>();

            rowInfoDtos.keySet().forEach(key -> {
                Map<String, CellInfoDto> cells = new HashMap<>();
                accountPosition.getPositionByPieces().forEach(positionPiece -> {
                    if (positionPiece.getPosition().substring(1, 2).equals(String.valueOf(key))) {
                        cells.put(positionPiece.getPosition().substring(0, 1), new CellInfoDto(positionPiece.getPiece(), accountPosition.getPlayer().getId(), "undefined"));
                    }
                });
                rowInfoDtos.get(key).putAll(cells);
            });

        });


        //Rajout sale des lignes / colonnes non existantes
        for (int i = 1; i <= 3; i++) {
            if (!rowInfoDtos.containsKey(i)) {
                rowInfoDtos.put(i, new HashMap<>());
            }
            Map<String, CellInfoDto> currentRow = rowInfoDtos.get(i);
            for (char j = 'a'; j <= 'c'; j++) {
                String column = String.valueOf(j);
                if (!currentRow.containsKey(column)) {
                    currentRow.put(column, new CellInfoDto("empty", -1, "undefined"));
                }
            }
        }
        for (int i = 1; i < 4; i++) {
            for (char j = 'a'; j < 'd'; j++) {
                System.out.print(" " + rowInfoDtos.get(i).get(String.valueOf(j))+" | ");
            }
            System.out.println();
        }
        //Appel à Drools pour remplir les cases possibles en utilisant l'id du joueur
        /*for (int i = 1; i <= 3; i++) {
            List<CellInfoDto> cells = new ArrayList<>(rowInfoDtos.get(i).values());
            List<CellInfoDto> suggestedCase = droolsService.suggestCase(cells, player_id);
            rowInfoDtos.get(i).values().forEach(cellInfoDto -> {
                if (suggestedCase.contains(cellInfoDto)) {
                    cellInfoDto.setStatus_case("suggested");
                }
            });
        }*/
        return rowInfoDtos;
    }

    public MoveDto addMove(int idGame, CreateMoveDto createMoveDto) throws GamesFunctionalException {
        //CurrentGame currentGame = currentGameRepository.getReferenceById(idGame);
        //Account account = accountRepository.findByToken(createMoveDto.token_user());
        //AccountPosition accountPosition = accountPlayerPositionRepository.findByCurrent_gameAndPlayer(currentGame, account);
        //vérification à faire avec Drools TODO
        //PositionsObject positionsObject = new PositionsObject("round", createMoveDto.move(), accountPosition);
        //accountPosition.setPositionByPieces(accountPosition.getPositionByPieces().add(positionsObject));
        //AccountPosition account_position = accountPlayerPositionRepository.save(accountPosition);
        return new MoveDto(true, 1, "cross", 2, 3);
    }
}
