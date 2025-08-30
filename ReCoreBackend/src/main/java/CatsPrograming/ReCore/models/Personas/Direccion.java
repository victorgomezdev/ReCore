package CatsPrograming.ReCore.models.Personas;

public class Direccion {
    private Integer id;
    private Integer idpersona;
    private String calle;
    private String numero;
    private String ciudad;
    private String provincia;
    private String codigoPostal;
    private String geoReferencia;

    public Direccion(Integer id, Integer idpersona, String calle, String numero, String ciudad, String provincia,
            String codigoPostal, String geoReferencia) {
        this.id = id;
        this.idpersona = idpersona;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
        this.geoReferencia = geoReferencia;
    }

    public Direccion(String calle, Integer idpersona, String numero, String ciudad, String ciudad1, String provincia,
            String codigoPostal, String geoReferencia) {
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
        this.geoReferencia = geoReferencia;
    }

    public Direccion(java.util.Map<String, Object> map) {
        this.id = map.get("id") != null ? ((Number) map.get("id")).intValue() : null;
        this.idpersona = map.get("idpersona") != null ? ((Number) map.get("idpersona")).intValue() : null;
        this.calle = (String) map.get("calle");
        this.numero = (String) map.get("numero");
        this.ciudad = (String) map.get("ciudad");
        this.provincia = (String) map.get("provincia");
        this.codigoPostal = (String) map.get("codigoPostal");
        this.geoReferencia = (String) map.get("geoReferencia");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Integer idpersona) {
        this.idpersona = idpersona;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getLocation() {
        return this.ciudad;
    }

    public void setLocation(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getGeoReferencia() {
        return geoReferencia;
    }

    public void setGeoReferencia(String geoReferencia) {
        this.geoReferencia = geoReferencia;
    }
}
