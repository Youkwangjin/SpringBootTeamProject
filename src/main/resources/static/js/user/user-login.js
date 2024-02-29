let userId = $('#userId');
let userPwd = $('#userPwd');
let userLoginBtn = $('#userLoginBtn');
let isFormValid = true;

$(document).ready(function () {
    $('form').submit(function (e) {
        e.preventDefault();
        isFormValid = true;

        if ($(userId).val() === "") {
            $('#userIdWarning').text("아이디를 입력해주세요.").show();
            isFormValid = false;
        }

        if ($(userPwd).val() === "") {
            $('#userPwdWarning').text("비밀번호를 입력해주세요.").show();
            isFormValid = false;
        }

        if (!isFormValid) {
            setTimeout(function () {
                $('.warning-message').hide();
            }, 1500);
            return;
        }
        // AJAX 요청
        $.ajax({
            url: '/user/login',
            type: 'POST',
            data: {
                user_id: $(userId).val(),
                user_pwd: $(userPwd).val()
            },
            dataType: 'json',
            success: function (response) {
                if (response.status === '성공!') { // 백엔드 로직에서 put(key, value)값 동일하게 하기!
                    window.location.href = '/user/mypage';
                } else {
                    if (response.message.includes("아이디", "비밀번호")) {
                        $('#userIdWarning').text(response.message).show();
                        $('#userPwdWarning').text(response.message).show();
                    } else if (response.message.includes("비밀번호")) {
                        $('#userPwdWarning').text(response.message).show();
                    }
                }
            },
            error: function () {
                alert('로그인 중 서버에 문제가 발생했습니다.');
            }
        });
    });
    $(userLoginBtn).on('click', function () {
        $('form').submit();
    });
});

