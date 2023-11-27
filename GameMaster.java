import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.Math;

public class GameMaster{

    private int turnCounter;
    private ArrayList<Card> deck;
    private ArrayList<Card> deckCopy;
    private boolean winner;
    private Player Player1;
    private Player Player2;
    private ArrayList<Integer> indices;
    private boolean random;
    private boolean newDeck;

    private void assembleDeck(){

        deck.add( new Card( "Dragon", "Aquira", 174, 26 ) );
        deck.add( new Card( "Ghost", "Brawn", 130, 48 ) );
        deck.add( new Card( "Fairy", "Cerulea", 162, 29 ) );
        deck.add( new Card( "Dragon", "Demi", 147, 28 ) );
        deck.add( new Card( "Ghost", "Elba", 155, 37 ) );
        deck.add( new Card( "Fairy", "Fye", 159, 42 ) );
        deck.add( new Card( "Dragon", "Glyede", 129, 26 ) );
        deck.add( new Card( "Ghost", "Hydran", 163, 35 ) );
        deck.add( new Card( "Fairy", "Ivy", 146, 45 ) );
        deck.add( new Card( "Dragon", "Jet", 170, 24 ) );
        deck.add( new Card( "Ghost", "Kineti", 139, 21 ) );
        deck.add( new Card( "Fairy", "Levi", 160, 43 ) );
        deck.add( new Card( "Dragon", "Meadow", 134, 29 ) );
        deck.add( new Card( "Ghost", "Naidem", 165, 26 ) );
        deck.add( new Card( "Fairy", "Omi", 145, 21 ) );
        deck.add( new Card( "Dragon", "Puddles", 170, 34 ) );
        deck.add( new Card( "Ghost", "Quarrel", 151, 29 ) );
        deck.add( new Card( "Fairy", "Raven", 168, 32 ) );
        deck.add( new Card( "Dragon", "Surge", 128, 27 ) );
        deck.add( new Card( "Ghost", "Takiru", 140, 26 ) );
        deck.add( new Card( "Fairy", "Ustelia", 163, 47 ) );
        deck.add( new Card( "Dragon", "Verwyn", 145, 25 ) );
        deck.add( new Card( "Ghost", "Wyverin", 158, 32 ) );
        deck.add( new Card( "Fairy", "Xios", 155, 27 ) );
        deck.add( new Card( "Dragon", "Yora", 159, 44 ) );
        deck.add( new Card( "Ghost", "Zulu", 125, 46 ) );
    }

    @SuppressWarnings("unchecked")
    public GameMaster(Player a, Player b, boolean r, boolean n)throws FileNotFoundException{

        turnCounter = 1;
        deck = new ArrayList<Card>();
        assembleDeck();
        deckCopy = (ArrayList<Card>)deck.clone();
        indices = new ArrayList<Integer>();
        winner = false;
        Player1 = a;
        Player2 = b;
        random = r;
        newDeck = n;
        if(newDeck == true){
            deck.clear();
            deckCopy.clear();
            FileReader deckReader = new FileReader("newCards.txt");
            Scanner sc = new Scanner(deckReader);
            while(sc.hasNextLine()){
                String[] newCard = sc.nextLine().split(" ");
                deck.add(new Card(newCard[0], newCard[1], Integer.parseInt(newCard[2]), Integer.parseInt(newCard[3])));
            }
            deckCopy = (ArrayList<Card>)deck.clone();
        }
        for(int i = 0; i < deck.size(); i++){
            indices.add(i);
        }
        
    }
    
