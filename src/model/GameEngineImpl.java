package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import model.enumeration.BetType;
import model.enumeration.Color;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.Slot;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine {

    /** Collection of player, slot and gameEngineCallback **/
    private List<Player> playerList = new ArrayList<Player>();
    private List<Slot> slotList = new ArrayList<Slot>();
    private List<GameEngineCallback> gameEngCallbackList = new ArrayList<GameEngineCallback>();

    @Override
    public void spin(int initialDelay, int finalDelay, int delayIncrement) {
        int count = 0;

        /** Random the start location based on the wheel size **/
        Collections.rotate(slotList, (int) (Math.random() * Slot.WHEEL_SIZE));
        
        for(int x = 0; x < slotList.size(); x++) {
            try {
                Thread.sleep(initialDelay);

                for (GameEngineCallback currentGEC : gameEngCallbackList) {
                    currentGEC.nextSlot(slotList.get(x), this);
                    count++;
                }

                initialDelay += delayIncrement; // Increase the delay

                /** Check if delay >= finaldelay, interrupt if true **/
                if (initialDelay >= finalDelay) {
                    Thread.currentThread().interrupt();
                }
                
                slotList.add(slotList.get(x));
                
            } catch (InterruptedException e) {
                break; // When delay is interrupt, will break out from the loop
            }
        }
        
        for(GameEngineCallback currentGEC : gameEngCallbackList) {
            currentGEC.result(slotList.get(count), this); // The ball fall into the position is base on count
        }
    }

    @Override
    public void calculateResult(Slot winningSlot) {
        
        for (Player currentPlayer : this.playerList) {
            if(currentPlayer.getBet() > 0) {
                /** Loop through playerList and apply win and loss **/
                /** Based on the playerList bet type, it will call the method applyWinLoss then pass in the current looped player details **/
//                BetType.valueOf(currentPlayer.getBetType().toString()).applyWinLoss(currentPlayer, winningSlot);
                
                 currentPlayer.getBetType().applyWinLoss(currentPlayer, winningSlot);// << correct way?
            }
        }
    }

    @Override
    public void addPlayer(Player player) {
        /** Add player into the collection of player **/
        this.playerList.add(player);
    }

    @Override
    public Player getPlayer(String id) {
        Player player = null;
        
        for (Player currentPlayer : this.playerList) {
            /** Check is the ID of player match or not and return the correct player **/
            if (id.equals(currentPlayer.getPlayerId())) {
                player = currentPlayer;
            }
        }
        
        return player;
    }

    @Override
    public boolean removePlayer(Player player) {
        /** Return true of the player found **/
        return this.playerList.remove(player);
    }

    @Override
    public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
        /** Add a collection of GameEngineCallback **/
        this.gameEngCallbackList.add(gameEngineCallback);
    }

    @Override
    public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
        /** Remove the gameEngineCallback **/
        return this.gameEngCallbackList.remove(gameEngineCallback);
    }

    @Override
    public Collection<Player> getAllPlayers() {
        /** Return the collection of player **/
        return this.playerList;
    }

    @Override
    public boolean placeBet(Player player, int bet, BetType betType) {
        
//        /** Make sure the bet is larger than 0 and player have sufficient point to place the bet **/
//        if(bet > 0 && player.getPoints() >= bet) {
//            player.setBet(bet);
//            player.setBetType(betType);
//            return true;
//        }
        
        if(player.setBet(bet) && betType != null) {
            player.setBetType(betType);
            return true;
        }
        
        return false;
    }

    @Override
    public Collection<Slot> getWheelSlots() {
        Color color = null;

        /** Array of number for the roulette **/
        String[] number = { "00","27","10","25","29","12","8","19","31","18","6","21","33","16","4","23","35","14","2","0","28","9","26","30","11",
                "7","20","32","17","5","22","34","15","3","24","36","13","1" };

        /** For loop to determine the position colour and add into slot collection **/
        for (int position = 0; position < Slot.WHEEL_SIZE; position++) {
            
            if (number[position].equals("00")) {
                color = Color.GREEN00;
            } else if (number[position].equals("0")) {
                color = Color.GREEN0;
            } else if (position % 2 == 0) {
                color = Color.BLACK;
            } else {
                color = Color.RED;
            }

            slotList.add(new SlotImpl(position, color, Integer.parseInt(number[position])));
        }
        
        return slotList;
    }
}
