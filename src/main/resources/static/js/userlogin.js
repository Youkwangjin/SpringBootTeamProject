let userId = $('#id');
let userPwd = $('#pwd');
let btn = $('#btn');

$(btn).on('click', function(){
    if($(userId).val() === "" ){
        $(userId).next('label').addClass('warning');
        setTimeout(function(){
            $('label').removeClass('warning');
        },1500);
    }
    else if($(userPwd).val () === ""){
        $(userPwd).next('label').addClass('warning');
        setTimeout(function(){
            $('label').removeClass('warning');
        },1500);
    }
})

