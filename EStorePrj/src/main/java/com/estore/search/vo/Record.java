package com.estore.search.vo;

import java.util.ArrayList;

/**
 * 
 *
 */
public class Record {
	
	/**brand Name  */
	private String  mBrandName;
		
	/** product Title */
	private String  mProductTitle;
	
	/**product Description */
	private String  mProductDescription;
	
	/**review Count  */
	private float   mReviewCount;
	
	/**average Rating  */
	private float     mAverageRating;
	
	/**record Image  */
	private String  mRecordImage;
	
	/**id */
	private String 	mId;
	
	/**camera Display */
	private String 	mCameraDisplay;
	
	/** camera focal range */
	private String 	mCamerafocalrange;
	
	/**camera Processor */
	private String 	mCameraProcessor;
	
	/**camera Optical */
	private String  mCameraOptical;
	
	/** camera Interface */
	private String 	mCameraInterface;
	
	/**camera Sensor  */
	private String  mCameraSensor;
	
	/**product Long Description  */
	private String mProductLongDescription;
	
	/**product Large Image  */
	private String mProductLargeImage;
	
	/** gallery Image */
	private ArrayList<String> mGalleryImage;
	
	/** Price*/
	private float mPrice;
	
	/**
	 * returns  Gallery Image
	 * @return ArrayList<String>  GalleryImage
	 */
	
	private String mCameraWarranty;
	/** CameraWarranty*/
	
	/**
	 * sets Camera Warranty
	 * @param pCameraWarranty
	 */
	public void setCameraWarranty(String pCameraWarranty) {
		this.mCameraWarranty = pCameraWarranty;
	}
	
	/**
	 * returns Camera Warranty
	 * @return String Camera Warranty 
	 */
	
	public String getCameraWarranty() {
		return mCameraWarranty;
	}
	
	public ArrayList<String> getGalleryImage() {
		return mGalleryImage;
	}
	/**
	 * sets Gallery Image
	 * @param pGalleryImage
	 */
	public void setGalleryImage(ArrayList<String> pGalleryImage) {
		this.mGalleryImage = pGalleryImage;
	}
	/**
	 * returns Camera Display
	 * @return String Camera Display 
	 */
	public String getCameraDisplay() {
		return mCameraDisplay;
	}
	/**
	 * sets Camera Display
	 * @param pCameraDisplay
	 */

	public void setCameraDisplay(String pCameraDisplay) {
		this.mCameraDisplay = pCameraDisplay;
	}
	/**
	 * returns Camera focal range
	 * @return
	 */
	public String getCamerafocalrange() {
		return mCamerafocalrange;
	}
	/**
	 * sets Camera focal range
	 * @param pCamerafocalrange
	 */
	public void setCamerafocalrange(String pCamerafocalrange) {
		this.mCamerafocalrange = pCamerafocalrange;
	}
	/**
	 * returns String Camera Processor
	 * @return
	 */
	public String getCameraProcessor() {
		return mCameraProcessor;
	}
	/**
	 * sets Camera Processor
	 * @param pCameraProcessor
	 */
	public void setCameraProcessor(String pCameraProcessor) {
		this.mCameraProcessor = pCameraProcessor;
	}
	/**
	 * returns Camera Optical
	 * @return String Camera Optical
	 */
	public String getCameraOptical() {
		return mCameraOptical;
	}
	/**
	 * sets Camera Optical
	 * @param pCameraOptical
	 */
	public void setCameraOptical(String pCameraOptical) {
		this.mCameraOptical = pCameraOptical;
	}
	/**
	 * returns Camera Interface
	 * @return String Camera Interface 
	 */
	public String getCameraInterface() {
		return mCameraInterface;
	}
	/**
	 * sets Camera Interface
	 * @param pCameraInterface
	 */
	public void setCameraInterface(String pCameraInterface) {
		this.mCameraInterface = pCameraInterface;
	}
	/**
	 * returns Id
	 * @return String Id
	 */
	public String getId() {
		return mId;
	}
	/**
	 * sets Id
	 * @param pId
	 */
	public void setId(String pId) {
		this.mId = pId;
	}
	/**
	 * returns Brand Name	
	 * @return String Brand Name
	 */

	public String getBrandName() {
		return mBrandName;
	}
	/**
	 * sets Brand Name
	 * @param pBrandName
	 */
	public void setBrandName(String pBrandName) {
		this.mBrandName = pBrandName;
	}
	/**
	 * returns Product Title
	 * @return String Product Title 
	 */
	public String getProductTitle() {
		return mProductTitle;
	}
	/**
	 * sets Product Title
	 * @param pProductTitle
	 */
	public void setProductTitle(String pProductTitle) {
		this.mProductTitle = pProductTitle;
	}
	/**
	 * returns Product Description
	 * @return String Product Description
	 */
	public String getProductDescription() {
		return mProductDescription;
	}
	/**
	 * sets Product Description
	 * @param pProductDescription
	 */
	public void setProductDescription(String pProductDescription) {
		this.mProductDescription = pProductDescription;
	}

	/**
	 * returns	 Review Count
	 * @return float Review Count
	 */
	public float getReviewCount() {
		return mReviewCount;
	}

	/**
	 * sets	 Review Count
	 * @param pReviewCount
	 */
	public void setReviewCount(float pReviewCount) {
		this.mReviewCount = pReviewCount;
	}
	
	/**
	 * returns Average Rating
	 * @return int Average Rating
	 */
	public float getAverageRating() {
		return mAverageRating;
	}

	/**
	 * sets Average Rating
	 * @param pAverageRating
	 */
	public void setAverageRating(float pAverageRating) {
		this.mAverageRating = pAverageRating;
	}

	/**
	 * returns Price 
	 * @return float Price 
	 */
	public float getPrice() {
		return mPrice;
	}

	/**
	 * sets Price 
	 * @param pPrice
	 */
	public void setPrice(float pPrice) {
		this.mPrice = pPrice;
	}
	
	/**
	 * returns Record Image
	 * @return String Record Image 
	 */
	public String getRecordImage() {
		return mRecordImage;
	}

	/**
	 * sets Record Image
	 * @param pRecordImage
	 */
	public void setRecordImage(String pRecordImage) {
		this.mRecordImage = pRecordImage;
	}

	/**
	 * returns Product Long Description
	 * @return String Product Long Description
	 */
	public String getProductLongDescription() {
		return mProductLongDescription;
	}

	/**
	 * sets
	 * @param pProductLongDescription
	 */
	public void setProductLongDescription(String pProductLongDescription) {
		this.mProductLongDescription = pProductLongDescription;
	}

	/**
	 * returns Product Large Image
	 * @return String Product Large Image
	 */
	public String getProductLargeImage() {
		return mProductLargeImage;
	}
	/**
	 * sets Product Large Image
	 * @param pProductLargeImage
	 */
	public void setProductLargeImage(String pProductLargeImage) {
		this.mProductLargeImage = pProductLargeImage;
	}

	/**
	 * returns Camera Sensor
	 * @return String Camera Sensor
	 */
	public String getCameraSensor() {
		return mCameraSensor;
	}
	/**
	 * sets Camera Sensor
	 * @param pCameraSensor
	 */
	public void setCameraSensor(String pCameraSensor) {
		this.mCameraSensor = pCameraSensor;
	}





}
