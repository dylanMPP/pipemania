package model;

import java.util.Random;

public class PipeManiaController {

    private Pipeline head;
    private Pipeline tail;
    private Pipeline fountain;
    private Pipeline drain;
    private int rowAmount;
    private int columnAmount;
    private Player player;
    private String msg;
    private static Scoreboard scoreboard;
    private int amountOfPipelines;

    public PipeManiaController() { // Constructor del double linked list (board of the game)
        scoreboard = new Scoreboard();
        this.head = null;
        this.tail = null;
        this.rowAmount =  0;
        this.columnAmount = 0;
        this.player = null;
        this.msg = "";
    } // constructor
    ////////////////////////////

    // I create the board by filling it with pipes of type 'x'
    // also, as a parameter to create the board I ask for its number of rows and columns to assign
    // give each pipe a row and column number.
    public boolean createBoard(int rowAmount, int columnAmount, String player){ // Creo la board rellenándola de tuberías tipo 'x'
        // además, como parámetro para crear la board pido su cantidad de filas y columnas para asignarle
        // a cada tubería un número de fila y columna.

        Random rand = new Random();
        int rowF = 0;
        int columnF = 0;
        int rowD = 0;
        int columnD = 0;

        this.rowAmount = rowAmount;
        this.columnAmount = columnAmount;

        Pipeline pipeline;

        for(int m=0; m<2; m++){ // creo un for para darle una row y una column a la fuente y al drenaje
            int randomR = rand.nextInt(this.rowAmount);
            int randomC = rand.nextInt(this.columnAmount);

            if(rowF==0 && columnF==0){ // ifelse para saber si la row de la fuente y drenaje ya fueron asignados o no
                rowF = randomR;
                columnF = randomC;
            } else {
                rowD = randomR;
                columnD = randomC;
            } // ifelse
        } // for para sacar las filas y columnas de la fuente y drenaje

        if(rowF==rowD && columnF==columnD){ // si la fuente y drenaje justamente poseen la misma posición
            rowD += 2;
            columnD += 2; // le sumo 2 a las filas y columnas del D para que queden bien separados

        }

        for(int i=0; i<(this.rowAmount); i++){
            for(int j=0; j<(this.columnAmount); j++){

                if(i==rowF && j==columnF){
                    fountain = new Pipeline("F",i,j);
                    pipeline = fountain; // Creo la fuente
                } else if(i==rowD && j==columnD){
                    drain = new Pipeline("D",i,j);
                    pipeline = drain; // Creo el drenaje
                } else {
                    pipeline = new Pipeline("X", i, j); // Creo la tubería
                }

                createBoard(pipeline); //
            }
        } // for row * column
        return true;
    } // createboard ///////////////////////////


    // Aquí creo como tal la board, seteando la head si es null y
    // añadiendo cada tubería al final (tail)
    public boolean createBoard(Pipeline pipeline) {

        if (head == null) {
            this.head = pipeline;
            return true;

        } else if (tail == null) {
            this.tail = pipeline;
            this.head.setNext(this.tail);
            return true;

        } else {
            this.tail.setNext(pipeline);
            pipeline.setPrevious(this.tail);
            this.tail = pipeline;
            return true;

        } // ifelse add pipeline
    } // createBoard //////////////////////////////////////


    // imprimir el board, este es el método que llama al recursivo
    // mando como parámetro el mensaje a llenar y la head para que comience a hacer recursividad
    public void printBoard() {
        msg = "";
        int i = 0;
        printBoard(head, i, this.columnAmount);
    } ////////////////////////////////////////////////////


    // hago el caso base, si el siguiente al current es null
    // entonces retorno null, si no entonces lleno el mensaje con el tipo de cada pipeline
    public void printBoard(Pipeline pipeline, int i, int column) {

        if (pipeline == null) {
            return;
        } // case base

        if (pipeline.getType().equals("||")) {

            if (i % column == 0) { // si el i es divisible entre la cantidad de columnas entonces ahi bajo el mensaje
                msg += "\n" + pipeline.getType() + "  ";

            } else {
                msg += pipeline.getType() + "  ";

            } // ifelse i == column
        } else {// if type is || para que quede bien printeada la board

            if (i % column == 0) { // si el i es divisible entre la cantidad de columnas entonces ahi bajo el mensaje
                msg += "\n" + pipeline.getType() + "   ";

            } else {
                msg += pipeline.getType() + "   ";

            } // ifelse i == column
        } // ifelse // if type is || para que quede bien printeada la board /////


        i++; // voy sumando las i para saber dónde bajar (es como un contador de columnas)

        printBoard(pipeline.getNext(), i, column);
    } // printboard ///////////////////////////////////////

