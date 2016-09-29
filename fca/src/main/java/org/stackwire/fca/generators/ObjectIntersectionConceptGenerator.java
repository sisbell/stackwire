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

import java.util.Arrays;
import java.util.Optional;

import org.stackwire.fca.Concept;
import org.stackwire.fca.Concept.Extent;
import org.stackwire.fca.Concept.Intent;
import org.stackwire.fca.ConceptGenerator;
import org.stackwire.fca.ConceptOperations;
import org.stackwire.fca.ConceptType;
import org.stackwire.fca.Context;
import org.stackwire.fca.functions.AttributesCommonToObjectsFunction;
import org.stackwire.fca.functions.ObjectsCommonToAttributesFunction;

public class ObjectIntersectionConceptGenerator implements ConceptGenerator {

	@Override
	public Context generateConceptsFor(Context formalContext) {
		AttributesCommonToObjectsFunction attributesFunction = new AttributesCommonToObjectsFunction(
				formalContext.getRelations());

		ObjectsCommonToAttributesFunction objectsFunction = new ObjectsCommonToAttributesFunction(
				formalContext.getRelations());

		Concept sup = Concept.newSupremum(formalContext.objectCount() - 1);
		formalContext.addConcept(sup);
		
		Concept inf = Concept.newInfimum(formalContext.attributeCount() - 1);
		objectsFunction.apply(inf.getIntent().getIndicies());
		
		formalContext.addConcept(Concept.create(new Extent(objectsFunction.apply(inf.getIntent().getIndicies())), 
				inf.getIntent(), ConceptType.FORMAL_CONCEPT));

		for (int g = 0; g < formalContext.objectCount(); g++) {
			for (Concept concept : formalContext.getConceptsOf(ConceptType.FORMAL_CONCEPT).get()) {
				Intent mOpr = new Intent(attributesFunction.apply(Arrays.asList(g)));
				Optional<Intent> intersectIntent = ConceptOperations.intersectIntents(mOpr, concept.getIntent());
				if (intersectIntent.isPresent()) {
					Intent intent = intersectIntent.get();
					if (!formalContext.hasConceptOf(intent, ConceptType.FORMAL_CONCEPT)) {
						Concept newConcept = Concept.create(new Extent(objectsFunction.apply(intent.getIndicies())), intent,
								ConceptType.FORMAL_CONCEPT);
						formalContext.addConcept(newConcept);
					} 
				}
			}
		}
		return formalContext;
	}

}
