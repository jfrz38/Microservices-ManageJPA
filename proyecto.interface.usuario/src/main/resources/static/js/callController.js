function search(event) {
	$(document).ready(function () {
	    var txtSearch = $("#input-search-form").val()
	    if (txtSearch == '') {
	        return
	    }
	    $('#responseFragment').empty();
	    $('#searchResult').empty();
		var url = '' // '/pruebaBusqueda';
		
	    if ($("#name-equal:radio").is(':checked')) {
	        url='/moviesByName/'+txtSearch;
	        
	    } else if($("#year-high:radio").is(':checked')){
	        if(!isNaN(txtSearch)){
	        	url='/moviesByYear/high/'+txtSearch;
	        }else{
	        	url="/error/Se deben introducir números"
	            	$("#responseFragment").load("/error/Se%20deben%20introducir%20números");
	            	return;
	        }
	    }else if($("#rating-high:radio").is(':checked')){
	        if(!isNaN(txtSearch)){
	        	url='/moviesByRate/high/'+txtSearch;
	        }else{
	        	url="/error/Se deben introducir números"
	            	$("#responseFragment").load("/error/Se%20deben%20introducir%20números");
	            	return;
	        }
	    }else if($("#name-contains:radio").is(':checked')){
	        url='/nameContains/'+txtSearch;
	    }else if($("#year-low:radio").is(':checked')){
	        if(!isNaN(txtSearch)){
	        	url='/moviesByYear/low/'+txtSearch;
	        }else{
	            	$("#responseFragment").load("/error/Se%20deben%20introducir%20números");
	            	return;
	        }
	    }else if($("#rating-low:radio").is(':checked')){
	        if(!isNaN(txtSearch)){
	        	url='/moviesByRate/low/'+txtSearch;
	        }else{
	        	$("#responseFragment").load("/error/Se%20deben%20introducir%20números");
	        	return;
	        }
	    }else{
	        $("#responseFragment").load("/error");
	        return;
	    }
	    
	    $("#searchResult").load(url);
	});
}
		
