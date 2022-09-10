import java.util.Random;

public class PipeManiaController {

        private Pipeline head;
        private Pipeline tail;
        private int rowAmount;
        private int columnAmount;
        private Player player;
        private String msg;

        public PipeManiaController(){ // Constructor del double linked list (board de juego)
            this.head = head;
            this.tail = tail;
            this.rowAmount = rowAmount;
            this.columnAmount = columnAmount;
            this.player = player;
            this.msg = "";
        } // constructor



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
            this.player = new Player(player);
            Pipeline pipeline;

            for(int m=0; m<2; m++){ // creo un for para darle una row y una column a la fuente y al drenaje
                int randomR = rand.nextInt(this.rowAmount);
                int randomC = rand.nextInt(this.columnAmount);
                System.out.println(randomR+" "+randomC);

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

            } else if( (columnF==columnD && rowF==rowD-1) || (columnF==columnD && rowF==rowD+1) || (rowF==rowD && columnF == columnD-1) || (rowF==rowD && columnF==columnD+1)
            || (columnF==columnD-1 && rowF==rowD-1) || (columnF==columnD+1 && rowF==rowD-1) || (columnF==columnD-1 && rowF==rowD+1) || (columnF==columnD+1 && rowF==rowD+1)) {
                // son todas las condiciones en las que la fuente quede alrededor del drenaje y entonces allí el jugador ganaría sin necesidad de hacer nada o no podría
                // ganar con las reglas del juego. Por tanto, valido todas esas situaciones y le sumo +2 a row y column de la fuente.
                rowF += 2;
                columnF += 2;

            } else if( (columnD==columnF && rowD==rowF-1) || (columnD==columnF && rowD==rowF+1) || (rowD==rowF && columnD == columnF-1) || (rowD==rowF && columnD==columnF+1)
                    || (columnD==columnF-1 && rowD==rowF-1) || (columnD==columnF+1 && rowD==rowF-1) || (columnD==columnF-1 && rowD==rowF+1) || (columnD==columnF+1 && rowD==rowF+1)){
                rowD += 2;
                columnD += 2;
            }

            for(int i=0; i<(this.rowAmount); i++){
                for(int j=0; j<(this.columnAmount); j++){

                    if(i==rowF && j==columnF){
                        pipeline = new Pipeline("F", i, j); // Creo la fuente
                    } else if(i==rowD && j==columnD){
                        pipeline = new Pipeline("D", i, j); // Creo el drenaje
                    } else {
                        pipeline = new Pipeline("X", i, j); // Creo la tubería
                    }

                    createBoard(pipeline); //
                }
            } // for row * column
            return true;
        } // createboard ///////////////////////////

        public boolean createBoard(Pipeline pipeline){ // Aquí creo como tal la board, seteando la head si es null y
            // añadiendo cada tubería al final (tail)

            if (head == null) {
                this.head = pipeline;
                return true;

            } else if(tail==null){
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



        public void printBoard(){ // imprimir el board, este es el método que llama al recursivo
            // mando como parámetro el mensaje a llenar y la head para que comience a hacer recursividad
            msg ="";
            int i = 0;
            printBoard(head, i, this.columnAmount);
        } ////////////////////////////////////////////////////

        public void printBoard(Pipeline pipeline, int i, int column){ // hago el caso base, si el siguiente al current es null
            // entonces retorno null, si no entonces lleno el mensaje con el tipo de cada pipeline

            if(pipeline==null){
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



        public boolean addPipeline(String type, int row, int column){
            return addPipeline(type, row, column, head);
        } // addPipeline

        public boolean addPipeline(String type, int row, int column, Pipeline current){
            if (current==null){
                return false;
            }

            if (current.getRow() == row && current.getColumn() == column) { // si la row de la current y su columna son las que estoy buscando entonces la cambio por la que agregó
                current.setType(type);
                return true;
            }

            return addPipeline(type, row, column, current.getNext());

        } // add pipeline //////////////////////////////////////////////////////////////


        //////  GET AND SET ///////
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
