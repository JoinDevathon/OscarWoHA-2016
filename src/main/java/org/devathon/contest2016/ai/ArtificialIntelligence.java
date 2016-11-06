package org.devathon.contest2016.ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.lang3.tuple.Pair;

public class ArtificialIntelligence {
	public HashSet<String> sentences = new HashSet<String>();

	/*
	 * This function here is the fun part
	 * I wrote it because I really really wanted to do something with bots and AI-kinda-ish-stuff-I-guess
	 */
	public String getMatch(String[] messageMatchables) {
		ArrayList<String> possibleMatches = new ArrayList<String>();

		for(String matchable : messageMatchables) {
			Iterator<String> sentencesIterator = this.sentences.iterator();
			while(sentencesIterator.hasNext()) {
				String sentence = sentencesIterator.next();
				if(sentence.toLowerCase().contains(matchable.toLowerCase())) {
					possibleMatches.add(sentence);
				}
			}
		}

		Pair<String, Integer> leader = null;

		Iterator<String> possibleIterator = possibleMatches.iterator();
		while(possibleIterator.hasNext()) {
			String possibleSentence = possibleIterator.next();

			String replacedSentence = possibleSentence.replaceAll("[^\\w\\s]", "");
			String[] possibles = replacedSentence.split(" ");

			int matchCount = 0;

			for(String possible : possibles) {
				for(String matchable : messageMatchables) {
					if(possible.equalsIgnoreCase(matchable)) {
						matchCount++;
					}
				}
			}

			if(leader == null) { leader = Pair.of(possibleSentence, matchCount); continue; }

			if(leader.getValue() <= matchCount) {
				leader = Pair.of(possibleSentence, matchCount);
			}
		}

        if(leader == null) {
            return "Grr.. Couldn't find an answer for you baby.";
        }

		System.out.println("Best match is \"" + leader.getKey() + "\" with " + leader.getValue() + " matches..");
		return leader.getKey();
	}

}
