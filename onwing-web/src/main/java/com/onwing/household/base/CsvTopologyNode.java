package com.onwing.household.base;

public class CsvTopologyNode {
	private String name;
	private String type;
	private int parentNodeIndex;

	public CsvTopologyNode(String name, String type, int parentNodeIndex) {
		this.name = name;
		this.type = type;
		this.parentNodeIndex = parentNodeIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getParentNodeIndex() {
		return parentNodeIndex;
	}

	public void setParentNodeIndex(int parentNodeIndex) {
		this.parentNodeIndex = parentNodeIndex;
	}

	@Override
	public boolean equals(Object arg0) {
		//只有名字和父节点index都相同，才被认为是同一个对象（树节点）
		if (arg0 != null && arg0 instanceof CsvTopologyNode) {
			return this.getName().equals(((CsvTopologyNode) arg0).getName())
					&& (this.getParentNodeIndex() == ((CsvTopologyNode) arg0).getParentNodeIndex());
		}
		return false;
	}
}
