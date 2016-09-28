package org.stackwire.fca.contexts;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.stackwire.fca.Concept;

public interface Context {

	/**
	 * Add formal concept to this context. Returns true if successfully added to
	 * context, otherwise false.
	 * 
	 * @param concept
	 * @param conceptType
	 * @return true if successfully added to context, otherwise false
	 */
	boolean addConcept(Concept formalConcept);

	/**
	 * Add relation to context
	 * 
	 * @param objectIndex
	 * @param attributeIndex
	 */
	void addRelation(int objectIndex, int attributeIndex);//PS: objectIndex, descriptionIndex

	/**
	 * Returns true if each object has the specified attribute, otherwise false
	 * 
	 * @param objectIndicies
	 *            object indicies
	 * @param attributeIndex
	 * @return true if each object has the specified attribute, otherwise false
	 */
	boolean allObjectsHaveAttribute(Collection<Integer> objectIndicies, int attributeIndex);

	/**
	 * Count of all objects belonging to this context
	 * 
	 * @return count of all objects belonging to this context
	 */
	int attributeCount();

	Context clarify();

	List<String> getAttributeNames();//PS: descriptionNames???

	List<String> getObjectNames();

	int[][] getRelations();

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
	boolean hasRelation(int objectIndex, int attributeIndex);

	int objectCount();

	Set<Set<Integer>> powerSetOfAttributes();

	Set<Set<Integer>> powerSetOfObjects();

	Context reduce();

}