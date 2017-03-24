 /**
	  * 页面屏蔽
	  */
	 function mask(){
		 var wrap=$("body");
		 var bWidth=$(window).width()-1;
		 var bHeight=$(window).height();
		 $("#loading").css({
			 	display:"block",
			 	width:bWidth,
			 	height:bHeight
			 });
		 $("#loading_msg").html(global.systemBisyInfo).css({
			 	display:"block",
			 	left:(bWidth-$("div.common-mask-msg",wrap).outerWidth())/2,
			 	top:(bHeight-$("div.common-mask-msg",wrap).outerHeight())/2
			 });
	 } 
	 /**
	  * 修改页面屏蔽信息
	  * @param msg 页面屏蔽时显示的信息
	  */
	 function updateMask(msg){
		 $("#loading_msg").html(msg);
	 }
	 /**
	  * 关闭页面屏蔽
	  */
	 function closeMask(){
		 $("#loading").hide();
		 $("#loading_msg").html("").hide();
	 }
	 function loadmask(){
		var wrap=$("body");
		var bWidth=$(window).width()-1;
		 var bHeight=$(window).height();
		 $("<div class=\"load-mask\"></div>").css({
			 	display:"block",
			 	width:bWidth,
			 	height:bHeight
			 }).appendTo(wrap);
		 $("<div class=\"load-mask-msg\"></div>").html(global.pageLoading).appendTo(wrap).css({
			 	display:"block",
			 	left:(bWidth-$("div.load-mask-msg",wrap).outerWidth())/2,
			 	top:(bHeight-$("div.load-mask-msg",wrap).outerHeight())/2
			 });
	 } 
	 function updateloadMask(msg){
		 var wrap=$("html");
		 wrap.find("div.load-mask-msg").html(msg);
	 }
	 function closeloadMask(){
		 var wrap=$("html");
		 wrap.find("div.load-mask-msg").remove();
		 wrap.find("div.load-mask").remove();
	 }