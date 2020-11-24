package ru.mathprog.simplex;

public class Cell {
    Float value;
    String strValue;

    Cell(Float n){
        this.value = n;
        this.strValue = Float.toString(n);
    }

    Cell(String s){
        this.strValue = s;
        this.value = Float.parseFloat(s);
    }

    public void setValue(Float value) {
        this.value = value;
        this.strValue = Float.toString(value);
    }

    public void setValue(String strValue) {
        this.strValue = strValue;
        try {
            this.value = Float.parseFloat(strValue);
        }
        catch (NumberFormatException e) {
            this.value = 0.f;
        }
    }

    public Float getValue() {
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
