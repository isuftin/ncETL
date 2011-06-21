package ucar.nc2;

import java.io.IOException;
import java.io.OutputStream;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import ucar.nc2.util.URLnaming;

/**
 *
 * @author isuftin
 */
public class WrapperNetcdfFile extends NetcdfFile {

    public WrapperNetcdfFile() {
        finish();
    }

    public void writeNcML(OutputStream out) throws IOException {
        XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
        Element ele = new Element("netcdf",  "http://www.unidata.ucar.edu/namespaces/netcdf/ncml-2.2");

        if (getLocation() != null) {
            ele.setAttribute("location", URLnaming.canonicalizeWrite(getLocation()));
        }
        
        if (getId() != null) {
            ele.setAttribute("id", getId());
        }
        
        if (getTitle() != null) {
            ele.setAttribute("title", getTitle());
        }
        
        Document doc = new Document(ele);
        output.output(doc, out);
    }
    
    
}
