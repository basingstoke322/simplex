package ru.mathprog.simplex;

import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Matrix implements Serializable, Iterable<Cell> {
    private Integer rawCount;
    private Integer partCount;
    Cell[][] matrix;

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
        matrix = new Cell[this.rawCount][this.partCount];
        for (int i = 0; i < this.rawCount; i++) {
            for (int j = 0; j < this.partCount; j++) {
                matrix[i][j] = new Cell(i + Integer.toString(j));
            }
        }
    }

    public void setPartCount(Integer partCount) {
        this.partCount = partCount;
    }

    public Integer getPartCount() { return partCount; }

    public void setRawCount(Integer rawCount) {
        this.rawCount = rawCount;
    }

    public Integer getRawCount() {
        return rawCount;
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
                if (j.equals(partCount - 1)) {
                    if (i.equals(rawCount - 1)) {
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
        return makeIter(rawCount);
    }

    public Iterator<String> getPartIter() {
        return makeIter(partCount);
    }

    public void setCell(String s, String val) {
        String[] arr = s.split("");
        int i = Integer.parseInt(arr[0]);
        int j = Integer.parseInt(arr[1]);
        this.matrix[i][j].setValue(val);
    }
}
