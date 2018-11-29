package org.ieszaidinvergeles.dam.chaterbot.objetos;

public class Chat {

    private long id,idConversacion;
    private String quien, texto;

    public Chat() {
        this(0,0,"","");
    }

    public Chat(long id, long idConversacion, String quien, String texto) {
        this.id = id;
        this.idConversacion = idConversacion;
        this.quien = quien;
        this.texto = texto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdConversacion() {
        return idConversacion;
    }

    public void setIdConversacion(long idConversacion) {
        this.idConversacion = idConversacion;
    }

    public String getQuien() {
        return quien;
    }

    public void setQuien(String quien) {
        this.quien = quien;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", idConversacion=" + idConversacion +
                ", quien='" + quien + '\'' +
                ", texto='" + texto + '\'' +
                '}';
    }
}
