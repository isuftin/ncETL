package thredds.catalog;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class InvServiceBuilder {

    private static final String DODS_NAME = "ncdods";
    private static final String DODS_DESCRIPTION = "OPeNDAP service endpoint";
    private static final String DODS_BASE = "/thredds/dodsC/";
    private static final String HTTP_NAME = "HTTPServer";
    private static final String HTTP_DESCRIPTION = "File server for direct access";
    private static final String HTTP_BASE = "/thredds/fileServer/";
    private static final String WMS_NAME = "wms";
    private static final String WMS_DESCRIPTION = "OGC WMS service endpoint";
    private static final String WMS_BASE = "/thredds/wms/";
    private static final String WCS_NAME = "wcs";
    private static final String WCS_DESCRIPTION = "OGC WCS service endpoint";
    private static final String WCS_BASE = "/thredds/wcs/";
    private static final String NCSS_NAME = "ncss";
    private static final String NCSS_DESCRIPTION = "NetCDF subset service";
    private static final String NCSS_BASE = "/thredds/ncss/grid/";
    private static final String NCML_NAME = "ncml";
    private static final String NCML_DESCRIPTION = "ncISO generated NcML for dataset";
    private static final String NCML_BASE = "/thredds/ncml/";
    private static final String UDDC_NAME = "uddc";
    private static final String UDDC_DESCRIPTION = "ncISO generated rubric using the Unidata Dataset Discovery Conventions";
    private static final String UDDC_BASE = "/thredds/uddc/";
    private static final String ISO_NAME = "iso";
    private static final String ISO_DESCRIPTION = "ncISO generated ISO 19115-2 Metadata record";
    private static final String ISO_BASE = "/thredds/iso/";
    private static final String COMPOUND_DESCRIPTION = "Service which contains all the allowed serviceTypes";
    private InvService compound;
    private boolean built = false;

    public InvServiceBuilder(String name) {
        compound = new InvService(name, "Compound", "", "", COMPOUND_DESCRIPTION);
    }

    public InvServiceBuilder service(String type) {
        if ("dods".equalsIgnoreCase(type) || "opendap".equalsIgnoreCase(type)) {
            InvService subservice = new InvService(DODS_NAME, type, DODS_BASE,
                                                   "", DODS_DESCRIPTION);
            compound.addService(subservice);
        }
        else if ("httpserver".equalsIgnoreCase(type)) {
            InvService subservice = new InvService(HTTP_NAME, type, HTTP_BASE,
                                                   "", HTTP_DESCRIPTION);
            compound.addService(subservice);
        }
        else if ("wms".equalsIgnoreCase(type)) {
            InvService subservice = new InvService(WMS_NAME, type, WMS_BASE,
                                                   "", WMS_DESCRIPTION);
            compound.addService(subservice);
        }
        else if ("wcs".equalsIgnoreCase(type)) {
            InvService subservice = new InvService(WCS_NAME, type, WCS_BASE,
                                                   "", WCS_DESCRIPTION);
            compound.addService(subservice);
        }
        else if ("ncss".equalsIgnoreCase(type)) {
            InvService subservice = new InvService(NCSS_NAME, type, NCSS_BASE,
                                                   "", NCSS_DESCRIPTION);
            compound.addService(subservice);
        }
        else if ("ncml".equalsIgnoreCase(type)) {
            InvService subservice = new InvService(NCML_NAME, type, NCML_BASE,
                                                   "", NCML_DESCRIPTION);
            compound.addService(subservice);
        }
        else if ("uddc".equalsIgnoreCase(type)) {
            InvService subservice = new InvService(UDDC_NAME, type, UDDC_BASE,
                                                   "", UDDC_DESCRIPTION);
            compound.addService(subservice);
        }
        else if ("iso".equalsIgnoreCase(type)) {
            InvService subservice = new InvService(ISO_NAME, type, ISO_BASE,
                                                   "", ISO_DESCRIPTION);
            compound.addService(subservice);
        }
        else {
            throw new IllegalArgumentException(type + " is not an allowed type");
        }
        return this;
    }
    
    public InvService build() {
        if (!built) {
            built = true;
            return compound;
        }
        else {
            throw new UnsupportedOperationException("build() can only be called once");
        }
    }
}
