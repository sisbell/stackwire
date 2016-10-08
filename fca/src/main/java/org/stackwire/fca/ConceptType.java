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

import java.util.Set;

import org.stackwire.fca.Concept.Extent;
import org.stackwire.fca.Concept.Intent;
import org.stackwire.fca.functions.AttributesCommonToObjectsFunction;
import org.stackwire.fca.functions.ObjectsCommonToAttributesFunction;

/**
 * An enumeration of the types of concepts
 */
public enum ConceptType {

	UNKNOWN, FORMAL_CONCEPT, PRECONCEPT, SEMICONCEPT;

	/**
	 * Returns concept type
	 * 
	 * FORMAL_CONCEPT: Requires the following to be true: A''= B' = A
	 * 
	 * SEMICONCEPT:
	 * 
	 * PRECONCEPT: if each element of A
	 * 
	 * @param formalContext
	 *            formal context associated with this formal concept
	 * @return concept type
	 * 
	 */
	public static ConceptType getConceptType(Context formalContext, Extent extent, Intent intent, double threshold) {
		if (formalContext == null) {
			throw new IllegalArgumentException("formalContext is null");
		}
		AttributesCommonToObjectsFunction attributesFunction = new AttributesCommonToObjectsFunction(
				formalContext.getRelations(), threshold);
		ObjectsCommonToAttributesFunction objectsFunction = new ObjectsCommonToAttributesFunction(
				formalContext.getRelations(), threshold);

		Set<Integer> aOpr = attributesFunction.apply(extent.getIndicies());
		Set<Integer> bOpr = objectsFunction.apply(intent.getIndicies());

		boolean BContainsAOpr = intent.getIndicies().containsAll(aOpr);
		boolean AContainsBOpr = extent.getIndicies().containsAll(bOpr);

		boolean x = aOpr.containsAll(intent.getIndicies()) && BContainsAOpr;
		boolean y = bOpr.containsAll(extent.getIndicies()) && AContainsBOpr;

		if (x && y) {
			return ConceptType.FORMAL_CONCEPT;
		} else if (x || y) {
			return ConceptType.SEMICONCEPT;
		} else if (BContainsAOpr && AContainsBOpr) {
			return ConceptType.PRECONCEPT;
		}

		return ConceptType.UNKNOWN;
	}
}
