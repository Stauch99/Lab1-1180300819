package P1;

import java.io.*;

public class MagicSquares {

    public static boolean isLegalMagicSquare(String fileName) throws IOException {
        boolean res=false; //set result variable first
        /*
        considering the exponent of the square is not constant, we need to get that value first
        then calculate the proper sum value using the exp value
        use the sum value to check row by row, column by column, diagram by diagram...oh there's just 2 diagrams..
        if any check failed, immediately print a message and return res(false)
        if passed all the checking, return res=true
         */
        
        //matrix loaded
        String filePath = "src\\P1\\txt\\"+fileName+".txt";
        File file = new File(filePath);
        String line;
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);

        line = bufferReader.readLine();

         //matrix exponent needs to check the \t symbol
        int matrixWidthTabSplit = line.split("\t").length;
        int matrixWidth = line.split("\\s+").length;
        if (matrixWidth!=matrixWidthTabSplit){
            System.out.println("The "+fileName+"th matrix has used none table blank.");
            return false;
        }

        int matrixSize = matrixWidth*matrixWidth;
        int[][] matrix = new int[matrixWidth][matrixWidth];
        int t;
        for (int i = 0; i < matrixWidth; i++) {

            String[] temp = line.split("\\s+");
            if(temp.length!=matrixWidth){
                System.out.println("The "+fileName+"th matrix lost some elements at line "+i+1+".");
                return false;   //consider that there will be missing matrix elements
            }

            for(int j =0;j<matrixWidth;j++){

                try{
                    t = Integer.parseInt(temp[j]);
                }catch (Exception e){
                    System.out.println("The "+fileName+"th matrix contains none-integer elements at ("+(i+1)+","+(j+1)+").");
                    return false;
                }
                matrix[i][j]= t;
            }

            line = bufferReader.readLine();
        }

        int matrixSum = matrixWidth*(matrixSize+1)/2;
        //verify part
        //checking rows
        int sum;
        for (int i = 0; i < matrixWidth; i++) {
            sum=0;
            for (int j = 0; j < matrixWidth; j++) {
                sum+=matrix[i][j];
            }
            if(sum!=matrixSum){
                System.out.println("The "+fileName+"th matrix didn't pass the row test at row "+i+".");
                return false;
            }
        }
        sum=0;
        //checking columns
        for (int i = 0; i < matrixWidth; i++) {
            sum=0;
            int j;
            for (j = 0; j < matrixWidth; j++) {
                sum+=matrix[j][i];
            }
            if(sum!=matrixSum){
                System.out.println("The "+fileName+"th matrix didn't pass the column test at column "+j+".");
                return false;
            }
        }
        sum=0;
        //checking diagrams
        for (int i = 0; i < matrixWidth; i++) {
            sum+=matrix[i][i];
        }
        if(sum!=matrixSum){
            System.out.println("The "+fileName+"th matrix didn't pass the diagram test on \\ direction.");
            return false;
        }
        sum=0;
        for (int i = 0; i < matrixWidth; i++) {
            sum+=matrix[matrixWidth-i-1][i];
        }
        if(sum!=matrixSum){
            System.out.println("The "+fileName+"th matrix didn't pass the diagram test on / direction.");
            return false;
        }
        return true;
    }
    /*Here's the explanation of the generating algorithm :
    put 1 in the center of the first line and arrange the following n^n-1 numbers by rules below
    (These rules are just for out of bound situation. )
    1. put every number at the nearby right top position of the previous number
    2. if that position's row number is out of top bound, then put it at the same column of the bottom row
    3. if that position's column number is out of right bound, then put it at the same row of the left column.
    4. if it's at the corner(row<1 and col>n), then puts it at the (1,n)
    5. if the target position has been taken, the puts it at the at the next line but same column of the previous arranged element
     */

//below are the original function
//    public static boolean generateMagicSquare(int n) {
//        //this is the original function
//        int[][] magic = new int[n][n];
//        int row = 0, col = n / 2, i, j, square = n * n;
//
//        for (i = 1; i <= square; i++) {
//            magic[row][col] = i;
//            if (i % n == 0)
//                row++;
//            else {
//                if (row == 0)
//                    row = n - 1;
//                else
//                    row--;
//                if (col == (n - 1))
//                    col = 0;
//                else
//                    col++;
//            }
//        }
//
//        for (i = 0; i < n; i++) {
//            for (j = 0; j < n; j++)
//                System.out.print(magic[i][j] + "\t");
//            System.out.println();
//        }
//
//        return true;
//    }


//below are the modified function with power~

    public static boolean generateMagicSquare(int n) {
        if(n<0){
            System.out.println("Input negative digit! Program will return false...\n");
            return false;
        }
        if(n%2==0){
            System.out.println("Input even digit! Program will return false...\n");
            return false;
        }

        int[][] magic = new int[n][n];
        int row = 0, col = n / 2, square = n * n;

        for (int i = 1; i <= square; i++) {
            magic[row][col] = i;
            if (i % n == 0)
                row++;
            else {
                if (row == 0)
                    row = n - 1;
                else
                    row--;
                if (col == (n - 1))
                    col = 0;
                else
                    col++;
            }
        }

        //write the generated matrix into a txt file
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("src\\P1\\txt\\6.txt");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    fileWriter.write(magic[i][j]+"\t");
                }
                fileWriter.write("\r\n"); //next line in windows type
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static void main (String[] args) throws IOException {

        //print the result of the matrices in the 5 txt files
        System.out.println("The result of 6 txt file will be printed below:\n");
        for (int i = 0; i < 5; i++) {
            boolean k = MagicSquares.isLegalMagicSquare(i+1+"");
            System.out.println("The "+(i+1)+"th matrix is "+k+" magic square.");
        }

//        //test the generateMagicSquare function
//        System.out.println("\nNow test the function [boolean generateMagicSquare(int n);].");
//        MagicSquares m2 = new MagicSquares();
//
//        System.out.println("We try to input a even digit:");
//        try{
//            MagicSquares.generateMagicSquare(6);
//        }catch(Exception e){
//            System.out.println(e);
//        }
//
//        System.out.println("Then a negative digit:");
//        try{
//            MagicSquares.generateMagicSquare(-4);
//        }catch(Exception e){
//            System.out.println(e);
//        }
        System.out.println("\nNow I made some fixes on the function and now test it:(renamed as generateMagicSquare2)");

        System.out.println("We try to input a even digit:");
        MagicSquares.generateMagicSquare(6);

        System.out.println("Then a negative digit:");
        MagicSquares.generateMagicSquare(-4);

        System.out.println("\nNow we generate a normal matrix and test if it's a magic square:");
        MagicSquares.generateMagicSquare(7);
        boolean res =MagicSquares.isLegalMagicSquare(6+"");
        if (res)
            System.out.println("Wow it did generate a valid magic square! Congratulations!");
        else
            System.out.println("Unfortunately! Not a valid magic square!");

    };
};

//All is done！
