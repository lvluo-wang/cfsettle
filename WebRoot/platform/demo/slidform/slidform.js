$(function() {
	/*
	number of fieldsets
	*/
	var fieldsetCount = $('#formElem').find('.step').length;

	/*
	current position of fieldset / navigation link
	*/
	var current 	= 1;
	 if(current==1)
		 $('#wrapper').find('.prev').addClass('disabled');
	 else
		 $('#wrapper').find('.prev').removeClass('disabled');
	 if(current==fieldsetCount)
		 $('#wrapper').find('.next').addClass('disabled');
	 else
		 $('#wrapper').find('.next').removeClass('disabled');
	/*
	sum and save the widths of each one of the fieldsets
	set the final sum as the total width of the steps element
	*/
	var stepsWidth	= 0;
    var widths 		= new Array();
	$('#steps .step').each(function(i){
        var $step 		= $(this);
		widths[i]  		= stepsWidth;
        stepsWidth	 	+= $step.width();
    });
	stepsWidth+=20;
	$('#steps').width(stepsWidth);
	
	/*
	to avoid problems in IE, focus the first input of the form
	*/
	$('#formElem').children(':first').find(':input:first').focus();	
	
	/*
	show the navigation bar
	*/
	$('#navigation').show();
	
	/*
	when clicking on a navigation link 
	the form slides to the corresponding fieldset
	*/
    $('#navigation a').bind('click',function(e){
		var $this	= $(this);
		var prev	= current;
		$this.closest('ul').find('li').removeClass('current selected');
        $this.parent().addClass('current selected');
		/*
		we store the position of the link
		in the current variable	
		*/
		current = $this.parent().index() + 1;
		/*
		animate / slide to the next or to the corresponding
		fieldset. The order of the links in the navigation
		is the order of the fieldsets.
		Also, after sliding, we trigger the focus on the first 
		input element of the new fieldset
		If we clicked on the last link (confirmation), then we validate
		all the fieldsets, otherwise we validate the previous one
		before the form slided
		*/
		var tmpWidth=widths[current-1];

    	$('#steps').stop().animate({
             marginLeft: '-' + tmpWidth + 'px'
        },500,function(){
			$('#formElem').children(':nth-child('+ parseInt(current) +')').find(':input:first').focus();	
		});

        if(current==1)
        $('#wrapper').find('.prev').addClass('disabled');
        else
        	$('#wrapper').find('.prev').removeClass('disabled');
        if(current==fieldsetCount)
            $('#wrapper').find('.next').addClass('disabled');
            else
            	$('#wrapper').find('.next').removeClass('disabled');
        e.preventDefault();
    });
    $('#wrapper').find('.next').bind('click',function(e){
    	if(current==fieldsetCount){
    		e.preventDefault();
    		return;
    	}
    		
    	current++;
    	$('#navigation').find('li').removeClass('current selected');
    	$('#navigation').find('li').each(function(i,n){
    		if(i==current-1)
    		$(n).addClass('current selected');
    	});
    	var tmpWidth=widths[current-1];

    	$('#steps').stop().animate({
             marginLeft: '-' + tmpWidth + 'px'
         },500,function(){
 			$('#formElem').children(':nth-child('+ parseInt(current) +')').find(':input:first').focus();	
 		});

		var $this	= $(this);
		var prev	= current;
		$this.parent().find('a').removeClass('disabled');
		
		if(current==fieldsetCount)
			$this.addClass('disabled');
        e.preventDefault();
    });
    $('#wrapper').find('.prev').bind('click',function(e){
    	if(current==1){
    		e.preventDefault();
    		return;
    	}
    	current--;
    	$('#navigation').find('li').removeClass('current selected');
    	$('#navigation').find('li').each(function(i,n){
    		if(i==current-1)
    		$(n).addClass('current selected');
    	});
    	var tmpWidth=widths[current-1];

    	$('#steps').stop().animate({
             marginLeft: '-' + tmpWidth + 'px'
         },500,function(){
 			$('#formElem').children(':nth-child('+ parseInt(current) +')').find(':input:first').focus();	
 		});
		var $this	= $(this);
		var prev	= current;
		$this.parent().find('a').removeClass('disabled');
		
		if(current==1)
			$this.addClass('disabled');
        e.preventDefault();
    });
	/*
	clicking on the tab (on the last input of each fieldset), makes the form
	slide to the next step
	*/
	$('#formElem > fieldset').each(function(){
		var $fieldset = $(this);
		$fieldset.children(':last').find(':input').keydown(function(e){
			if (e.which == 9){
				$('#navigation li:nth-child(' + (parseInt(current)+1) + ') a').click();
				/* force the blur for validation */
				$(this).blur();
				e.preventDefault();
			}
		});
	});

	
	/*
	if there are errors don't allow the user to submit
	*/
	$('#registerButton').bind('click',function(){
		if($('#formElem').data('errors')){
			alert('Please correct the errors in the Form');
			return false;
		}	
	});
});