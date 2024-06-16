package fr.isencaen.gameplatform.service;
import fr.isencaen.gameplatform.exceptions.AccountFunctionalException;
import fr.isencaen.gameplatform.exceptions.GamesFunctionalException;
import fr.isencaen.gameplatform.models.*;
import fr.isencaen.gameplatform.models.dto.*;
import fr.isencaen.gameplatform.repositories.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private static final String GAME_NOT_FOUND = "GAME_NOT_FOUND";
    private static final String GAME_NOT_FOUND_MESSAGE = "The game doesn't exist";
    private final CurrentGameRepository currentGameRepository;
    private final AccountRepository accountRepository;
    private final AccountPlayerPositionRepository accountPlayerPositionRepository;


    public GameService(GameRepository gameRepository, CurrentGameRepository currentGameRepository, AccountRepository accountRepository, AccountPlayerPositionRepository accountPlayerPositionRepository) {
        this.gameRepository = gameRepository;
        this.currentGameRepository = currentGameRepository;
        this.accountRepository = accountRepository;
        this.accountPlayerPositionRepository = accountPlayerPositionRepository;

    }

    public CurrentGameDto invite(InviteDto inviteDto, int idCurrentGame) throws GamesFunctionalException {
        return this.addPlayer(idCurrentGame, inviteDto.pseudo());
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll().stream().toList();
    }
    public CurrentGameDto createGame(String gameName, String accountToken) throws GamesFunctionalException {
        //Vérifie que je jeu existe
        return this.gameRepository.findByName(gameName).stream().findFirst().map(game -> {
                    //Recuperation of the player
                    Account accountP1 = accountRepository.findByToken(accountToken);//récupère le compte du joueur
                    //Creation of the game
                    CurrentGame currentGame = new CurrentGame(game, new ArrayList<>());
                    currentGame = this.currentGameRepository.save(currentGame);
                    //Adding player/game time/moves to the game
                    AccountPosition accountPosition = new AccountPosition(accountP1, new ArrayList<>(), 0, currentGame);
                    accountPosition = this.accountPlayerPositionRepository.save(accountPosition);
                    currentGame.getAccountPlayersPositions().add(accountPosition);

                    CurrentGame currentGamecreated = currentGameRepository.save(currentGame);//create the game in the database
                    return getGameInfos(currentGamecreated);
                })
                .orElseThrow(() -> new GamesFunctionalException(GAME_NOT_FOUND_MESSAGE, GAME_NOT_FOUND));
    }
    public CurrentGameDto addPlayer(int idCurrentGame, String playerPseudo) throws GamesFunctionalException {
        return this.currentGameRepository.findById(idCurrentGame).map(currentGame -> {
            try {
                if (!accountRepository.existsByPseudo(playerPseudo)) {
                    throw new AccountFunctionalException(GAME_NOT_FOUND_MESSAGE, "PLAYER_NOT_FOUND");
                }else {
                    Account accountP2 = accountRepository.findByPseudo(playerPseudo);
                    List<AccountPosition> positionsPlayers = currentGame.getAccountPlayersPositions();
                    positionsPlayers.add(new AccountPosition(accountP2, new ArrayList<>(), 0, currentGame));//ajoute le joueur à la partie

                    CurrentGame gameModified = currentGameRepository.save(currentGame);
                    return getGameInfos(gameModified);
                }
            } catch (AccountFunctionalException e) {
                throw new IllegalArgumentException("Player doesn't exist");
            }
        }).orElseThrow(() -> new GamesFunctionalException(GAME_NOT_FOUND_MESSAGE, GAME_NOT_FOUND));
    }
    public CurrentGameDto getGameInfos(String id) throws GamesFunctionalException {
        Optional<CurrentGame> currentGame = currentGameRepository.findById(Integer.parseInt(id));
        if (currentGame.isPresent()) {
            return new CurrentGameDto(currentGame.get());
        } else {
            throw new GamesFunctionalException(GAME_NOT_FOUND_MESSAGE, GAME_NOT_FOUND);
        }
    }
    public CurrentGameDto getGameInfos(CurrentGame currentGamecreated) {
        List<AccountPositionDto> accountspositionsdto = new ArrayList<>();
        for (AccountPosition accountPosition : currentGamecreated.getAccountPlayersPositions()) {
            AccountPositionDto accountpositiondto = new AccountPositionDto(accountPosition);
            accountspositionsdto.add(accountpositiondto);
        }
        return new CurrentGameDto(currentGamecreated.getIdCurrentGame(), accountspositionsdto);
    }

    public List<MyGameInfoDto> getMyGames(String userToken) throws AccountFunctionalException {
        Account account = accountRepository.findByToken(userToken);
                //.orElseThrow( () -> new AccountFunctionalException("The player doesn't exist", "PLAYER_NOT_FOUND"));
        return currentGameRepository.findByAccount(account)
                .stream()
                .map(MyGameInfoDto::of)
                .toList();
    }

    public Map<Integer, Map<String, CellInfoDto>> getCellsInRow(Integer idCurrentGame) {
        CurrentGame currentGame = currentGameRepository.getReferenceById(idCurrentGame);
        List<AccountPosition> accountPositions = currentGame.getAccountPlayersPositions();
        //Récupération des cases dans l'ordre dans
        Map<Integer, Map<String, CellInfoDto>> rowInfoDtos = new HashMap<>();


        accountPositions.forEach(accountPosition -> accountPosition.getPositionByPieces().forEach(positionPiece -> {
            if (!rowInfoDtos.containsKey(Integer.parseInt(positionPiece.getPosition().substring(1, 2)))) {
                rowInfoDtos.put(Integer.parseInt(positionPiece.getPosition().substring(1, 2)), new HashMap<>());
            }
        }));
        accountPositions.forEach(accountPosition ->
                //Get the cells with their pieces in the form, the empty cells do not exist
                rowInfoDtos.keySet().forEach(key -> {
                    Map<String, CellInfoDto> cells = new HashMap<>();
                    accountPosition.getPositionByPieces().forEach(positionPiece -> {
                        if (positionPiece.getPosition().substring(1, 2).equals(String.valueOf(key))) {
                            cells.put(positionPiece.getPosition().substring(0, 1), new CellInfoDto(positionPiece.getPiece(), accountPosition.getPlayer().getUserId(), "undefined"));
                        }
                    });
                    rowInfoDtos.get(key).putAll(cells);
                })
        );


        //Rajout sale des lignes / colonnes non existantes
        for (int i = 1; i <= 3; i++) {
            rowInfoDtos.computeIfAbsent(i, k -> new HashMap<>());

            Map<String, CellInfoDto> currentRow = rowInfoDtos.get(i);
            for (char j = 'a'; j <= 'c'; j++) {
                String column = String.valueOf(j);
                currentRow.putIfAbsent(column, new CellInfoDto("empty", -1, "undefined"));
            }
        }
        //call to Drools to fill empty case with using id of the player
        return rowInfoDtos;
    }

    public MoveDto addMove() {
        return new MoveDto(true, 1, "cross", 2, 3);
    }
}
