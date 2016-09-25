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

import java.util.Collection;

import org.stackwire.fca.utils.Utils;

/**
 * Set of objects with the same attributes
 */
public final class Concept {

	/**
	 * The set of things (objects) to which a concept applies
	 */
	public static class Extent extends SemanticIndexSet {

		public Extent() {
			super();
		}

		public Extent(Collection<Integer> indicies) {
			super(indicies);
		}

		public Extent(int index) {
			super(index);
		}
	}

	/**
	 * The attributes an object must have to belong to a concept
	 */
	public static class Intent extends SemanticIndexSet {

		public Intent() {
			super();
		}

		public Intent(Collection<Integer> indicies) {
			super(indicies);
		}

		public Intent(int index) {
			super(index);
		}
	}

	public static Concept create(int index, Extent extent, ConceptType conceptType) {
		return new Concept(index, extent, conceptType);
	}

	public static Concept create(int index, Extent extent, Intent intent, ConceptType conceptType) {
		return new Concept(index, extent, intent, conceptType);
	}

	/**
	 * Returns supremum. This concept contains the set of objects that have the
	 * null element as an attribute element. This is all objects.
	 * 
	 * @param count
	 *            the count of objects in formal concept
	 * @return supremum, which is the formal concept with null attributes
	 */
	public static Concept newSupremum(int count) {
		return Concept.create(0, new Extent(Utils.range(0, count)), ConceptType.FORMAL_CONCEPT);
	}

	private int index;

	private Extent extent;

	private Intent intent;
	
	private ConceptType conceptType;

	private Concept(int index) {
		this.index = index;
		this.extent = new Extent();
		this.intent = new Intent();
	}

	private Concept(int index, Extent extent, ConceptType conceptType) {
		this.index = index;
		this.extent = extent;
		this.intent = new Intent();
		this.conceptType = conceptType;
	}

	private Concept(int index, Extent extent, Intent intent, ConceptType conceptType) {
		this.index = index;
		this.extent = extent;
		this.intent = intent;
		this.conceptType = conceptType;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Concept other = (Concept) obj;
		if (extent == null) {
			if (other.extent != null)
				return false;
		} else if (!extent.equals(other.extent))
			return false;
		if (intent == null) {
			if (other.intent != null)
				return false;
		} else if (!intent.equals(other.intent))
			return false;
		return true;
	}

	public ConceptType getConceptType() {
		return conceptType;
	}

	public Extent getExtent() {
		return extent;
	}

	public int getIndex() {
		return index;
	}

	public Intent getIntent() {
		return intent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extent == null) ? 0 : extent.hashCode());
		result = prime * result + ((intent == null) ? 0 : intent.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "\r\nFormalConcept [index=" + index + ", extent=" + extent + ", intent=" + intent + "]";
	}
	
	
}
