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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.stackwire.fca.Clarifier.ClarifierResult;
import org.stackwire.fca.Concept.Extent;
import org.stackwire.fca.Concept.Intent;
import org.stackwire.fca.utils.Utils;

import com.google.common.collect.Sets;

/**
 * Service for adding and retrieving concepts. Relations should all be added
 * prior to generating concepts
 * 
 * @see ConceptGenerator
 */
public class Context {

	/**
	 * Create formal context with the specified number of objects and the
	 * specified number of attributes. When creating with this method, the
	 * relations are empty and will need to be added using
	 * FormalContext.addRelation
	 * 
	 * @param objectCount
	 *            number of objects in context
	 * @param attributeCount
	 *            number of attributes in context
	 * @return new formal context
	 */
	public static Context create(int objectCount, int attributeCount) {
		return new Context(generateLabel(objectCount, "x"), generateLabel(attributeCount, "y"));
	}

	/**
	 * Creates formal context with specified relations. The object and attribute
	 * labels will automatically be generated.
	 * 
	 * @param relations
	 * @return formal context with specified relations
	 */
	public static Context create(int[][] relations) {
		return new Context(generateLabel(relations.length, "x"), generateLabel(relations[0].length, "y"),
				relations, null);
	}

	/**
	 * Creates formal context with the specified object names and attribute
	 * names. When creating with this method, the relations are empty and will
	 * need to be added using FormalContext.addRelation
	 * 
	 * @param objectNames
	 *            object names
	 * @param attributeNames
	 *            attribute names
	 * @return new formal context
	 */
	public static Context create(List<String> objectNames, List<String> attributeNames) {
		return new Context(objectNames, attributeNames);
	}

	/**
	 * 
	 * The relations table may contain values x >=0. 0 denotes no attribute, 1
	 * maps to description 1, 2 maps to description 2, and so on. If each
	 * element is 0 or 1, the element maps to the global description with an
	 * empty description path table. path_table[p,p]. where p = max[val(m) -1],
	 * i.e. if m is element {0, 1}, then p[0,0] or empty. m is strictly
	 * monotonically increasing with no gaps (whole numbers)
	 * 
	 * @param objectNames
	 * @param attributeNames
	 * @param relations
	 * @param descriptionPaths
	 * @return formal context
	 */
	public static Context create(List<String> objectNames, List<String> attributeNames, int[][] relations,
			int[][] descriptionPaths) {
		return new Context(objectNames, attributeNames, relations, descriptionPaths);
	}

	/**
	 * Generates list of strings with each entry as {prefix + count}
	 * 
	 * @param count
	 *            size of list to generate
	 * @param prefix
	 *            prefix to use in constructing strings
	 * 
	 * @return list of strings with each entry as {prefix + count}
	 */
	private static ArrayList<String> generateLabel(int count, String prefix) {
		ArrayList<String> s = new ArrayList<>();
		for (int i = 1; i <= count; i++) {
			s.add(prefix + i);
		}
		return s;
	}

	/**
	 * Boolean cross table of relations.
	 */
	private final int[][] relations;

	/**
	 * Transition table for descriptions
	 */
	private final int[][] descriptionPaths;

	/**
	 * Attribute names
	 */
	private final ArrayList<String> attributeNames;

	/**
	 * Object names
	 */
	private final ArrayList<String> objectNames;

	private final Collection<Concept> formalConcepts = new ArrayList<>();

	private final Collection<Concept> preConcepts = new ArrayList<>();

	private final Collection<Concept> semiConcepts = new ArrayList<>();

	/**
	 * Constructor
	 * 
	 * @param objectNames
	 *            object names
	 * @param attributeNames
	 *            attribute names
	 */
	private Context(List<String> objectNames, List<String> attributeNames) {
		this(objectNames, attributeNames, null, null);
	}

