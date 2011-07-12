-- All comments whether single or multi-line
-- must end in ;

-- Lookup tables, be sure to drop the table before creating it;
--DROP TABLE collection_type;
CREATE TABLE collection_type 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), PRIMARY KEY (id));

--DROP TABLE data_type;
CREATE TABLE data_type 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), PRIMARY KEY (id));

--DROP TABLE data_format;
CREATE TABLE data_format 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), PRIMARY KEY (id));

--DROP TABLE documentation_type;
CREATE TABLE documentation_type 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), PRIMARY KEY (id));

--DROP TABLE service_type;
CREATE TABLE service_type 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), PRIMARY KEY (id));

--DROP TABLE date_type_enum;
CREATE TABLE date_type_enum 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), PRIMARY KEY (id));
    
--DROP TABLE spatial_range_type;    
CREATE TABLE spatial_range_type
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(32), PRIMARY KEY (id));

--DROP TABLE up_down_type;
CREATE TABLE up_down_type
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), type varchar(4), PRIMARY KEY (id));

-- This is an append table, users define their own vocabularies;      
CREATE TABLE controlled_vocabulary 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), vocab varchar(32), inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));

-- Application configuration table, mostly for future use;
CREATE TABLE global_config 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), base_dir varchar(512), thredds_dir varchar(512), PRIMARY KEY (id));
        
-- Catalog schema tables;
CREATE TABLE catalog 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), location varchar(512), name varchar(64), expires date, version varchar(8), inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));
        
CREATE TABLE ingest 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), catalog_id INT CONSTRAINT CATALOG2_FK REFERENCES catalog, name varchar(128), ftpLocation varchar(512), rescanEvery bigint, fileRegex varchar(64), successDate date, successTime time, username varchar(64), password varchar(64), active boolean, inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));

CREATE TABLE dataset 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), catalog_id INT CONSTRAINT CATALOG3_FK REFERENCES catalog, collection_type_id INT CONSTRAINT COLLECTION1_FK REFERENCES collection_type, data_type_id INT CONSTRAINT DATATYPE_FK REFERENCES data_type, name varchar(64), ncid varchar(128), authority varchar(64), inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));

CREATE TABLE service 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), catalog_id INT CONSTRAINT CATALOG4_FK REFERENCES catalog, service_id INT CONSTRAINT SERVICE1_FK REFERENCES service,  service_type_id INT CONSTRAINT SERVICETYPE_FK REFERENCES service_type, name varchar(64), base varchar(32),  description varchar(512), suffix varchar(32), inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));

CREATE TABLE access 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), dataset_id INT CONSTRAINT DATASET1_FK REFERENCES dataset, service_id INT CONSTRAINT SERVICE2_FK REFERENCES service, dataformat_id INT CONSTRAINT DATAFORMAT_FK REFERENCES data_format, url_path varchar(512), inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));
        
CREATE TABLE documentation 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), dataset_id INT CONSTRAINT DATASET2_FK REFERENCES dataset, documentation_type_id INT CONSTRAINT DOCTYPE_FK REFERENCES documentation_type, xlink_href varchar(256), xlink_title varchar(256), text varchar(1024), inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));
        
CREATE TABLE property 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), dataset_id INT CONSTRAINT DATASET3_FK REFERENCES dataset, name varchar(128), value varchar(512), inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));

CREATE TABLE keyword 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), value varchar(64), controlled_vocabulary_id INT CONSTRAINT VOCAB1_FK REFERENCES controlled_vocabulary, inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));

CREATE TABLE contributor 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), role varchar(64), text varchar(256), inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));

CREATE TABLE creator 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(256), controlled_vocabulary_id INT CONSTRAINT VOCAB2_FK REFERENCES controlled_vocabulary, contact_url varchar(512), contact_email varchar(256), inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));

CREATE TABLE date_type_formatted
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), format varchar(256), value varchar(256), date_type_enum_id INT CONSTRAINT DATEENUM1_FK REFERENCES date_type_enum, inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));

CREATE TABLE project
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(256), controlled_vocabulary_id INT CONSTRAINT VOCAB3_FK REFERENCES controlled_vocabulary, inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));
        
CREATE TABLE publisher 
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(256), controlled_vocabulary_id INT CONSTRAINT VOCAB4_FK REFERENCES controlled_vocabulary, contact_url varchar(512), contact_email varchar(256), inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));

CREATE TABLE geospatial_coverage
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), dataset_id INT CONSTRAINT DATASET10_FK REFERENCES dataset, controlled_vocabulary_id INT CONSTRAINT VOCAB5_FK REFERENCES controlled_vocabulary, name varchar(128), zpositive_id INT CONSTRAINT UPDOWN_FK REFERENCES up_down_type, inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));

CREATE TABLE spatial_range
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), geospatial_coverage_id INT CONSTRAINT GSCOVER1_FK REFERENCES geospatial_coverage, spatial_range_type_id INT CONSTRAINT SRTYPE1_FK REFERENCES spatial_range_type, start DOUBLE, size DOUBLE, resolution DOUBLE, units varchar(32), inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));

CREATE TABLE time_coverage
    (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), dataset_id INT CONSTRAINT DATASET11_FK REFERENCES dataset, start_id INT CONSTRAINT DTF2_FK REFERENCES date_type_formatted, end_id INT CONSTRAINT DTF3_FK REFERENCES date_type_formatted, duration varchar(32), resolution varchar(32), inserted boolean DEFAULT false, updated boolean DEFAULT false, PRIMARY KEY (id));

-- Join tables - help to manage many to many relationships;
CREATE TABLE keyword_join 
    (dataset_id INT CONSTRAINT DATASET5_FK REFERENCES dataset, keyword_id INT CONSTRAINT KEYWORD_FK REFERENCES keyword, inserted boolean DEFAULT false, updated boolean DEFAULT false);

CREATE TABLE contributor_join 
    (dataset_id INT CONSTRAINT DATASET6_FK REFERENCES dataset, contributor_id INT CONSTRAINT CONTRIB_FK REFERENCES contributor, inserted boolean DEFAULT false, updated boolean DEFAULT false);
        
CREATE TABLE creator_join 
    (dataset_id INT CONSTRAINT DATASET7_FK REFERENCES dataset, creator_id INT CONSTRAINT CREATOR_FK REFERENCES creator, inserted boolean DEFAULT false, updated boolean DEFAULT false);

CREATE TABLE project_join 
    (dataset_id INT CONSTRAINT DATASET8_FK REFERENCES dataset, project_id INT CONSTRAINT PROJECT_FK REFERENCES project, inserted boolean DEFAULT false, updated boolean DEFAULT false);

CREATE TABLE publisher_join 
    (dataset_id INT CONSTRAINT DATASET9_FK REFERENCES dataset, publisher_id INT CONSTRAINT PUBLISHER_FK REFERENCES publisher, inserted boolean DEFAULT false, updated boolean DEFAULT false);

CREATE TABLE date_join
    (dataset_id INT CONSTRAINT DATASET12_FK REFERENCES dataset, date_type_formatted_id INT CONSTRAINT DTF1_FK REFERENCES date_type_formatted, inserted boolean DEFAULT false, updated boolean DEFAULT false);

