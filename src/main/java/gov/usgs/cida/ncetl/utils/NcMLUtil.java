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
import ucar.nc2.Group;
import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFileWriteable;
import ucar.nc2.WrapperNetcdfFile;
import ucar.nc2.dataset.NetcdfDataset;
import ucar.nc2.ncml.NcMLReader;

/**
 *
 * @author jwalker
 */
public final class NcMLUtil {
    
    private NcMLUtil(){}

    /**
     * Write an aggregation of some files to a single netCDF file
     * 
     * @param ncmlIn aggregation or wrapper NcML
     * @param outfile output netCDF file
     * @throws IOException 
     */
    public static void writeNetCDFFile(InputStream ncmlIn, String outfile)
            throws IOException {
        NetcdfDataset dataset = NcMLReader.readNcML(ncmlIn, null);
        // catch file too big?
        NetcdfFileWriteable file = NetcdfFileWriteable.createNew(outfile);
    }

    /**
     * Read the global attributes of a dataset for retaining the history
     * and comments
     * @param inFile netcdf file to open (dataset should work)
     * @throws IOException 
     */
    public static Group globalAttributesToMeta(String inFile, WrapperNetcdfFile attNcml) throws
            IOException {
        NetcdfFile ncf = NetcdfFile.open(inFile);
        List<Attribute> globalAttributes = ncf.getGlobalAttributes();
        Group group = new Group(attNcml, attNcml.getRootGroup(), inFile);
        for (Attribute att : globalAttributes) {
            group.addAttribute(att);
        }
        return group;
    }

    /**
     * Uses ncISO to generate ncml, should be replaced by the wrapper work
     * and this should be moved to actually calling ncISO on the catalog.xml
     * 
     * @param filename NetCDF dataset location
     * @return ncml file created
     * @throws ThreddsUtilitiesException 
     */
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
    
    public static File createAggregationWrapper(String dataset) {
        String datasetdir = FileHelper.dirAppend(FileHelper.getDatasetsDirectory(), dataset);
        String ncmlWrapperName = FileHelper.dirAppend(datasetdir, "aggregation.ncml");
        File ncml = new File(ncmlWrapperName);
        if (!ncml.exists()) {
//            createNcmlStub();
        }
        return null;
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