	/**
	 * Constructor
	 * 
	 * @param objectNames
	 * @param attributeNames
	 * @param relations
	 * @param paths
	 */
	private Context(List<String> objectNames, List<String> attributeNames, int[][] relations, int[][] paths) {
		if (objectNames == null || objectNames.isEmpty()) {
			throw new IllegalArgumentException("objectNames is empty");
		}

		if (attributeNames == null || attributeNames.isEmpty()) {
			throw new IllegalArgumentException("attributesNames is empty");
		}
		this.objectNames = new ArrayList<>(objectNames);
		this.attributeNames = new ArrayList<>(attributeNames);
		if (relations == null) {
			this.relations = new int[objectNames.size()][attributeNames.size()];
		} else {
			this.relations = relations;
		}
		if (paths == null) {
			this.descriptionPaths = new int[][] {};
		} else {
			this.descriptionPaths = paths;
		}

	}

	/**
	 * Add formal concept to this context. Returns true if successfully added to
	 * context, otherwise false. Concepts of unknown type will not be added.
	 * 
	 * @param concept
	 *            concept to add
	 * @return true if successfully added to context, otherwise false
	 */
	public boolean addConcept(Concept concept) {
		ConceptType type = concept.getConceptType();
		Optional<Collection<Concept>> concepts = getConceptsOfLive(type);
		if (concepts.isPresent()) {
			return concepts.get().add(concept);
		}
		return false;
	}

	/**
	 * Add relation to context. It's up the developer to ensure that the object
	 * and attribute indexes are within bounds.
	 * 
	 * @param objectIndex
	 * @param attributeIndex
	 */
	public void addRelation(int objectIndex, int attributeIndex) {
		relations[objectIndex][attributeIndex] = 1;
	}