    public void clearBoard(){
        this.msg = "";
        this.head = null;
        this.tail = null;
        this.rowAmount =  0;
        this.columnAmount = 0;
        this.player = null;
    }
    ////////////////////////////

    public boolean addPipeline(String type, int row, int column) {
        type = type.toUpperCase();
        return addPipeline(type, row, column, head);
    } // addPipeline
    ////////////////////////////

    //it looks for the row and column desired by the user recursively, when it finds
    // it it returns true and if it doesn't find it or if it is the same row and column as the source or drain, it returns false.
    public boolean addPipeline(String type, int row, int column, Pipeline current) {
        if (current == null) {
            return false;
        }

        if (current.getRow() == row && current.getColumn() == column) { // si la row de la current y su columna son las que estoy buscando entonces la cambio por la que agregó

            // if it's fountain or drain
            if (current.getType().equalsIgnoreCase("F") || current.getType().equalsIgnoreCase("D")) {
                return false;
            } else {

                current.setType(type);
                return true;
            }
        }

        return addPipeline(type, row, column, current.getNext());

    } // add pipeline //////////////////////////////////////////////////////////////
    ////////////////////////////

    public boolean simulate() {
        int i = 1;
        int iterations = 0;
        String dir = "";
        return simulate(drain, i, iterations, dir);
    } // simulate method
    ////////////////////////////

