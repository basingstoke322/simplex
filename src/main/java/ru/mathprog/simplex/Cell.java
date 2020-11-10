package ru.mathprog.simplex;

public class Cell {
    Integer value;
    String strValue;

    Cell(Integer n){
        this.value = n;
        this.strValue = Integer.toString(n);
    }

    Cell(String s){
        this.strValue = s;
        this.value = Integer.parseInt(s);
    }

    public void setValue(Integer value) {
        this.value = value;
        this.strValue = Integer.toString(value);
    }

    public void setValue(String strValue) {
        this.strValue = strValue;
        this.value = Integer.parseInt(strValue);
    }

    public Integer getValue() {
        return value;
    }

    public String getStrValue() {
        return strValue;
    }

    @Override
    public String toString(){
        return this.strValue;
    }
}
