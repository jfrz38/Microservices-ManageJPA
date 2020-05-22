function insertMovie(){

	$('#responseFragment').empty();
	
	var name = $('#name-input')[0].value;
	var year = $('#year-input')[0].value;
	var description = $('#description-input')[0].value;
	var imageURL = $('#image-input')[0].value;
	
	if (isNaN(year)) {
		$("#responseFragment").load("/error/Se%20deben%20introducir%20números");
		return;
	}
	
	if(name==''){
		$("#responseFragment").load("/error/Introduce%20un%20título");
		return;
	}
	
	if(description==''){
		$("#responseFragment").load("/error/Introduce%20una%20descripción");
		return;
	}
	
	
	var movie = {
			name: name,
			year: year,
			description: description,
			imageURL : imageURL
		}

var request = $.ajax({
    url: '/insertMovie',
    type: 'POST',
    data: JSON.stringify(movie),
    contentType: 'application/json; charset=utf-8'
});

request.done(function(data) {
	$("#responseFragment").html(data);
	console.log("ok",data);
});
}

$(document).on('blur', '#image-input', function () {

	var imageURL = $('#image-input')[0].value;
	if(imageURL == ''){
		$('.image').attr("src",'images/movieDefault.jpg')
	}else{
		$('.image').attr("src",imageURL)
	}

});