import exceptions.*;
import gameutils.MafiaRoom;
import gameutils.Role;
import players.BulletProof;
import players.Joker;
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
                        String line = stdin.nextLine().trim();
                        if (line.equals("")) {
                            throw new IllegalArgumentException("No names specified.");
                        }
                        String[] names = line.split(" ");
                        if (room != null) {
                            throw new GameAlreadyStartedException();
                        }
                        room = new MafiaRoom(names);
                        System.out.println("Room created with " + names.length + (names.length == 1 ? "player" : "players") + ".");
                    } else if (curToken.equals("assign_role")) {
                        try {
                            String playerName = stdin.next();
                            Role role = Role.valueOf(stdin.next().toUpperCase());
                            if (room == null) {
                                throw new NoRoomCreatedException();
                            }
                            room.assignRole(playerName, role);
                            System.out.println(role.name() + " role assigned to " + playerName + ".");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Role not found.");
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
                        if (!room.gameStarted()) {
                            throw new GameNotStartedException();
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
                    if (room.isJokerWinner()) {
                        System.out.println("Joker won!");
                        break;
                    }
                    if (room.isVillagersWinner()) {
                        System.out.println("Villagers won!");
                        break;
                    } else if (room.isMafiaWinner()) {
                        System.out.println("Mafia won!");
                        break;
                    }

                }
            } catch (MafiaException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
