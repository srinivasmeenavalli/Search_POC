package com.estore.search.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Collation;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Correction;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;

import com.estore.search.constants.EStoreConstants;
import com.estore.search.constants.EStoreConstants.PAGE_VIEWS;
import com.estore.search.constants.EStoreConstants.SORT_OPTIONS;
import com.estore.search.vo.BreadCrumbVO;
import com.estore.search.vo.Facet;
import com.estore.search.vo.FacetValue;
import com.estore.search.vo.PageView;
import com.estore.search.vo.Record;
import com.estore.search.vo.Response;
import com.estore.search.vo.SortOption;
import com.estore.search.vo.SuggestionVO;

/**
 * 
 * 
 * **/
public class SolrResultsParser {

	
	/**
	 * 
	 * 
	 * **/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Response parseResults(Object response) {

		System.out.println("#Enter SolrResultsParser.parseResults");

		QueryResponse solrResponse = (QueryResponse) response;
		Response finalRespObj = new Response();
		/* pagination start */
		int numberFound = (int) solrResponse.getResults().getNumFound();
		NamedList nameList = (NamedList) solrResponse.getHeader().get("params");
		Integer rows = Integer.parseInt((String) nameList.get("rows"));
		System.out.println("Rows " + rows);
		Integer start = Integer.parseInt((String) nameList.get("start"));
		Integer pageNo = (start / rows) + EStoreConstants.INTEGER_1;
		finalRespObj.setSelectedPageNO(pageNo);
		Integer pageCreation = (Integer) (numberFound / rows);
		if ((numberFound % rows) != EStoreConstants.INTEGER_0) {
			pageCreation = pageCreation + EStoreConstants.INTEGER_1;
		}
		finalRespObj.setDocumentNumFound(numberFound);
		finalRespObj.setPageCreation(pageCreation);
		if ((pageNo % 5) == EStoreConstants.INTEGER_1) {
			finalRespObj.setBegin(pageNo);
			finalRespObj.setEnd(pageNo + EStoreConstants.INTEGER_4);
		} else if ((pageNo % 5) == EStoreConstants.INTEGER_2) {
			finalRespObj.setBegin(pageNo - EStoreConstants.INTEGER_1);
			finalRespObj.setEnd(pageNo + EStoreConstants.INTEGER_3);
		} else if ((pageNo % 5) == EStoreConstants.INTEGER_3) {
			finalRespObj.setBegin(pageNo - EStoreConstants.INTEGER_2);
			finalRespObj.setEnd(pageNo + EStoreConstants.INTEGER_2);
		} else if ((pageNo % 5) == EStoreConstants.INTEGER_4) {
			finalRespObj.setBegin(pageNo - EStoreConstants.INTEGER_3);
			finalRespObj.setEnd(pageNo + EStoreConstants.INTEGER_1);
		} else if ((pageNo % 5) == EStoreConstants.INTEGER_0) {
			finalRespObj.setBegin(pageNo - EStoreConstants.INTEGER_4);
			finalRespObj.setEnd(pageNo);
		}
		/* pagination end */
		/* page view start */
		String searchTerm = (String) nameList.get("searchterm");
		/* For Phrase Query */
		if(searchTerm != null){
			/*searchTerm = searchTerm.replace("\"","");
			System.out.println("search Term"+searchTerm+"search Term");
			System.out.println("#search Term "+ searchTerm);*/
			finalRespObj.setSearchTerm(searchTerm);
		}
		/* filter Query */
		List<BreadCrumbVO> filterQueryList = new ArrayList<BreadCrumbVO>();
		if (nameList.get("fq") instanceof String) {
			String lFilterQuery = (String) nameList.get("fq");
			String[] queryValue = lFilterQuery.split(":");
			BreadCrumbVO queryDetail = new BreadCrumbVO();
			queryDetail.setBreadCrumbLabel(queryValue[EStoreConstants.INTEGER_0]);
			if (queryValue[0].equalsIgnoreCase("Price")) {
				String priceGap = queryValue[EStoreConstants.INTEGER_1];
				priceGap = priceGap.substring(priceGap.indexOf("TO") + EStoreConstants.INTEGER_2, priceGap.indexOf("}")).trim();
				Float exactPriceGap = Float.parseFloat(priceGap) - EStoreConstants.FACET_RANGE_GAP;
				priceGap = Float.toString(exactPriceGap);
				System.out.println("priceGapstart" + priceGap + "priceGapend");
				queryDetail.setBreadCrumbValue(priceGap);
			} else {
				queryDetail.setBreadCrumbValue(queryValue[EStoreConstants.INTEGER_1]);
			}

			filterQueryList.add(queryDetail);
		} else if (nameList.get("fq") instanceof List) {
			List<String> filterQuery = (List) nameList.get("fq");
			if (filterQuery != null) {
				for (String lFilterQuery : filterQuery) {
					String[] queryValue = lFilterQuery.split(":");
					BreadCrumbVO queryDetail = new BreadCrumbVO();
					queryDetail.setBreadCrumbLabel(queryValue[EStoreConstants.INTEGER_0]);
					if (queryValue[0].equalsIgnoreCase("Price")) {
						String priceGap = queryValue[EStoreConstants.INTEGER_1];
						priceGap = priceGap.substring(priceGap.indexOf("TO") + EStoreConstants.INTEGER_2, priceGap.indexOf("}")).trim();
						Float exactPriceGap = Float.parseFloat(priceGap) - EStoreConstants.FACET_RANGE_GAP;
						priceGap = Float.toString(exactPriceGap);
						System.out.println("priceGapstart" + priceGap + "priceGapend");
						queryDetail.setBreadCrumbValue(priceGap);
					} else {
						queryDetail.setBreadCrumbValue(queryValue[EStoreConstants.INTEGER_1]);
					}
					filterQueryList.add(queryDetail);
				}
			}
		}
		finalRespObj.setBreadCrumbs(filterQueryList);
		/* filter Query */
		if (rows != 0) {
			finalRespObj.setSelectedPageView(rows);
		} else {
			finalRespObj.setSelectedPageView(12);
		}
		List<PageView> pageViewList = populatePageView();
		finalRespObj.setPageViews(pageViewList);
		/* page view end */
		/* sort start */
		List<SortOption> sortOptionList = populateSortOption();
		finalRespObj.setSortOptions(sortOptionList);
		String userSort = (String) nameList.get("sort");
		if (userSort != null) {
			if (userSort.contains("desc")) {
				finalRespObj.setSelectedSort(EStoreConstants.INTEGER_2);
			} else if (userSort.contains("rel")) {
				finalRespObj.setSelectedSort(EStoreConstants.INTEGER_3);
			} else {
				finalRespObj.setSelectedSort(EStoreConstants.INTEGER_1);
			}
		} else {
			finalRespObj.setSelectedSort(EStoreConstants.INTEGER_3);
		}

		/* sort end */
		/* record start */
		List<Record> recordList = setRecordValueInResponse(solrResponse);
		/* record end */
		/* facet start */
		List<Facet> lfacets = setFacetValueInResponse(solrResponse);
		/* facet end */
		if (recordList != null) {
			finalRespObj.setRecords(recordList);
		}
		if (lfacets != null) {
			finalRespObj.setFacets(lfacets);
		}
		/*suggestion start*/
		SpellCheckResponse spellCheck =solrResponse.getSpellCheckResponse();
		if(spellCheck != null){
			List<Collation> collationList =spellCheck.getCollatedResults();
			List<SuggestionVO> suggestionList = new ArrayList<SuggestionVO>();
			if(collationList != null){
				for (Collation collation : collationList){
					SuggestionVO suggestion = new SuggestionVO();
					List<Correction> correctWord=collation.getMisspellingsAndCorrections();
					if(correctWord != null){
						for (Correction correction : correctWord){
							suggestion.setSuggestWord(correction.getCorrection());
							Long hitFound = collation.getNumberOfHits();
							suggestion.setFreq(hitFound.toString());
							suggestionList.add(suggestion);
							System.out.println("NumberOfHits for spell check   "+collation.getNumberOfHits());
							System.out.println("Correct word of Collation for spell check   "+correction.getCorrection());
						}
					}
				}
			}
			finalRespObj.setSuggestionList(suggestionList);
		}
		/*suggestion end*/
		System.out.println("#Return/Exit SolrResultsParser.parseResults");
		return finalRespObj;
	}


