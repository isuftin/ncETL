package thredds.catalog;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class InvServiceBuilder {
    private static final String SERVICE_DESCRIPTION = "Service which contains all the allowed serviceTypes";
    
    private InvService compound;
    
    public InvServiceBuilder(String name) {
        compound = new InvService(name, "Compound", "", "", SERVICE_DESCRIPTION);
    }
    
//    public InvServiceBuilder service(ServiceType type) {
//        switch (type) {
//            case HTTPServer: break;
//        }
//        InvService subservice = new InvService(SERVICE_DESCRIPTION,
//                                               SERVICE_DESCRIPTION,
//                                               SERVICE_DESCRIPTION,
//                                               SERVICE_DESCRIPTION,
//                                               SERVICE_DESCRIPTION)
//        
//        compound.addService(compound);
//    }
}
