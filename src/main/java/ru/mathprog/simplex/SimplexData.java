package ru.mathprog.simplex;

import java.util.ArrayList;

public class SimplexData {
    ArrayList<ArrayList<Float>> array;
    ArrayList<Float> priceArray;
    ArrayList<Float> countArray;
    ArrayList<String> basis, free;
    Integer rows, cols, exprLength;

    SimplexData(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.exprLength = (rows - 1) * cols;
        array = new ArrayList<>(rows - 1);
        for(int i = 0; i < rows - 1; i++){
            ArrayList<Float> n = new ArrayList<>(cols);
            array.add(n);
            for(int j = 0; j < exprLength; j++){
                n.add(0.f);
            }
        }
        priceArray = new ArrayList<>(exprLength);
        for (int i = 0; i < exprLength; i++) {
            priceArray.add(0.f);
        }
        countArray = new ArrayList<>();
        for(int i = 0; i < cols + 1; i++) {
            countArray.add(0.f);
        }

        basis = new ArrayList<>();
        free = new ArrayList<>();

        for (int i = 0; i < cols * rows; i++) {
            if(i % rows == 0) basis.add("x" + (i + 1));
            else free.add("x" + (i + 1));
        }
    }

    void parseData(InputData data) {
        for(int i = 1; i < data.prices.length; i++) {
            for (int j = 0; j < data.counts.length; j++) {
                setElem(j, (i - 1) + j * cols, data.matrix[i][j].getValue() / data.matrix[0][j].getValue());
            }
        }

        for(int i = 0; i < cols; i++) {
            setCount(i, data.counts[i].getValue() / data.matrix[0][i].getValue());
        }

        float sum = 0;
        for(float x : countArray) {
            sum += x;
        }
        setCount(cols, data.prices[0].getValue() * sum);

        for(int i = 1; i < data.prices.length; i++) {
            for (int j = 0; j < cols; j++) {
                int col = (i - 1) * cols + j;
                sum = 0;
                for(int x = 0; x < array.size(); x++) {
                    sum += getElem(x, col);
                }
                setPrice(col,  data.prices[0].getValue() * sum - data.prices[j+1].getValue());
            }
        }
    }

    void setPrice(int i, float val){
        this.priceArray.set(i, val);
    }

    void setCount(int i, float val) {
        this.countArray.set(i, val);
    }

    void setElem(int i, int j, float val) {
        this.array.get(i).set(j, val);
    }

    Float getPrice(int i) {
        return this.priceArray.get(i);
    }

    Float getCount(int i) {
        return this.countArray.get(i);
    }

    Float getElem(int i, int j) {
        return this.array.get(i).get(j);
    }

    @Override
    public String toString(){
        return priceArray.toString() + countArray.toString();
    }

    public ArrayList<ArrayList<String>> rowsIter() {
        ArrayList<ArrayList<String>> arr = new ArrayList<>();
        for (int i = 0; i < rows + 1; i++) {
            ArrayList<String> n = new ArrayList<>();
            arr.add(n);
            for (int j = 0; j < exprLength + 2; j++) {
                if(i == 0) {
                    if(j == 0) n.add(" ");
                    else if(j == 1) n.add("b");
                    else n.add(free.get(j - 2));
                }
                else if(i == rows) {
                    if(j == 1) n.add(String.format("%.2f", getCount(i - 1)));
                    else if(j == 0) n.add("F");
                    else n.add(String.format("%.2f", getPrice(j - 2)));
                }
                else {
                    if(j == 1) n.add(String.format("%.2f", getCount(i - 1)));
                    else if(j == 0) n.add(basis.get(i - 1));
                    else n.add(String.format("%.2f", getElem(i - 1, j - 2)));
                }
            }
        }
        return arr;
    }

    public void swap(int row, int col) {
        String s = basis.get(row);
        basis.set(row, free.get(col));
        free.set(col, s);
    }
}
