package org.stackwire.fca.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.stackwire.fca.utils.SemanticIndexSet;

public class SemanticIndexSetTest {

	@Test
	public void createWithNullOk() {
		new SemanticIndexSet(null);
	}
	
	@Test
	public void addDuplicate() {
		SemanticIndexSet set = new SemanticIndexSet();
		set.addIndex(0);
		set.addIndex(0);
		
		assertEquals(1, set.getCount());
		assertTrue(set.hasIndex(0));
	}
}
