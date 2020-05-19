$(document).ready(function () {
    $('.star').click(function () {
        console.log("Entra")
        $('.star').addClass('selected');
        var count = $(this).attr('name');
        for (var i = 0; i < count - 1; i++) {
            $('.star').eq(i).removeClass('selected');
        }

        console.log("count =" + (5 - i))
    });
});