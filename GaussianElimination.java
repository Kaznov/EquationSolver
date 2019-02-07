package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
 
public class GaussianElimination{
    
    private static float[][] A;
    private static float[] B;
    private static List<String>vars;
    
    
    public static void solve(List<Equation> eqList){
        
        ToArr(eqList);
        // printing Matrix
        System.out.println("\n" + "Matrix : " + "\n");
        printMatrix(A, B);
        
        int N = B.length;
        for (int k = 0; k < N; k++) 
        {
            /** find pivot row **/
            int max = k;
            for (int i = k + 1; i < N; i++) 
                if (Math.abs(A[i][k]) > Math.abs(A[max][k])) 
                    max = i;
 
            /** swap row in A matrix **/    
            float[] temp = A[k]; 
            A[k] = A[max]; 
            A[max] = temp;
 
            /** swap corresponding values in constants matrix **/
            float t = B[k]; 
            B[k] = B[max]; 
            B[max] = t;
 
            /** pivot within A and B **/
            for (int i = k + 1; i < N; i++) 
            {
                float factor = A[i][k] / A[k][k];
                B[i] -= factor * B[k];
                for (int j = k; j < N; j++) 
                    A[i][j] -= factor * A[k][j];
            }
        }
        /** Print row echelon form **/
        printRowEchelonForm(A, B);
 
        /** back substitution **/
        float[] solution = new float[N];
        for (int i = N - 1; i >= 0; i--) 
        {
            float sum = 0;
            for (int j = i + 1; j < N; j++) 
                sum += A[i][j] * solution[j];
            solution[i] = (B[i] - sum) / A[i][i];
        }        
        /** Print solution **/
        printSolution(solution , vars);
    }
    /** function to print in row    echleon form **/
    public static void printRowEchelonForm(float[][] A, float[] B)
    {
        int N = B.length;
        System.out.println("\nRow Echelon form : ");
        for (int i = 0; i < N; i++)
           {
               for (int j = 0; j < N; j++)
                   System.out.printf("%.3f ", A[i][j]);
               System.out.printf("| %.3f\n", B[i]);
           }
           System.out.println();
    }
    /** function to print solution **/
    public static void printSolution(float[] sol , List<String>vars)
    {
        int N = sol.length;
        System.out.println("\nSolution : ");
        for (int i = 0; i < N; i++){
            System.out.println(vars.get(i) + "=" + sol[i]);   
        }
    }
    
    public static void ToArr(List<Equation> list){
        int n = list.size();
        A = new float[n][n];
        B = new float[n];
        for(float[] row: A)
            Arrays.fill(row, 0);
        vars = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Equation e = list.get(i);
            B[i] = -1 * e.getConstant();
            for(int k = 0; k < e.getList().size(); k++){
                if(!vars.contains(e.getList().get(k).getVar())){
                    vars.add(e.getList().get(k).getVar());
                }
            }
        }
        Collections.sort(vars);
        // printing Variables
        
        System.out.println("\n");
        System.out.println("Variables Names : " + "\n");
        
        for(int i = 0; i < vars.size(); i++){
            System.out.println(vars.get(i));
        }
        for(int i = 0; i < n; i++){
            Equation e = list.get(i);
            for(int k = 0; k < e.getList().size(); k++){
                String ss = e.getList().get(k).getVar();
                int index = vars.indexOf(ss);
                A[i][index] = e.getList().get(k).getCoff();
            }
        }
    }
    
    public static void printMatrix(float [][]arr, float []constants){
                
        for(int i = 0; i < constants.length; i++){
            for(int k = 0; k < constants.length; k++){
                System.out.print(arr[i][k] + "   ");
            }
                System.out.println("|  " + constants[i]);
        }
    }
    
}