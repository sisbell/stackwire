/**
 * Copyright 2016 Shane Isbell
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.stackwire.fca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;

public class FormalContext {

	public static FormalContext create(int[][] relations) {
		return new FormalContext(generateAnon(relations.length, "obj"), generateAnon(relations[0].length, "attr"),
				relations);
	}

	public static FormalContext create(int objectCount, int attributeCount) {
		return new FormalContext(generateAnon(objectCount, "obj"), generateAnon(attributeCount, "attr"));
	}

	private static ArrayList<String> generateAnon(int count, String prefix) {
		ArrayList<String> s = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			s.add(prefix + "-" + i);
		}
		return s;
	}

	public static FormalContext create(ArrayList<String> objectNames, ArrayList<String> attributeNames) {
		return new FormalContext(objectNames, attributeNames);
	}

	public static FormalContext create(ArrayList<String> objectNames, ArrayList<String> attributeNames,
			int[][] relations) {
		return new FormalContext(objectNames, attributeNames, relations);
	}

	/**
	 * Boolean matrix of relations.
	 */
	private final int[][] relations;

	private final ArrayList<String> attributeNames;

	private final ArrayList<String> objectNames;

	private final Collection<FormalConcept> formalConcepts = new ArrayList<>();

	private FormalContext(ArrayList<String> objectNames, ArrayList<String> attributeNames) {
		this(objectNames, attributeNames, null);
	}

	private FormalContext(ArrayList<String> objectNames, ArrayList<String> attributeNames, int[][] relations) {
		if (objectNames == null || objectNames.isEmpty()) {
			throw new IllegalArgumentException("objectNames is empty");
		}

		if (attributeNames == null || attributeNames.isEmpty()) {
			throw new IllegalArgumentException("attributesNames is empty");
		}
		this.objectNames = objectNames;
		this.attributeNames = attributeNames;
		if (relations == null) {
			this.relations = new int[objectNames.size()][attributeNames.size()];
		} else {
			this.relations = relations;
		}
	}

	public Set<Set<Integer>> powerSetOfObjects() {
		return Sets.powerSet(Utils.rangeSet(0, objectNames.size() - 1));
	}

	public Set<Set<Integer>> powerSetOfAttributes() {
		return Sets.powerSet(Utils.rangeSet(0, attributeNames.size() - 1));
	}

	public void addFormalConcepts(Collection<FormalConcept> concepts) {
		formalConcepts.addAll(concepts);
	}

	/**
	 * Add formal concept to this context
	 * 
	 * @param formalConcept
	 *            formal concept to add
	 */
	public void addFormalConcept(FormalConcept formalConcept) {
		formalConcepts.add(formalConcept);
	}

	/**
	 * Add relation to context
	 * 
	 * @param objectIndex
	 * @param attributeIndex
	 */
	public void addRelation(int objectIndex, int attributeIndex) {
		relations[objectIndex][attributeIndex] = 1;
	}

	/**
	 * Count of all objects belonging to this context
	 * 
	 * @return count of all objects belonging to this context
	 */
	public int attributeCount() {
		return attributeNames.size();
	}

	/**
	 * Get all formal concepts belonging to this context
	 * 
	 * @return all formal concepts belonging to this context
	 */
	public Collection<FormalConcept> getFormalConcepts() {
		return formalConcepts;
	}

	/**
	 * Returns true if object has attribute, otherwise false
	 * 
	 * @param objectIndex
	 *            index of object
	 * @param attributeIndex
	 *            index of attribute
	 * 
	 * @return true if object has attribute, otherwise false
	 */
	public boolean hasRelation(int objectIndex, int attributeIndex) {
		try {
			return relations != null && relations[objectIndex][attributeIndex] == 1;
		} catch (Exception e) {
			throw new IllegalArgumentException("Out of Bounds: Object Index = " + objectIndex + ", Attribute Index = "
					+ attributeIndex + ", Object Count = " + this.objectCount() + ". Attribute Count = "
					+ this.attributeCount());

		}
	}

	public int objectCount() {
		return objectNames.size();
	}

	/**
	 * Returns true if each object has the specified attribute, otherwise false
	 * 
	 * @param objectIndicies
	 *            object indicies
	 * @param attributeIndex
	 * @return true if each object has the specified attribute, otherwise false
	 */
	public boolean allObjectsHaveAttribute(Collection<Integer> objectIndicies, int attributeIndex) {
		for (Integer objectIndex : objectIndicies) {
			if (!hasRelation(objectIndex, attributeIndex)) {
				return false;
			}
		}
		return true;
	}

	public void printRelations() {
		int rows = relations.length;
		int cols = relations[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.print(relations[i][j] + " ");
			}
			System.out.println();
		}
	}

	public int[][] getRelations() {
		return relations;
	}
}
