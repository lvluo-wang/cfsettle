$.extend($.fn.validatebox.defaults.rules, {
	funcName: valid_fieldName,
    funcKey:{
		validator: function (value, param) {
        	if (value){
        		return (/^[\w|\d|\.|_]+$/.test(value))&&getLength(value)<=30;
        	}else{
        		return true;
        	}
        },
        message: global.valid_func_key
	}
	
});