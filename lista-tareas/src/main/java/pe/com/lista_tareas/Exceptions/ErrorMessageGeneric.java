package pe.com.lista_tareas.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageGeneric {
    private int status;
    private String message;
    private String description;
    private Date timestamp;
}
