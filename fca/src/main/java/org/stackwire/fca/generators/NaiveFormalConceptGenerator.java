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
package org.stackwire.fca.generators;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import org.stackwire.fca.Concept;
import org.stackwire.fca.Concept.Extent;
import org.stackwire.fca.Concept.Intent;
import org.stackwire.fca.ConceptType;
import org.stackwire.fca.FormalConceptGenerator;
import org.stackwire.fca.FormalContext;
import org.stackwire.fca.operators.AttributesCommonToObjectsFunction;

/**
 * Naive implementation as defined in Concept Data Analysis (pg 30)
 * 
 * Create power set P(G). For each element x of P(G), calculate A' as its
 * intent. For each element x of P(G), if A''= B' = A, add as a formal concept
 * 
 */
public class NaiveFormalConceptGenerator implements FormalConceptGenerator {

	@Override
	public FormalContext generateConceptsFor(FormalContext formalContext) {
		Set<Set<Integer>> powerSet = formalContext.powerSetOfObjects();
		Function<Collection<Integer>, Set<Integer>> commonAttributes = new AttributesCommonToObjectsFunction(
				formalContext.getRelations());
		for (Set<Integer> objects : powerSet) {
			Extent extent = new Extent(objects);
			Intent intent = new Intent(commonAttributes.apply(objects));
			ConceptType conceptType = FormalContext.getConceptType(formalContext, extent, intent);
			formalContext.addConcept(Concept.create(0, extent, intent, conceptType));
		}
		return formalContext;
	}
}
