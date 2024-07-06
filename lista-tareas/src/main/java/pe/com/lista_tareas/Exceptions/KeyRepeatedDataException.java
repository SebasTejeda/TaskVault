package pe.com.lista_tareas.Exceptions;

public class KeyRepeatedDataException extends RuntimeException{

    public KeyRepeatedDataException() {
        super();
    }

    public KeyRepeatedDataException(String message) {
        super(message);
    }
}
