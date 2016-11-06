package org.devathon.contest2016.ai;

public class Learner {
	private ArtificialIntelligence ai = null;
	public AnnaStand annaStand = null;
	public Learner() {
		ai = new ArtificialIntelligence();
	}
	
	public void learn(String input) {
		ai.sentences.add(input);
	}
	
	public ArtificialIntelligence getAI() {
		return ai;
	}
}
