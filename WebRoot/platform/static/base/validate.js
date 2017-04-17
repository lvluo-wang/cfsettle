var valid_fieldName={
	validator: function (value, param) {
		if (value){
			return (/^[\u0391-\uFFE5\w]+$/.test(value))&&getLength(value)<=20;
		}else{
			return true;
		}
	},
	message: global.valid_fieldName
};
var valid_fieldNo={
	validator: function (value, param) {
		if (value){
			return (/^[A-Za-z0-9_]+$/.test(value))&&getLength(value)<=20;
		}else{
			return true;
		}
	},
	message: global.valid_fieldNo
};
$.extend($.fn.validatebox.defaults.rules, {
    CHS: {
        validator: function (value, param) {
        	if (value){
        		return /^[\u0391-\uFFE5]+$/.test(value);
        	}else{
        		return true;
        	}
        },
        message: global.valid_CHS
    },
    ZIP: {
        validator: function (value, param) {
        	if (value){
        		return /^[1-9]\d{5}$/.test(value);
        	}else{
        		return true;
        	}
        },
        message: global.valid_ZIP
    },
    QQ: {
        validator: function (value, param) {
        	if (value){
        		return /^[1-9]\d{4,10}$/.test(value);
        	}else{
        		return true;
        	}
        },
        message: global.valid_QQ
    },
    
    mobile: {
        validator: function (value, param) {
        	if (value){
        		return /^((\(\d{2,3}\))|(\d{3}\-))?1[3578]\d{9}$/.test(value);
        	}else{
        		return true;
        	}
        },
        message: global.valid_mobile
    },
    email: {
    	validator: function(value, param){
	    	if (value){
	    		return /^([a-zA-Z0-9_-]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value);
	    	}else{
	    		return true;
	    	}
    	},
    	message: global.valid_email
    },
    

    
    loginName: {
        validator: function (value, param) {
        	if (value){
        		return /^[\u0391-\uFFE5\w]+$/.test(value)&&value.length<=20;
        	}else{
        		return true;
        	}
        },
        message: global.valid_loginName
    },
    serialNumber: {
        validator: function (value, param) {
        	if (value){
        		return /^[A-Za-z0-9_]+$/.test(value);
        	}else{
        		return true;
        	}
        },
        message: global.valid_serialNumber
    },
    safepass: {
        validator: function (value, param) {
        	if (value){
        		return safePassword2(value);
        	}else{
        		return true;
        	}
        },
        message: global.valid_safepass
    },
    textLength:{
    	validator: function (value, param) {
        	if (value){
        		if (param.length==1){
        			return value.length>=param[0];
        		}
        		return (value.length>=param[0])&&value.length<=param[1];
        	}else{
        		return true;
        	}
        },
        message: global.valid_textLength
    },
    equalTo: {
        validator: function (value, param) {
        	if (value){
        		return value == $("#"+param[0]).val();
        	}else{
        		return true;
        	}
        },
        message: global.valid_equalTo
    },
    /*
     *纯数字编号
     */
    number: {
        validator: function (value, param) {
        	if (value){
        		return /^\d+$/.test(value);
        	}else{
        		return true;
        	}
        },
        message: global.valid_number
    },
    idcard: {
        validator: function (value, param) {
        	if (value){
        		return idCard(value);
        	}else{
        		return true;
        	}
        },
        message:global.valid_idcard
    },
    alpha:{  
        validator:function(value,param){  
            if (value){  
                return /^[a-zA-Z\u00A1-\uFFFF]*$/.test(value);  
            } else {  
                return true;  
            }  
        },  
        message:global.valid_alpha
    },
    positive_int:{  
        validator:function(value,param){  
            if (value){  
                return /^[1-9][0-9]*$/.test(value);  
            } else {  
                return true;  
            }  
        },  
        message:global.valid_positive_int
    },
    positive_int_zero:{  
        validator:function(value,param){  
            if (value){  
                return /^[1-9][0-9]*$|(^0$)/.test(value);  
            } else {  
                return true;  
            }  
        },  
        message:global.valid_positive_int_zero
    },
    /**
     * 金额小数小于2位
     * @param {Object} value
     * @param {Object} param
     * @return {TypeName} 
     */
    money: {
        validator: function (value, param) {
        	if (value){
				return /^(([1-9]{1}(\d){0,12})|[0]{1})(\.(\d){1,2})?$/.test(value);
        	}else{
        		return true;
        	}
        },
        message: global.valid_money
    },
	xmoney: {
        validator: function (value, param) {
        	if (value){
				value = value.replace(/,/g,"");
        	    return /^(-?)(([1-9]{1}(\d){0,12})|[0]{1})(\.(\d){1,2})?$/.test(value);
        	}else{
        		return true;
        	}
        },
        message: global.valid_xmoney
    },
	wanMoney: {
        validator: function (value, param) {
        	if (value){
				value = value.replace(/,/g,"");
        	    return /^(([1-9]{1}(\d){0,7})|[0]{1})(\.(\d){1,4})?$/.test(value);
        	}else{
        		return true;
        	}
        },
        message: global.valid_wan_money
    },
	xwanMoney: {
        validator: function (value, param) {
        	if (value){
				value = value.replace(/,/g,"");
        	    return /^(-?)(([1-9]{1}(\d){0,7})|[0]{1})(\.(\d){1,4})?$/.test(value);
        	}else{
        		return true;
        	}
        },
        message: global.valid_wan_xmoney
    },
	percentage: {
        validator: function (value, param) {
        	if (value){
        	    return /(^(-?)(([1-9]{1}(\d){0,1})|[0]{1})(\.(\d){0,2})?$)|(^(-?)100(\.(0){0,2})?$)/.test(value);
        	}else{
        		return true;
        	}
        },
        message: global.valid_percentage
    },
	positive_percentage: {
		validator: function (value, param) {
			if (value){
				return /(^(([1-9]{1}(\d){0,1})|[0]{1})(\.(\d){0,2})?$)|(^(-?)100(\.(0){0,2})?$)/.test(value);
			}else{
				return true;
			}
		},
		message: global.valid_percentage
	},
	positive_percent: {
		validator: function (value, param) {
			if (value){
				return /(^(([1-9]{1}(\d){0,1})|[0]{1})(\.(\d){0,2})?$)/.test(value);
			}else{
				return true;
			}
		},
		message: global.valid_percent
	},
	xpercentage: {
        validator: function (value, param) {
        	if (value){
        	    return /(^(-?)(([1-9]{1}(\d){0,2})|[0]{1})(\.(\d){0,2})?$)|(^(-?)100(\.(0){0,2})?$)/.test(value);
        	}else{
        		return true;
        	}
        },
        message: global.valid_percentage
    },
    compareDate:{
    	validator:function(value,param){
    		if(value&&param){
    			var oStartDT = $("#"+param[0]);
    			var startDate=new Date(Date.parse(oStartDT.val().replace("-","/")));
    			var endDate=new Date(Date.parse(value.replace("-","/")));
    			return startDate<endDate;
    		}else{
    			return true;
    		}
    	},
    	message:global.valid_compareDate
    },
	compareNum: {
		validator: function (value, param) {
			if(value&&param) {
				var amount = $('#' + param[0]).val();
				return  parseInt(amount) >=parseInt(value);
			}else{
				return true;
			}
		}, 
		message: '项目成立金额不能大于募集金额'
	},
    compareSum:{
        validator: function (value,param) {
			if(value&&param){
				var sum=0;
				for(var i=0;i<param.length;i++){
					var val = parseFloat($('#' + param[i]).val()).toFixed(2);
					sum += parseFloat(val);
				}
                return  parseFloat(sum).toFixed(2) == parseFloat(value);
            }else{
				return true;
			}
        },
		message: '佣金计提总比例不等于区域经理+营业部+团队+客户经理佣金计提'
	},

	orgCode:{
    	validator: function (value, param) {
        	if (value){
        		return /^[A-Za-z0-9]{8}-[A-Za-z0-9]{1}/.test(value);
        	}else{
        		return true;
        	}
        },
        message: global.valid_orgCode
    },
	tel:{
		validator:function(value,param){
			if( value ){
				//86-0571-88888888-7868 简单的电话格式
				var rp = /^((\+?\d{2,3}-)?0\d{2,3}-)?\d{7,8}(-\d{3,4})?$/;
				return rp.test(value);
			}else{
				return true;
			}
		},
		message:global.valid_tel
	},
	char_num_ul:{
		validator:function(value,param){
			if(value){
				var rp = /^[\w\d_]+$/;
				return rp.test(value);
			}else{
				return true;
			}
		},
		message:global.valid_char_num_ul_msg
	},
	chs_char_num_ul:{
		validator:function(value,param){
			if(value){
				var rp = /^[\u0391-\uFFE5\w\d_]+$/;
				return rp.test(value);
			}else{
				return true;
			}
		},
		message:global.valid_chs_char_num_ul_msg
	},
	age:{
		validator:function(value,param){
			if(value){
				var rp = /^([1-9]|[1-9][0-9]|100)$/;
				return rp.test(value);
			}else{
				return true;
			}
		},
		message:global.valid_age_msg
	},
	/**只能输入数字，字母，中文，但是不能是纯数字*/
	num_alpha:{
		validator:function(value,param){
			if(value){
				return /^([a-zA-Z\u4E00-\u9FA5].*$)|(^.*[a-zA-Z\u4E00-\u9FA5]$)/.test(value);
			}else{
				return true;
			}
		},
		message:global.valid_num_alpha
	},
	length:{
		validator:function(value,param){
			var len=$.trim(value).length;
			return len>=param[0]&&len<=param[1];
		},
		message:global.valid_chs_char_num_not_match_rule
	},
	userNo: valid_fieldNo,
    fieldName:valid_fieldName
});


