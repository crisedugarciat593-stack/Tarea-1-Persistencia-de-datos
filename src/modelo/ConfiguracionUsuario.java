package modelo;

public class ConfiguracionUsuario {
    private String idUsuario;
    private String rutaLogo;
    private String rutaFirmaElectronica;
    private String configuracionSmtp;

    public ConfiguracionUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setRutaLogo(String rutaLogo) { this.rutaLogo = rutaLogo; }
    public void setRutaFirmaElectronica(String rutaFirmaElectronica) { this.rutaFirmaElectronica = rutaFirmaElectronica; }
    public void setConfiguracionSmtp(String configuracionSmtp) { this.configuracionSmtp = configuracionSmtp; }
}