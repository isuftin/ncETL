/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thredds.catalog;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import ucar.nc2.units.DateType;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class InvCatalogSetter {
    
    private InvCatalogImpl cat = null;
    
    public InvCatalogSetter(InvCatalog cat) {
        if (cat == null) {
            throw new IllegalArgumentException("null catalog not allowed");
        }
        this.cat = (InvCatalogImpl)cat;
    }

   
    public void setBaseURI(URI baseURI) {
        cat.baseURI = baseURI;
    }

    public void setDatasets(List<InvDataset> datasets) {
        cat.datasets = datasets;
        cat.finish();
    }

//    public void setDsHash(Map<String, InvDataset> dsHash) {
//        cat.dsHash = dsHash;
//    }

    
    public void setExpires(DateType expires) {
        cat.expires = expires;
    }

    public void setName(String name) {
        cat.name = name;
    }

    public void setProperties(List<InvProperty> properties) {
        cat.properties = properties;
    }

    public void setServices(List<InvService> services) {
        cat.services = services;
        cat.serviceHash = new HashMap<String, InvService>();
        for (InvService serv : services) {
            cat.serviceHash.put(serv.getName(), serv);
        }
    }

    public void setVersion(String version) {
        cat.version = version;
    }
}
