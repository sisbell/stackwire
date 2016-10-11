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
package org.stackwire.fca.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An ordered set of integers
 */
public class SemanticIndexSet {

	protected final Set<Integer> indicies = new HashSet<>();

	public SemanticIndexSet() {
	}

	public SemanticIndexSet(Collection<Integer> indicies) {
		if (indicies != null) {
			this.indicies.addAll(indicies);
		}
	}

	/**
	 * Add index to set
	 * 
	 * @param index
	 *            index to add
	 * @return current instance of SemanticIndexSet
	 */
	public final SemanticIndexSet addIndex(Integer index) {
		if (!indicies.contains(index)) {
			indicies.add(index);
		}
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SemanticIndexSet other = (SemanticIndexSet) obj;
		if (!indicies.containsAll(other.getIndicies()) || !other.getIndicies().containsAll(indicies))
			return false;
		return true;
	}

	/**
	 * Return unmodifiable set of indicies that matches the specified predicate.
	 * 
	 * @param predicate
	 *            predicate
	 * 
	 * @return unmodifiable set of indicies that matches the specified predicate
	 */
	public final Set<Integer> filter(Predicate<Integer> predicate) {
		return Collections.unmodifiableSet(indicies.stream().filter(predicate).collect(Collectors.toSet()));
	}

	/**
	 * First index
	 * 
	 * @return
	 */
	public final Optional<Integer> first() {
		return indicies.stream().findFirst();
	}

	public final int getCount() {
		return indicies.size();
	}

	public final Set<Integer> getIndicies() {
		return Collections.unmodifiableSet(indicies);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + indicies.hashCode();
		return result;
	}

	/**
	 * Return true if underlying set contains the specified index, otherwise
	 * returns false
	 * 
	 * @param index
	 *            the index to check
	 * 
	 * @return true if underlying set contains the specified index, otherwise
	 *         returns false
	 */
	public final boolean hasIndex(int index) {
		return indicies.contains(index);
	}

	/**
	 * Returns true if set is empty, otherwise false
	 * 
	 * @return true if set is empty, otherwise false
	 */
	public final boolean isEmpty() {
		return !indicies.isEmpty();
	}

	/**
	 * Only keep first element of indicies
	 */
	public final void keepFirst() {
		Optional<Integer> first = first();
		if (first.isPresent()) {
			indicies.clear();
			indicies.add(first.get());
		}
	}

	public final Stream<Integer> stream() {
		return indicies.stream();
	}

	@Override
	public String toString() {
		return indicies.toString();
	}
}
