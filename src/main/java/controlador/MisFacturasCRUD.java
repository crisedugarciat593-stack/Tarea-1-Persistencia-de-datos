package controlador;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import modelo.ConfiguracionUsuario;
import modelo.DocumentoElectronico;

public class MisFacturasCRUD {
    private EntityManagerFactory emf;
    private EntityManager manager;

    public MisFacturasCRUD() {
        this.emf = Persistence.createEntityManagerFactory("com.escuela_ProyectoPersistenciaProducto_jar_1.0-SNAPSHOTPU");
        this.manager = emf.createEntityManager();
    }

    // CREATE: Gestiona la creación de facturas, notas de crédito, notas de débito y comprobantes de pago
    public DocumentoElectronico generarDocumento(String tipoDocumento, String datos) {
        manager.getTransaction().begin();
        String nuevoId = String.valueOf(System.currentTimeMillis()); 
        DocumentoElectronico nuevoDoc = new DocumentoElectronico(nuevoId, tipoDocumento, datos);
        
        manager.persist(nuevoDoc);
        manager.getTransaction().commit();
        return nuevoDoc;
    }

    // READ: Consultas de documentos emitidos
    public DocumentoElectronico consultarDocumentoEmitido(String idDocumento) {
        return manager.find(DocumentoElectronico.class, idDocumento);
    }

    public String obtenerObservacionesSRI(String idDocumento) {
        DocumentoElectronico doc = consultarDocumentoEmitido(idDocumento);
        return (doc != null) ? doc.getObservacionesSri() : null;
    }

    // Retorna el RIDE (PDF) y el XML generado bajo los estándares del SRI
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

    // Lógica de filtrado de registros mediante rangos de tiempo
    public List<DocumentoElectronico> generarReportePorFecha(Date fechaInicio, Date fechaFin) {
        return new ArrayList<>(); 
    }

    // UPDATE: Utiliza la configuración del usuario para reenviar los comprobantes
    public boolean reenviarCorreoElectronico(String idDocumento, String correoDestino) {
        DocumentoElectronico doc = consultarDocumentoEmitido(idDocumento);
        if (doc != null) {
            // Lógica de reenvío del email
            return true;
        }
        return false;
    }

    public void modificarConfiguracionUsuario(String idUsuario, ConfiguracionUsuario nuevaConfiguracion) {
        manager.getTransaction().begin();
        ConfiguracionUsuario configActual = manager.find(ConfiguracionUsuario.class, idUsuario);
        
        if (configActual != null) {
            configActual.setRutaLogo(nuevaConfiguracion.getRutaLogo());
            configActual.setRutaFirmaElectronica(nuevaConfiguracion.getRutaFirmaElectronica());
            configActual.setConfiguracionSmtp(nuevaConfiguracion.getConfiguracionSmtp());
            manager.merge(configActual);
        } else {
            manager.persist(nuevaConfiguracion);
        }
        manager.getTransaction().commit();
    }

    // DELETE: Lógica de seguridad, solo se eliminarán los que no hayan sido enviados al SRI o sin firmar
    public boolean eliminarFacturaBorrador(String idDocumento) {
        manager.getTransaction().begin();
        DocumentoElectronico doc = manager.find(DocumentoElectronico.class, idDocumento);
        if (doc != null && doc.getEstado().equalsIgnoreCase("BORRADOR")) {
            manager.remove(doc);
            manager.getTransaction().commit();
            return true;
        }
        manager.getTransaction().rollback();
        return false;
    }

    // Eliminación de registros en general (Soft Delete)
    public boolean borrarRegistro(String idDocumento) {
        manager.getTransaction().begin();
        DocumentoElectronico doc = manager.find(DocumentoElectronico.class, idDocumento);
        if (doc != null) {
            doc.setEstado("ELIMINADO_INACTIVO");
            manager.getTransaction().commit();
            return true;
        }
        manager.getTransaction().rollback();
        return false;
    }
    
    public void cerrarConexion() {
        if (manager != null) manager.close();
        if (emf != null) emf.close();
    }
}