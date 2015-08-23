/**********************自定义验证部分*************************/
//1   20个字符，中文算2个
$.validator.addMethod("char20", function(value, element) {
	 if(value.replace(/[^\x00-\xff]/g,"**").length>20){
		 return false;
	 }
	 return true;
}, "字符长度最多为20个(中文计为2个),请重新确认!");

//2   50个字符，中文算2个
$.validator.addMethod("char50", function(value, element) {
	 if(value.replace(/[^\x00-\xff]/g,"**").length>50){
		 return false;
	 }
	 return true;
}, "字符长度最多为50个(中文计为2个),请重新确认!");

//3   150个字符，中文算2个
$.validator.addMethod("char150", function(value, element) {
	 if(value.replace(/[^\x00-\xff]/g,"**").length>150){
		 return false;
	 }
	 return true;
}, "字符长度最多为150个(中文计为2个),请重新确认!");

//4  正整数
$.validator.addMethod("positiveDigits", function(value, element) {
	var rule=/^[0-9]*$/;
	if(rule.test(value)){
		return true;
	}
	return false;
}, "只能输入正整数,请重新确认!");

//5 正浮点数
$.validator.addMethod("positiveDecimal", function(value, element) {
  var rule = /^\d+\.?\d*$/;
  return rule.test(value);
}, "只能输入合法的格式,请重新确认!");

//7 小于1000亿
$.validator.addMethod("hundredBillion", function(value, element) {
  var ruleValue=9999999.999999;
  return value<=ruleValue;
}, "只能输入小于10000000万元的正数,请重新确认!");

//8 mobilePhone
$.validator.addMethod("mobilePhone", function(value, element) {
  var ruleValue=/^1{1}\d{10}$/;//中国手机
  return this.optional(element)||ruleValue.test(value);
}, "请输入正确的中国(大陆)手机号码!");

//9、人名
$.validator.addMethod("trueName", function(value, element) {
	//以汉字和字母开头,后面可以有汉字、字母、点,长度20位
	var rule=/^([\u4E00-\u9fa5]|[A-Z]|[a-z]){1}(·|[\u4E00-\u9fa5]|[A-Z]|[a-z]){0,19}$/;
	if (rule.test(value)){
		if(value.replace(/[^\x00-\xff]/g,"**").length<=20){
			return true;
		}
	}
	return false;
}, "只能输入中/英文/间隔号且长度不大于20(中文计为2)!");

//10 正数
$.validator.addMethod("positive", function(value, element) {
  var ruleValue=/^\d*$/;//不能有负符号
  return ruleValue.test(value);
}, "请输入正数!");

//11 微信号
$.validator.addMethod("weiXin", function(value, element) {
	var email=/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,4}$/;
	var mobilePhone=/^1{1}\d{10}$/;//中国手机号码
	//中英文数字
	var numberChar=/^([\u4E00-\u9fa5]|[A-Z]|[a-z]|[0-9]){1,50}$/;
  	return this.optional(element)||email.test(value)||mobilePhone.test(value)||numberChar.test(value);
}, "请输入正确的(微信号/邮箱/手机/QQ)微信号!");

//12 myEmail
$.validator.addMethod("myEmail",function(value,element){
	var email=/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,4}$/;
	return this.optional(element)||email.test(value);
},"请输入正确的电子邮箱地址!");