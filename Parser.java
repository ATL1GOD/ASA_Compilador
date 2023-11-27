import java.util.*;

public class Parser {
    private final List<Token> tokens; 
    private int i=0;
    private Token comparar;
    private final Token ultimo= new Token(TipoToken.EOF,"");
    Stack<Integer> pila = new Stack<>();

    public Parser (List<Token> tokens){
        this.tokens=tokens; //constructor de la lista de tokens
    }
    private static final String[][] Acciones ={
        /*Estado 0*/{},
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
        
    }
}
