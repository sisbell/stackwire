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
import java.util.ListIterator;
import java.util.Set;

import org.stackwire.fca.operators.AttributesCommonToObjectsFunction;
import org.stackwire.fca.operators.ObjectsCommonToAttributesFunction;

/**
 * Set of objects with the same attributes
 */
public final class FormalConcept {

	/**
	 * The set of things (objects) to which a concept applies
	 */
	public static class Extent extends SemanticIndexSet {

		public Extent() {
			super();
		}

		public Extent(int index) {
			super(index);
		}

		public Extent(Collection<Integer> indicies) {
			super(indicies);
		}
	}

	/**
	 * The attributes an object must have to belong to a concept
	 */
	public static class Intent extends SemanticIndexSet {

		public Intent() {
			super();
		}

		public Intent(int index) {
			super(index);
		}

		public Intent(Collection<Integer> indicies) {
			super(indicies);
		}

		public ListIterator<Integer> getReversedIndiciesIterator() {
			return indicies.listIterator(getCount());
		}
	}

	public static FormalConcept create(int index, Extent extent) {
		return new FormalConcept(index, extent);
	}

	public static FormalConcept create(int index, Extent extent, Intent intent) {
		return new FormalConcept(index, extent, intent);
	}

	/**
	 * Returns true if formal concept is valid, otherwise false. 
	 * 
	 * Requires the following to be true: A''= B' = A
	 * 
	 * @param formalContext formal context associated with this formal concept
	 * @return true if formal concept is valid, otherwise false
	 */
	public boolean validate(FormalContext formalContext) {
		AttributesCommonToObjectsFunction attributesFunction = new AttributesCommonToObjectsFunction(
				formalContext.getRelations());
		ObjectsCommonToAttributesFunction objectsFunction = new ObjectsCommonToAttributesFunction(
				formalContext.getRelations());
		Set<Integer> result = attributesFunction.andThen(objectsFunction).apply(this.getExtent().getIndicies());
		if (getExtent().getCount() == 0 || getIntent().getCount() == 0 || result.size() != getExtent().getCount()) {
			return false;
		}
		return result.containsAll(getExtent().getIndicies()) && getExtent().getIndicies().containsAll(result);
	}

	/**
	 * Returns supremum. This concept contains the set of objects that have the
	 * null element as an attribute element. This is all objects.
	 * 
	 * @param count
	 *            the count of objects in formal concept
	 * @return supremum, which is the formal concept with null attributes
	 */
	public static FormalConcept newSupremum(int count) {
		return FormalConcept.create(0, new Extent(Utils.range(0, count)));
	}

	private int index;

	private Extent extent;

	private Intent intent;

	private FormalConcept(int index) {
		this.index = index;
		this.extent = new Extent();
		this.intent = new Intent();
	}

	private FormalConcept(int index, Extent extent) {
		this.index = index;
		this.extent = extent;
		this.intent = new Intent();
	}

	private FormalConcept(int index, Extent extent, Intent intent) {
		this.index = index;
		this.extent = extent;
		this.intent = intent;
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
	public String toString() {
		return "\r\nFormalConcept [index=" + index + ", extent=" + extent + ", intent=" + intent + "]";
	}

}