	/**
	 * 
	 * 
	 * **/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Record> setRecordValueInResponse(QueryResponse solrResponse) {

		System.out.println("#Enter SolrResultsParser.setRecordValueInResponse");

		List<Record> recordList = new ArrayList<Record>();
		SolrDocumentList results = solrResponse.getResults();
		Record record = null;
		for (int i = 0; i < results.size(); ++i) {
			record = new Record();
			SolrDocument docuemnt = results.get(i);
			String id = (String) docuemnt.getFieldValue(EStoreConstants.ID);
			if (id != null) {
				record.setId(id);
			}

			ArrayList<String> brandName = (ArrayList<String>) docuemnt.getFieldValue(EStoreConstants.PRODUCT_BRAND_NAME);
			ArrayList<String> galleryImage = (ArrayList<String>) docuemnt.getFieldValue(EStoreConstants.GALLERY_IMAGE);
			String recordimage = (String) docuemnt.getFieldValue(EStoreConstants.RECORD_IMAGE);
			/* For Camera Properties */
			String cameraDisplay = (String) docuemnt.getFieldValue(EStoreConstants.CAMERA_DISPLAY);
			String camerafocalrange = (String) docuemnt.getFieldValue(EStoreConstants.CAMERA_FOCAL_LENGTH);
			String cameraProcessor = (String) docuemnt.getFieldValue(EStoreConstants.CAMERA_PROCESSOR);
			String cameraOptical = (String) docuemnt.getFieldValue(EStoreConstants.CAMERA_OPTICAL);
			String cameraInterface = (String) docuemnt.getFieldValue(EStoreConstants.CAMERA_INTERFACE);
			String cameraSensor = (String) docuemnt.getFieldValue(EStoreConstants.CAMERA_SENSOR);
			String camerawarranty=(String)docuemnt.getFieldValue(EStoreConstants.CAMERA_WARRANTY);
			Float averageRating =(Float) docuemnt.getFieldValue(EStoreConstants.RATING);
			/*Camera Features Start*/
			if (averageRating !=null){
			record.setAverageRating(Float.valueOf(averageRating));
					
			}
			
			if(camerawarranty !=null){
				record.setCameraWarranty(camerawarranty);
				
				}

			if (cameraDisplay != null) {
				record.setCameraDisplay(cameraDisplay);

			}
			if (cameraSensor != null) {

				record.setCameraSensor(cameraSensor);

			}
			if (camerafocalrange != null) {
				record.setCamerafocalrange(camerafocalrange);
			}
			if (cameraProcessor != null) {
				record.setCameraProcessor(cameraProcessor);
			}
			if (cameraOptical != null) {
				record.setCameraOptical(cameraOptical);

			}
			if (cameraInterface != null) {
				record.setCameraInterface(cameraInterface);

			}

			if (brandName != null) {
				record.setBrandName((String) brandName.get(EStoreConstants.INTEGER_0));

			}

			if (recordimage != null) {
				record.setRecordImage(recordimage);

			}

			StringBuilder concatLImage = new StringBuilder();
			if (galleryImage != null) {
				ArrayList<String> galleryList = new ArrayList<String>();
				for (String lImage : galleryImage) {

					concatLImage.append(lImage).append("|");

				}

				String strImgGallery = concatLImage.toString();
				StringTokenizer imgGalleryTonkeniser = new StringTokenizer(strImgGallery, "|");
				while (imgGalleryTonkeniser.hasMoreTokens()) {

					galleryList.add(imgGalleryTonkeniser.nextToken());
				}

				record.setGalleryImage(galleryList);
			}
			Float productPrice = (Float) docuemnt.getFieldValue(EStoreConstants.PRODUCT_PRICE);
			if (productPrice != null) {
				Float lProductPrice = (Float) Float.valueOf(productPrice);
				record.setPrice(lProductPrice);

			}
			ArrayList productDesc = (ArrayList) docuemnt.getFieldValue("product.short_desc");
			if (productDesc != null) {
				record.setProductDescription((String) productDesc.get(0));

			}
			String productName = (String) docuemnt.getFieldValue("product.name");
			if (productName != null) {
				record.setProductTitle(productName);

			}
			String productReview = (String) docuemnt.getFieldValue("product.review.count");

			if (productReview != null) {
				record.setReviewCount(Float.parseFloat(productReview));
			}
			/* For Product Long Description */

			String productLongDescription = (String) docuemnt.getFieldValue("product.long_desc");

			if (productLongDescription != null) {
				record.setProductLongDescription(productLongDescription);
			}
			String productLargeImage = (String) docuemnt.getFieldValue("product.img_url_large");
			if (productLargeImage != null) {
				record.setProductLargeImage(productLargeImage);

			}
			recordList.add(record);
		}
		System.out.println("#Return/Exit SolrResultsParser.setRecordValueInResponse");
		return recordList;
	}

