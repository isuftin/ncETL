<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:nc="http://www.unidata.ucar.edu/namespaces/netcdf/ncml-2.2">
	<xsl:output method="xml"/>
	<xsl:template match="/">
		<xsl:variable name="titleAttribute" select="/nc:netcdf/nc:attribute[@name='title']"/>
		<xsl:variable name="globalAttributeCnt" select="count(/nc:netcdf/nc:attribute)"/>
		<xsl:variable name="variableCnt" select="count(/nc:netcdf/nc:variable)"/>
		<xsl:variable name="variableAttributeCnt" select="count(/nc:netcdf/nc:variable/nc:attribute)"/>
		<xsl:variable name="standardNameCnt" select="count(/nc:netcdf/nc:variable/nc:attribute[@name='standard_name'])"/>
		<!-- Identifier Fields: 2 possible -->
		<xsl:variable name="idCnt" select="count(/nc:netcdf/nc:attribute[@name='id'])"/>
		<xsl:variable name="identifierNameSpaceCnt" select="count(/nc:netcdf/nc:attribute[@name='naming_authority'])"/>
		<xsl:variable name="identifierCnt" select="$idCnt + $identifierNameSpaceCnt"/>
		<!-- Text Search Fields: 7 possible -->
		<xsl:variable name="titleCnt" select="count(/nc:netcdf/nc:attribute[@name='title'])"/>
		<xsl:variable name="summaryCnt" select="count(/nc:netcdf/nc:attribute[@name='summary'])"/>
		<xsl:variable name="keywordsCnt" select="count(/nc:netcdf/nc:attribute[@name='keywords'])"/>
		<xsl:variable name="keywordsVocabCnt" select="count(/nc:netcdf/nc:attribute[@name='keywords_vocabulary'])"/>
		<xsl:variable name="stdNameVocabCnt" select="count(/nc:netcdf/nc:attribute[@name='standard_name_vocabulary'])"/>
		<xsl:variable name="commentCnt" select="count(/nc:netcdf/nc:attribute[@name='comment'])"/>
		<xsl:variable name="historyCnt" select="count(/nc:netcdf/nc:attribute[@name='history'])"/>
		<xsl:variable name="textSearchCnt" select="$titleCnt + $summaryCnt + $keywordsCnt + $keywordsVocabCnt
					+ $stdNameVocabCnt + $commentCnt + $historyCnt"/>
		<!-- Extent Search Fields: 17 possible -->
		<xsl:variable name="geospatial_lat_minCnt" select="count(/nc:netcdf/nc:attribute[@name='geospatial_lat_min'])"/>
		<xsl:variable name="geospatial_lat_maxCnt" select="count(/nc:netcdf/nc:attribute[@name='geospatial_lat_max'])"/>
		<xsl:variable name="geospatial_lat_unitsCnt" select="count(/nc:netcdf/nc:attribute[@name='geospatial_lat_units'])"/>
		<xsl:variable name="geospatial_lat_resolutionCnt" select="count(/nc:netcdf/nc:attribute[@name='geospatial_lat_resolution'])"/>
		<xsl:variable name="geospatial_lon_minCnt" select="count(/nc:netcdf/nc:attribute[@name='geospatial_lon_min'])"/>
		<xsl:variable name="geospatial_lon_maxCnt" select="count(/nc:netcdf/nc:attribute[@name='geospatial_lon_max'])"/>
		<xsl:variable name="geospatial_lon_unitsCnt" select="count(/nc:netcdf/nc:attribute[@name='geospatial_lon_units'])"/>
		<xsl:variable name="geospatial_lon_resolutionCnt" select="count(/nc:netcdf/nc:attribute[@name='geospatial_lon_resolution'])"/>
		<xsl:variable name="geospatialCnt" select="$geospatial_lat_minCnt + $geospatial_lat_maxCnt + $geospatial_lat_unitsCnt + $geospatial_lat_resolutionCnt						
					+ $geospatial_lon_minCnt + $geospatial_lon_maxCnt + $geospatial_lon_unitsCnt + $geospatial_lon_resolutionCnt"/>
		<!--  -->
		<xsl:variable name="timeStartCnt" select="count(/nc:netcdf/nc:attribute[@name='time_coverage_start'])"/>
		<xsl:variable name="timeEndCnt" select="count(/nc:netcdf/nc:attribute[@name='time_coverage_end'])"/>
		<xsl:variable name="timeResCnt" select="count(/nc:netcdf/nc:attribute[@name='time_coverage_resolution'])"/>
		<xsl:variable name="timeDurCnt" select="count(/nc:netcdf/nc:attribute[@name='time_coverage_duration'])"/>
		<xsl:variable name="temporalCnt" select="$timeStartCnt + $timeEndCnt + $timeResCnt + $timeDurCnt"/>
		<!--  -->
		<xsl:variable name="vertical_minCnt" select="count(/nc:netcdf/nc:attribute[@name='vertical_min'])"/>
		<xsl:variable name="vertical_maxCnt" select="count(/nc:netcdf/nc:attribute[@name='vertical_max'])"/>
		<xsl:variable name="vertical_unitsCnt" select="count(/nc:netcdf/nc:attribute[@name='vertical_units'])"/>
		<xsl:variable name="vertical_resolutionCnt" select="count(/nc:netcdf/nc:attribute[@name='vertical_resolution'])"/>
		<xsl:variable name="vertical_positiveCnt" select="count(/nc:netcdf/nc:attribute[@name='vertical_positive'])"/>
		<xsl:variable name="verticalCnt" select="$vertical_minCnt + $vertical_maxCnt + $vertical_unitsCnt + $vertical_resolutionCnt + $vertical_positiveCnt"/>
		<!--  -->
		<xsl:variable name="extentCnt" select="$geospatialCnt + $temporalCnt + $verticalCnt"/>
		<!-- Responsible Party Fields: 14 possible -->
		<xsl:variable name="creatorNameCnt" select="count(/nc:netcdf/nc:attribute[@name='creator_name'])"/>
		<xsl:variable name="creatorURLCnt" select="count(/nc:netcdf/nc:attribute[@name='creator_url'])"/>
		<xsl:variable name="creatorEmailCnt" select="count(/nc:netcdf/nc:attribute[@name='creator_email'])"/>
		<xsl:variable name="creatorDateCnt" select="count(/nc:netcdf/nc:attribute[@name='date_created'])"/>
		<xsl:variable name="modifiedDateCnt" select="count(/nc:netcdf/nc:attribute[@name='date_modified'])"/>
		<xsl:variable name="issuedDateCnt" select="count(/nc:netcdf/nc:attribute[@name='date_issued'])"/>
		<xsl:variable name="creatorInstCnt" select="count(/nc:netcdf/nc:attribute[@name='institution'])"/>
		<xsl:variable name="creatorProjCnt" select="count(/nc:netcdf/nc:attribute[@name='project'])"/>
		<xsl:variable name="creatorAckCnt" select="count(/nc:netcdf/nc:attribute[@name='acknowledgment'])"/>
		<xsl:variable name="creatorCnt" select="$creatorNameCnt + $creatorURLCnt + $creatorEmailCnt + $creatorDateCnt 
					+ $modifiedDateCnt + $issuedDateCnt + $creatorInstCnt + $creatorProjCnt + $creatorAckCnt"/>
		<!--  -->
		<xsl:variable name="contributorNameCnt" select="count(/nc:netcdf/nc:attribute[@name='contributor_name'])"/>
		<xsl:variable name="contributorRoleCnt" select="count(/nc:netcdf/nc:attribute[@name='contributor_role'])"/>
		<xsl:variable name="contributorCnt" select="$contributorNameCnt + $contributorRoleCnt"/>
		<!--  -->
		<xsl:variable name="publisherNameCnt" select="count(/nc:netcdf/nc:attribute[@name='publisher_name'])"/>
		<xsl:variable name="publisherURLCnt" select="count(/nc:netcdf/nc:attribute[@name='publisher_URL'])"/>
		<xsl:variable name="publisherEmailCnt" select="count(/nc:netcdf/nc:attribute[@name='publisher_email'])"/>
		<xsl:variable name="publisherCnt" select="$publisherNameCnt + $publisherURLCnt + $publisherEmailCnt"/>
		<!--  -->
		<xsl:variable name="responsiblePartyCnt" select="$creatorCnt + $contributorCnt + $publisherCnt"/>
		<!-- Other Fields: 2 possible -->
		<xsl:variable name="cdmTypeCnt" select="count(/nc:netcdf/nc:attribute[@name='cdm_data_type'])"/>
		<xsl:variable name="procLevelCnt" select="count(/nc:netcdf/nc:attribute[@name='processing_level'])"/>
		<xsl:variable name="licenseCnt" select="count(/nc:netcdf/nc:attribute[@name='license'])"/>
		<xsl:variable name="otherCnt" select="$cdmTypeCnt + $procLevelCnt + $licenseCnt"/>
		<xsl:variable name="totalScore" select="$identifierCnt + $otherCnt + $textSearchCnt + $extentCnt + $responsiblePartyCnt"/>
		<!-- Display Results Fields -->
		<UnidataDataDiscoveryConformance>
			<title>
				<xsl:value-of select="$titleAttribute/@value"/>
			</title>
			<globalAttributeCnt>
				<xsl:value-of select="$globalAttributeCnt"/>
			</globalAttributeCnt>
			<variableCnt>
				<xsl:value-of select="$variableCnt"/>
			</variableCnt>
			<variableAttributeCnt>
				<xsl:value-of select="$variableAttributeCnt"/>
			</variableAttributeCnt>
			<standardNameCnt>
				<xsl:value-of select="$standardNameCnt"/>
			</standardNameCnt>
			<totalScore>
				<xsl:value-of select="$totalScore"/>
			</totalScore>
			<!--  -->
			<identifier>
				<identifierCnt>
					<xsl:value-of select="$identifierCnt"/>
				</identifierCnt>
				<idCnt>
					<xsl:value-of select="$idCnt"/>
				</idCnt>
				<identifierNameSpaceCnt>
					<xsl:value-of select="$identifierNameSpaceCnt"/>
				</identifierNameSpaceCnt>
			</identifier>
			<!--  -->
			<textSearch>
				<textSearchCnt>
					<xsl:value-of select="$textSearchCnt"/>
				</textSearchCnt>
				<titleCnt>
					<xsl:value-of select="$titleCnt"/>
				</titleCnt>
				<summaryCnt>
					<xsl:value-of select="$summaryCnt"/>
				</summaryCnt>
				<keywordsCnt>
					<xsl:value-of select="$keywordsCnt"/>
				</keywordsCnt>
				<keywordsVocabCnt>
					<xsl:value-of select="$keywordsVocabCnt"/>
				</keywordsVocabCnt>
				<stdNameVocabCnt>
					<xsl:value-of select="$stdNameVocabCnt"/>
				</stdNameVocabCnt>
				<historyCnt>
					<xsl:value-of select="$historyCnt"/>
				</historyCnt>
				<commentCnt>
					<xsl:value-of select="$commentCnt"/>
				</commentCnt>
			</textSearch>
			<!--  -->
			<extent>
				<extentCnt>
					<xsl:value-of select="$extentCnt"/>
				</extentCnt>
				<geospatial>
					<geospatial_lat_minCnt>
						<xsl:value-of select="$geospatial_lat_minCnt"/>
					</geospatial_lat_minCnt>
					<geospatial_lat_maxCnt>
						<xsl:value-of select="$geospatial_lat_maxCnt"/>
					</geospatial_lat_maxCnt>
					<geospatial_lat_unitsCnt>
						<xsl:value-of select="$geospatial_lat_unitsCnt"/>
					</geospatial_lat_unitsCnt>
					<geospatial_lat_resolutionCnt>
						<xsl:value-of select="$geospatial_lat_resolutionCnt"/>
					</geospatial_lat_resolutionCnt>
					<geospatial_lon_minCnt>
						<xsl:value-of select="$geospatial_lon_minCnt"/>
					</geospatial_lon_minCnt>
					<geospatial_lon_maxCnt>
						<xsl:value-of select="$geospatial_lon_maxCnt"/>
					</geospatial_lon_maxCnt>
					<geospatial_lon_unitsCnt>
						<xsl:value-of select="$geospatial_lon_unitsCnt"/>
					</geospatial_lon_unitsCnt>
					<geospatial_lon_resolutionCnt>
						<xsl:value-of select="$geospatial_lon_resolutionCnt"/>
					</geospatial_lon_resolutionCnt>
					<!--  -->
					<geospatialCnt>
						<xsl:value-of select="$geospatialCnt"/>
					</geospatialCnt>
				</geospatial>
				<temporal>
					<timeStartCnt>
						<xsl:value-of select="$timeStartCnt"/>
					</timeStartCnt>
					<timeEndCnt>
						<xsl:value-of select="$timeEndCnt"/>
					</timeEndCnt>
					<timeResCnt>
						<xsl:value-of select="$timeResCnt"/>
					</timeResCnt>
					<timeDurCnt>
						<xsl:value-of select="$timeDurCnt"/>
					</timeDurCnt>
					<temporalCnt>
						<xsl:value-of select="$temporalCnt"/>
					</temporalCnt>
				</temporal>
				<vertical>
					<vertical_minCnt>
						<xsl:value-of select="$vertical_minCnt"/>
					</vertical_minCnt>
					<vertical_maxCnt>
						<xsl:value-of select="$vertical_maxCnt"/>
					</vertical_maxCnt>
					<vertical_unitsCnt>
						<xsl:value-of select="$vertical_unitsCnt"/>
					</vertical_unitsCnt>
					<vertical_resolutionCnt>
						<xsl:value-of select="$vertical_resolutionCnt"/>
					</vertical_resolutionCnt>
					<vertical_positiveCnt>
						<xsl:value-of select="$vertical_positiveCnt"/>
					</vertical_positiveCnt>
					<verticalCnt>
						<xsl:value-of select="$verticalCnt"/>
					</verticalCnt>
				</vertical>
			</extent>
			<!--  -->
			<responsibleParty>
				<responsiblePartyCnt>
					<xsl:value-of select="$responsiblePartyCnt"/>
				</responsiblePartyCnt>
				<creatorNameCnt>
					<xsl:value-of select="$creatorNameCnt"/>
				</creatorNameCnt>
				<creatorURLCnt>
					<xsl:value-of select="$creatorURLCnt"/>
				</creatorURLCnt>
				<creatorEmailCnt>
					<xsl:value-of select="$creatorEmailCnt"/>
				</creatorEmailCnt>
				<creatorDateCnt>
					<xsl:value-of select="$creatorDateCnt"/>
				</creatorDateCnt>
				<creatorInstCnt>
					<xsl:value-of select="$creatorInstCnt"/>
				</creatorInstCnt>
				<creatorProjCnt>
					<xsl:value-of select="$creatorProjCnt"/>
				</creatorProjCnt>
				<creatorAckCnt>
					<xsl:value-of select="$creatorAckCnt"/>
				</creatorAckCnt>
				<creatorCnt>
					<xsl:value-of select="$creatorCnt"/>
				</creatorCnt>
				<!--  -->
				<publisherNameCnt>
					<xsl:value-of select="$publisherNameCnt"/>
				</publisherNameCnt>
				<publisherURLCnt>
					<xsl:value-of select="$publisherURLCnt"/>
				</publisherURLCnt>
				<publisherEmailCnt>
					<xsl:value-of select="$publisherEmailCnt"/>
				</publisherEmailCnt>
				<publisherCnt>
					<xsl:value-of select="$publisherCnt"/>
				</publisherCnt>
				<!--  -->
				<contributorNameCnt>
					<xsl:value-of select="$contributorNameCnt"/>
				</contributorNameCnt>
				<contributorRoleCnt>
					<xsl:value-of select="$contributorRoleCnt"/>
				</contributorRoleCnt>
				<contributorCnt>
					<xsl:value-of select="$contributorCnt"/>
				</contributorCnt>
			</responsibleParty>
			<!--  -->
			<other>
				<otherCnt>
					<xsl:value-of select="$otherCnt"/>
				</otherCnt>
				<cdmTypeCnt>
					<xsl:value-of select="$cdmTypeCnt"/>
				</cdmTypeCnt>
				<procLevelCnt>
					<xsl:value-of select="$procLevelCnt"/>
				</procLevelCnt>
				<licenseCnt>
					<xsl:value-of select="$licenseCnt"/>
				</licenseCnt>
			</other>
		</UnidataDataDiscoveryConformance>
	</xsl:template>
</xsl:stylesheet>

