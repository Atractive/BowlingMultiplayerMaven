
import com.mycompany.bowlingmultiplayermaven.MultiPlayerGameImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class MultiPlayerGameImplTest {
    
    MultiPlayerGameImpl game;
    String[] name = {"Benjamin","Diego","Matthias","Maxime"};
    
    @Before
    public void setUp() {
        this.game = new MultiPlayerGameImpl();
    }
    
    @Test
    public void startNewGameTestGood() throws Exception {
        assertEquals("Joueur Benjamin, tour n°1, boule n°1",
                game.startNewGame(name));
    }
    
    @Test (expected = UnsupportedOperationException.class)
    public void startNewGameTestWith0player() throws Exception {
        game.startNewGame(new String[0]);
    }
    
    @Test (expected = UnsupportedOperationException.class)
    public void startNewGameTestWith1player() throws Exception {
        game.startNewGame(new String[1]);
    }
    
    @Test (expected = UnsupportedOperationException.class)
    public void startNewGameTest2Times() throws Exception {
        game.startNewGame(name);
        game.startNewGame(name);
    }

    @Test
    public void lancerBoule() throws Exception {
        game.startNewGame(name);
        assertEquals("Joueur Benjamin, tour n°1, boule n°2",
                game.lancer(6));
    }
    
    @Test
    public void lancerJoueur() throws Exception {
        game.startNewGame(name);
        game.lancer(4);
        assertEquals("Joueur Diego, tour n°1, boule n°1",
                game.lancer(6));
    }
    
    @Test
    public void lancertour() throws Exception {
        game.startNewGame(name);
        rollMany(7,3);
        assertEquals("Joueur Benjamin, tour n°2, boule n°1",
                game.lancer(6));
    }
    
    @Test
    public void lancerStrike() throws Exception {
        game.startNewGame(name);
        assertEquals("Joueur Diego, tour n°1, boule n°1",
                game.lancer(10));
    }
    
    @Test
    public void lancerOnlyOne() throws Exception{
        game.startNewGame(name);
        rollMany(79,1);
        assertEquals(20,game.scoreFor("Benjamin"));
    }
    
    @Test (expected = UnsupportedOperationException.class)
    public void lancerWrongkeel1() throws Exception {
        game.startNewGame(name);
        game.lancer(12);
    }
    
    @Test (expected = UnsupportedOperationException.class)
    public void lancerWrongkeel2() throws Exception {
        game.startNewGame(name);
        game.lancer(-2);
    }
    
    @Test (expected = UnsupportedOperationException.class)
    public void lancerNoGame() throws Exception {
        game.lancer(6);
    }
    
    @Test (expected = UnsupportedOperationException.class)
    public void lancerEndGame() throws Exception {
        game.startNewGame(name);
        rollMany(80,3);
        game.lancer(4);
    }
    
    @Test
    public void scoreForTestGood() throws Exception {
        game.startNewGame(name);
        rollMany(2,3);
        assertEquals(6,game.scoreFor("Benjamin"));
    }
    
    @Test
    public void scoreForTestWhenSomeoneElsePlay() throws Exception {
        game.startNewGame(name);
        rollMany(3,3);
        assertEquals(6,game.scoreFor("Benjamin"));
    }
    
    @Test
    public void scoreForStrike() throws Exception {
        game.startNewGame(name);
        //Strike de Benjamin
        game.lancer(10);
        //Les 3 joueurs suivant joue
        rollMany(6,2);
        //Benjamin fait un 6 sur son 1er tour après le strike
        rollMany(2,3);
        assertEquals(22,game.scoreFor("Benjamin"));
    }
    
    @Test
    public void scoreForSpare() throws Exception {
        game.startNewGame(name);
        rollMany(2,5);
        rollMany(6,2);
        game.lancer(6);
        game.lancer(2);
        assertEquals(24,game.scoreFor("Benjamin"));
    }
    

    @Test
    public void scoreForPerfect() throws Exception {
        game.startNewGame(name);
        rollMany(44, 10);
        assertEquals(300, game.scoreFor("Benjamin"));
    }
    
    @Test (expected = UnsupportedOperationException.class)
    public void scoreForWrongName() throws Exception {
        game.startNewGame(name);
        game.scoreFor("Abdul");
    }
    
    
    // Quelques methodes utilitaires pour faciliter l'écriture des tests
    private void rollMany(int n, int pins) throws Exception {
	for (int i = 0; i < n; i++) {
            game.lancer(pins);
	}
}

    private void rollSpare() throws Exception {
	game.lancer(7);
	game.lancer(3);
    }

    private void rollStrike() throws Exception {
	game.lancer(10);
    }  

}