	/**
 * 
 * 
 * **/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Facet> setFacetValueInResponse(QueryResponse solrResponse) {

		System.out.println("#Return/Exit SolrResultsParser.setFacetValueInResponse");
		List<FacetField> lFacetField = solrResponse.getFacetFields();
		Facet facet = null;
		FacetValue facetValue = null;
		List<Facet> lfacets = new ArrayList<Facet>();
		List<FacetValue> facetValues = null;
		if (lFacetField != null) {
			for (FacetField facetField : lFacetField) {
				facetValues = new ArrayList<FacetValue>();
				facet = new Facet();
				facet.setFacetName(facetField.getName());
				List<Count> lCount = facetField.getValues();
				for (Count count : lCount) {
					facetValue = new FacetValue();
					facetValue.setFacetCount(String.valueOf(count.getCount()));
					facetValue.setFacetLabel(count.getName());
					facetValue.setFacetName(count.getFacetField().getName());
					facetValues.add(facetValue);
				}
				facet.setFacetValues(facetValues);
				facet.setMultiSelect(Boolean.FALSE);
				lfacets.add(facet);
			}
		}
		List<RangeFacet> facetRangeList = solrResponse.getFacetRanges();
		if (facetRangeList != null) {
			for (RangeFacet rangeFacet : facetRangeList) {
				facetValues = new ArrayList<FacetValue>();
				facet = new Facet();
				facet.setFacetName(rangeFacet.getName());

				List<org.apache.solr.client.solrj.response.RangeFacet.Count> lRangeCount = rangeFacet.getCounts();
				for (org.apache.solr.client.solrj.response.RangeFacet.Count count : lRangeCount) {
					facetValue = new FacetValue();
					facetValue.setFacetCount(String.valueOf(count.getCount()));
					facetValue.setFacetLabel(count.getValue());
					facetValue.setFacetName(count.getRangeFacet().getName());
					facetValues.add(facetValue);

				}
				facet.setFacetValues(facetValues);
				facet.setMultiSelect(Boolean.FALSE);
				lfacets.add(facet);
			}
		}
		System.out.println("#Return/Exit SolrResultsParser.setFacetValueInResponse");
		return lfacets;
	}

