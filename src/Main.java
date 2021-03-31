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
                String curToken = stdin.next();
                if (curToken.equals("create_game")) {
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

                if (room != null && room.gameStarted()) {
                    // Collect votes
                    if (!room.isNight()) {
                        // It's day
                        while (!stdin.hasNext("end_vote")) {
                            Player voter = room.getPlayerByName(stdin.next());
                            Player votee = room.getPlayerByName(stdin.next());

                            voter.vote(votee);
                        }
                        stdin.skip("end_vote");
                        room.processVotes();
                        room.startNight();
                    } else {
                        // It's night
                        while (!stdin.hasNext("end_night")) {
                            Player actor = room.getPlayerByName(stdin.next());
                            Player target = room.getPlayerByName(stdin.next());

                            actor.playOn(target);
                        }
                        stdin.skip("end_night");
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
            } catch (GameAlreadyStartedException | NoRoleException | IllegalStateException | NoRoomCreatedException | PlayerNotFoundException | CannotWakeUpException | CannotPlayException e) {
                System.out.println(e.getMessage());
            } catch (JokerWonException e) {
                System.out.println("Joker won!");
                break;
            }
        }
    }
}
