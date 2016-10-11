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
import java.io.OutputStream;
import java.util.stream.Collectors;

import org.stackwire.fca.Context;

public class CsvContextWriter {

	public void write(OutputStream outputStream, Context context) throws IOException {
		double[][] relations = context.getRelations();

		StringBuilder sb = new StringBuilder();
		sb.append(",");
		sb.append(context.getAttributeNames().stream().collect(Collectors.joining(","))).append("\r\n");

		int rows = relations.length;
		int cols = relations[0].length;
		for (int i = 0; i < rows; i++) {
			sb.append(context.getObjectNames().get(i)).append(",");
			for (int j = 0; j < cols; j++) {
				sb.append(relations[i][j]).append(",");
			}
			sb.append("\r\n");
		}

		outputStream.write(sb.toString().getBytes());
		outputStream.close();
	}
}
