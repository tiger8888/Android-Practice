package com.chensuworks.nativeeditor.tokenizer;

public class Token {

    private int index;
    private int type;
    private int line;
    private int column;
    private int length;
    private String value;

    public Token(int index, int type, int line, int column, int length, String value) {
        this.index = index;
        this.type = type;
        this.line = line;
        this.column = column;
        this.length = length;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public boolean equals(Token other) {
        return (other != null) && (this.index == other.index) && (this.type == other.type)
                && (this.line == other.line) && (this.column == other.column)
                && (this.length == other.length) && (this.value.equals(other.value));
    }

    public PositionInfo getPositionInfo() {
        return new PositionInfo(line, column);
    }

    public int getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
