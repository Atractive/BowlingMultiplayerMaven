
package com.mycompany.bowlingmultiplayermaven;

import bowling.MultiPlayerGame;
import bowling.SinglePlayerGame;
import java.util.Arrays;
import java.util.HashMap;


public class MultiPlayerGameImpl implements MultiPlayerGame{
    
    HashMap<String,SinglePlayerGame> game ;
    
    int nbPlayer;
    String[] tabPlayer;
    
    int nbCurrentPlayer;
    String currentPlayer;
    
    int nbtour;
    
    public MultiPlayerGameImpl() {
        
        this.game = new HashMap();
        
        this.nbPlayer = -1;
        this.tabPlayer = null;
        
        this.nbCurrentPlayer = -1;
        this.currentPlayer = null;
        
        this.nbtour = 0;
        
    }

    @Override
    public String startNewGame(String[] playerName) throws Exception {
        
        if (playerName.length == 0 || playerName.length == 1) throw new UnsupportedOperationException("Il n'y a pas assez de joueur.");
        if (this.nbCurrentPlayer != -1) throw new UnsupportedOperationException("La partie a déjà commencée.");
        
        this.nbPlayer = playerName.length;
        this.tabPlayer = new String[this.nbPlayer];
        
        for (int i=0; i<this.nbPlayer; i++){
            this.tabPlayer[i] = playerName[i];
            this.game.put(this.tabPlayer[i],new SinglePlayerGame());
        }
        
        this.nbCurrentPlayer = 0;
        this.currentPlayer = tabPlayer[this.nbCurrentPlayer];
        this.nbtour = 1;
        
        return this.toString(this.nbtour,1);
    
    }

    @Override
    public String lancer(int nombreDeQuillesAbattues) throws Exception {
        
        if (nombreDeQuillesAbattues < 0 || nombreDeQuillesAbattues > 10 ) throw new UnsupportedOperationException("Le nombre de quilles abattues est impossible.");
        if (this.nbCurrentPlayer == -1) throw new UnsupportedOperationException("La partie n'a pas commencée."); 
        if (this.game.get(this.currentPlayer).getCurrentFrame() == null) throw new UnsupportedOperationException("La partie est terminée.");
        
        this.game.get(currentPlayer).lancer(nombreDeQuillesAbattues);
     
        if (this.game.get(currentPlayer).getCurrentFrame().isFinished()){
            this.nbCurrentPlayer = (this.nbCurrentPlayer+1)%this.tabPlayer.length;
            this.currentPlayer = tabPlayer[this.nbCurrentPlayer];
            
            if (this.nbCurrentPlayer == 0){
                this.nbtour++;
            }
            
            return this.toString(this.nbtour,1);
        }
        return this.toString(this.nbtour,2);
        
    }

    @Override
    public int scoreFor(String playerName) throws Exception {
        if (Arrays.asList(this.tabPlayer).contains(playerName) == false) throw new UnsupportedOperationException("Le joueur n'existe pas."); 
        return this.game.get(playerName).score();
    }
    
    public String toString(int tour, int boule){
        return "Joueur "+this.currentPlayer+
                ", tour n°"+tour+
                ", boule n°"+boule;
    }
    
}
