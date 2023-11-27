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
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{},
        /*Estado 0*/{}
    };
}
