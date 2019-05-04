package view;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.Slot;
import view.interfaces.GameEngineCallback;

/**
 * 
 * Skeleton/Partial example implementation of GameEngineCallback showing Java
 * logging behaviour
 * 
 * @author Caspar Ryan
 * @see view.interfaces.GameEngineCallback
 * 
 */
public class GameEngineCallbackImpl implements GameEngineCallback {
    private static final Logger logger = Logger.getLogger(GameEngineCallback.class.getName());

    public GameEngineCallbackImpl() {
        // FINE shows wheel spinning output, INFO only shows result
        logger.setLevel(Level.FINE);
    }

    @Override
    public void nextSlot(Slot slot, GameEngine engine) {
        // intermediate results logged at Level.FINE
        logger.log(Level.FINE, String.format("Next slot: Position: %d, Color: %s, Number: %d", slot.getPosition(), slot.getColor().toString().charAt(0) + slot.getColor().toString().substring(1).toLowerCase(), slot.getNumber()));
    }

    @Override
    public void result(Slot result, GameEngine engine) {
        logger.log(Level.INFO, String.format("RESULT=Position: %d, Color: %s, Number: %d\n", result.getPosition(), result.getColor().toString().charAt(0) + result.getColor().toString().substring(1).toLowerCase(), result.getNumber()));

        /** Calculate the result of bet **/
        engine.calculateResult(result); 

        logger.log(Level.INFO, String.format("FINAL PLAYER POINT BALANCES"));

        StringBuilder message = new StringBuilder();

        /** Loop through the player collection and format it with String,format **/
        for (Player ply : engine.getAllPlayers()) {
            message.append(String.format("\n%s", ply.toString()));
        }

        logger.log(Level.INFO, message.toString());
    }
}
