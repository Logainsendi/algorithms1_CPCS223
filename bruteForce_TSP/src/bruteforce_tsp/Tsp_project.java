//***************************************************
// Students Names:  Shahad Maher Magram      2010332
//                  Roaa Hatim Altunsi       1914946
//                  Seham Khaldoun Nahlawi   1915762
//                  Logain Ezzat Sendi       2005341

// Assignment Title: Empirical Analysis of Solving the Traveling Salesman Problem 
//***************************************************
package bruteforce_tsp;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Tsp_project {
    
// ************************* Global Variabls *************************
    
static int n;// number of areas
static int minC=Integer.MAX_VALUE; //to store the minimum cost 
static boolean[] vis;// to keep track of visited areas
static int[][] dis;// adjacent matrix to reprezent distances
static ArrayList<Integer> pathCost; // to store cost of each path 
static ArrayList<ArrayList<Integer>> allPaths; // to store all paths 


    // *********************** TSP method ***************************
    public static void TSP(int pos, ArrayList<Integer> path, int cost){
        // pos = current position
        // path = current path
        // cost = current cost
        
        // base case
        if(path.size()==n){
             cost+=dis[pos][0];
             
            // Store the cost of the current path
            pathCost.add(cost);
             
            // Store the path  
            allPaths.add(path);
            allPaths.get(allPaths.size()-1).add(1);
             
            // Update the minimum cost 
            minC=(cost<minC?cost:minC);
                
 
            
        }else{
            // Visit rest of the unvisited areas
            for (int i = 1; i <n; i++) {
                
                // Go to the next unvisited area
                if(!vis[i]){
                       
                    // Mark area i as visited
                    vis[i]=true;
                         
                    // Add area i to the path
                    ArrayList<Integer> newP = new ArrayList<>();
                    for (int j = 0; j < path.size(); j++) {
                        newP.add(path.get(j));
                    }
                    newP.add((i+1));
                         
                    TSP(i,newP,cost+dis[pos][i]);
                         
                    // Mark area i as unvisited
                    vis[i]=false;
                }
            }
        }
        
    }
    
    // *************************** Main function **********************************
    public static void main(String[] args) {
        long initialTime, endTime, elapsedTime;
        Scanner input=new Scanner(System.in);
        
        // Read n from the user
        System.out.print("Enter the number of areas: ");
        n=input.nextInt();
         
        // Set the size of the arrays
        vis= new boolean[n];
        dis= new int[n][n];
        pathCost = new ArrayList<>();
        allPaths = new ArrayList<>();
        
        // Initializ vis
        for (int i = 0; i <n; i++) {
            vis[i]=false;
        }
        
        // Creat Random object
        Random ran= new Random(10);
        
       // Generat the distances randomly between 10 and 100
        for (int i = 0; i <n-1; i++) {
            for (int j = i+1; j <n; j++) {
                
                // Distance between area i and area j
                int d = 10+ran.nextInt(100-10+1);
                dis[i][j]=d;
                dis[j][i]=d;
            }
        }
        
        
        // Print the distances Matrix
        System.out.println("\nThe Distances Matrix: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(dis[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("");
        
        // Add the first area to the path
        ArrayList<Integer> p = new ArrayList<>();
        p.add(1);
        vis[0]=true;
        
        // Start timeing to measure efficiency
        initialTime = System.nanoTime();
        
        // Call TSP method
        TSP(0,p,0);
        
        // Measure elapsed time 
        endTime = System.nanoTime();
	elapsedTime = endTime - initialTime;
        
        // Print all the paths awith their cost
        System.out.println("All the paths awith their cost: ");
        for (int i = 0; i < pathCost.size(); i++) {
            
            for (int j = 0; j <= n; j++) {
                System.out.print("AREA "+allPaths.get(i).get(j));
                if(j!=n) System.out.print(" -> ");
            }
            System.out.println("\tThe cost is: "+pathCost.get(i));
        }
        
        // Print the minimum distance and the optimal path
        System.out.println("\nThe minimum cost is "+minC+"\nThe optimal path/s is/are: ");
        for (int i = 0; i < pathCost.size(); i++) {
            if(pathCost.get(i)!=minC) continue;
          
            for (int j = 0; j <= n; j++) {
                System.out.print("AREA "+allPaths.get(i).get(j));
                if(j!=n) System.out.print(" -> ");
            }
            System.out.println("");
        }
        
        // Print the total time taken by algorithm 
        System.out.println("\nThe total execution time in nanoSeconds: " + elapsedTime);
        
    }
    
}
