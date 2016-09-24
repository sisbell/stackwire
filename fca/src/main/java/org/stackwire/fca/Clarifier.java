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

import java.util.HashSet;
import java.util.Set;

/**
 * Removes duplicate rows and duplicate columns from the formal context
 */
public final class Clarifier {

	public static class ClarifierResult {

		private final Set<Integer> duplicateRows;

		private final Set<Integer> duplicateColumns;

		private int[][] clarifiedCrossTable;

		public ClarifierResult(int[][] clarifiedCrossTable, Set<Integer> duplicateRows, Set<Integer> duplicateColumns) {
			this.clarifiedCrossTable = clarifiedCrossTable;
			this.duplicateRows = duplicateRows;
			this.duplicateColumns = duplicateColumns;
		}

		public int[][] getClarifiedCrossTable() {
			return clarifiedCrossTable;
		}

		public Set<Integer> getDuplicateColumns() {
			return duplicateColumns;
		}

		public Set<Integer> getDuplicateRows() {
			return duplicateRows;
		}

	}
	
	/**
	 * Removes duplicate rows and duplicate columns from the specified cross table and returns results
	 * 
	 * @param crossTable source cross table to analyze
	 * @return results
	 */
	public static ClarifierResult clarify(int crossTable[][]) {
		Set<Integer> duplicateRows = new HashSet<>();
		for (int i = 0; i < crossTable.length - 1; i++) {
			duplicateRows.addAll(duplicateRows(crossTable, crossTable[i], i + 1, duplicateRows));
		}

		Set<Integer> duplicateColumns = new HashSet<>();
		for (int j = 0; j < crossTable.length - 1; j++) {
			int[] sourceColumn = new int[crossTable.length];
			for (int i = 0; i < crossTable.length - 1; i++) {
				sourceColumn[i] = crossTable[i][j];
			}
			duplicateColumns.addAll(duplicateColumns(crossTable, sourceColumn, j + 1, duplicateColumns));
		}

		return new ClarifierResult(remove(crossTable, duplicateRows, duplicateColumns), duplicateRows,
				duplicateColumns);
	}

	private Clarifier() { }

}
