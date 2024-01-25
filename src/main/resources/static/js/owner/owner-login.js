let businessNum = $('#businessNum');
let ownerPwd = $('#ownerPwd');
let ownerLoginBtn = $('#ownerLoginBtn');
let isFormValid = true;

$(ownerLoginBtn).on('click', function (){
    isFormValid = true;

    if ($(businessNum).val() === "") {
        $('#ownerIdWarning').text("사업자번호를 입력해주세요.").show();
        isFormValid = false;
    }

    if ($(ownerPwd).val() === "") {
        $('#ownerPwdWarning').text("비밀번호를 입력해주세요.").show();
        isFormValid = false;
    }

    if (!isFormValid) {
        setTimeout(function () {
            $('.warning-message').hide();
        }, 1500);
        return;
    }

    $.ajax({
        url: '/ownerLogSuccess',
        type: 'POST',
        data: {
            business_num: $(businessNum).val(),
            owner_pwd: $(ownerPwd).val()
        },
        dataType: 'json',
        success: function (response) {
            if (response.status === '성공!') { // 백엔드 로직에서 put(key, value)값 동일하게 하기!
                window.location.href = '/ownerSessionKeep';
            } else {
                if (response.message.includes("사업자번호", "비밀번호")) {
                    $('#ownerIdWarning').text(response.message).show();
                    $('#ownerPwdWarning').text(response.message).show();
                } else if (response.message.includes("비밀번호")) {
                    $('#ownerPwdWarning').text(response.message).show();
                }
            }
        },
        error: function () {
            alert('로그인 중 서버에 문제가 발생했습니다.');
        }
    });
});