    public String dealCard(){
        String dealMessage = "";
        String altMessage = "";
        if(random == false){
          for(Card card: deck){
            if(turnCounter % 2 != 0){
                if(Player1.handIsFull() == false){
                    Player1.drawCard(card);
                    dealMessage += Player1.getName() + " draws " + card.getName() + ".\n\n";
                    turnCounter += 1;
                    deckCopy.remove(card);
                }else{
                    altMessage += Player1.getName() + "'s hand is full.";
                    break;
                }
            }else{
                if(Player2.handIsFull() == false){
                    Player2.drawCard(card);
                    dealMessage += Player2.getName() + " draws " + card.getName() + ".\n\n";
                    turnCounter += 1;
                    deckCopy.remove(card);
                }else{
                    altMessage += Player2.getName() + "'s hand is full.";
                    break;
                }
            }
        }  
        }
        else{
            for(int i = 0; i < deck.size(); i++ ){
            int index = (int) (Math.random()*indices.size());
            if(turnCounter % 2 != 0){
                if(Player1.handIsFull() == false){
                    Player1.drawCard(deck.get(indices.get(index)));
                    dealMessage += Player1.getName() + " draws " + deck.get(indices.get(index)).getName() + ".\n\n";
                    deckCopy.remove(deck.get(indices.get(index)));
                    indices.remove(index);
                    turnCounter += 1;
                }else{
                    altMessage += Player1.getName() + "'s hand is full.";
                    break;
                }
            }else{
                if(Player2.handIsFull() == false){
                    Player2.drawCard(deck.get(indices.get(index)));
                    dealMessage += Player2.getName() + " draws " + deck.get(indices.get(index)).getName() + ".\n\n";
                    deckCopy.remove(deck.get(indices.get(index)));
                    indices.remove(index);
                    turnCounter += 1;
                }else{
                    altMessage += Player2.getName() + "'s hand is full.";
                    break;
                }
            }
        }  
        }
        return dealMessage;     
    }

