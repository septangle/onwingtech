package com.onwing.household.base;

import java.util.ArrayList;

public class CommunityTopologyNode {
	private String id; // 对应数据库表主键id
	private String name; // 显示名称
	private String type; // 节点类型或者说是 树的节点深度

	// 指示当前节点所有子节点在数组中的位置
	private ArrayList<Integer> childNodeIndexList = new ArrayList<Integer>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public ArrayList<Integer> getChildNodeIndexList() {
		return childNodeIndexList;
	}

	public void setChildNodeIndexList(ArrayList<Integer> childNodeIndexList) {
		this.childNodeIndexList = childNodeIndexList;
	}

	@Override
	public String toString() {
		String childNodeIndexListStr = "";
		for (Integer childNodeIndex : childNodeIndexList) {
			childNodeIndexListStr += childNodeIndex.toString() + "_";
		}
		childNodeIndexListStr = childNodeIndexListStr.substring(0, childNodeIndexListStr.length() - 1);
		String result = String.format("%s*%s*%s*%s", id, name, type, childNodeIndexListStr);
		return result;
	}

}
