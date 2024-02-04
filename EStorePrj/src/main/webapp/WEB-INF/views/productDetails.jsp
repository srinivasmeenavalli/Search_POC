
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Camera Store</title>


<spring:url value="/resources/css/discover_electronics.css"
	var="mainCss" />
<link href="${mainCss}" rel="stylesheet" />
<spring:url value="/resources/images/discover-logo.png" var="mainImage" />
<spring:url value="/resources/images/Home2_beach_768x350.jpg"
	var="mediaBanner" />
<spring:url value="/resources/images/addToCart.png" var="addToCart" />
<spring:url value="/resources/images/whitestar.gif" var="ReviewStar" />
<spring:url value="/resources/images/yellowstar.gif" var="yellowstar" />

<script type="text/javascript">
	function openTab(evt, divTag) {

		var i, tabcontent, tablinks;
		var tbContents = document.getElementsByClassName("tabContent");

		for (i = 1; i < tbContents.length; i++) {
			tbContents[i].style.display = "none";
		}
		tablinks = document.getElementsByClassName("tabTitleFocus");

		for (i = 0; i < tablinks.length; i++) {

			tablinks[i].className = tablinks[i].className.replace(
					" tabTitleFocus", "tabTitleUnfocus");
			evt.currentTarget.className = " tabTitleFocus";

		}

		document.getElementById(divTag).style.display = "block";

	}
	function showOverView() {

		document.getElementById("Overview").style.display = "block";
	}
</script>

