import java.util.*;

public class ASA_LL {
    private final List<Token> tokens;
    private int i = 0;
    private Token comparador;
    private final Token ultimo = new Token(TipoToken.EOF, "");
    Stack<Integer> pila = new Stack<>();

    public ASA_LL(List<Token> tokens) {
        this.tokens = tokens; // constructor de la lista de tokens
    }

    private static final String[][] Acciones = {
            /* Estado 0 */ { "s1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 1 */ { "", "", "s3", "s5", "", "s8", "", "", "", "2", "4", "6", "", "7", "", "", "", "", "" },
            /* Estado 2 */ { "", "s9", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 3 */ { "", "", "", "s5", "", "s8", "", "", "", "", "10", "6", "", "7", "", "", "", "", "" },
            /* Estado 4 */ { "", "r2", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 5 */ { "", "r3", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 6 */ { "", "r4", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 7 */ { "", "r7", "", "", "s12", "", "", "", "", "", "", "", "11", "", "", "", "", "", "" },
            /* Estado 8 */ { "", "r10", "", "", "r10", "", "s14", "", "", "", "", "", "", "", "13", "", "", "", "" },
            /* Estado 9 */ { "", "", "", "", "", "s17", "", "", "", "", "", "", "", "", "", "15", "", "16", "" },
            /* Estado 10 */ { "", "r1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 11 */ { "", "r5", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 12 */ { "", "", "", "", "", "s8", "", "", "", "", "", "18", "", "7", "", "", "", "", "" },
            /* Estado 13 */ { "", "r8", "", "", "r8", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 14 */ { "", "", "", "", "", "s19", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 15 */ { "", "", "", "", "", "", "", "acc", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 16 */ { "", "", "", "", "s21", "", "", "r13", "", "", "", "", "", "", "", "", "20", "", "" },
            /* Estado 17 */ { "", "", "", "", "r16", "s23", "", "r16", "", "", "", "", "", "", "", "", "", "", "22" },
            /* Estado 18 */ { "", "r6", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 19 */ { "", "r9", "", "", "r9", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 20 */ { "", "", "", "", "", "", "", "r11", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 21 */ { "", "", "", "", "", "s17", "", "", "", "", "", "", "", "", "", "24", "", "16", "" },
            /* Estado 22 */ { "", "", "", "", "r14", "", "", "r14", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 23 */ { "", "", "", "", "r15", "", "", "r15", "", "", "", "", "", "", "", "", "", "", "" },
            /* Estado 24 */ { "", "", "", "", "", "", "", "r12", "", "", "", "", "", "", "", "", "", "", "" }
    };

    public void parse() {
        comparador = tokens.get(i);
        pila.push(0);

        if (comparador.equals(ultimo)) {
            System.out.println("La entrada realizada, no es correcta o aceptable, intentelo nuevamente");
        }

        else {
            while (true) {
                comparador = tokens.get(i);
                int estado = pila.peek();
                int columna = columnaTerminal(comparador.tipo); // funcion
                int estadoMovimiento;
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
        else if (t.equals("ASTERISCO"))
            return 3;
        else if (t.equals("COMA"))
            return 4;
        else if (t.equals("IDENTIFICADOR"))
            return 5;
        else if (t.equals("PUNTO"))
            return 6;
        else if (t.equals("EOF"))
            return 7;
        else
            return 8; // ERROR
    }

    void reduccion(int estadoAnterior) {
        String action;
        int edoActual;
        int edoSig;
        switch (estadoAnterior) {
            case 0: // Q -> select D from T
                // se quitan 4 elementos de la pila
                pila.pop();
                pila.pop();
                pila.pop();
                pila.pop();
                edoActual = pila.peek(); // obtenemos el elemento que esta en la punta de la pila para saber en que estado andamos
                action = Acciones[edoActual][8]; // sacamos la siguiente acción
                edoSig = Integer.parseInt(action); // convertimos en enetero el string de la matriz
                pila.push(edoSig); // ponemos en la pila el nuevo estado
                break;
            case 1: // D -> distinct P
                pila.pop();
                pila.pop();
                edoActual = pila.peek();
                action = Acciones[edoActual][9];
                edoSig = Integer.parseInt(action);
                pila.push(edoSig);
                break;
            case 2: // D -> P
                pila.pop();
                edoActual = pila.peek();
                action = Acciones[edoActual][9];
                edoSig = Integer.parseInt(action);
                pila.push(edoSig);
                break;
            case 3:// P -> *
            case 4:// P -> A, el caso 3 es igual que el caso 4
                pila.pop();
                edoActual = pila.peek();
                action = Acciones[edoActual][10];
                edoSig = Integer.parseInt(action);
                pila.push(edoSig);
                break;
            case 5: // A -> A1A2
                pila.pop();
                pila.pop();
                edoActual = pila.peek();
                action = Acciones[edoActual][11];
                edoSig = Integer.parseInt(action);
                pila.push(edoSig);
                break;
            case 6: // A1 -> ,A
                pila.pop();
                pila.pop();
                edoActual = pila.peek();
                action = Acciones[edoActual][12];
                edoSig = Integer.parseInt(action);
                pila.push(edoSig);
                break;
            case 7: // A1 -> E
                edoActual = pila.peek();
                action = Acciones[edoActual][12];
                edoSig = Integer.parseInt(action);
                pila.push(edoSig);
                break;
            case 8: // A2 -> id A3
                pila.pop();
                pila.pop();
                edoActual = pila.peek();
                action = Acciones[edoActual][13];
                edoSig = Integer.parseInt(action);
                pila.push(edoSig);
                break;
            case 9: // A3 -> . id
                pila.pop();
                pila.pop();
                edoActual = pila.peek();
                action = Acciones[edoActual][14];
                edoSig = Integer.parseInt(action);
                pila.push(edoSig);
                break;
            case 10: // A3 -> E
                edoActual = pila.peek();
                action = Acciones[edoActual][14];
                edoSig = Integer.parseInt(action);
                pila.push(edoSig);
                break;
            case 11: // T -> T2T1
                pila.pop();
                pila.pop();
                edoActual = pila.peek();
                action = Acciones[edoActual][15];
                edoSig = Integer.parseInt(action);
                pila.push(edoSig);
                break;
            case 12: // T1 -> , T
                pila.pop();
                pila.pop();
                edoActual = pila.peek();
                action = Acciones[edoActual][16];
                edoSig = Integer.parseInt(action);
                pila.push(edoSig);
                break;
            case 13: // T1 -> epsilon
                edoActual = pila.peek();
                action = Acciones[edoActual][16];
                edoSig = Integer.parseInt(action);
                pila.push(edoSig);
                break;
            case 14: // T2 -> id T3
                pila.pop();
                pila.pop();
                edoActual = pila.peek();
                action = Acciones[edoActual][17];
                edoSig = Integer.parseInt(action);
                pila.push(edoSig);
                break;
            case 15: // T3 -> id
                pila.pop();
                edoActual = pila.peek();
                action = Acciones[edoActual][18];
                edoSig = Integer.parseInt(action);
                pila.push(edoSig);
                break;
            case 16: // T3 -> epsilon
                edoActual = pila.peek();
                action = Acciones[edoActual][18];
                edoSig = Integer.parseInt(action);
                pila.push(edoSig);
                break;
            default:
                System.out.println("Error en la reducción");
                System.exit(0);
                break;
        }
    }
}
