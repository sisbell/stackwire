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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SemanticIndexSet {

	protected final Set<Integer> indicies = new HashSet<>();

	public SemanticIndexSet() {
	}

	public SemanticIndexSet(Collection<Integer> indicies) {
		this.indicies.addAll(indicies);
	}

	public SemanticIndexSet(int index) {
		indicies.add(index);
	}

	public SemanticIndexSet addIndex(Integer object) {
		if (!indicies.contains(object)) {
			indicies.add(object);
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
		if (indicies == null) {
			if (other.getIndicies() != null)
				return false;
		} else if (!indicies.containsAll(other.getIndicies()))
			return false;
		return true;
	}

	public Set<Integer> filter(Predicate<Integer> p) {
		return indicies.stream().filter(p).collect(Collectors.toSet());
	}

	/**
	 * First index
	 * 
	 * @return
	 */
	public Optional<Integer> first() {
		return indicies.stream().findFirst();
	}

	public int getCount() {
		return indicies.size();
	}

	public Set<Integer> getIndicies() {
		return indicies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((indicies == null) ? 0 : indicies.hashCode());
		return result;
	}
	
	public boolean hasIndex(int i) {
		return indicies.contains(i);
	}
	
	public boolean isEmpty() {
		return !indicies.isEmpty();
	}

	/**
	 * Only keep first element of indicies
	 */
	public void keepFirst() {
		Optional<Integer> first = first();
		if(first.isPresent()) {
			indicies.clear();
			indicies.add(first.get());
		}	
	}

	public Stream<Integer> stream() {
		return indicies.stream();
	}

	@Override
	public String toString() {
		return indicies.toString();
	}
}
