package model;

import model.enumeration.BetType;
import model.interfaces.Player;

public class SimplePlayer implements Player {

    private String playerId;
    private String playerName;
    private int initialPoints;
    private int betAmount;
    private BetType betType;

    /** Constructor for SimplayePlayer class **/
    public SimplePlayer(String playerId, String playerName, int initialPoints) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.initialPoints = initialPoints;
    }

    @Override
    public String getPlayerName() {
        return this.playerName; // Return the player name
    }

    @Override
    public void setPlayerName(String playerName) {
        this.playerName = playerName; // Set the player name
    }

    @Override
    public int getPoints() {
        return this.initialPoints; // Return the player points
    }

    @Override
    public void setPoints(int points) {
        this.initialPoints = points; // Update the player points 
    }

    @Override
    public String getPlayerId() {
        return this.playerId; // Return the player id
    }

    @Override
    public boolean setBet(int bet) {

        /** Verify the bet amount and ensure the player have sufficient point to place bet **/
        if (bet > 0 && (this.initialPoints - bet >= 0)) {
            this.betAmount = bet; // Set the bet amount if enough points
            return true;
        }

        resetBet();
        return false; // Return false if player have not enough points to place bet
    }

    @Override
    public int getBet() {
        return this.betAmount; // Return the player bet amount
    }

    @Override
    public void setBetType(BetType betType) {
        this.betType = betType; // Set the player bet amount
    }

    @Override
    public BetType getBetType() {
        return this.betType; // Return the player bet type
    }

    @Override
    public void resetBet() {
        this.betType = null;
        this.betAmount = 0; // Reset the player bet to 0
    }

    @Override
    public String toString() {
        /** Format string to "/" for bet amount and bet type if bet amount is 0 to indicate that player did not place any bet for the game **/
        return String.format("Player: id=%s, name=%s, bet=%s, betType=%s, points=%d", this.playerId, this.playerName,
                this.getBet() > 0? this.getBet() : "/", this.getBet() > 0? this.getBetType() : "/", this.initialPoints);
    }
}
