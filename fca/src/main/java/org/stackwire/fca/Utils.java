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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

	public static List<Integer> range(int start, int end) {
		return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
	}
	
	public static Set<Integer> rangeSet(int start, int end) {
		return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toSet());
	}
	
	public static IntStream rangeStream(int start, int end) {
		return IntStream.rangeClosed(start, end);
	}
}
