import java.util.List;
import java.util.Stack;

public class Parser {
    private final List<Token> tokens;
    private int i = 0;
    private Token comparador;
    private final Token ultimo = new Token(TipoToken.EOF, "");
    Stack<Integer> pila = new Stack<>();

    public Parser(List<Token> tokens) {
        this.tokens = tokens; // constructor de la lista de tokens
    }

    private static final String[][] Acciones = {
            /***********
             * select | from | distinct | id | . | * | , | $ | Q | D | P | A | A1 | A2 | T |
             * T1 | T2 16
             */
            /* Estado 0 */ { "s2", "", "", "", "", "", "", "", "1", "", "", "", "", "", "", "", "" },
            /* Estado 1 */ { "", "", "", "", "", "", "", "acc", "", "", "", "", "", "", "", "", "" },
            /* Estado 2 */ { "", "", "s3", "s8", "", "s5", "", "", "", "9", "4", "6", "7", "", "", "", "" },
            /* Estado 3-- */ { "", "", "", "s8", "", "s5", "", "", "", "", "16", "6", "7", "", "", "", "" },
            /* Estado 4 */ { "", "r3", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 5 */ { "", "r4", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 6- */ { "", "r5", "", "", "", "", "s17", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 7 */ { "", "r7", "", "", "", "", "r7", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 8 */ { "", "r10", "", "", "s21", "", "r10", "", "", "", "", "", "", "20", "", "", "" },
            /* Estado 9 */ { "", "s10", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 10 */ { "", "", "", "s13", "", "", "", "", "", "", "", "", "", "", "11", "12", "" },
            /* Estado 11 */ { "", "", "", "", "", "", "s18", "r1", "", "", "", "", "", "", "", "", "" },
            /* Estado 12 */ { "", "", "", "", "", "", "r12", "r12", "", "", "", "", "", "", "", "", "" },
            /* Estado 13 */ { "", "", "", "s15", "", "", "r15", "r15", "", "", "", "", "", "", "", "", "14" },
            /* Estado 14 */ { "", "", "", "", "", "", "r13", "r13", "", "", "", "", "", "", "", "", "" },
            /* Estado 15 */ { "", "", "", "", "", "", "r14", "r14", "", "", "", "", "", "", "", "", "" },
            /* Estado 16 */ { "", "r2", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 17 */ { "", "", "", "", "", "", "", "", "", "", "", "", "19", "", "", "", "" },
            /* Estado 18 */ { "", "", "", "s8", "", "", "", "", "", "", "", "", "", "", "", "23", "" },
            /* Estado 19 */ { "", "r6", "", "s13", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 20 */ { "", "r8", "", "", "", "", "r6", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 21 */ { "", "", "", "s22", "", "", "r8", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 22 */ { "", "r9", "", "", "", "", "r9", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 23 */ { "", "", "", "", "", "", "r11", "r11", "", "", "", "", "", "", "", "", "" },
    };

    public void parse() {
        comparador=tokens.get(i);
        pila.push(0);

        if (comparador.equals(ultimo)) {
            System.out.println("La entrada realizada, no es correcta o aceptable, intentelo nuevamente");
        }

        else {
            while (true) {
                comparador = tokens.get(i);
                int estado = pila.peek();
                int columna = columnaTerminal(comparador.tipo); // funcion
                int estadoMovimiento ;
                String movimiento = Acciones[estado][columna];
                if (movimiento.startsWith("s")) { // s = desplazamiento
                    estadoMovimiento = Integer.parseInt(movimiento.substring(1));
                    pila.push(estadoMovimiento);
                    i++;
                } else if (movimiento.startsWith("r")) { // r = reduccion = retroceso
                    estadoMovimiento = Integer.parseInt(movimiento.substring(1));
                    reduccion(estadoMovimiento);// funcion
                } else if (movimiento.equals("acc")) {
                    System.out.println("La consulta ingresada es correcta");
                    return;
                } else { // error
                    System.out.println("La consulta ingresada es incorrecta, intentalo nuevamente");
                    return;
                }
            }
        }
    }

    private int columnaTerminal(TipoToken tipo) {
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

    void reduccion(int estadoAnterior) {
        String accion;
        int estadoActual, estadoSiguiente;
        switch (estadoAnterior) {
            case 0: // Q -> select D from T
                pila.pop();
                pila.pop();
                pila.pop();
                pila.pop();
                estadoActual = pila.peek();
                accion = Acciones[estadoActual][8];
                estadoSiguiente = Integer.parseInt(accion);
                pila.push(estadoSiguiente);
                break;
            case 1: // D -> distinct P
                pila.pop();
                pila.pop();
                estadoActual = pila.peek();
                accion = Acciones[estadoActual][9];
                estadoSiguiente = Integer.parseInt(accion);
                pila.push(estadoSiguiente);
                break;
            case 2:// D -> P
                pila.pop();
                estadoActual = pila.peek();
                accion = Acciones[estadoActual][9];
                estadoSiguiente = Integer.parseInt(accion);
                pila.push(estadoSiguiente);
                break;
            case 3:// P->*
            case 4:// P->A
                pila.pop();
                estadoActual = pila.peek();
                accion = Acciones[estadoActual][10];
                estadoSiguiente = Integer.parseInt(accion);
                pila.push(estadoSiguiente);
                break;
            case 5: // A->A,A1
                pila.pop();
                pila.pop();
                pila.pop();
                estadoActual = pila.peek();
                accion = Acciones[estadoActual][11];
                estadoSiguiente = Integer.parseInt(accion);
                pila.push(estadoSiguiente);
                break;
            case 6:// A-> A1
                pila.pop();
                estadoActual = pila.peek();
                accion = Acciones[estadoActual][11];
                estadoSiguiente = Integer.parseInt(accion);
                pila.push(estadoSiguiente);
                break;
            case 7:// A1->idA2
                pila.pop();
                pila.pop();
                estadoActual = pila.peek();
                accion = Acciones[estadoActual][12];
                estadoSiguiente = Integer.parseInt(accion);
                pila.push(estadoSiguiente);
                break;

            case 8:// A2->.id
                pila.pop();
                pila.pop();
                estadoActual = pila.peek();
                accion = Acciones[estadoActual][13];
                estadoSiguiente = Integer.parseInt(accion);
                pila.push(estadoSiguiente);
                break;
            case 9: // A2-> E
                estadoActual = pila.peek();
                accion = Acciones[estadoActual][13];
                estadoSiguiente = Integer.parseInt(accion);
                pila.push(estadoSiguiente);
                break;
            case 10:// T->T,T1
                pila.pop();
                pila.pop();
                pila.pop();
                estadoActual = pila.peek();
                accion = Acciones[estadoActual][14];
                estadoSiguiente = Integer.parseInt(accion);
                pila.push(estadoSiguiente);
                break;
            case 11: // T->T1
                pila.pop();
                estadoActual = pila.peek();
                accion = Acciones[estadoActual][14];
                estadoSiguiente = Integer.parseInt(accion);
                pila.push(estadoSiguiente);
                break;
            case 12:// T1->idT2
                pila.pop();
                pila.pop();
                estadoActual = pila.peek();
                accion = Acciones[estadoActual][15];
                estadoSiguiente = Integer.parseInt(accion);
                pila.push(estadoSiguiente);
                break;
            case 13:// T2->id
                pila.pop();
                estadoActual = pila.peek();
                accion = Acciones[estadoActual][16];
                estadoSiguiente = Integer.parseInt(accion);
                pila.push(estadoSiguiente);
                break;
            case 14:// T2->E
                estadoActual = pila.peek();
                accion = Acciones[estadoActual][16];
                estadoSiguiente = Integer.parseInt(accion);
                pila.push(estadoSiguiente);
                break;
            default:
                System.out.println("Error en la reducción");
                System.exit(0);
                break;

        }
    }
}
