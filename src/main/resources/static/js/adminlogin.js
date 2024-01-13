// // 아이디와 비밀번호가 공백일 때 적용되는 자바스크립트 (광진)
let adminId = $('#id');
let adminPwd = $('#pwd');
let btn = $('#btn');

$(btn).on('click', function(){
    if($(adminId).val() === "" ){
        $(adminId).next('label').addClass('warning');
        setTimeout(function(){
            $('label').removeClass('warning');
        },1500);
    }
    else if($(adminPwd).val () === ""){
        $(adminPwd).next('label').addClass('warning');
        setTimeout(function(){
            $('label').removeClass('warning');
        },1500);
    }
});

$(document).ready(function() {
    // 사용자 로그인 링크 클릭 시 사용자 로그인 폼 표시, 공급자 로그인 폼 숨김
    $('#userLogin').click(function() {
      $('#userLoginForm').show();
      $('#providerLoginForm').hide();
    });
  
    // 공급자 로그인 링크 클릭 시 공급자 로그인 폼 표시, 사용자 로그인 폼 숨김
    $('#providerLogin').click(function() {
      $('#userLoginForm').hide();
      $('#providerLoginForm').show();
    });
  });
  