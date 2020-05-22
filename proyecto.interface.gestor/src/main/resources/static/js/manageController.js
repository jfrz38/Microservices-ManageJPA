function saveMovie(id) {

	$('#responseFragment').empty();
	var name = $('#name-input')[0].value;
	var year = $('#year-input')[0].value;
	var description = $('#description-input')[0].value;
	var imageURL = $('#image-input')[0].value;

	if (isNaN(year)) {
		$("#responseFragment").load("/error/Se%20deben%20introducir%20números");
		return;
	}
	
	var movie = {
		name: name,
		year: year,
		description: description,
		imageURL : imageURL
	}

	var request = $.ajax({
		url : '/updateMovie/' + id,
		type : 'PUT',
		data : JSON.stringify(movie),
		contentType : 'application/json; charset=utf-8'
	});

	request.done(function(data) {
		$("#responseFragment").html(data);
	});

}

function deleteMovie(id) {

	$('#responseFragment').empty();
	var request = $.ajax({
		url : '/deleteMovie/' + id,
		type : 'DELETE',
		contentType : 'application/json; charset=utf-8'
	});
	request.done(function(data) {
		$("#responseFragment").html(data);
	});

}

function deleteComment(id) {

	$('#responseFragment').empty();

	var request = $.ajax({
		url : '/deleteComment/' + id,
		type : 'DELETE',
		contentType : 'application/json; charset=utf-8'
	});
	request.done(function(data) {
		$("#responseFragment").html(data);
	});
}