import java.util.Scanner;

public class BattleShip {
    public static int numRows = 7;
    public static int numCols = 7;
    public static int playerShips;
    public static int computerShips;
    public static String[][] grid = new String[numRows][numCols];
    public static int[][] missedGuesses = new int[numRows][numCols];
    private static BattleShip BattleShips;

    public static void main(String[] args){
        System.out.println("** Welcome to Battle Ship game **");
        System.out.println("*****Are you Ready*****\n");
        Map();
        kordinat();
        lawan();
        do {
            Battle();
        }while(BattleShips.playerShips != 0 && computerShips != 0);

        gameOver();
    }
    public static void Map(){
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();

        for(int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = " ";
                if (j == 0)
                    System.out.print(i + "|" + grid[i][j]);
                else if (j == grid[i].length - 1)
                    System.out.print(grid[i][j] + "|" + i);
                else
                    System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();
    }
    public static void kordinat(){
        Scanner input = new Scanner(System.in);

        System.out.println("\nMenyiapkan Kapal Perang");
        BattleShip.playerShips = 5;
        for (int i = 1; i <= BattleShip.playerShips; ) {
            System.out.print("Input Kordinat X" + i + ": ");
            int x = input.nextInt();
            System.out.print("Input Kordinat Y" + i + ": ");
            int y = input.nextInt();

            if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " "))
            {
                grid[x][y] =   "@";
                i++;
            }
            else if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && grid[x][y] == "@")
                System.out.println("Kordinat "+ x +"."+ y + " telah Digunakan");
            else if((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                System.out.println("Titik Kordinat yang dapat diinput hanya sampai 6 bos");
        }
        printOceanMap();
    }
    public static void lawan(){
        System.out.println("\nLawan Menyiapkan Kapal Perang");
        //Deploying five ships for computer
        BattleShips.computerShips = 5;
        for (int i = 1; i <= BattleShips.computerShips; ) {
            int x = (int)(Math.random() * 7);
            int y = (int)(Math.random() * 7);

            if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " "))
            {
                grid[x][y] =   "x";
                System.out.println(i + ". ship DEPLOYED");
                i++;
            }
        }
        printOceanMap();
    }
    public static void Battle(){
        playerTurn();
        computerTurn();
        printOceanMap();
        System.out.println();
        System.out.println("Kapal Saya: " + BattleShips.playerShips + " | Kapal Lawan: " + BattleShips.computerShips);
        System.out.println();
    }
    public static void playerTurn(){
//        historypenembakan();
        System.out.println("\nYOUR TURN");
        int x = -1, y = -1;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Input Kordinat X: ");
            x = input.nextInt();
            System.out.print("Input Kordinat Y: ");
            y = input.nextInt();
            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid gues
            {
                if (grid[x][y] == "x") //if computer ship is already there; computer loses ship
                {
                    System.out.println("Boom! Masih mauko");
                    grid[x][y] = "x"; //Hit mark
                    --BattleShips.computerShips;
                }
                else if (grid[x][y] == "@") {
                    System.out.println("Oh tidak, Penghianat:(");
                    grid[x][y] = "!";
                    --BattleShips.playerShips;
//                    ++BattleShips.computerShips;
                }
                else if (grid[x][y] == " ") {
                    System.out.println("Sorry, ndak kena bro");
                }
            }
            else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))  //invalid guess
                System.out.println("Sory gaes. koordinat yang kamu masukkan tidak terdeteksi");


        }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
    }
    public static void computerTurn(){
        System.out.println("\nLAWAN TURN");
        //Guess co-ordinates
        int x = -1, y = -1;
        do {
            x = (int)(Math.random() * 7);
            y = (int)(Math.random() * 7);

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid guess
            {
                if (grid[x][y] == "@") //if player ship is already there; player loses ship
                {
                    System.out.println("BOOM");
                    grid[x][y] = "x";
                    --BattleShips.playerShips;
//                    ++BattleShips.computerShips;
                }
                else if (grid[x][y] == "x") {
                    System.out.println("Masih mauko");
                    grid[x][y] = "!";
                }
                else if (grid[x][y] == " ") {
                    System.out.println("Computer missed");
                    if(missedGuesses[x][y] != 1)
                        missedGuesses[x][y] = 1;
                }
            }
        }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
    }
    public static void gameOver(){
        System.out.println("Kapal Saya: " + BattleShips.playerShips + " | Kapal Lawan: " + BattleShips.computerShips);
        if(BattleShips.playerShips > 0 && BattleShips.computerShips <= 0)
            System.out.println("Hore! Kita Menang:)");
        else
            System.out.println("Sorry, Kita Kalah:(");
        System.out.println();
    }
    public static void printOceanMap(){
        System.out.println();
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();
        for(int x = 0; x < grid.length; x++) {
            System.out.print(x + "|");

            for (int y = 0; y < grid[x].length; y++){
                System.out.print(grid[x][y]);
            }
            System.out.println("|" + x);
        }
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();
    }
}