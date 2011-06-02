/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.usgs.cida.ncetl.utils;

import java.io.IOException;
import java.io.InputStream;
import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFileWriteable;
import ucar.nc2.dataset.NetcdfDataset;
import ucar.nc2.ncml.NcMLReader;

/**
 *
 * @author jwalker
 */
public class NetCDFUtil {
    
    public static void writeNetCDFFile(InputStream ncmlIn, String outfile) throws IOException {
        NetcdfDataset dataset = NcMLReader.readNcML(ncmlIn, null);
        // catch file too big?
        NetcdfFileWriteable file = NetcdfFileWriteable.createNew(outfile);
        
    }
    
}
