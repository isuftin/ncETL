package thredds.catalog;

import java.util.LinkedList;
import thredds.catalog.ThreddsMetadata.Contributor;
import thredds.catalog.ThreddsMetadata.Source;
import thredds.catalog.ThreddsMetadata.Vocab;
import ucar.nc2.constants.FeatureType;

/**
 * NOTE: There are cases in which I cast an InvDataset to InvDatasetImpl without checking
 * As far as I know, this is always legal, but if it presents a problem (further
 * subclassing, etc) then this should be fixed by doing something clever that
 * I didn't take the time to figure out at this point.
 * To sum up, there are smells here, but I didn't bother to remedy them.
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class InvDatasetWrapper {

    /** dataset to build up */
    private InvDatasetImpl dataset; //= new InvDatasetImpl(parent, name);
    private boolean built = false;

    public InvDatasetWrapper(String name, String id) {
        dataset = new InvDatasetImpl(null, name);
        dataset.id = id;
    }

    /**
     * Copy constructor for existing dataset
     * @param from dataset to copy
     */
    public InvDatasetWrapper(InvDataset from) {
        // I'm worried about this, we may need to cut this constructor out
        dataset = new InvDatasetImpl((InvDatasetImpl)from);
    }

    public InvDatasetWrapper authorityName(String authorityName) {
        dataset.authorityName = authorityName;
        return this;
    }

    public InvDatasetWrapper access(String serviceName, String urlPath,
                                    String dataFormat) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public InvDatasetWrapper collectionType(String ct) {
        dataset.collectionType = CollectionType.getType(ct);
        return this;
    }

    public InvDatasetWrapper contributor(String name, String role) {
        Contributor contrib = new Contributor(name, role);
        if (dataset.contributors == null) {
            dataset.contributors = new LinkedList<Contributor>();
        }
        dataset.contributors.add(contrib);
        return this;
    }

    /**
     *
     * http://www.unidata.ucar.edu/mailing_lists/archives/thredds/2004/msg00057.html
     * @param name creator name
     * @param url web address
     * @param email contact address
     * @param vocab (optional) controlledVocabulary
     * @return 
     */
    public InvDatasetWrapper creator(String name, String url, String email,
                                     String vocab) {
        Vocab vocabulary = new Vocab(name, vocab);
        Source source = new Source(vocabulary, url, email);
        if (dataset.creators == null) {
            dataset.creators = new LinkedList<Source>();
        }
        dataset.creators.add(source);
        return this;
    }

    public InvDatasetWrapper dataFormatType(String dataFormatType) {
        dataset.dataFormatType = DataFormatType.getType(dataFormatType);
        return this;
    }

    public InvDatasetWrapper dataType(String dataType) {
        dataset.dataType = FeatureType.getType(dataType);
        return this;
    }

    public InvDatasetWrapper documentation(String type, String text) {
        InvDocumentation doc = new InvDocumentation(null, null, null, type, text);
        if (dataset.docs == null) {
            dataset.docs = new LinkedList<InvDocumentation>();
        }
        dataset.docs.add(doc);
        return this;
    }

    public InvDatasetWrapper xlinkDocumentation(String href, String title) {
        InvDocumentation doc = new InvDocumentation(href, null, title, null,
                                                    null);
        if (dataset.docs == null) {
            dataset.docs = new LinkedList<InvDocumentation>();
        }
        dataset.docs.add(doc);
        return this;
    }

    public InvDatasetWrapper keyword(String keyword, String vocab) {
        Vocab vocabulary = new Vocab(keyword, vocab);
        if (dataset.keywords == null) {
            dataset.keywords = new LinkedList<Vocab>();
        }
        dataset.keywords.add(vocabulary);
        return this;
    }

    public InvDatasetWrapper metadata() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public InvDatasetWrapper project(String project, String vocab) {
        Vocab vocabulary = new Vocab(project, vocab);
        if (dataset.projects == null) {
            dataset.projects = new LinkedList<Vocab>();
        }
        dataset.projects.add(vocabulary);
        return this;
    }

    public InvDatasetWrapper property(String key, String value) {
        InvProperty prop = new InvProperty(key, value);
        if (dataset.properties == null) {
            dataset.properties = new LinkedList<InvProperty>();
        }
        dataset.properties.add(prop);
        return this;
    }

    public InvDatasetWrapper publisher(String name, String url, String email,
                                       String vocab) {
        Vocab vocabulary = new Vocab(name, vocab);
        Source source = new Source(vocabulary, url, email);
        if (dataset.publishers == null) {
            dataset.publishers = new LinkedList<Source>();
        }
        dataset.publishers.add(source);
        return this;
    }

    public InvDatasetWrapper restrictAccess() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * May need to do something more complicated here, such as a serviceBuilder
     * @param name serviceName
     * @return 
     */
    public InvDatasetWrapper service(String name) {
        dataset.setServiceName(name);
        return this;
    }

    public InvDatasetWrapper geospatialCoverage() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public InvDatasetWrapper timeCoverage() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public InvDatasetWrapper variable() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Returns the underlying dataset, can only be run once
     * @return underlying dataset
     */
    public InvDataset build() {
        if (!built) {
            built = true;
            return dataset;
        }
        else {
            throw new UnsupportedOperationException("build() can only be called once");
        }
    }
}
