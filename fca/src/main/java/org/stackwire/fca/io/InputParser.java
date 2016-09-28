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
package org.stackwire.fca.io;

import java.io.IOException;
import java.io.InputStream;

import org.stackwire.fca.FormalConceptGenerator;
import org.stackwire.fca.FormalContext;

/**
 * Service for creating a formal context from the specified input
 */
public interface InputParser {

	/**
	 * Parse input stream and return formal context. It is required that the returned formal context must
	 * already have all concepts generated.
	 * 
	 * @param inputStream input stream to parse
	 * @return formal context
	 * @param generator the concept generator to use for adding concepts
	 * @throws IOException if I/O or validation exception with stream
	 */
	FormalContext parse(InputStream inputStream, FormalConceptGenerator generator) throws IOException;
	
}
