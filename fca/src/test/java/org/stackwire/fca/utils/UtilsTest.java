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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class UtilsTest {
	
	@Test
	public void duplicateAndRemoveColumns() throws Exception {
		int[][] crossTable = { { 1, 1, 1, 1 }, { 1, 0, 1, 1 }, { 0, 1, 1, 1 }, { 0, 1, 1, 1 }, { 1, 0, 0, 0 } };
		int[][] expected = { { 1, 1 }, { 1, 0 }, { 0, 1 }, { 0, 1 }, { 1, 0 } };

		Set<Integer> results = Utils.duplicateColumns(crossTable, new int[] { 1, 1, 1, 1, 0 }, 0, null);
		int[][] result = Utils.remove(crossTable, Arrays.asList(), results);

		assertTrue(Arrays.deepEquals(expected, result));

	}

	@Test
	public void duplicateColumns() throws Exception {
		int[][] crossTable = { { 1, 1, 1, 1 }, { 1, 0, 1, 1 }, { 0, 1, 1, 1 }, { 0, 1, 1, 1 }, { 1, 0, 0, 0 } };

		Set<Integer> results = Utils.duplicateColumns(crossTable, new int[] { 1, 1, 1, 1, 0 }, 0, null);
		assertEquals(2, results.size());
		assertTrue(results.contains(2));
		assertTrue(results.contains(3));
	}

	@Test
	public void duplicateRows() throws Exception {
		int[][] crossTable = { { 1, 1, 1, 1 }, { 1, 0, 1, 1 }, { 0, 1, 1, 1 }, { 0, 1, 1, 1 }, { 1, 1, 1, 1 } };

		Set<Integer> results = Utils.duplicateRows(crossTable, new int[] { 1, 1, 1, 1 }, 0, null);
		assertEquals(2, results.size());
		assertTrue(results.contains(0));
		assertTrue(results.contains(4));
	}

	@Test
	public void removeColumn() throws Exception {
		int[][] crossTable = { { 1, 1, 1, 1 }, { 1, 0, 1, 1 }, { 0, 1, 1, 1 }, { 0, 1, 1, 1 }, { 1, 0, 0, 0 } };
		int[][] expected = { { 1, 1, 1 }, { 1, 0, 1 }, { 0, 1, 1 }, { 0, 1, 1 }, { 1, 0, 0 } };

		int[][] result = Utils.remove(crossTable, Arrays.asList(), Arrays.asList(2));

		assertTrue(Arrays.deepEquals(expected, result));
	}

	@Test
	public void removeRow() throws Exception {
		int[][] crossTable = { { 1, 1, 1, 1 }, { 1, 0, 1, 1 }, { 0, 1, 1, 1 }, { 0, 1, 1, 1 }, { 1, 0, 0, 0 } };
		int[][] expected = { { 1, 0, 1, 1 }, { 0, 1, 1, 1 }, { 0, 1, 1, 1 }, { 1, 0, 0, 0 } };

		int[][] result = Utils.remove(crossTable, Arrays.asList(0), Arrays.asList());

		assertTrue(Arrays.deepEquals(expected, result));
	}

	@Test
	public void removeRowAndColumn() throws Exception {
		int[][] crossTable = { { 1, 1, 1, 1 }, { 1, 0, 1, 1 }, { 0, 1, 1, 1 }, { 0, 1, 1, 1 }, { 1, 0, 0, 0 } };
		int[][] expected = { { 1, 0, 1 }, { 0, 1, 1 }, { 0, 1, 1 }, { 1, 0, 0 } };

		int[][] result = Utils.remove(crossTable, Arrays.asList(0), Arrays.asList(2));

		assertTrue(Arrays.deepEquals(expected, result));
	}
}