    public boolean simulate(Pipeline current, int i, int iterations, String dir){

        Pipeline downAux = null;
        Pipeline upAux = null;
        boolean flagNext = true;
        boolean flagPrevious = true;

        if (iterations==0){

            // Guardo la pipeline de arriba y abajo del drenaje en una variable.
            // Valido si la drain está en la primera o última row, pues no podría conseguir su up o down.
            if(drain.getRow()==0){
                downAux = getDownPipe(drain);

                if(drain.getColumn()==0){
                    flagPrevious = false;
                }

            } else if(drain.getRow()==rowAmount-1){
                upAux = getUpPipe(drain);

                if(drain.getColumn()==columnAmount-1){
                    flagNext = false;
                }

            } else {
                downAux = getDownPipe(drain);
                upAux = getUpPipe(drain);
            }

            // hago flag para validar que el drenaje no tenga más de una llegada
            int flag = 0;

            // si el next, previous, arriba y abajo poseen un tipo distinto a "X" entonces la flag la convierto en 1 pues ya posee una llegada
            if(downAux!=null && upAux==null && flagPrevious && flagNext) {

                if (!drain.getNext().getType().equalsIgnoreCase("X")) {

                    if (drain.getNext().getType().equalsIgnoreCase("o")) {
                        return false;
                    } else {
                        flag += 1;
                        dir = "R";
                    }

                }
                if (!drain.getPrevious().getType().equalsIgnoreCase("X")) {

                    if (drain.getPrevious().getType().equalsIgnoreCase("o")) {
                        return false;
                    } else {
                        flag += 1;
                        dir = "L";
                    }
                }
                if (!downAux.getType().equalsIgnoreCase("X")) {

                    if (downAux.getType().equalsIgnoreCase("o")) {
                        return false;
                    } else {
                        flag += 1;
                        dir = "D";
                    }
                }

            } else if (downAux!=null && upAux==null && !flagPrevious && flagNext) {

                if (!drain.getNext().getType().equalsIgnoreCase("X")) {

                    if (drain.getNext().getType().equalsIgnoreCase("o")) {
                        return false;
                    } else {
                        flag += 1;
                        dir = "R";
                    }

                }
                if (!downAux.getType().equalsIgnoreCase("X")) {

                    if (downAux.getType().equalsIgnoreCase("o")) {
                        return false;
                    } else {
                        flag += 1;
                        dir = "D";
                    }
                }

            } else if (downAux!=null && upAux==null && flagPrevious && !flagNext){

                if (!drain.getPrevious().getType().equalsIgnoreCase("X")) {

                    if (drain.getPrevious().getType().equalsIgnoreCase("o")) {
                        return false;
                    } else {
                        flag += 1;
                        dir = "L";
                    }
                }
                if (!downAux.getType().equalsIgnoreCase("X")) {

                    if (downAux.getType().equalsIgnoreCase("o")) {
                        return false;
                    } else {
                        flag += 1;
                        dir = "D";
                    }
                }

            } else if(downAux==null && upAux!=null && flagPrevious && flagNext) {
                if (!drain.getNext().getType().equalsIgnoreCase("X")) {

                    if (drain.getNext().getType().equalsIgnoreCase("o")) {
                        return false;
                    } else {
                        flag += 1;
                        dir = "R";
                    }
                }
                if (!drain.getPrevious().getType().equalsIgnoreCase("X")) {

                    if (drain.getPrevious().getType().equalsIgnoreCase("o")) {
                        return false;
                    } else {
                        flag += 1;
                        dir = "L";
                    }
                }
                if (!upAux.getType().equalsIgnoreCase("X")) {

                    if (upAux.getType().equalsIgnoreCase("o")) {
                        return false;
                    } else {
                        flag += 1;
                        dir = "U";
                    }
                } // determino en qué dirección está la llegada

            } else if (downAux==null && upAux!=null && !flagPrevious && flagNext) {

                if (!drain.getNext().getType().equalsIgnoreCase("X")) {

                    if (drain.getNext().getType().equalsIgnoreCase("o")) {
                        return false;
                    } else {
                        flag += 1;
                        dir = "R";
                    }
                }
                if (!upAux.getType().equalsIgnoreCase("X")) {

                    if (upAux.getType().equalsIgnoreCase("o")) {
                        return false;
                    } else {
                        flag += 1;
                        dir = "U";
                    }
                } // determino en qué dirección está la llegada

            } else if (downAux==null && upAux!=null && flagPrevious && !flagNext){

                if (!drain.getPrevious().getType().equalsIgnoreCase("X")) {

                    if (drain.getPrevious().getType().equalsIgnoreCase("o")) {
                        return false;
                    } else {
                        flag += 1;
                        dir = "L";
                    }
                }
                if (!upAux.getType().equalsIgnoreCase("X")) {

                    if (upAux.getType().equalsIgnoreCase("o")) {
                        return false;
                    } else {
                        flag += 1;
                        dir = "U";
                    }
                } // determino en qué dirección está la llegada


            } else {
                if(!drain.getNext().getType().equalsIgnoreCase("X")){

                    if(drain.getNext().getType().equalsIgnoreCase("o")){
                        return false;
                    } else {
                        flag += 1;
                        dir = "R";
                    }
                }
                if(!drain.getPrevious().getType().equalsIgnoreCase("X")){

                    if(drain.getPrevious().getType().equalsIgnoreCase("o")){
                        return false;
                    } else {
                        flag += 1;
                        dir = "L";
                    }
                }
                if(!downAux.getType().equalsIgnoreCase("X")){

                    if(downAux.getType().equalsIgnoreCase("o")){
                        return false;
                    } else {
                        flag += 1;
                        dir = "D";
                    }
                }
                if(!upAux.getType().equalsIgnoreCase("X")){

                    if(upAux.getType().equalsIgnoreCase("o")){
                        return false;
                    } else {
                        flag += 1;
                        dir = "U";
                    }
                } // determino en qué dirección está la llegada
            }

            // Si el drenaje posee más de una llegada o no tiene llegadas, le retorno falso, no ha ganado
            if(flag>=2 || flag==0){
                return false;
            } else {
                current = drain;
            } // si solo tiene una llegada entonces el current, en la primera vez de iteración, es el drain.
        } // ✔

        // BASE CASE // ✔

        Pipeline down = null;
        Pipeline up = null;

        if(getDownPipe(current)==null){
            down = new Pipeline("N", -1,-1);
        } else {
            down = getDownPipe(current);
        }

        if(getUpPipe(current)==null){
            up = new Pipeline("N", -1,-1);
        } else {
            up = getUpPipe(current);
        }

        if(dir.equalsIgnoreCase("R") && current.getNext().getType().equalsIgnoreCase("F")){
            return true;

        } else if(dir.equalsIgnoreCase("L") && current.getPrevious().getType().equalsIgnoreCase("F")) {
            return true;

        } else if(dir.equalsIgnoreCase("D") && down.getType().equalsIgnoreCase("F")){
            return true;

        } else if(dir.equalsIgnoreCase("U") && up.getType().equalsIgnoreCase("F")){
            return true;

        } else {  // END OF BASE CASE //

            // Segun la dirección en la que esté la tubería, reviso su next, previous, up, down o cambiod de 90 grados
            if (dir.equalsIgnoreCase("90UD")) {

                if(getDownPipe(current)!=null){

                    if (getDownPipe(current).getType().equalsIgnoreCase("||")) {
                        i++;
                        iterations++;
                        return simulate(getDownPipe(current), i, iterations, "D");

                    } else if(!getDownPipe(current).getType().equalsIgnoreCase("X")){
                        return false;

                    } else if(getUpPipe(current)!=null){
                        if (getUpPipe(current).getType().equalsIgnoreCase("||")) {
                            i++;
                            iterations++;
                            return simulate(getUpPipe(current), i, iterations, "U");

                        } else if(!getUpPipe(current).getType().equalsIgnoreCase("X")){
                            return false;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }

                } else if(getUpPipe(current)!=null){

                    if (getUpPipe(current).getType().equalsIgnoreCase("||")) {
                        i++;
                        iterations++;
                        return simulate(getUpPipe(current), i, iterations, "U");

                    } else if(!getUpPipe(current).getType().equalsIgnoreCase("X")){
                        return false;
                    } else {
                        return false;
                    }

                } else {
                    return false;
                }

            } else if(dir.equalsIgnoreCase("90RL")) {

                boolean rlFlag = true;

                if(current.getNext()!=null){

                    if (current.getNext().getType().equalsIgnoreCase("=")) {
                        i++;
                        iterations++;
                        return simulate(current.getNext(), i, iterations, "R");

                    } else if(!current.getNext().getType().equalsIgnoreCase("X")){
                        return false;

                    } else if(current.getPrevious()!=null){
                        if (current.getPrevious().getType().equalsIgnoreCase("=")) {
                            i++;
                            iterations++;
                            return simulate(current.getPrevious(), i, iterations, "L");

                        } else if(!current.getPrevious().getType().equalsIgnoreCase("X")){
                            return false;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }

                } else if(current.getPrevious()!=null){

                    if (current.getPrevious().getType().equalsIgnoreCase("=")) {
                        i++;
                        iterations++;
                        return simulate(current.getPrevious(), i, iterations, "L");

                    } else if(!current.getPrevious().getType().equalsIgnoreCase("X")){
                        return false;
                    } else {
                        return false;
                    }

                } else {
                    return false;
                }


            } else if (dir.equalsIgnoreCase("R")) {// dir 90

                // si la siguiente tubería es de tipo = o o, entonces sigo, pues son válidas

                if(current.getNext()!=null){

                    if (current.getNext().getType().equalsIgnoreCase("=")) {
                        i++;
                        iterations++;
                        return simulate(current.getNext(), i, iterations, "R");

                    } else if (current.getNext().getType().equalsIgnoreCase("o")) {
                        i++;
                        iterations++;
                        return simulate(current.getNext(), i, iterations, "90UD"); // direction Up Or Down 90

                    } else {
                        return false;
                    }

                } else {
                    return false;
                }


            } else if (dir.equalsIgnoreCase("L")) {// dir right

                if(current.getPrevious()!=null){

                    if (current.getPrevious().getType().equalsIgnoreCase("=")) {
                        i++;
                        iterations++;
                        return simulate(current.getPrevious(), i, iterations, "L");

                    } else if (current.getPrevious().getType().equalsIgnoreCase("o")) {
                        i++;
                        iterations++;
                        return simulate(current.getPrevious(), i, iterations, "90UD");

                    } else {
                        return false;
                    }

                } else {
                    return false;
                }


            } else if (dir.equalsIgnoreCase("D")) {// dir left

                System.out.println(current.getRow()+" - "+current.getColumn());
                if(getDownPipe(current)!=null){

                    if (getDownPipe(current).getType().equalsIgnoreCase("||")) {
                        i++;
                        iterations++;
                        return simulate(getDownPipe(current), i, iterations, "D");

                    } else if (getDownPipe(current).getType().equalsIgnoreCase("o")) {
                        i++;
                        iterations++;
                        return simulate( getDownPipe(current) , i, iterations, "90RL"); // right or left direction 90

                    } else {
                        return false;
                    }

                } else {
                    return false;
                }

            } else {// dir down

                if(getUpPipe(current)!=null){

                    if (getUpPipe(current).getType().equalsIgnoreCase("||")) {
                        i++;
                        iterations++;
                        return simulate(getUpPipe(current), i, iterations, "U");

                    } else if (getUpPipe(current).getType().equalsIgnoreCase("o")) {
                        i++;
                        iterations++;
                        return simulate(getUpPipe(current), i, iterations, "90RL");

                    } else {
                        return false;
                    }

                } else {
                    return false;
                }
            } // dir up
        }

    } // Simulate method
    ////////////////////////////

    // Este método encuentra la pipeline que está arriba de la que yo le coloque. Es recursivo
    public Pipeline getUpPipe(Pipeline current){
        int i = 0;
        return getUpPipe(current, i);
    }

    public Pipeline getUpPipe(Pipeline current, int i){
        if(current==null){
            return null;
        }

        if(i==8){
            return current;
        } else {
            i++; // aqui se lo sumo y entonces comienza a hacer las cosas en i = 1
            return getUpPipe(current.getPrevious(), i);
        }
    }

    // Este método encuentra la pipeline que está abajo de la que yo le coloque. Es recursivo
    public Pipeline getDownPipe(Pipeline current){
        int i = 0;
        return getDownPipe(current, i);
    }

    public Pipeline getDownPipe(Pipeline current, int i){
        if(current==null){
            return null;
        }

        if(i==8){
            return current;
        } else {
            i++;
            return getDownPipe(current.getNext(), i);
        }
    }
    ////////////////////////////

    // Este método se usa en el método de eliminar. Me devuelve el tipo de una pipeline dándole la row y column
    // buscada.
    public String getPipelineType(int row, int column){
        return getPipelineType(head, row, column);
    } // get pipeline type with its row and column
    ////////////////////////////

    // Si la row y column del current son iguales a las buscadas, retorno el tipo de el current. Si no, hace
    // recursividad
    public String getPipelineType(Pipeline current, int row, int column){

        if(current.getRow()==row && current.getColumn()==column){
            return current.getType();
        } else {
            return getPipelineType(current.getNext(), row, column);
        }

    } // get pipeline type
    ////////////////////////////

    public void calculateScore(String nickName, long time, long amountOfPipelines) {

        double score = amountOfPipelines * 100 - (60 - (time)) * 10;
        Player player = new Player(nickName, score);
        scoreboard.addPlayer(player);
    }
    ////////////////////////////

    public String showScoreboard() {

        if(scoreboard.getRoot()!=null){
            scoreboard.printScore();
            return scoreboard.getMsg();
        } else {
            return null;
        }

    }
    ////////////////////////////

    public void calculateAmountOfPipelines() {
        amountOfPipelines = 0;
        calculateAmountOfPipelines(head);
    } ////////////////////////////////////////////////////


    public void calculateAmountOfPipelines(Pipeline pipeline) {

        if (pipeline == null) {
            return;
        } // case base

        if (!pipeline.getType().equalsIgnoreCase("X") && !pipeline.getType().equalsIgnoreCase("F") && !pipeline.getType().equalsIgnoreCase("D")) {
            amountOfPipelines += 1;
        }

        calculateAmountOfPipelines(pipeline.getNext());
    } /////////////////////////////////////////

    //////  GET AND SET ///////
    public int getAmountOfPipelines(){
        return amountOfPipelines;
    }

    public void setAmountOfPipelines(int amountOfPipelines){
        this.amountOfPipelines = amountOfPipelines;
    }

    public Pipeline getHead() {
        return head;
    }

    public void setHead(Pipeline head) {
        this.head = head;
    }

    public Pipeline getTail() {
        return tail;
    }

    public void setTail(Pipeline tail) {
        this.tail = tail;
    }

    public int getRowAmount() {
        return rowAmount;
    }

    public void setRowAmount(int rowAmount) {
        this.rowAmount = rowAmount;
    }

    public int getColumnAmount() {
        return columnAmount;
    }

    public void setColumnAmount(int columnAmount) {
        this.columnAmount = columnAmount;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

} // pipemania controller
