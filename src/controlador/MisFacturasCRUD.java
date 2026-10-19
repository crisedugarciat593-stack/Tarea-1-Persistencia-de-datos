package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.ConfiguracionUsuario;
import modelo.DocumentoElectronico;

public class MisFacturasCRUD {
    private List<DocumentoElectronico> baseDatosDocumentos;
    private Map<String, ConfiguracionUsuario> baseDatosConfiguraciones;

    public MisFacturasCRUD() {
        this.baseDatosDocumentos = new ArrayList<>();
        this.baseDatosConfiguraciones = new HashMap<>();
    }

    //  CREATE 

    // Gestiona la creación de facturas, notas de crédito, notas de débito y comprobantes de pago
    public DocumentoElectronico generarDocumento(String tipoDocumento, String datos) {
        String nuevoId = String.valueOf(baseDatosDocumentos.size() + 1);
        DocumentoElectronico nuevoDoc = new DocumentoElectronico(nuevoId, tipoDocumento, datos);
        baseDatosDocumentos.add(nuevoDoc);
        return nuevoDoc;
    }

    //    READ 

    public DocumentoElectronico consultarDocumentoEmitido(String idDocumento) {
        return baseDatosDocumentos.stream()
                .filter(doc -> doc.getId().equals(idDocumento))
                .findFirst()
                .orElse(null);
    }

    public String obtenerObservacionesSRI(String idDocumento) {
        DocumentoElectronico doc = consultarDocumentoEmitido(idDocumento);
        return (doc != null) ? doc.getObservacionesSri() : null;
    }

    // Retorna el RIDE (PDF) y el XML generado bajo los estandares del SRI
    public Map<String, String> descargarRideXml(String idDocumento) {
        DocumentoElectronico doc = consultarDocumentoEmitido(idDocumento);
        if (doc != null) {
            Map<String, String> archivos = new HashMap<>();
            archivos.put("XML", "<factura id='" + idDocumento + "'></factura>");
            archivos.put("RIDE", "PDF_DATA_" + idDocumento);
            return archivos;
        }
        return null;
    }

    public List<DocumentoElectronico> generarReportePorFecha(Date fechaInicio, Date fechaFin) {
        // logica de filtrado de registros mediante rangos de tiempo
        return new ArrayList<>();
    }

    //        UPDATE 

    // Utiliza la configuración del usuario para reenviar los comprobantes
    public boolean reenviarCorreoElectronico(String idDocumento, String correoDestino) {
        DocumentoElectronico doc = consultarDocumentoEmitido(idDocumento);
        if (doc != null) {
            // Logica de reenvio del email
            return true;
        }
        return false;
    }

    public void modificarConfiguracionUsuario(String idUsuario, ConfiguracionUsuario nuevaConfiguracion) {
        baseDatosConfiguraciones.put(idUsuario, nuevaConfiguracion);
    }

    //     DELETE 

    // Logica de seguridad: Solo se eliminaran los que no hallan sido enviados al SRI o sin firmar
    public boolean eliminarFacturaBorrador(String idDocumento) {
        return baseDatosDocumentos.removeIf(doc -> 
            doc.getId().equals(idDocumento) && doc.getEstado().equalsIgnoreCase("BORRADOR")
        );
    }

    // eliminacion de registros en general
    public boolean borrarRegistro(String idDocumento) {
        DocumentoElectronico doc = consultarDocumentoEmitido(idDocumento);
        if (doc != null) {
            doc.setEstado("ELIMINADO_INACTIVO");
            return true;
        }
        return false;
    }
}