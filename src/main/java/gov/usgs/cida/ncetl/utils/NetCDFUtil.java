/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.usgs.cida.ncetl.utils;

import gov.noaa.eds.threddsutilities.exception.ThreddsUtilitiesException;
import gov.noaa.eds.threddsutilities.util.ThreddsTranslatorUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import ucar.nc2.Attribute;
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
    
    public static void globalAttributesToMeta(String filename) throws IOException {
        NetcdfFile ncf = NetcdfFile.open(filename);
        List<Attribute> globalAttributes = ncf.getGlobalAttributes();
        for (Attribute att : globalAttributes) {
            String name = att.getName();
            String val = att.getStringValue();
            String output = (new StringBuilder().append("<attribute name=\"")
                    .append(name).append("\" value=\"").append(val)
                    .append("\" />")).toString();
            System.out.println(output);
        }
    }
    
    public static synchronized File createNcML(String filename) throws ThreddsUtilitiesException {
        String ncmlName = filename + ".ncml";
        File ncmlFile = new File(ncmlName);
        if (!ncmlFile.exists()) {
                ncmlFile = ThreddsTranslatorUtil.getNcml(filename, ncmlName);
        }
        return ncmlFile;
    }
}