/* 密码由字母和数字组成，至少6位 */
var safePassword = function (value) {
    return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
};
/*小写字母、大写字母、数字及下划线，任意两种以上，至少6位,20位以内*/
var safePassword2 = function (value) {
		return !(/^(([A-Z]*|[a-z]*|\d*|[_]*)|.{0,5})$|\s/.test(value))&&value.length<=20;
};
/**
 * 身份证验证
 * @param value 待验证的数据
 * @returns
 */
var idCard = function (value) {
    if (value.length == 18 && 18 != value.length) return false;
    var number = value.toLowerCase();
    var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
    var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
    if (re == null || a.indexOf(re[1]) < 0) return false;
    if (re[2].length == 9) {
        number = number.substr(0, 6) + '19' + number.substr(6);
        d = ['19' + re[4], re[5], re[6]].join('-');
    } else d = [re[9], re[10], re[11]].join('-');
    if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
    for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];
    return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
};



/**
 * 时间验证 
 * @param format 验证的格式，默认yyyy-MM-dd
 * @param reObj
 * @returns
 */
var isDateTime = function (format, reObj) {
    format = format || 'yyyy-MM-dd';
    var input = this, o = {}, d = new Date();
    var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
    var len = f1.length, len1 = f3.length;
    if (len != f2.length || len1 != f4.length) return false;
    for (var i = 0; i < len1; i++) if (f3[i] != f4[i]) return false;
    for (var i = 0; i < len; i++) o[f1[i]] = f2[i];
    o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
    o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
    o.dd = s(o.dd, o.d, d.getDate(), 31);
    o.hh = s(o.hh, o.h, d.getHours(), 24);
    o.mm = s(o.mm, o.m, d.getMinutes());
    o.ss = s(o.ss, o.s, d.getSeconds());
    o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
    if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0) return false;
    if (o.yyyy < 100) o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
    d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
    var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM && d.getDate() == o.dd && d.getHours() == o.hh && d.getMinutes() == o.mm && d.getSeconds() == o.ss && d.getMilliseconds() == o.ms;
    return reVal && reObj ? d : reVal;
    function s(s1, s2, s3, s4, s5) {
        s4 = s4 || 60, s5 = s5 || 2;
        var reVal = s3;
        if (s1 != undefined && s1 != '' || !isNaN(s1)) reVal = s1 * 1;
        if (s2 != undefined && s2 != '' && !isNaN(s2)) reVal = s2 * 1;
        return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
    }
};

function formatMoney(value)
{
	return new Number(value).formatMoney(2);
};

/***
*利率格式化
*/
function formatRate(value)
{		
	return new Number(value).formatMoney(4);
};

/**
		 * 获取字符串长度
		 * @param {Object} str
		 * @return {TypeName} 
		 */
		function getLength(str){
			var ret = 0;
			if( str ){
				ret = str.length;
			}
			return ret;
			/*
			var i,sum=0;   
		    for(i=0;i<str.length;i++){
		      if((str.charCodeAt(i)>=0)&&(str.charCodeAt(i)<=255))   
		        sum++;   
		      else
		  		sum+=2;
		    }   
		    return sum;
			*/
		}
		
