package br.com.ykronnos.testesunitarioscurso.exeptions;

import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date timestamp;
    private String menssage;
    private String details;

    public ExceptionResponse(Date timestamp, String menssage, String details) {
        this.timestamp = timestamp;
        this.menssage = menssage;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMenssage() {
        return menssage;
    }

    public void setMenssage(String menssage) {
        this.menssage = menssage;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
