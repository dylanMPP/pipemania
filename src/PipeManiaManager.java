import java.util.Scanner;
import java.util.Random;

public class PipeManiaManager {

    private static Scanner reader;
    private static PipeManiaController controller;

    public static void main(String[] args) {
        init();
        showMainMenu();
    } // main ///////////////////////////////

    public static void init() {
        controller = new PipeManiaController();
        reader = new Scanner(System.in);
    } // init ////////////////////////////////////////////////////


    public static void showMainMenu(){

        System.out.println("\n\n→| | WELCOME TO THE PIPEMANIA GAME | |");

        boolean runFlag = true;

        while (runFlag) {

            System.out.println("\n" + "\n——————————————————————————————\n                MENU\n\nWhat do you want to do? \n\n1) New game. \n2) See scoreboard. \n0) Exit.\n\n——————————————————————————————");
            int decision = reader.nextInt(); // I ask the user what he/she wants to do.

            switch (decision) {

                case 1:
                    showNewGame();
                    break;

                case 2:

                    break;

                case 3:

                    break;

                case 4:

                    break;

                case 0:
                    System.out.println("Thank you for using our system! Have a good one.");
                    runFlag = false;
                    break;

                default:
                    System.out.println("\nPlease, type a valid option.\n");
                    break;
            } // switch
        } // while
    } // main menu ////////////////////////////////////////////////


    public static void showNewGame(){

        boolean runFlag = true;

        System.out.println("\nType the amount of rows that you want for the board:");
        int row = Integer.parseInt(reader.next());

        System.out.println("\nType the amount of columns that you want for the board:");
        int column = Integer.parseInt(reader.next());

        System.out.println("\nType your nickname:");
        String player = reader.next();

        if (controller.createBoard(row, column, player)){ // if the board has been created succesfully or not

            while(runFlag) {
                System.out.println("\n\n-----------------------------\n           NEW GAME");
                controller.printBoard();
                System.out.println(controller.getMsg()+"\n"); // printeo la board y le pregunto qué desea hacer
                System.out.println("I'm going to...\n\n1) Add a pipeline. \n2) Simulate. \n0) Get back. \n-----------------------------");
                int decision2 = reader.nextInt();

                switch (decision2){

                    case 1:
                        System.out.println("What's the type of the new pipeline?");
                        String type = reader.next();
                        reader.nextLine();

                        System.out.print("In wich row do you want to add the new pipeline?");
                        int rowP = reader.nextInt();

                        System.out.print("In wich column do you want to add the new pipeline?");
                        int columnP = reader.nextInt();

                        if (controller.addPipeline(type, rowP, columnP)){
                            System.out.println("The pipeline with type "+type+" has been added succesfully.");
                        } else {
                            System.out.println("The pipeline hasn't been added succesfully.");
                        } // ifelse

                        break;

                    case 2:

                        break;

                    case 0:
                        runFlag = false;
                        break;

                    default:
                        System.out.println("\nPlease, type a valid option.\n");
                        break;
                } // switch
            } // while

        } else {
            System.out.println("Sorry! The board couldn't be created :/");
        } // ifelse - create board //////////////////////////////////////
    } // administrate dealer menu


} // PipeMania Manager class
