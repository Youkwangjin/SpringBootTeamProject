let adminId = $('#id');
let adminPwd = $('#pwd');
let btn = $('#btn');

$(btn).on('click', function () {
    if ($(adminId).val() === "") {
        $(adminId).next('label').addClass('warning');
        setTimeout(function () {
            $('label').removeClass('warning');
        }, 1500);
    } else if ($(adminPwd).val() === "") {
        $(adminPwd).next('label').addClass('warning');
        setTimeout(function () {
            $('label').removeClass('warning');
        }, 1500);
    }
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
  