$.extend($.fn.validatebox.defaults.rules, {
	miNo: valid_fieldNo,
    miName: valid_fieldName,
	bankNo:{
        validator: function (value, param) {
			if(value == "") return true;
        	if (value){
        		//return value.length<=12;
        		return /^([0-9]){12}$/.test(value);
        	}else{
        		return true;
        	}
        },
        message: global.member_bankNo
    }

});