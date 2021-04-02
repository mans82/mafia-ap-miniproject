package gameutils;

import exceptions.GameAlreadyStartedException;
import exceptions.GameNotStartedException;
import exceptions.NoRoleException;
import exceptions.PlayerNotFoundException;
import players.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MafiaRoom {

    private int dayNum = 0;
    private boolean isNight = true;
    private HashMap<String, Player> players = new HashMap<>();
    private boolean jokerWins = false;

    public MafiaRoom(String[] names) {
        for (String name : names) {
            players.put(name, null);
        }
    }

    public boolean gameStarted() {
        return this.dayNum > 0;
    }

    public boolean isNight() {
        return this.isNight;
    }

    public void assignRole(String name, Role role) throws PlayerNotFoundException {

        if (!this.players.containsKey(name)) {
            throw new PlayerNotFoundException();
        }

        Player playerObject = null;
        switch (role) {
            case VILLAGER:
                playerObject = new Villager(name);
                break;
            case MAFIA:
                playerObject = new Mafia(name);
                break;
            case GODFATHER:
                playerObject = new GodFather(name);
                break;
            case SILENCER:
                playerObject = new Silencer(name);
                break;
            case JOKER:
                playerObject = new Joker(name);
                break;
            case DOCTOR:
                playerObject = new Doctor(name);
                break;
            case BULLETPROOF:
                playerObject = new BulletProof(name);
                break;
            case DETECTIVE:
                playerObject = new Detective(name);
                break;
        }

        this.players.put(name, playerObject);
    }

    public void startGame() throws GameAlreadyStartedException, NoRoleException {
        if (this.gameStarted()) {
            throw new GameAlreadyStartedException();
        }

        if (this.players.containsValue(null)) {
            throw new NoRoleException();
        }

        for (String name : this.players.keySet()) {
            Player curPlayer = this.players.get(name);

            System.out.println(name + ": " + curPlayer.getRoleName());
        }
        this.dayNum = 1;

        System.out.println();
        System.out.println("Ready? Set... GO!");

    }

    public void startDay() throws IllegalStateException, GameNotStartedException {
        if (!this.gameStarted()) {
            throw new GameNotStartedException();
        }
        if (!this.isNight) {
            throw new IllegalStateException("it is already day");
        }
        this.isNight = false;
        System.out.println("Day " + this.dayNum);
    }

    public void startNight() throws IllegalStateException, GameNotStartedException {
        if (!this.gameStarted()) {
            throw new GameNotStartedException();
        }
        if (this.isNight) {
            throw new IllegalStateException("it is already night");
        }
        this.isNight = true;
        System.out.println("Night " + this.dayNum++);
    }

    public Player getPlayerByName(String name) throws PlayerNotFoundException {
        Player result = this.players.get(name);
        if (result == null) {
            throw new PlayerNotFoundException();
        }

        return result;
    }

    private int getMaxVotes() {
        int max = 0;
        for (Player curPlayer : players.values()) {
            if (curPlayer.getVoteCount() > max) {
                max = curPlayer.getVoteCount();
            }
        }

        return max;
    }

    private Player[] getMaxVotedPlayers() {
        ArrayList<Player> votedPlayers = new ArrayList<>();
        int maxVotes = this.getMaxVotes();
        if (maxVotes == 0) {
            return new Player[0];
        }
        for (Player curPlayer : players.values()) {
            if (curPlayer.getVoteCount() == maxVotes) {
                votedPlayers.add(curPlayer);
            }
        }

        Player[] result = new Player[votedPlayers.size()];
        votedPlayers.toArray(result);
        return result;
    }

    private Doctor getDoctor() {
        for (Player curPlayer : players.values()) {
            if (curPlayer instanceof Doctor) {
                return (Doctor) curPlayer;
            }
        }

        return null;
    }

    private Silencer getSilencer() {
        for (Player curPlayer : players.values()) {
            if (curPlayer instanceof Silencer) {
                return (Silencer) curPlayer;
            }
        }

        return null;
    }

    public void processVotes() {
        Player[] votedPlayers = this.getMaxVotedPlayers();
        if (votedPlayers.length != 1) {
            System.out.println("nobody died");
        } else {
            System.out.println(votedPlayers[0].getName() + " died");
            if (votedPlayers[0] instanceof Joker) {
                this.jokerWins = true;
            } else {
                votedPlayers[0].die();
            }
        }
    }
    
    public void printWakingUpAlivePlayers() {
        for (Player curPlayer : players.values()) {
            if (!curPlayer.isDead() && (
                    curPlayer instanceof Mafia
                    || curPlayer instanceof Doctor
                    || curPlayer instanceof Detective
                )
            ) {
                System.out.println(curPlayer.getName() + ": " + curPlayer.getRoleName());
            }
        }
    }

    public void resetPlayersState() {
        for (Player curPlayer : players.values()) {
            curPlayer.resetState();
        }
    }

    private int getMafiaVotesCountOfPlayer(Player player) {
        int result = 0;
        for (Player curPlayer : players.values()) {
            if (curPlayer instanceof Mafia) {
                Mafia curMafia = (Mafia) curPlayer;
                if (curMafia.getPlayerVotedToKill() == player) {
                    result++;
                }
            }
        }

        return result;
    }

    private int getMaxMafiaVotes() {
        int max = 0;
        for (Player curPlayer : players.values()) {
            int mafiaVotes = this.getMafiaVotesCountOfPlayer(curPlayer);
            if (mafiaVotes > max) {
                max = mafiaVotes;
            }
        }

        return max;
    }

    private Player[] getMaxMafiaVotedAlivePlayers() {
        int maxMafiaVotes = this.getMaxMafiaVotes();
        if (maxMafiaVotes == 0) {
            return new Player[0];
        }
        ArrayList<Player> resultList = new ArrayList<>();
        for (Player curPlayer : players.values()) {
            if (this.getMafiaVotesCountOfPlayer(curPlayer) == maxMafiaVotes) {
                resultList.add(curPlayer);
            }
        }

        Player[] result = new Player[resultList.size()];
        resultList.toArray(result);
        return result;
    }

    public void processNightEvents() throws IllegalStateException{
        if (!this.isNight) {
            throw new IllegalStateException("It is currently day, not night");
        }
        // Mafia kills
        Player[] mafiaTargets = this.getMaxMafiaVotedAlivePlayers();
        Player deadPlayer = null;
        Doctor gameDoctor = this.getDoctor();
        for (Player mafiaTarget : mafiaTargets) {
            System.out.println("mafia tried to kill " + mafiaTarget.getName());
        }
        if (mafiaTargets.length == 0 || mafiaTargets.length >= 3 || (mafiaTargets.length == 2 && gameDoctor == null)) {
            System.out.println("nobody died");
        } else if (mafiaTargets.length == 2) {
            if (mafiaTargets[0] == gameDoctor.getSavedPlayer()) {
                deadPlayer = mafiaTargets[1];
            } else if (mafiaTargets[1] == gameDoctor.getSavedPlayer()) {
                deadPlayer = mafiaTargets[0];
            }
        } else {// mafiaTargets.length == 1
            if (gameDoctor == null || !gameDoctor.getSavedPlayer().equals(mafiaTargets[0])) {
                deadPlayer = mafiaTargets[0];
            }
        }

        if (deadPlayer != null) {
            if (deadPlayer instanceof BulletProof && !((BulletProof) deadPlayer).usedExtraHealth()) {
                ((BulletProof) deadPlayer).useExtraHealth();
            } else {
                deadPlayer.die();
                System.out.println(deadPlayer.getName() + " was killed");
            }
        }

        // Silencer events
        Silencer gameSilencer = this.getSilencer();
        if (gameSilencer != null && gameSilencer.getSilencedPlayer() != null) {
            System.out.println("Silenced " + gameSilencer.getSilencedPlayer().getName());
        }
    }

    private int getAlivePlayerCountByRoleName(String name) {
        int result = 0;
        for (Player curPlayer : players.values()) {
            if (!curPlayer.isDead() && curPlayer.getRoleName().equals(name)) {
                result++;
            }
        }

        return result;
    }

    public void printGameState() {
        String[] roles = {
                "BulletProof",
                "Detective",
                "Doctor",
                "GodFather",
                "Joker",
                "Mafia",
                "Silencer",
                "Villager",
        };

        for (String role : roles) {
            int count = this.getAlivePlayerCountByRoleName(role);
            if (count > 0) {
                System.out.println(role + " = " + count);
            }
        }
    }

    private int getRolesCount(String[] roles) {

        int result = 0;

        for (String role : roles) {
            result += this.getAlivePlayerCountByRoleName(role);
        }

        return result;
    }

    private int getTotalAliveCivilianCount() {
        String[] civilianRoles = {
                "BulletProof",
                "Detective",
                "Doctor",
                "Villager",
        };

        return this.getRolesCount(civilianRoles);
    }

    private int getTotalMafiaCount() {
        String[] mafiaRoles = {
                "Mafia",
                "GodFather",
                "Silencer",
        };

        return this.getRolesCount(mafiaRoles);
    }

    public boolean isMafiaWinner() {
        return this.getTotalMafiaCount() >= this.getTotalAliveCivilianCount();
    }

    public boolean isVillagersWinner() {
        return this.getTotalMafiaCount() == 0;
    }

    public boolean isJokerWinner() {
        return this.jokerWins;
    }

}
