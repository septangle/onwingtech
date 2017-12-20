console.log('\'Allo \'Allo!');

window.onwing = (function (onwing, $, undefined) {

  var validateRegx = {
    tel: ['^0\\d{2,3}\\-?\\d{7,8}$','电话号码格式错误'],
    mobile: ['^1\\d{10}$','手机号码格式错误'],
    password: ['^[\\w\!\@\#\$\%\^\&\(\)\*]{6,}$','密码格式错误'],
    username: ['^[\\w\u4e00-\u9fa5]{2,}$','用户名格式错误']
  }
  onwing.validate = function(validateRegxArr){
    var reg = new RegExp(validateRegx.username[0],'g');
    var test = '周闯'
    return console.info(reg.test(test));
  }
  return onwing;
})(window.onwing || {}, jQuery);

onwing.validate();

