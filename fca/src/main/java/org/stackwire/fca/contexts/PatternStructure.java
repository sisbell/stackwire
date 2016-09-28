package org.stackwire.fca.contexts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.stackwire.fca.patterns.Description;
import org.stackwire.fca.patterns.PatternConcept;

public class PatternStructure {

	private Collection<PatternConcept> concepts = new ArrayList<>();
	
	/**
	 * Objects to description cross table
	 */
	private int[][] relations;
	
	/**
	 * 
	 * @param objectNames
	 * @param descriptions complete meet semilattice of descriptions
	 * @param relations
	 * @return
	 */
	public static PatternStructure create(List<String> objectNames, List<Description> descriptions, int[][] relations) {
		return null;
	}
	
	

}
