package ai.gleamer.ugly;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList(); // arraylist pourquoi pas mais il faut préciser le type
    int[] places = new int[6]; // pourrait être mit dans une classe Player
    int[] purses  = new int[6]; // pourrait être mit dans une classe Player
    boolean[] inPenaltyBox  = new boolean[6]; // pourrait être mit dans une classe Player
    
    LinkedList popQuestions = new LinkedList(); // linkedlist pourquoi pas, deque serait plus logique et eventuellement même une queue
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
		// le 50 peut être mis dans une variable plus globale pour améliorer la rejouabilité et rendre la compréhension meilleure
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }
	// Pourquoi cette méthode alors que les autres n'en ont pas.
	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	// nom de la méthode a revoir. Pourquoi retourner un boolean si c'est toujours true ?
	public boolean add(String playerName) {
		
		
	    players.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				places[currentPlayer] = places[currentPlayer] + roll;
				if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
				
				System.out.println(players.get(currentPlayer) 
						+ "'s new location is " 
						+ places[currentPlayer]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
		
			places[currentPlayer] = places[currentPlayer] + roll;
			if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
			
			System.out.println(players.get(currentPlayer) 
					+ "'s new location is " 
					+ places[currentPlayer]);
			System.out.println("The category is " + currentCategory());
			askQuestion();
		}
		
	}

	private void askQuestion() {
		if (currentCategory() == "Pop") // "Pop".equals(currentCategory()); 
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());		
	}
	
	
	//hashmap, if ou encore un switch serait plus propre.
	private String currentCategory() {
		if (places[currentPlayer] == 0) return "Pop"; // Ces strings sont répétés beaucoup trop de fois. Des variables serait plus adéquates.
		if (places[currentPlayer] == 4) return "Pop";
		if (places[currentPlayer] == 8) return "Pop";
		if (places[currentPlayer] == 1) return "Science";
		if (places[currentPlayer] == 5) return "Science";
		if (places[currentPlayer] == 9) return "Science";
		if (places[currentPlayer] == 2) return "Sports";
		if (places[currentPlayer] == 6) return "Sports";
		if (places[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() { // pourquoi ne pas respecter la meme norme que wrongAnswer()
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");// code dupliqué
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer) 
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");
				
				boolean winner = didPlayerWin();
				currentPlayer++; // mieux vaut crééer une fonction pour le passage au joueur suivant
				if (currentPlayer == players.size()) currentPlayer = 0;
				
				return winner; // pourquoi ?
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true; // pourquoi ?
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!"); // <<< typo + encore dupliqué
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			currentPlayer++; // idem qu'au dessus
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){ // un void serait plus propre
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++; // encore
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true; // inutile
	}


	private boolean didPlayerWin() {
		//Le nom de la fonction n'est pas en rapport avec ce qu'elle fait.
		//Il faut retirer le ! pour vérifier la victoire
		//Si on change les points et qu'au lieu de gagner un gold on peu en gagner 2 et que je suis a 5. La condition ne fonctionne plus. Mieux vaut un >= pour etre sur de l'atteindre.
		return !(purses[currentPlayer] == 6);
	}
}
