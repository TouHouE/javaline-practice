

$("#test_button").click(function() {
	$.get("img/get_wallpaper", function(result) {
		let src_list = result.split("+");
		let len = src_list.length
		for(let i = 0; i < len; ++i) {

			$("div").append("<img src=\"http://localhost:9090/img/" + src_list[i] + "\" width=\"45%\"/>")
		
		}
	});
});