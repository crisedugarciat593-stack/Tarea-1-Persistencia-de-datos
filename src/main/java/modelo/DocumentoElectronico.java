package modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_documento_electronico")
public class DocumentoElectronico {
    
    @Id
    @Column(name = "doc_id", nullable = false)
    private String id;
    
    @Column(name = "doc_tipo")
    private String tipo; 
    
    @Column(name = "doc_estado")
    private String estado; 
    
    @Column(name = "doc_observaciones")
    private String observacionesSri;
    
    @Column(name = "doc_datos")
    private String datos;

    // Constructor vacío (Requerido por JPA)
    public DocumentoElectronico() {
    }

    // Constructor con parámetros
    public DocumentoElectronico(String id, String tipo, String datos) {
        this.id = id;
        this.tipo = tipo;
        this.datos = datos;
        this.estado = "GENERADO";
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacionesSri() {
        return observacionesSri;
    }

    public void setObservacionesSri(String observacionesSri) {
        this.observacionesSri = observacionesSri;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }
}