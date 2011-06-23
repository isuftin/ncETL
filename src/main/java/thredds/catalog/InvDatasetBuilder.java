package thredds.catalog;

import thredds.catalog.ThreddsMetadata.Contributor;
import thredds.catalog.ThreddsMetadata.Source;
import thredds.catalog.ThreddsMetadata.Vocab;
import ucar.nc2.constants.FeatureType;

/**
 *
 * @author Jordan Walker <jiwalker@usgs.gov>
 */
public class InvDatasetBuilder {

    private InvDatasetImpl dataset; //= new InvDatasetImpl(parent, name);

    public InvDatasetBuilder(String name, String id) {
        dataset = new InvDatasetImpl(null, name);
        dataset.id = id;
    }

//    public InvDatasetBuilder id(String id) {
//        dataset.id = id;
//        return this;
//    }
    public InvDatasetBuilder authorityName(String authorityName) {
        dataset.authorityName = authorityName;
        return this;
    }

    public InvDatasetBuilder access(String serviceName, String urlPath,
                                    String dataFormat) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public InvDatasetBuilder collectionType(String ct) {
        dataset.collectionType = CollectionType.getType(ct);
        return this;
    }

    public InvDatasetBuilder contributor(String name, String role) {
        Contributor contrib = new Contributor(name, role);
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
    public InvDatasetBuilder creator(String name, String url, String email,
                                     String vocab) {
        Vocab vocabulary = new Vocab(name, vocab);
        Source source = new Source(vocabulary, url, email);
        dataset.creators.add(source);
        return this;
    }

    public InvDatasetBuilder dataFormatType(String dataFormatType) {
        dataset.dataFormatType = DataFormatType.getType(dataFormatType);
        return this;
    }

    public InvDatasetBuilder dataType(String dataType) {
        dataset.dataType = FeatureType.getType(dataType);
        return this;
    }

    public InvDatasetBuilder documentation(String type, String text) {
        InvDocumentation doc = new InvDocumentation(null, null, null, type, text);
        dataset.docs.add(doc);
        return this;
    }

    public InvDatasetBuilder xlinkDocumentation(String href, String title) {
        InvDocumentation doc = new InvDocumentation(href, null, title, null,
                                                    null);
        dataset.docs.add(doc);
        return this;
    }

    public InvDatasetBuilder keyword(String keyword, String vocab) {
        Vocab vocabulary = new Vocab(keyword, vocab);
        dataset.keywords.add(vocabulary);
        return this;
    }

    public InvDatasetBuilder metadata() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public InvDatasetBuilder project(String project, String vocab) {
        Vocab vocabulary = new Vocab(project, vocab);
        dataset.projects.add(vocabulary);
        return this;
    }

    public InvDatasetBuilder property(String key, String value) {
        InvProperty prop = new InvProperty(key, value);
        dataset.properties.add(prop);
        return this;
    }

    public InvDatasetBuilder publisher(String name, String url, String email,
                                       String vocab) {
        Vocab vocabulary = new Vocab(name, vocab);
        Source source = new Source(vocabulary, url, email);
        dataset.publishers.add(source);
        return this;
    }

    public InvDatasetBuilder restrictAccess() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * May need to do something more complicated here, such as a serviceBuilder
     * @param name serviceName
     * @param type 
     * @param base
     * @param suffix
     * @param desc
     * @return 
     */
    public InvDatasetBuilder service(String name) {
        dataset.setServiceName(name);
        return this;
    }

    public InvDatasetBuilder geospatialCoverage() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public InvDatasetBuilder timeCoverage() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public InvDatasetBuilder variable() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public InvDataset build() {
        return dataset;
    }
}
