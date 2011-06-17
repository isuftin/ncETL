/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.usgs.cida.ncetl.utils;
import java.io.FileNotFoundException;
import org.jdom.JDOMException;
import thredds.server.metadata.bean.Extent;
import thredds.server.metadata.exception.ThreddsUtilitiesException;
import thredds.server.metadata.util.ThreddsTranslatorUtil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import thredds.server.metadata.util.NCMLModifier;
import thredds.server.metadata.util.ThreddsExtentUtil;
import ucar.nc2.Attribute;
import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFileWriteable;
import ucar.nc2.dataset.NetcdfDataset;
import ucar.nc2.ncml.NcMLReader;

/**
 *
 * @author jwalker
 */
public class NcMLUtil {
    
    private NcMLUtil(){}

    public static void writeNetCDFFile(InputStream ncmlIn, String outfile)
            throws IOException {
        NetcdfDataset dataset = NcMLReader.readNcML(ncmlIn, null);
        // catch file too big?
        NetcdfFileWriteable file = NetcdfFileWriteable.createNew(outfile);
    }

    public static void globalAttributesToMeta(String filename) throws
            IOException {
        NetcdfFile ncf = NetcdfFile.open(filename);
        List<Attribute> globalAttributes = ncf.getGlobalAttributes();
        for (Attribute att : globalAttributes) {
            String name = att.getName();
            String val = att.getStringValue();
            String output = (new StringBuilder().append("<attribute name=\"").append(
                    name).append("\" value=\"").append(val).append("\" />")).toString();
            System.out.println(output);
        }
    }

    public static synchronized File createNcML(String filename) throws
            ThreddsUtilitiesException {
        String ncmlName = filename + ".ncml";
        File ncmlFile = new File(ncmlName);
        if (!ncmlFile.exists()) {
            try {
                String ncmlPath = ncmlFile.getCanonicalPath();
                ncmlFile = ThreddsTranslatorUtil.getNcml(filename, ncmlPath);
                Extent extent = ThreddsExtentUtil.getExtent(ncmlPath);
                NCMLModifier ncmod = new NCMLModifier();
                Element rootElement = getRootElement(ncmlPath);
                ncmod.addCFMetadata(extent, rootElement);
                Document document = rootElement.getDocument();
                writeDocument(document, ncmlFile);
            }
            catch (Exception ex) {
                throw new ThreddsUtilitiesException(
                        "Difficulty writing ncml, check dataset",
                                                    ThreddsUtilitiesException.EXCEPTION_TYPES.IO_EXCEPTION);
            }
        }
        return ncmlFile;
    }

    public static Element getRootElement(String filename) throws ThreddsUtilitiesException {
        try {
            Document doc = getDocument(filename);
            return doc.getRootElement();
        }
        catch (JDOMException ex) {
            throw new ThreddsUtilitiesException(
                        "JDOMException while getting root element",
                                                    ThreddsUtilitiesException.EXCEPTION_TYPES.IO_EXCEPTION);
        }
        catch (IOException ex) {
            throw new ThreddsUtilitiesException(
                        "IOException while getting root element",
                                                    ThreddsUtilitiesException.EXCEPTION_TYPES.IO_EXCEPTION);
        }

    }

    public static Document getDocument(String location) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        return builder.build(location);
    }
    
    public static void writeDocument(Document doc, File ncml) throws FileNotFoundException, IOException {
        XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
        FileWriter writer = new FileWriter(ncml);
        output.output(doc, writer);
    }
}
