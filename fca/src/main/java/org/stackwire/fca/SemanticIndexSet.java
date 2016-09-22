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
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SemanticIndexSet {

	protected final ArrayList<Integer> indicies = new ArrayList<>();
	
	public SemanticIndexSet() {		
	}
	
	public SemanticIndexSet(int index) {
		indicies.add(index);
	}
	
	public SemanticIndexSet(Collection<Integer> indicies) {
		this.indicies.addAll(indicies);
	}
	
	public SemanticIndexSet addIndex(Integer object) {
		if (!indicies.contains(object)) {
			indicies.add(object);
		}
		return this;
	}
	
	public boolean isEmpty() {
		return !indicies.isEmpty();
	}
	
	public int getCount() {
		return indicies.size();
	}
	
	public Integer getIndex(int i) {
		return indicies.get(i);
	}
	
	public ArrayList<Integer> getIndicies() {
		return indicies;
	}
	
	public List<Integer> filter(Predicate<Integer> p) {
		return indicies.stream().filter(p).collect(Collectors.toList());
	}
	
	public Stream<Integer> stream() {
		return indicies.stream();
	}
	
	public boolean hasIndex(int i) {
		return indicies.contains(i);
	}

	@Override
	public String toString() {
		return indicies.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((indicies == null) ? 0 : indicies.hashCode());
		return result;
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
}
