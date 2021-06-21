/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

public class RailFenceCrypto {

    public int[] doProcessOnKey(String pass) {

        pass = pass.toUpperCase();
        int min, i, j;
        char orginalKey[] = pass.toCharArray();
        char temp;
        char[] passArr = pass.toCharArray();
        int[] order = new int[pass.length()];

        for (i = 0; i < pass.length(); i++) {
            min = i;
            for (j = i; j < pass.length(); j++) {
                if (passArr[min] > passArr[j]) {
                    min = j;
                }
            }

            if (min != i) {
                temp = passArr[i];
                passArr[i] = passArr[min];
                passArr[min] = temp;
            }
        }

        for (i = 0; i < pass.length(); i++) {

            for (j = 0; j < pass.length(); j++) {

                if (orginalKey[i] == passArr[j]) {
                    order[i] = j;
                    break;
                }
            }

        }
        return order;
    }

    public Character[][] encrypt(String mes, String pass, int[] order, Character pad) {

        pass = pass.trim().toUpperCase();
        mes = mes.trim().toUpperCase();
        mes = mes.replaceAll(" ", "");
        int col = pass.length();
        int mesL = mes.length();
        int row;

        // set the row by dividing the message length by the key length
        if (mesL % col == 0) {
            row = mesL / col;
        } else {
            row = mesL / col + 1;
        }

        Character[][] arr = new Character[row][col];

        // assigning the message to a table
        int a = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (a < mesL) {
                    arr[i][j] = mes.charAt(a);
                    a++;
                } else {
                    arr[i][j] = pad;
                }
            }
        }

        Character[][] arrFinal = new Character[row][col];
        // changing the column order based on the alphabetical oreder of the pass key
        for (int h = 0; h < row; h++) {
            for (int i = 0; i < col; i++) {
                for (int j = 0; j < col; j++) {
                    if (j == order[i]) {
                        arrFinal[h][j] = arr[h][i];
                        break;
                    }
                }
            }
        }

        return arrFinal;
    }

    //transposing the matrix, columns become rows, and printing the array as a string
    public String result(Character[][] arrFinal) {
        int m = arrFinal.length;
        int n = arrFinal[0].length;

        Character[][] transposedArr = new Character[n][m];

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                transposedArr[x][y] = arrFinal[y][x];
            }
        }
        String result = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result = result + transposedArr[i][j];
            }
        }
        return result;
    }

    public Character[][] decrypt(String enMes, String pass, int[] order) {
        try {
            pass = pass.trim();
            pass = pass.toUpperCase();
            enMes = enMes.toUpperCase();
            char[] mesToDec = enMes.toCharArray();

            int col = pass.length();
            int mesL = enMes.length();
            int row;
            if (mesL % col == 0) {
                row = mesL / col;
            } else {
                row = mesL / col + 1;
            }

            // putting the encrypted text in an array
            Character[][] arrTemp = new Character[col][row];
            int a = 0;

            for (int f = 0; f < col; f++) {
                for (int j = 0; j < row; j++) {
                    arrTemp[f][j] = mesToDec[a];
                    a++;

                }

            }

            // reverse engineering the array by transposing it back and getting it as a string
            String mess = result(arrTemp);
            mesToDec = mess.toCharArray();

            // putting the string in an array
            a = 0;
            Character[][] arrF = new Character[row][col];
            for (int f = 0; f < row; f++) {
                for (int j = 0; j < col; j++) {
                    arrF[f][j] = mesToDec[a];
                    a++;
                }
            }

            Character[][] arrFinal = new Character[row][col];

            // re ordering the array based on the alphetibcal order of the pass key
            for (int h = 0; h < row; h++) {
                for (int i = 0; i < col; i++) {
                    for (int j = 0; j < col; j++) {
                        if (j == order[i]) {
                            arrFinal[h][i] = arrF[h][j];
                            break;
                        }
                    }
                }
            }

            return arrFinal;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(" Wrong key");
        }
        return new Character[0][0];
    }
}
