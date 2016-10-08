package org.stackwire.fca.contexts;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.stackwire.fca.Concept;

public class BaseContext implements Context {

	@Override
	public boolean addConcept(Concept formalConcept) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addRelation(int objectIndex, int attributeIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean allObjectsHaveAttribute(Collection<Integer> objectIndicies, int attributeIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int attributeCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Context clarify() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getObjectNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[][] getRelations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasRelation(int objectIndex, int attributeIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int objectCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<Set<Integer>> powerSetOfAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Set<Integer>> powerSetOfObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Context reduce() {
		// TODO Auto-generated method stub
		return null;
	}

}