</head>
<body onload="showOverView();">

	<div class="PageContent">
		<div class="UserPanel">
			<div class="options">
				<a href="/discover/browse"> Home </a>| <a href="/discover/about-us">
					About Us </a>| <a href="/discover/contact-us"> Contact Us </a>
			</div>
			<div class="ShoppingCartStatus">
				<div class="shoppingCartText">
					<a href="#"> Shopping Cart </a>
				</div>
				<div class="numItems">0</div>
				<div class="itemsText">Items</div>
				<div class="clearFloat"></div>
			</div>

			<div class="clearFloat"></div>
		</div>
		<div class="PageLogo">
			<a href="#"><img src="${mainImage}" alt="Discover Electronics"
				width="188" height="58"></a>
		</div>
		<div class="PageHeader">

			<div class="SearchBox">
				<form action="/CameraStore/browse" method="GET">
					<span class="searchBoxContainer" style="float: left;"> <label>Search:</label>
						<input class="searchInput" name="searchTerm" type="text"
						id="searchText"></span> <input class="submit" type="submit"
						value="">
				</form>
			</div>

		</div>
		<div class="clearBoth"></div>
		<div class="RecordDetails">
			<c:set var="productLargeImage" value="${record.productLargeImage}"></c:set>

			<div class="toMainPage">
				<a href="javascript: window.history.back()">&nbsp;&lt; Back to
					Results</a>
			</div>

			<!-- Main Page Ending -->
			<div class="mainPart floatLeft">
				<!-- image part -->
				<c:forEach var="record" items="${records}">
					<div class="imagePart floatLeft">
						<c:set var="largeImage" value="${record.productLargeImage}" />
						<img class="mediumImage" alt="" src="${largeImage}">
						<div style="margin-top: 15px;">
							<div class="thumbnailGroup floatLeft">
								<span class="showBlock">More images:</span>
								<c:forEach var="galleryImage" items="${record.galleryImage}">
									<img src="${galleryImage}" />
								</c:forEach>

							</div>

						</div>
					</div>
					<div class="adhoc">
						<!-- introduction part -->
						<div class="introPart floatLeft">
							<div class="brand">
								<c:out value="${record.brandName}" />
							</div>
							<span class="title"><c:out value="${record.productTitle}" />
							</span>
							<!-- handle colors here -->
							<div class="introDetails">
								<!-- Handle Camera backpack and cases here -->
								<!-- Here are cameras -->
								<div class="firstColumn floatLeft">
									<span>Video Capture Resolution: <c:out
											value="${record.cameraDisplay}" />
									</span> <span>Lens Focal Range: <c:out
											value="${record.camerafocalrange}" /></span> <span>Processor
										Model:<c:out value="${record.cameraProcessor}" />
									</span>
								</div>
								<div class="secondColumn floatLeft">
									<span>Sensor Type: <c:out value="${record.cameraSensor}" /></span>
									<span>Interface:<c:out value="${record.cameraInterface}" />
									</span>
								</div>

								<div class="clear"></div>
							</div>
							<!-- Ranking Part -->
							<div class="ranking">
								<div class="floatLeft">
									<c:forEach begin="0" end="4" varStatus="status">
										<c:choose>
											<c:when
												test="${(record.averageRating + 0.5) ge (status.index+1.0)}">
												<img alt="" src="<c:out value="${yellowstar}" />">
											</c:when>
											<c:otherwise>
												<img alt="" src="<c:out value="${ReviewStar}" />">
											</c:otherwise>
										</c:choose>
									</c:forEach>



									&nbsp;&nbsp;&nbsp;
								</div>
								<div class="floatLeft">

									Review (
									<c:out value="${record.reviewCount}" />
									)



								</div>
							</div>
						</div>
						<div class="offerBox  floatLeft">
							<div class="floatLeft shippingDetails">
								<span class="title">Shipping Details</span>
								<div>
									<div class="properties floatLeft">
										<span>Next day delivery</span> <span>Free delivery </span> <span>Standard
											delivery</span>
									</div>
									<div class="values floatRight">
										<span>order before 2pm</span> <span>5 - 8 days</span> <span>1
											- 5 days</span>
									</div>
								</div>
							</div>
							<div class="specialOffers floatLeft">
								<span class="title">Special Offers</span>
								<div class="properties">
									<span>Free Giftwrap</span>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
				<div class="clear"></div>
				<!-- tab parts -->
				<div class="tab">
					<!-- titles -->
					<div class="tabHead">
						<span class="tabTitleFocus" onclick="openTab(event,'Overview');">Overview</span>
						<span class="tabTitleUnfocus"
							onclick="openTab(event,'Specifications');">Specifications</span>
						<span class="tabTitleUnfocus"
							onclick="openTab(event,'Accessories');">Accessories</span> <span
							class="tabTitleUnfocus" onclick="openTab(event,'WarrantyInfo');">Warranty
							Info</span>
					</div>
					<!-- content -->
					<div class="tabContent">
						<div class="tabContent" id="Overview">
							<c:forEach var="record" items="${records}">
								<c:out value="${record.productLongDescription}" />
							</c:forEach>

						</div>

						<div class="tabContent" id="Specifications">No Specification
							available.</div>

						<div class="tabContent" id="Accessories">No accessories
							available.</div>

						<div class="tabContent" id="WarrantyInfo">
							<c:forEach var="record" items="${records}">

								<c:choose>
									<c:when test="${empty record.cameraWarranty}">
                                                               
                            No warranty information available.
                        </c:when>
									<c:otherwise>
                                                               
                           <c:out value="${record.cameraWarranty}" /> 
                        </c:otherwise>
								</c:choose>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
			<div class="rightPart floatLeft">
				<div class="shippingCart">
					<span class="price"> <c:forEach var="record"
							items="${records}">
                          $<c:out value="${record.price}" />
						</c:forEach>
					</span>


					<div class="addToCart">
						<a href="#"> <img src="${addToCart}" alt="Add to Cart">
						</a>
					</div>
					<span class="addToWishList">+&nbsp;Add to Wishlist</span>
					<hr class="line">
				</div>
			</div>

			<!-- Image Part FloatLeft -->

		</div>
		<!-- For Record Details Ending-->

	</div>
	<!-- For PageContent Ending -->


</body>
</html>