let businessNum = $('#businessNum');
let ownerPwd = $('#ownerPwd');
let ownerLoginBtn = $('#ownerLoginBtn');
let isFormValid = true;

$(document).ready(function () {
    // 폼 submit 이벤트에 대한 처리
    $('form').submit(function (e) {
        e.preventDefault();

        if ($(businessNum).val() === "") {
            $('#ownerIdWarning').text("사업자번호를 입력해주세요.").show();
            return;
        } else if ($(ownerPwd).val() === "") {
            $('#ownerPwdWarning').text("비밀번호를 입력해주세요.").show();
            return;
        }

        // AJAX 요청
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

    // 버튼 클릭 이벤트도 여전히 유지
    $(ownerLoginBtn).on('click', function () {
        $('form').submit();
    });
});
