package modelo;


public class DocumentoElectronico {
    private String id;
    private String tipo; 
    private String estado; 
    private String observacionesSri;
    private String datos;

    public DocumentoElectronico(String id, String tipo, String datos) {
        this.id = id;
        this.tipo = tipo;
        this.datos = datos;
        this.estado = "GENERADO";
    }

    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public String getEstado() { return estado; }
    public String getObservacionesSri() { return observacionesSri; }
    
    public void setEstado(String estado) { this.estado = estado; }
    public void setObservacionesSri(String observacionesSri) { this.observacionesSri = observacionesSri; }
}