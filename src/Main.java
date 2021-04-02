import exceptions.*;
import gameutils.MafiaRoom;
import gameutils.Role;
import players.Player;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner stdin = new Scanner(System.in);
        MafiaRoom room = null;

        // Game loop
        while (stdin.hasNext()) {
            try {
                if (stdin.hasNext("(create_game)|(assign_role)|(start_game)|(get_game_state)")) {
                    String curToken = stdin.next();
                    if (curToken.equals("create_game")) {
                        stdin.skip(" ");
                        String[] names = stdin.nextLine().split(" ");
                        if (room != null) {
                            throw new GameAlreadyStartedException();
                        }
                        room = new MafiaRoom(names);
                    } else if (curToken.equals("assign_role")) {
                        try {
                            String playerName = stdin.next();
                            Role role = Role.valueOf(stdin.next().toUpperCase());
                            if (room == null) {
                                throw new NoRoomCreatedException();
                            }
                            room.assignRole(playerName, role);
                        } catch (IllegalArgumentException e) {
                            System.out.println("role not found");
                        }
                    } else if (curToken.equals("start_game")) {
                        if (room == null) {
                            throw new NoRoomCreatedException();
                        }
                        room.startGame();
                        room.startDay();
                    } else if (curToken.equals("get_game_state")) {
                        if (room == null) {
                            throw new NoRoomCreatedException();
                        }
                        room.printGameState();
                    }
                }

                if (room != null && room.gameStarted()) {
                    // Collect votes
                    if (!room.isNight()) {
                        // It's day
                        while (!stdin.hasNext("end_vote")) {
                            Player voter = room.getPlayerByName(stdin.next());
                            Player votee = room.getPlayerByName(stdin.next());

                            if (!voter.isSilenced()) {
                                voter.vote(votee);
                            } else {
                                throw new VoterSilencedException();
                            }
                        }
                        stdin.next();
                        room.processVotes();
                        room.resetPlayersState();
                        room.startNight();
                    } else {
                        // It's night
                        while (!stdin.hasNext("end_night")) {
                            Player actor = room.getPlayerByName(stdin.next());
                            Player target = room.getPlayerByName(stdin.next());

                            actor.playOn(target);
                        }
                        stdin.next();
                        room.processNightEvents();
                        room.startDay();
                    }

                    // Check if someone wins
                    if (room.isVillagersWinner()) {
                        System.out.println("Villagers won!");
                        break;
                    } else if (room.isMafiaWinner()) {
                        System.out.println("Mafia won!");
                        break;
                    }

                }
            } catch (GameAlreadyStartedException | GameNotStartedException | NoRoleException | IllegalStateException | NoRoomCreatedException | PlayerNotFoundException | VoterSilencedException | CannotWakeUpException | CannotPlayException e) {
                System.out.println(e.getMessage());
            } catch (JokerWonException e) {
                System.out.println("Joker won!");
                break;
            }
        }
    }
}
