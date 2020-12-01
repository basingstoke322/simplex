package ru.mathprog.simplex;

import java.util.ArrayList;

public class Simplex {
    ArrayList<SimplexData> steps = new ArrayList<>();
    SimplexData current;
    public String result;

    Simplex(InputData data) {
        current = new SimplexData(data.getRows(), data.getCols());
        current.parseData(data);
        while (!optimal()){
            SimplexData next = new SimplexData(current.rows, current.cols);
            int resCol = chooseCol(), resRow = chooseRow(resCol);
            float resElem = current.getElem(resRow, resCol);

            steps.add(current);

            next.basis = (ArrayList<String>) current.basis.clone();
            next.free = (ArrayList<String>) current.free.clone();
            next.swap(resRow, resCol);

            for (int i = 0; i < current.priceArray.size(); i++) {
                if(i == resCol) {
                    next.setElem(resRow, resCol, 1 / resElem);
                }
                else {
                    next.setElem(resRow, i, current.getElem(resRow, i) / resElem);
                }
            }

            next.setCount(resRow, current.getCount(resRow) / resElem);

            for (int i = 0; i < current.array.size(); i++) {
                if(i == resRow) {
                    continue;
                }
                next.setElem(i, resCol, current.getElem(i, resCol) / resElem * -1);
            }

            next.setPrice(resCol, current.getPrice(resCol) / resElem * -1);

            for (int i = 0; i < next.array.size(); i++) {
                if(i == resRow) continue;
                for (int j = 0; j < next.priceArray.size(); j++) {
                    if(j == resCol) continue;
                    next.setElem(i, j, current.getElem(i, j) + next.getElem(resRow, j) * next.getElem(i, resCol));
                }

                next.setCount(i, current.getCount(i) + next.getCount(resRow) * next.getElem(i, resCol));
            }

            for (int i = 0; i < next.priceArray.size(); i++) {
                if(i == resCol) continue;
                next.setPrice(i, current.getPrice(i) - current.getPrice(resCol) * current.getElem(resRow, i) / current.getElem(resRow, resCol) );
            }

            next.setCount(next.countArray.size() - 1, current.getCount(current.countArray.size() - 1) -
                    current.getPrice(resCol) * next.getCount(resRow));

            current = next;
        }
        steps.add(current);

        ArrayList<Float> resArr = new ArrayList<>();
        for (int i = 0; i < current.rows * current.cols; i++) {
            resArr.add(0.f);
        }
        for (int i = 0; i < current.cols; i++) {
            resArr.set(Integer.parseInt(current.basis.get(i).replace("x", "")) - 1, current.getCount(i));
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Ответ: F = ");
        sb.append(String.format("%.2f", current.getCount(current.countArray.size() - 1)));
        sb.append(" X = [");

        for (Float f: resArr) {
            sb.append(String.format("%.2f ", f));
        }

        sb.append("]");

        for (int i = 0; i < current.rows; i++) {
            float sum = 0;
            for (int j = 0; j < current.cols; j++) {
                sum += resArr.get(i * (current.rows - 1) + j);
            }
            sb.append(" Ресурс ").append(i + 1).append(": ").append(String.format("%.2f ", sum));
        }

        result = sb.toString();
    }

    int chooseCol() {
        float max = 0;
        int index = 0;
        for(int i = 0; i < current.priceArray.size(); i++) {
            float x = current.getPrice(i);
            if(x > max){
                max = x;
                index = i;
            }
        }
        return index;
    }

    int chooseRow(int col) {
        int row = 0;
        float min = Float.MAX_VALUE, x;
        for(int i = 0; i < current.array.size(); i++){
            x = current.getCount(i) / current.getElem(i, col);
            if(x < min) {
                min = x;
                row = i;
            }
        }
        return row;
    }

    boolean optimal(){
        boolean res = true;
        for(float x: current.priceArray) {
            res &= x <= 0;
        }
        return res;
    }

    @Override
    public String toString() {
        return current.toString();
    }

    public ArrayList<ArrayList<ArrayList<String>>> rowsIter() {
        ArrayList<ArrayList<ArrayList<String>>> arr = new ArrayList<>();
        for (SimplexData d: steps) {
            arr.add(d.rowsIter());
        }
        return arr;
    }
}
