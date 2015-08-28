/**********************自定义验证部分*************************/

//mobilePhone
$.validator.addMethod("mobilePhone", function(value, element) {
  var ruleValue=/^1{1}\d{10}$/;//中国手机
  return this.optional(element)||ruleValue.test(value);
}, "请输入正确的中国(大陆)手机号码!");

//人名
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

//myEmail
$.validator.addMethod("myEmail",function(value,element){
	var email=/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,4}$/;
	return this.optional(element)||email.test(value);
},"请输入正确的电子邮箱地址!");