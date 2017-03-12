$(function() {
	$('#side-menu').metisMenu();
	
	$("iframe#content").load(function(){
		this.style.height = this.contentWindow.document.body.scrollHeight + 'px';
		this.style.width = '100%';
	});
	
    $(window).bind("load resize", function() {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });

    var url = window.location;
    var element = $('ul.nav a')
						.click(function(){
							$("#content").attr("src", $(this).attr("data-href"));
						})
						.filter(function() {
							if(this.href == url || url.href.indexOf(this.href) == 0){
								$("#content").attr("src", $(this).attr("data-href"));
								return true;
							}
							return false;
						})
						.addClass('active')
						.parent()
						.parent()
						.addClass('in')
						.parent();
						
    if (element.is('li')) {
        element.addClass('active');
    }
	
	
});
