//***************************************************
// Students Names:  Shahad Maher Magram      2010332
//                  Roaa Hatim Altunsi       1914946
//                  Seham Khaldoun Nahlawi   1915762
//                  Logain Ezzat Sendi       2005341

// Assignment Title: Empirical Analysis of Solving the Traveling Salesman Problem 
//***************************************************
package dynamicprogramming_tsp;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class TspDp {
    
// ************************* Global Variabls *************************
static int n;// number of areas
static int inf=Integer.MAX_VALUE;// infinity
static int dp[][][];// array to implement dynamic programming
static int[][] dis;// adjacent matrix to reprezent distances
static int allvisited;// = (2^n)-1 that represent the maske when all areas are visited
static ArrayList<ArrayList<Integer>> allPaths; // to store all paths 


// ***************************************** TSP Dynamic Programming *****************************************
public static ArrayList<Integer> TSPdp(int msk,int pos){
    // msk = mask that represent which areas are visited and wich are not
    // pos = current position 
    
  
    // Check if all the areas are visited 
    if(msk==allvisited){ 
        
        // Add the distince between pos and the first area to the cost
        ArrayList<Integer> opt= new ArrayList<>();
        // The first indix in opt will stor the cost of the path
        opt.add(dis[pos][0]);
        opt.add(1); // start traveling from area 1
        
        return opt;
    }
    
    // Check if this path has already been calculated 
    if(dp[msk][pos][0]!=-1){
        ArrayList<Integer> opt= new ArrayList<>();
        for (int i = 0; i < dp[msk][pos].length; i++) {
            if(dp[msk][pos][i]==-1)break;
            opt.add(dp[msk][pos][i]);
        }
        return opt;
    }

    // Initializ the answer with infinity
    ArrayList<Integer> ans = new ArrayList<>();
    ans.add(inf);

    // Visit rest of the unvisited areas
    for(int area=0;area<n;area++){
        
        // Check if the area is unvisited 
        if((msk&(1<<area))==0){ 
           
            // Collect the path
            ArrayList<Integer> newAns = new ArrayList<>(); 
            newAns = TSPdp( msk|(1<<area),area); 
            newAns.set(0, newAns.get(0)+dis[pos][area]); 
            newAns.add(area+1);
            
            // Add the path to allPaths
            if(pos==0){
                newAns.add(1);
                allPaths.add(newAns);
            }
            
            // Get the minimum cost and path 
           ans=(ans.get(0)<newAns.get(0)?ans:newAns);
        }
    }

    // Return the answer
    for (int i = 0; i < ans.size(); i++) {
        dp[msk][pos][i]=ans.get(i);
    }
     return ans;
}

    // *************************** Main function **********************************
    public static void main(String[] args) {
        long initialTime, endTime, elapsedTime;
        Scanner input=new Scanner(System.in);
         
        // Read n from the user
        System.out.print("Enter the number of areas: ");
        n=input.nextInt();
       
        // ************************** Initialization **************************
        allPaths = new ArrayList<>();
        allvisited = ((1<<n)-1);
        dis= new int[n][n];
        dp=new int[(1<<n)][n][n+2];
        // *******************************************************************
         
        // Initializ dp and dis
        for(int i=0;i<(1<<n);i++){
            for(int j=0;j<n;j++){
                if(i<n)
                    dis[i][j]=0;
                for (int k = 0; k < n+2; k++) {
                    dp[i][j][k]=-1;
                }
                
            }
        }
         
        // Creat Random object
        Random ran= new Random(10);
        
        // Fill the dis matrix
        for (int i = 0; i <n-1; i++) {
            for (int j = i+1; j <n; j++) {
               
                // Generat the distance between area i and area j randomly between 10 and 100
                int d = 10+ran.nextInt(100-10+1);
                dis[i][j]=d;
                dis[j][i]=d;
            }
        }
        
        // Start timeing to measure efficiency
        initialTime = System.nanoTime();
        
        // Get the optimal cost and path by call TSPdp Function
        ArrayList<Integer> opt= TSPdp(1,0);
        
        // Measure elapsed time 
        endTime = System.nanoTime();
	elapsedTime = endTime - initialTime;
        
        // Print the distances Matrix
        System.out.println("\nThe Distances Matrix: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(dis[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("");
        
        // Print all the paths awith their cost
        System.out.println("All the paths awith their cost: ");
        for (int i = 0; i < allPaths.size(); i++) {
            for (int j = 1; j <allPaths.get(i).size(); j++) {
                System.out.print("AREA "+allPaths.get(i).get(j)+" ");
                if(j!=allPaths.get(i).size()-1) System.out.print("-> ");
            }
            System.out.println("\tThe cost is: "+allPaths.get(i).get(0));
            
        }
        
        // Print the minimum distance and the optimal path
        System.out.println("\nThe minimum cost is "+opt.get(0)+"\nThe optimal path/s is/are: ");
        for (int i = 0; i < allPaths.size(); i++) {
            int c =allPaths.get(i).get(0);
            if(c!=opt.get(0)) continue;
            
            for (int j = 1; j <allPaths.get(i).size(); j++) {
                System.out.print("AREA "+allPaths.get(i).get(j)+" ");
                if(j!=allPaths.get(i).size()-1) System.out.print("-> ");
            }
                System.out.println("");
            
        }
        
        // Print the total time taken by algorithm 
        System.out.println("\nThe total execution time in nanoSeconds: " + elapsedTime);

    }
    
}

