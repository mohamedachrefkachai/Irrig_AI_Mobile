package com.irrigai.mobile.model;

public class Tree {
    private String id;
    private String treeCode;
    private int rowNumber;
    private int indexInRow;
    private String healthStatus;

    public Tree() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTreeCode() { return treeCode; }
    public void setTreeCode(String treeCode) { this.treeCode = treeCode; }

    public int getRowNumber() { return rowNumber; }
    public void setRowNumber(int rowNumber) { this.rowNumber = rowNumber; }

    public int getIndexInRow() { return indexInRow; }
    public void setIndexInRow(int indexInRow) { this.indexInRow = indexInRow; }

    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }
}