	/**
	 * Add relation to context with the specified value. It's up the developer
	 * to ensure that the object and attribute indexes are within bounds.
	 * 
	 * @param objectIndex
	 * @param attributeIndex
	 */
	public void addRelation(int objectIndex, int attributeIndex, int value) {
		relations[objectIndex][attributeIndex] = value;
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

	/**
	 * Count of all objects belonging to this context
	 * 
	 * @return count of all objects belonging to this context
	 */
	public int attributeCount() {
		return attributeNames.size();
	}

	/**
	 * Makes the relations matrix distinct by collapsing rows that have the same
	 * attributes and collapses columns that have the same objects.
	 * 
	 * See Formal Concept Analysis (Ganter & Wilfe) defintion 23
	 * 
	 * @return a new formal context with the clarified relations
	 */
	public Context clarify() {
		ClarifierResult result = Clarifier.clarify(relations);

		List<String> newObjectNames = new ArrayList<String>(objectNames);
		for (int rowIndex : result.getDuplicateRows()) {
			newObjectNames.remove(rowIndex);
		}

		List<String> newAttributeNames = new ArrayList<String>(attributeNames);
		for (int columnIndex : result.getDuplicateColumns()) {
			newAttributeNames.remove(columnIndex);
		}

		return Context.create(newObjectNames, newAttributeNames, result.getClarifiedCrossTable(),
				descriptionPaths);
	}

	/**
	 * Returns unmodifiable list of attribute names. Will not be empty.
	 * 
	 * @return unmodifiable list of attribute names
	 */
	public List<String> getAttributeNames() {
		return Collections.unmodifiableList(attributeNames);
	}

	/**
	 * Returns concepts that have the specified extent and concept type. Is
	 * empty if no match.
	 * 
	 * @param extent
	 *            the extent to match
	 * @param conceptType
	 * @return concepts that have the specified extent and concept type
	 */
	public Collection<Concept> getConceptOf(Extent extent, ConceptType conceptType) {
		Optional<Collection<Concept>> concepts = getConceptsOf(conceptType);
		if (concepts.isPresent()) {
			return concepts.get().stream().filter(i -> extent.equals(i.getExtent())).collect(Collectors.toSet());
		}
		return Collections.emptyList();
	}

	/**
	 * Returns concepts that have the specified intent and concept type. Is
	 * empty if no match.
	 * 
	 * @param intent
	 *            the intent to match
	 * @param conceptType
	 * @return concepts that have the specified intent and concept type
	 */
	public Collection<Concept> getConceptOf(Intent intent, ConceptType conceptType) {
		Optional<Collection<Concept>> concepts = getConceptsOf(conceptType);
		if (concepts.isPresent()) {
			return concepts.get().stream().filter(i -> intent.equals(i.getIntent())).collect(Collectors.toSet());
		}
		return Collections.emptyList();
	}

	/**
	 * Get all concepts of the specified type. The collection may be empty. If
	 * optional itself is empty, then the specified content type is unsupported.
	 * 
	 * @return all concepts of the specified type
	 */
	public Optional<Collection<Concept>> getConceptsOf(ConceptType type) {
		if (ConceptType.FORMAL_CONCEPT.equals(type)) {
			return Optional.of(Collections.unmodifiableCollection(new ArrayList<>(formalConcepts)));
		} else if (ConceptType.PRECONCEPT.equals(type)) {
			return Optional.of(Collections.unmodifiableCollection(new ArrayList<>(preConcepts)));
		} else if (ConceptType.SEMICONCEPT.equals(type)) {
			return Optional.of(Collections.unmodifiableCollection(new ArrayList<>(semiConcepts)));
		}
		return Optional.empty();
	}

	public Optional<Collection<Concept>> getConceptsOfLive(ConceptType type) {
		if (ConceptType.FORMAL_CONCEPT.equals(type)) {
			return Optional.of(formalConcepts);
		} else if (ConceptType.PRECONCEPT.equals(type)) {
			return Optional.of(preConcepts);
		} else if (ConceptType.SEMICONCEPT.equals(type)) {
			return Optional.of(semiConcepts);
		}
		return Optional.empty();
	}

	public int[][] getDescriptionPaths() {
		return descriptionPaths;
	}

	/**
	 * Returns unmodifiable list of object names. Will not be empty.
	 * 
	 * @return unmodifiable list of object names
	 */
	public List<String> getObjectNames() {
		return Collections.unmodifiableList(objectNames);
	}

	/**
	 * Returns relation cross table of this context. Rows represent objects,
	 * while columns represent attributes. A value of 1 or higher indicates that
	 * the attribute exists for the associated object.
	 * 
	 * This is the backing table to the context. Any direct changes will create
	 * an inconsistency with the underlying generated concepts
	 * 
	 * @return relation cross table of this context.
	 */
	public int[][] getRelations() {
		return relations;
	}

	public boolean hasConceptOf(Extent extent, ConceptType conceptType) {
		Optional<Collection<Concept>> concepts = getConceptsOf(conceptType);
		if (concepts.isPresent()) {
			for (Concept concept : concepts.get()) {
				if (concept.getExtent().equals(extent)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasConceptOf(Intent intent, ConceptType conceptType) {
		Optional<Collection<Concept>> concepts = getConceptsOf(conceptType);
		if (concepts.isPresent()) {
			for (Concept concept : concepts.get()) {
				if (concept.getIntent().equals(intent)) {
					return true;
				}
			}
		}
		return false;
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
			return relations != null && relations[objectIndex][attributeIndex] > 0;
		} catch (Exception e) {
			throw new IllegalArgumentException("Out of Bounds: Object Index = " + objectIndex + ", Attribute Index = "
					+ attributeIndex + ", Object Count = " + this.objectCount() + ". Attribute Count = "
					+ this.attributeCount());

		}
	}

	/**
	 * Returns count of objects
	 * 
	 * @return count of objects
	 */
	public int objectCount() {
		return objectNames.size();
	}

	/**
	 * Returns power set of attributes
	 * 
	 * @return power set of attributes
	 */
	public Set<Set<Integer>> powerSetOfAttributes() {
		return Sets.powerSet(Utils.rangeSet(0, attributeNames.size() - 1));
	}

	/**
	 * Returns power set of object
	 * 
	 * @return power set of attributes
	 */
	public Set<Set<Integer>> powerSetOfObjects() {
		return Sets.powerSet(Utils.rangeSet(0, objectNames.size() - 1));
	}

	/**
	 * Prints relation matrix to system out
	 */
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

	public Context reduce() {
		return null;
	}
	
	/*
	public ConceptLattice findConceptLattice() {
		return null;
	}
	*/

}