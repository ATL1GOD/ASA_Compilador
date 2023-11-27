import java.util.*;

public class Parser {
    private final List<Token> tokens; 
    private int i=0;
    private Token comparador;
    private final Token ultimo= new Token(TipoToken.EOF,"");
    Stack<Integer> pila = new Stack<>();

    public Parser (List<Token> tokens){
        this.tokens=tokens; //constructor de la lista de tokens
    }
    private static final String[][] Acciones ={
        /*            */
        /*Estado 0*/{"s2","",""},
        /*Estado 1*/{},
        /*Estado 2*/{},
        /*Estado 3*/{},
        /*Estado 4*/{},
        /*Estado 5*/{},
        /*Estado 6*/{},
        /*Estado 7*/{},
        /*Estado 8*/{},
        /*Estado 9*/{},
        /*Estado 10*/{},
        /*Estado 11*/{},
        /*Estado 12*/{},
        /*Estado 13*/{},
        /*Estado 14*/{},
        /*Estado 15*/{},
        /*Estado 16*/{},
        /*Estado 17*/{},
        /*Estado 18*/{},
        /*Estado 19*/{},
        /*Estado 20*/{},
        /*Estado 21*/{},
        /*Estado 22*/{},
        /*Estado 23*/{},
        /*Estado 24*/{},
    };

    public void parse(){
        pila.push(0);

        if(comparador.equals(ultimo)){
            System.out.println("La entrada realizada, no es correcta o aceptable, intentelo nuevamente");
        }

        else{
            while(true){
                comparador=tokens.get(i);
                int estado=pila.peek();
                int columna = columnaTerminal(comparador.tipo); //funcion
                int estadoMovimiento=0;
                String movimiento=Acciones[estado][columna];
                if(movimiento.startsWith("s")){ //s = desplazamiento
                    estadoMovimiento=Integer.parseInt(movimiento.substring(1));
                    pila.push(estadoMovimiento);
                    i++;
                } 
                else if (movimiento.startsWith("r")){ // r = reduccion = retroceso
                    estadoMovimiento=Integer.parseInt(movimiento.substring(1));
                    reduccion(estadoMovimiento);//funcion
                }
                else if (movimiento.equals("acc")){
                    System.out.println("La consulta ingresada es correcta");
                    return;
                }
                else{ //error
                    System.out.println("La consulta ingresada es incorrecta, intentalo nuevamente");
                    return;
                }
            }
        }
    }

    private int columnaTerminal(TipoToken tipo){
        String t = String.valueOf(tipo); // Convertimos el tipo de token en String para compararlo
        if (t.equals("SELECT"))
            return 0;
        else if (t.equals("FROM"))
            return 1;
        else if (t.equals("DISTINCT"))
            return 2;
        else if (t.equals("IDENTIFICADOR"))
            return 3;
        else if (t.equals("PUNTO"))
            return 4;
        else if (t.equals("ASTERISCO"))
            return 5;
        else if (t.equals("COMA"))
            return 6;
        else if (t.equals("EOF"))
            return 7;
        else
            return 8; // ERROR
    }

    void reduccion(int estadoAnterior){

    }
}
