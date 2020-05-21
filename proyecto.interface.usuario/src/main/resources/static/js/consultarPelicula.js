function sendComment(id){
	
	$('#responseFragment').empty();
	
	var name = $( "#name" )[0].value
	if(name == '' || name === undefined){
		$("#responseFragment").load("/error/Se%20debe%20introducir%20un%20nombre");
    	return;
	}
	
	var list = $( ".star" );
	var rating = 0;
	for(var i = 0; i<list.length;i++){
		if(list[i].className.includes('selected')) rating++;
	}
	
	if(rating == 0){
		$("#responseFragment").load("/error/Se%20debe%20introducir%20una%20puntuación");
    	return;
	}
	var comment = $( "#select-5" )[0].value
	
	var comment ={
		authorName: name,
		rating: rating,
		comment: comment
	}

	var request = $.ajax({
	    url: '/comment/'+id,
	    type: 'POST',
	    data: JSON.stringify(comment),
	    contentType: 'application/json; charset=utf-8'
	});
	
	request.done(function(data) {
		$("#responseFragment").html(data);
	});
}

$(document).on('click', '.star', function () {

	$('.star').addClass('selected');
    var count = $(this).attr('data-name');
    for (var i = 0; i < count - 1; i++) {
        $('.star').eq(i).removeClass('selected');
    }

});