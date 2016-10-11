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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class Utils {

	public static Set<Integer> duplicateColumns(double[][] crossTable, double[] sourceColumn, int columnOffset,
			Set<Integer> skipColumns) {
		Set<Integer> dupColumns = new HashSet<>();
		for (int j = columnOffset; j < crossTable[0].length; j++) {
			if (skipColumns != null && skipColumns.contains(j)) {
				continue;
			}

			boolean isDuplicationColumn = true;
			for (int i = 0; i < crossTable.length; i++) {
				if (Math.abs(sourceColumn[i] - crossTable[i][j]) > .0000001) {
					isDuplicationColumn = false;
					break;
				}
			}
			if (isDuplicationColumn) {
				dupColumns.add(j);
			}
		}

		return dupColumns;
	}

	public static Set<Integer> duplicateRows(double[][] crossTable, double[] sourceRow, int rowOffset,
			Set<Integer> skipRows) {
		Set<Integer> dupRows = new HashSet<>();
		for (int i = rowOffset; i < crossTable.length; i++) {
			if (skipRows != null && skipRows.contains(i)) {
				continue;
			}
			if (Arrays.equals(crossTable[i], sourceRow)) {
				dupRows.add(i);
			}
		}
		return dupRows;
	}

	public static Set<Integer> intersect(Set<Set<Integer>> set) {
		Iterator<Set<Integer>> it = set.iterator();
		Set<Integer> result = it.next();
		while (it.hasNext()) {
			result = Sets.intersection(result, Sets.newHashSet(it.next()));
		}
		return result;
	}

	public static List<Integer> range(int start, int end) {
		return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
	}

	public static Set<Integer> rangeSet(int start, int end) {
		return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toSet());
	}

	public static IntStream rangeStream(int start, int end) {
		return IntStream.rangeClosed(start, end);
	}

	/**
	 * 
	 * @param crossTable
	 * @param rowsToRemove
	 *            rows to remove from cross table
	 * @param columnsToRemove
	 *            columns to remove from cross table
	 * @return
	 */
	public static double[][] remove(double[][] crossTable, Collection<Integer> rowsToRemove,
			Collection<Integer> columnsToRemove) {
		if (crossTable == null) {
			throw new IllegalArgumentException("crossTable is null");
		}

		if (rowsToRemove == null) {
			rowsToRemove = ImmutableSet.of();
		}
		if (columnsToRemove == null) {
			columnsToRemove = ImmutableSet.of();
		}

		if (rowsToRemove.isEmpty() && columnsToRemove.isEmpty()) {
			return crossTable;
		}

		int rows = crossTable.length;
		int columns = crossTable[0].length;
		double[][] newCrossTable = new double[rows - rowsToRemove.size()][columns - columnsToRemove.size()];

		int newRow = 0;
		for (int i = 0; i < rows; ++i) {
			if (rowsToRemove.contains(i)) {
				continue;
			}
			int newColumn = 0;
			for (int j = 0; j < columns; ++j) {
				if (columnsToRemove.contains(j)) {
					continue;
				}
				newCrossTable[newRow][newColumn++] = crossTable[i][j];
			}
			newRow++;
		}
		return newCrossTable;
	}

	public static Set<Set<Integer>> toSets(SemanticIndexSet... indexSet) {
		Set<Set<Integer>> items = Arrays.stream(indexSet).map(SemanticIndexSet::getIndicies)
				.collect(Collectors.toSet());
		if (items.isEmpty()) {
			return null;
		}
		return items;
	}
}
