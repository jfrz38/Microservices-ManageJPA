function createMovie(){
	console.log("create movie");
	var movie ={
			name: "Saasdasdasdsdr",
			year: 1979,
			description: "A llorar al parque",
			imageURL: ""
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