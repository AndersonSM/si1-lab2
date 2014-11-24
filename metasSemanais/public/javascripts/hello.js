$(document).ready(function(){
	$(".atrib").click(function(){
		$("#desc"+this.id.substring(4)).toggle("slow");
    });	
});