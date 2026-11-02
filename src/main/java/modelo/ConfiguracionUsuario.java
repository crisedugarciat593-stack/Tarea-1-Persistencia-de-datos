package modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_configuracion_usuario")
public class ConfiguracionUsuario {
    
    @Id
    @Column(name = "usr_id", nullable = false)
    private String idUsuario;

    @Column(name = "usr_ruta_logo")
    private String rutaLogo;
    
    @Column(name = "usr_ruta_firma")
    private String rutaFirmaElectronica;
    
    @Column(name = "usr_smtp")
    private String configuracionSmtp;

    // Constructor vacío (Requerido por JPA)
    public ConfiguracionUsuario() {
    }

    // Constructor con parámetros
    public ConfiguracionUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRutaLogo() {
        return rutaLogo;
    }

    public void setRutaLogo(String rutaLogo) {
        this.rutaLogo = rutaLogo;
    }

    public String getRutaFirmaElectronica() {
        return rutaFirmaElectronica;
    }

    public void setRutaFirmaElectronica(String rutaFirmaElectronica) {
        this.rutaFirmaElectronica = rutaFirmaElectronica;
    }

    public String getConfiguracionSmtp() {
        return configuracionSmtp;
    }

    public void setConfiguracionSmtp(String configuracionSmtp) {
        this.configuracionSmtp = configuracionSmtp;
    }
}