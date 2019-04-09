<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<script src="<c:url value="/resources/core/jquery.1.10.2.min.js" />"></script>
<script src="<c:url value="/resources/core/jquery.autocomplete.min.js" />"></script>
<link href="<c:url value="/resources/core/main.css" />" rel="stylesheet">

</head>
<body>
	<h2>Typeahead feature</h2>

	<div>
	    <div>city : 
		<input type="text"  id="w-input-search" value="">
		</div>
		
	</div>
	
	<script>
	
	$(document).ready(function() {
		
		$('#w-input-search').autocomplete({
			delay: 1000,
			max:2,
			serviceUrl: '${pageContext.request.contextPath}/suggest_cities_unlimited',
			paramName: "tagName",
			delimiter: ",",
		    transformResult: function(response) {
		    	
		        return {
		        	
		            suggestions: $.map($.parseJSON(response), function(item) {
		            	
		                return { value: item.tagName, data: item.id };
		            })
		            
		        };
		        
		    }
	
		    
		});
		$('#w-input-search').autocomplete("option", "delay", 1000);
			
	});
	</script>
	
</body>
</html>