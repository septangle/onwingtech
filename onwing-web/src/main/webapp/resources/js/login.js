
$(document).ready(function(){

 // var winWidth= window.screen.width;
 // var winWidth = device.screen.width;
 var w = Math.max(document.documentElement.clientWidth, window.innerWidth || 0);
 // var w = (window.innerWidth > 0) ? window.innerWidth : screen.width;
 var h = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);

 var winHeight= window.screen.height;

 var tempw = w*0.245;
 var temph = tempw*1.22

 $("#login").css("width",tempw);
 $("#login").css("height",temph);
 $("#login").css("margin-top",-(temph)/2);
 $("#login").css("margin-left",-(tempw)/2);


 $(".userNameText").click(function(){
   $(".userName").css("color",'#3c94dd');
   $(".pass").css("color","#999999");
 });

 $(".password").click(function(){
   $(".pass").css("color",'#3c94dd');
   $(".userName").css("color","#999999");
   console.log("password");
 });


});
