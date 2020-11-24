package ru.mathprog.simplex;

import java.io.Serializable;
import java.util.Iterator;

public class InputData implements Serializable, Iterable<Cell> {
    private Integer rows;
    private Integer cols;
    Cell[][] matrix;
    Cell[] prices;
    Cell[] counts;

    private Iterator<String> makeIter(Integer count){
        return new Iterator<>() {
            Integer i = 0;
            final Integer max = count;

            @Override
            public boolean hasNext() {
                return i < max;
            }

            @Override
            public String next() {
                return Integer.toString(1 + i++);
            }
        };
    }

    public void init(){
        matrix = new Cell[this.rows][this.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                matrix[i][j] = new Cell(i + Integer.toString(j));
            }
        }
        counts = new Cell[this.cols];
        for(int i = 0; i < this.cols; i++) {
            counts[i] = new Cell(0.f);
        }
        prices = new Cell[this.rows];
        for(int i = 0; i < this.rows; i++) {
            prices[i] = new Cell(0.f);
        }
    }

    public void setCols(Integer cols) {
        this.cols = cols;
    }

    public Integer getCols() { return cols; }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getRows() {
        return rows;
    }

    public void setMatrix(Cell[][] matrix) {
        this.matrix = matrix;
    }

    public Cell[][] getMatrix() {
        return matrix;
    }

    public Iterator<Cell> iterator() {
        return new Iterator<>() {
            Integer i = 0, j = 0;
            boolean b = true;

            @Override
            public boolean hasNext() {
                return b;
            }

            @Override
            public Cell next() {
                Cell res = matrix[i][j];
                if (j.equals(cols - 1)) {
                    if (i.equals(rows - 1)) {
                        b = false;
                    }
                    i++;
                    j = 0;
                } else {
                    j++;
                }
                return res;
            }
        };
    }

    public Iterator<String> getRawIter(){
        return makeIter(rows);
    }

    public Iterator<String> getPartIter() {
        return makeIter(cols);
    }

    public void setMatrixCell(String s, String val) {
        String[] arr = s.split("");
        int i = Integer.parseInt(arr[1]) - 1;
        int j = Integer.parseInt(arr[2]) - 1;
        this.matrix[i][j].setValue(val);
    }

    public void setPricesCell(String s, String val) {
        String[] arr = s.split("");
        int i = Integer.parseInt(arr[1]) - 1;
        this.prices[i].setValue(val);
    }

    public void setCountsCell(String s, String val) {
        String[] arr = s.split("");
        int j = Integer.parseInt(arr[1]) - 1;
        this.counts[j].setValue(val);
    }
}
