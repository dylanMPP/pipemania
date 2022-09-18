package ui;

import model.PipeManiaController;

import java.util.Calendar;
import java.util.Scanner;
import java.util.Random;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

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

        System.out.println("\n\n| | WELCOME TO THE PIPEMANIA GAME | |");

        boolean runFlag = true;

        while (runFlag) {

            System.out.println("\n\n——————————————————————————————\n\n                MENU\n\nWhat do you want to do? \n\n1) New game. \n2) See scoreboard. \n0) Exit.\n\n——————————————————————————————");
            int decision = reader.nextInt(); // I ask the user what he/she wants to do.

            switch (decision) {
                case 1 -> showNewGame();
                case 2 -> showScoreboard();
                case 0 -> {
                    System.out.println("\nThank you for playing PIPEMANIA! Comeback soon =)");
                    runFlag = false;
                }
                default -> System.out.println("\nPlease, type a valid option.\n");
            } // switch
        } // while
    } // main menu ////////////////////////////////////////////////


    public static void showNewGame(){

        boolean runFlag = true;
        int row = 8;
        int column = 8;

        System.out.println("\nType your nickname: (without spaces please)");
        String player = reader.next();

        if (controller.createBoard(row, column, player)){ // if the board has been created successfully or not

            int amountOfPipes = 0;
            long t1 = System.currentTimeMillis();

            while(runFlag) {

                System.out.println("\n\n-----------------------------\n           NEW GAME");
                controller.printBoard();
                System.out.println(controller.getMsg()+"\n"); // printeo la board y le pregunto qué desea hacer
                System.out.println("I'm going to...\n\n1) Add a pipeline. \n2) Simulate. \n0) Get back. \n-----------------------------");
                int decision2 = reader.nextInt();

                switch (decision2){

                    case 1:
                        System.out.println("\nWhat's the type of the new pipeline? (=, ||, O, X)");
                        String type = reader.next();

                        boolean anotherRunFlag = true;

                        while(anotherRunFlag){
                            if(!(type.equalsIgnoreCase("X") || type.equalsIgnoreCase("||") || type.equalsIgnoreCase("=") ||
                                    type.equalsIgnoreCase("O"))){
                                System.out.println("Please, type a valid pipeline.");
                                type = reader.next();
                            } else {
                                anotherRunFlag = false;
                            }
                        }

                        System.out.print("\nIn which row do you want to add the new pipeline?");
                        int rowP = reader.nextInt();

                        System.out.print("\nIn which column do you want to add the new pipeline?");
                        int columnP = reader.nextInt();

                        if (controller.addPipeline(type, rowP, columnP)){
                            System.out.println("\nThe pipeline with type "+type+" has been added successfully =)");
                        } else {
                            System.out.println("\nThe pipeline hasn't been added successfully =(");
                        } // ifelse

                        break;

                    case 2:

                        if(controller.simulate()){
                            controller.calculateAmountOfPipelines();
                            amountOfPipes = controller.getAmountOfPipelines();
                            long t2 = System.currentTimeMillis();
                            long dif = (t2 - t1)/1000;
                            controller.calculateScore(player, dif, amountOfPipes);

                            System.out.println("\n\nYEAH, YOU HAVE WON THE GAME!!!\n    =) \nIN "+dif+" SECONDS WITH "+amountOfPipes+" PIPELINES");
                            controller.clearBoard();
                            runFlag = false;
                        } else {
                            System.out.println("\n\nNO! The position of the pipelines are incorrect = ( Try again");
                        }

                        break;

                    case 0:
                        controller.clearBoard();
                        runFlag = false;
                        break;

                    default:
                        System.out.println("\nPlease, type a valid option.\n");
                        break;
                } // switch
            } // while

        } else {
            System.out.println("\nSorry! The board couldn't be created :/");
        } // ifelse - create board //////////////////////////////////////

    } // show new game menu

    public static void showScoreboard() {

        if(controller.showScoreboard()==null){
            System.out.println("\nThe scoreboard can't be shown");
        } else {
            System.out.println("\n------ THIS ARE THE BEST PIPE-PLAYERS ------\n"+controller.showScoreboard()+"\n-------------------------------------------");
        }

    } // show scoreboard menu

} // PipeMania Manager class
