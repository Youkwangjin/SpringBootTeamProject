let adminId = $('#adminId');
let adminPwd = $('#adminPwd');
let adminLoginBtn = $('#adminLoginBtn');
let isFormValid = true;

$(adminLoginBtn).on('click', function () {
    isFormValid = true;

    if ($(adminId).val() === "") {
        $('#adminIdWarning').text("아이디를 입력해주세요.").show();
        isFormValid = false;
    }

    if ($(adminPwd).val() === "") {
        $('#adminPwdWarning').text("비밀번호를 입력해주세요.").show();
        isFormValid = false;
    }

    if (!isFormValid) {
        setTimeout(function () {
            $('.warning-message').hide();
        }, 1500);
        return;
    }

    $.ajax({
        url: '/adminLoginOk',
        type: 'POST',
        data: {
            admin_id: $(adminId).val(),
            admin_pwd: $(adminPwd).val()
        },
        dataType: 'json',
        success: function (response) {
            if (response.status === '성공!') {
                window.location.href = '/adminSessionKeep';
            } else {
                alert('비정상적인 접근이 감지되었습니다. 이 활동은 기록되며 필요한 경우 조치가 취해질 수 있습니다.');
            }
        },
        error: function () {
            alert('로그인 중 서버에 문제가 발생했습니다.');
        }
    });
});

$(document).ready(function () {
    $('#userLogin').click(function () {
        $('#userLoginForm').show();
        $('#providerLoginForm').hide();
    });

    $('#providerLogin').click(function () {
        $('#userLoginForm').hide();
        $('#providerLoginForm').show();
    });
});
  