   public String play(String action){
        String playMessage = "";
        if(action.equals("swap")){
            if(turnCounter % 2 != 0){
                playMessage += Player1.getName() + " swaps out " + Player1.getActiveCard().getName() + "...\n";
                playMessage += Player1.swap();
                turnCounter += 1;
            }else{
                playMessage += Player2.getName() + " swaps out " + Player2.getActiveCard().getName() + "...\n";
                playMessage += Player2.swap();
                turnCounter += 1;
            }
            
        }else if(action.equals("attack")){
            if(turnCounter % 2 != 0){
                
                playMessage += "   " + Player1.getName() + " attacks with " + Player1.getActiveCard().getName() + ".\n";
                playMessage += dealDamage(Player1.getActiveCard(), Player2.getActiveCard());
                if(Player2.getActiveCard().getHealth() <= 0){
                    playMessage += "   " + Player2.getName() + " discards " + Player2.getActiveCard().getName() + ".\n";
                    Player2.discard();
                    int toRemove = 0;
                    for(int i=0; i<1; i++){
                        if(deckCopy.get(i).getHealth() * deckCopy.get(i).getPower() > deckCopy.get(i+1).getHealth() * deckCopy.get(i+1).getPower()){
                            Player2.drawCard(deckCopy.get(i));
                            playMessage += "   " + Player2.getName() + " draws " + deckCopy.get(i).getName() + ".\n";
                            deckCopy.add(deckCopy.get(i+1));
                        }else{
                            Player2.drawCard(deckCopy.get(i+1));
                            playMessage += "   " + Player2.getName() + " draws " + deckCopy.get(i+1).getName() + ".\n";
                            deckCopy.add(deckCopy.get(i));
                        } 
                    }
                    deckCopy.remove(toRemove+1);
                    deckCopy.remove(toRemove);
                Player1.claimToken();
                playMessage += "   " + Player1.getName() + " gets a token!\n";
                if(Player1.getTokens() >= 3){
                    playMessage += Player1.getName() + " wins!!!\n"; 
                    winner = true;
                }
                }
                turnCounter += 1;
             
            }else{
                playMessage += "   " + Player2.getName() + " attacks with " + Player2.getActiveCard().getName() + ".\n";
                playMessage += dealDamage(Player2.getActiveCard(), Player1.getActiveCard());
                if(Player1.getActiveCard().getHealth() <= 0){
                    playMessage += "   " + Player1.getName() + " discards " + Player1.getActiveCard().getName() + ".\n";
                    Player1.discard();
                    int toRemove = 0;
                    for(int i=0; i<1; i++){
                        if(deckCopy.get(i).getHealth() * deckCopy.get(i).getPower() > deckCopy.get(i+1).getHealth() * deckCopy.get(i+1).getPower()){
                            Player1.drawCard(deckCopy.get(i));
                            playMessage += "   " + Player1.getName() + " draws " + deckCopy.get(i).getName() + ".\n";
                            deckCopy.add(deckCopy.get(i+1));
                        }else{
                            Player1.drawCard(deckCopy.get(i+1));
                            playMessage += "   " + Player1.getName() + " draws " + deckCopy.get(i+1).getName() + ".\n";
                            deckCopy.add(deckCopy.get(i));
                        } 
                    }
                    deckCopy.remove(toRemove+1);
                    deckCopy.remove(toRemove);
                Player2.claimToken();
                playMessage += "   " + Player2.getName() + " gets a token!\n";
                if(Player2.getTokens() >= 3){
                    playMessage += Player2.getName() + " wins!!!\n";
                    winner = true;
                    
                }
                }
                turnCounter += 1;
            }
        }
        return playMessage;
    }
    /*  Dragon cards are resistant to Ghost cards.
        Ghost cards are resistant to Fairy cards.
        Fairy cards are resistant to Dragon cards.
    */
    public boolean checkResistance(String type1, String type2){
        boolean resistant = false;

        if(type1.equalsIgnoreCase("Dragon") && type2.equalsIgnoreCase("Ghost")){
            resistant = true;
        }else if(type1.equalsIgnoreCase("Ghost") && type2.equalsIgnoreCase("Fairy")){
            resistant = true;
        }else if(type1.equalsIgnoreCase("Fairy") && type2.equalsIgnoreCase("Dragon")){
            resistant = true;
        }else{
            resistant = false;
        }return resistant;
            
    }
    /*  Dragon cards are weak to Fairy cards.
        Fairy cards are weak to Ghost cards.
        Ghost cards are weak to Dragon cards.
    */
    public boolean checkWeakness(String type1, String type2){
        boolean weak = false;

         if(type1.equalsIgnoreCase("Dragon") && type2.equalsIgnoreCase("Fairy")){
            weak = true;
        }else if(type1.equalsIgnoreCase("Fairy") && type2.equalsIgnoreCase("Ghost")){
            weak = true;
        }else if(type1.equalsIgnoreCase("Ghost") && type2.equalsIgnoreCase("Dragon")){
            weak = true;
        }else{
            weak = false;
        }return weak;        
    }

    public String dealDamage(Card inPlay, Card target){
        String inPlayType = inPlay.getType();
        String targetType = target.getType();
        boolean resistant = checkResistance(targetType, inPlayType);
        boolean weak = checkWeakness(targetType, inPlayType);
        int damage = inPlay.getPower();
        String messageDamage = "";

        if(resistant == true){
            damage /= 2;
            messageDamage += "     " + targetType + " is resistant to " + inPlayType + ".\n";
        }else if(weak == true){
            damage *= 2;
            messageDamage += "     " + targetType + " is weak to " + inPlayType + ".\n";
        }
        target.takeDamage(damage);
        messageDamage += "   " + inPlay.getName() + " deals " + damage + " damage on " + target.getName() + ".\n";
        messageDamage += "   " + target.getName() + " has " + target.getHealth() + " health left.\n";
        return messageDamage;
    }

    public boolean hasWinner(){
        boolean won;
        if(winner == true){
            won = true;
        }else{
            won = false;
        }
        return won;
    }

    public String gameReport(){
        String gameReport = "";
        gameReport += "---=== GAME SUMMARY ===---\n";
        gameReport += "This game lasted " + (turnCounter-1) + " turns.\n";
        gameReport += Player1.statusReport() + "\n";
        gameReport += Player2.statusReport();
        return gameReport;
    }
}