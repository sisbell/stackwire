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

import static org.stackwire.fca.utils.Utils.duplicateColumns;
import static org.stackwire.fca.utils.Utils.duplicateRows;
import static org.stackwire.fca.utils.Utils.remove;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.stackwire.fca.utils.Pair;
import org.stackwire.fca.utils.Sets;
import org.stackwire.fca.utils.Utils;

/**
 * Removes duplicate rows and duplicate columns from the formal context
 */
public final class Clarifier {

	/**
	 * Result of a clarify operation
	 */
	public static class ClarifierResult {

		/**
		 * Clarified cross table
		 */
		private double[][] clarifiedCrossTable;

		/**
		 * Each set within the collection is a set of columns that are
		 * duplicates of each other. If the set only contains one element then
		 * is has no duplicate columns.
		 */
		private final Collection<Set<Integer>> collapsedColumns;

		/**
		 * Each set within the collection is a set of rows that are duplicates
		 * of each other. If the set only contains one element then is has no
		 * duplicate rows.
		 */
		private final Collection<Set<Integer>> collapsedRows;

		public ClarifierResult(double[][] clarifiedCrossTable, Collection<Set<Integer>> collapsedRows,
				Collection<Set<Integer>> collapsedColumns) {
			this.clarifiedCrossTable = clarifiedCrossTable;
			this.collapsedRows = collapsedRows;
			this.collapsedColumns = collapsedColumns;
		}

		public double[][] getClarifiedCrossTable() {
			return clarifiedCrossTable;
		}

		public Collection<Set<Integer>> getCollapsedColumns() {
			return collapsedColumns;
		}

		public Collection<Set<Integer>> getCollapsedRows() {
			return collapsedRows;
		}

	}

	/**
	 * Removes duplicate rows and duplicate columns from the specified cross
	 * table and returns results
	 * 
	 * @param crossTable
	 *            source cross table to analyze
	 * @return results clarifier result
	 */
	public static ClarifierResult clarify(double[][] crossTable) {

		Pair<Set<Integer>, Collection<Set<Integer>>> row = processRows(crossTable);
		Pair<Set<Integer>, Collection<Set<Integer>>> column = processColumns(crossTable);

		return new ClarifierResult(remove(crossTable, row.t, column.t), row.u, column.u);
	}

	public static ClarifierResult columnClarify(double[][] crossTable) {
		Pair<Set<Integer>, Collection<Set<Integer>>> column = processColumns(crossTable);
		return new ClarifierResult(remove(crossTable, null, column.t),
				Sets.wrapSets(Utils.rangeSet(0, crossTable[0].length)), column.u);
	}

	private static Pair<Set<Integer>, Collection<Set<Integer>>> processColumns(double[][] crossTable) {
		Set<Integer> duplicateColumns = new HashSet<>();
		Collection<Set<Integer>> collapsedColumns = new ArrayList<>();
		for (int j = 0; j < crossTable[0].length - 1; j++) {
			double[] sourceColumn = new double[crossTable.length];
			for (int i = 0; i < crossTable.length - 1; i++) {
				sourceColumn[i] = crossTable[i][j];
			}
			Set<Integer> dc = duplicateColumns(crossTable, sourceColumn, j + 1, duplicateColumns);
			if (!dc.isEmpty()) {
				collapsedColumns.add(Sets.union(dc, j));
				duplicateColumns.addAll(dc);
			} else if (!duplicateColumns.contains(j)) {
				collapsedColumns.add(Sets.singletonSet(j));
			}
		}

		if (!duplicateColumns.contains(crossTable[0].length - 1)) {
			collapsedColumns.add(Sets.singletonSet(crossTable[0].length - 1));
		}
		return new Pair<>(duplicateColumns, collapsedColumns);
	}

	private static Pair<Set<Integer>, Collection<Set<Integer>>> processRows(double[][] crossTable) {
		Set<Integer> duplicateRows = new HashSet<>();
		Collection<Set<Integer>> collapsedRows = new ArrayList<>();
		for (int i = 0; i < crossTable.length - 1; i++) {
			Set<Integer> dr = duplicateRows(crossTable, crossTable[i], i + 1, duplicateRows);
			if (!dr.isEmpty()) {
				duplicateRows.addAll(dr);
				collapsedRows.add(Sets.union(dr, i));
			} else if (!duplicateRows.contains(i)) {
				collapsedRows.add(Sets.singletonSet(i));
			}
		}

		if (!duplicateRows.contains(crossTable.length - 1)) {
			collapsedRows.add(Sets.singletonSet(crossTable.length - 1));
		}
		return new Pair<>(duplicateRows, collapsedRows);
	}

	public static ClarifierResult rowClarify(double[][] crossTable) {
		Pair<Set<Integer>, Collection<Set<Integer>>> row = processRows(crossTable);
		return new ClarifierResult(remove(crossTable, row.t, null), row.u,
				Sets.wrapSets(Utils.rangeSet(0, crossTable.length)));
	}

	private Clarifier() {
	}

}
