package org.stackwire.fca.patterns;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Equivalent to formal context
 *
 */
public class PatternStructure {
	
	

	private Collection<PatternConcept> concepts = new ArrayList<>();
	
	//private Map<Integer, Description> relations = new HashMap<>();
	
	private int[][] relations;
	
	public void addConcept(PatternConcept patternConcept) {
		concepts.add(patternConcept);
	}
	
	/**
	 * Add relation to context
	 * 
	 * @param objectIndex
	 * @param descriptionIndex
	 */
	public void addRelation(int objectIndex, int descriptionIndex) {
		relations[objectIndex][descriptionIndex] = 1;
	}
	
	/**
	 * Returns true if object has description, otherwise false
	 * 
	 * @param objectIndex
	 *            index of object
	 * @param descriptionIndex
	 *            index of attribute
	 * 
	 * @return true if object has attribute, otherwise false
	 */
	public boolean hasRelation(int objectIndex, int descriptionIndex) {
		try {
			return relations != null && relations[objectIndex][descriptionIndex] == 1;
		} catch (Exception e) {
			throw new IllegalArgumentException("Out of Bounds:");

		}
	}
}
