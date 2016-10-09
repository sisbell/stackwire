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

import java.util.ArrayList;

import org.stackwire.fca.Concept;
import org.stackwire.fca.Concept.Extent;
import org.stackwire.fca.Concept.Intent;
import org.stackwire.fca.ConceptGenerator;
import org.stackwire.fca.ConceptType;
import org.stackwire.fca.Context;
import org.stackwire.fca.tags.IndexTag;

/**
 * Formal concept generator implemented with the in-close algorithm
 * 
 */
public final class InCloseConceptGenerator implements ConceptGenerator {

	private ArrayList<ArrayList<Integer>> A = new ArrayList<>();

	private ArrayList<ArrayList<Integer>> B = new ArrayList<>();

	private double threshold;

	@Override
	public Context generateConceptsFor(Context formalContext, double threshold) {
		this.threshold = threshold;

		Concept sup = Concept.newSupremum(formalContext.objectCount() - 1);
		formalContext.addConcept(sup);

		A.add(new ArrayList<>(sup.getExtent().getIndicies()));
		B.add(new ArrayList<>());

		inClose(formalContext, 0, 0);
		for (int i = 0; i < A.size(); i++) {
			if (!B.get(i).isEmpty()) {
				Concept concept = new Concept.ConceptBuilder(new Extent(A.get(i)), new Intent(B.get(i)))
						.conceptTag(new IndexTag(i)).build();
				formalContext.addConcept(concept);
			}
		}
		return formalContext;
	}

	/**
	 * Generate formal concepts using the in-close algorithm
	 * 
	 * @param formalContext
	 *            formal context
	 * @param currentFormalConcept
	 *            current formal concept
	 * @param attributeColumnOffset
	 *            start position of the column in the boolean matrix
	 */
	protected void inClose(Context formalContext, int r, int y) {
		int rnew = r + 1;

		for (int j = y; j <= formalContext.attributeCount() - 1; j++) {
			A.add(rnew, new ArrayList<Integer>());
			B.add(rnew, new ArrayList<Integer>());
			for (int i : A.get(r)) {
				if (formalContext.hasRelation(i, j, threshold)) {
					if (!A.get(rnew).contains(i)) {
						A.get(rnew).add(i);
					}
				}
			}
			if (!A.get(rnew).isEmpty()) {
				if (A.get(rnew).size() == A.get(r).size()) {
					if (!B.get(r).contains(j)) {
						B.get(r).add(j);
					}
				} else if (isCanonical(formalContext, r, rnew, j - 1)) {
					if (!B.get(rnew).containsAll(B.get(r))) {
						B.get(rnew).addAll(B.get(r));
					}
					if (!B.get(rnew).contains(j)) {
						B.get(rnew).add(j);
					}

					inClose(formalContext, rnew, j + 1);
				}
			}
		}
	}

	protected boolean isCanonical(Context formalContext, int r, int rnew, int y) {
		for (int k = B.get(r).size() - 1; k >= 0; k--) {
			for (int j = y; j >= B.get(r).get(k) + 1; j--) {
				int h = 0;
				for (h = 0; h <= A.get(rnew).size() - 1; h++) {
					if (!formalContext.hasRelation(A.get(rnew).get(h), j, threshold)) {
						break;
					}
				}
				if (h == A.get(rnew).size()) {
					return false;
				}
			}
			y = B.get(r).get(k) - 1;
		}

		for (int j = y; j >= 0; j--) {
			int h = 0;
			for (h = 0; h <= A.get(rnew).size() - 1; h++) {
				if (!formalContext.hasRelation(A.get(rnew).get(h), j, threshold)) {
					break;
				}
			}
			if (h == A.get(rnew).size()) {
				return false;
			}
		}
		return true;
	}

}
