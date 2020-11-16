package com.projectV.ProjectV.services;

public class QueryComposer {
    // Sheet1!A1:B2
    // String range = "Sheet1!"+id+":"+id;  read row
    static String SheetName = "Sheet1";
    static int colOffset = 6;
    static int rowOffset = 2;
    String colStart;            // A
    String rowStart;            // 6
    String colEnd;              // AH
    String rowEnd;              // 16


    public String createRange()
    {
        String range = QueryComposer.SheetName + "!" + colStart + rowStart + ":" + colEnd + rowEnd;
        return range;
    }

    public String readRowQuery(String id)
    {
        rowStart = String.valueOf(Integer.parseInt(id) + QueryComposer.colOffset);
        rowEnd = String.valueOf(Integer.parseInt(id) + QueryComposer.colOffset);
        colStart = "";
        colEnd = "";
        return this.createRange();
    }

    public String readAllQuery(int totalEmp)
    {
        rowStart = String.valueOf(QueryComposer.colOffset);
        rowEnd = String.valueOf(totalEmp + QueryComposer.colOffset);
        colStart = "A";
        colEnd = "AH";
        return this.createRange();
    }

    public String readCell(String row, String col)
    {
        rowStart = String.valueOf(Integer.parseInt(row) + QueryComposer.colOffset);
        rowEnd = rowStart;
        colStart = colMapper(col);
        colEnd = colStart;
        return this.createRange();
    }

    public String colMapper(String col)
    {
        String mappedCol;
        int colNum = QueryComposer.rowOffset + Integer.parseInt(col);          // A 65, Z 90
        if (colNum <=26)
        {
            colNum = colNum + 64;
            char ch = (char) colNum;
            mappedCol = String.valueOf(ch);
        }
        else
        {
            colNum = colNum - 26;
            colNum = colNum + 64;
            char ch = (char) colNum;
            mappedCol = "A" + ch;
        }
        return mappedCol;
    }

    public static void main(String[] args) {
        QueryComposer qc = new QueryComposer();
        System.out.println(qc.readRowQuery("10"));          // Sheet1!16:16
        System.out.println(qc.readAllQuery(14));       // Sheet1!A6:AH20
        System.out.println(qc.readCell("10","21"));   // Sheet1!W16:W16

    }

}