	/**
 * 
 * 
 * **/
	private List<PageView> populatePageView() {

		System.out.println("#Enter SolrResultsParser.populatePageView");

		List<PageView> lPageViews = new ArrayList<PageView>();
		PageView pageView12 = new PageView();
		pageView12.setPageViewLabel("12 per page");
		pageView12.setPageViewValue(PAGE_VIEWS.VIEW_12);
		lPageViews.add(pageView12);
		PageView pageView24 = new PageView();
		pageView24.setPageViewLabel("24 per page");
		pageView24.setPageViewValue(PAGE_VIEWS.VIEW_24);
		lPageViews.add(pageView24);
		PageView pageView36 = new PageView();
		pageView36.setPageViewLabel("36 per page");
		pageView36.setPageViewValue(PAGE_VIEWS.VIEW_36);
		lPageViews.add(pageView36);
		PageView pageView48 = new PageView();
		pageView48.setPageViewLabel("48 per page");
		pageView48.setPageViewValue(PAGE_VIEWS.VIEW_48);
		lPageViews.add(pageView48);
		PageView pageView60 = new PageView();
		pageView60.setPageViewLabel("60 per page");
		pageView60.setPageViewValue(PAGE_VIEWS.VIEW_60);
		lPageViews.add(pageView60);
		System.out.println("#Return/Exit SolrResultsParser.populatePageView");
		return lPageViews;
	}

	/**
 * 
 * 
 * **/
	private List<SortOption> populateSortOption() {
		System.out.println("#Enter SolrResultsParser.populateSortOption");
		List<SortOption> lSortOption = new ArrayList<SortOption>();
		SortOption sortOptionASC = new SortOption();
		sortOptionASC.setSortOptionLabel("Price (Ascending)");
		sortOptionASC.setSortOptionValue(SORT_OPTIONS.ASCENDING);
		lSortOption.add(sortOptionASC);
		SortOption sortOptionDESC = new SortOption();
		sortOptionDESC.setSortOptionLabel("Price (Descending)");
		sortOptionDESC.setSortOptionValue(SORT_OPTIONS.DESCENDING);
		lSortOption.add(sortOptionDESC);
		SortOption sortOptionREL = new SortOption();
		sortOptionREL.setSortOptionLabel("Relevance");
		sortOptionREL.setSortOptionValue(SORT_OPTIONS.RELEVANCE);
		lSortOption.add(sortOptionREL);
		System.out.println("#Return/Exit SolrResultsParser.populateSortOption");
		return lSortOption;
	}
}
