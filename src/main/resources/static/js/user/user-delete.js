window.onload = function () {
    let pw1 = document.querySelector('#user_pwd');
    let pw2 = document.querySelector('#user_repwd');
    let name = document.querySelector('#user_name');
    let email = document.querySelector('#user_email');
    let tel = document.querySelector('#user_tel');
    let jumin = document.querySelector('#user_jumin');

    let pwPattern = /^.{4,}$/;
    let namePattern = /^[가-힣]{2,}$/;
    let emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    let telPattern = /^\d{3}-\d{3,4}-\d{4}$/;
    let juminPattern = /^\d{6}-\d{7}$/;

    pw1.addEventListener("focusout", checkPw);
    pw2.addEventListener("focusout", check2Pw);
    name.addEventListener("focusout", checkName);
    email.addEventListener("focusout", checkEmail);
    tel.addEventListener("focusout", checkTel);
    jumin.addEventListener("focusout", checkJumin);

    const form = document.querySelector('form');
    form.addEventListener('submit', function (event) {
        if (!isFormValid()) {
            event.preventDefault(); // 폼 제출을 막음
        }
    });

    function isFormValid() {
        let isValid = true;

        if (pw1.value === "" || !pwPattern.test(pw1.value)) {
            setErrorStyle('user_pwd');
            isValid = false;
        } else {
            resetErrorStyle('user_pwd');
        }

        if (pw2.value !== pw1.value || pw2.value === "") {
            setErrorStyle('user_repwd');
            isValid = false;
        } else {
            resetErrorStyle('user_repwd');
        }

        if (name.value === "" || !namePattern.test(name.value)) {
            setErrorStyle('user_name');
            isValid = false;
        } else {
            resetErrorStyle('user_name');
        }

        if (email.value === "" || !emailPattern.test(email.value)) {
            setErrorStyle('user_email');
            isValid = false;
        } else {
            resetErrorStyle('user_email');
        }

        if (tel.value === "" || !telPattern.test(tel.value)) {
            setErrorStyle('user_tel');
            isValid = false;
        } else {
            resetErrorStyle('user_tel');
        }

        if (jumin.value === "" || !juminPattern.test(jumin.value)) {
            setErrorStyle('user_jumin');
            isValid = false;
        } else {
            resetErrorStyle('user_jumin');
        }

        return isValid;
    }

    function checkPw() {
        let pwPattern = /^.{4,}$/;
        if (pw1.value === "") {
            setErrorStyle('user_pwd');
        } else if (!pwPattern.test(pw1.value)) {
            setErrorStyle('user_pwd');
        } else {
            resetErrorStyle('user_pwd');
        }
    }

    function check2Pw() {
        if (pw2.value === pw1.value && pw2.value !== "") {
            resetErrorStyle('user_repwd');
        } else if (pw2.value !== pw1.value) {
            setErrorStyle('user_repwd');
        }

        if (pw2.value === "") {
            setErrorStyle('user_repwd');
        }
    }

    function checkName() {
        let namePattern = /^[가-힣]{2,}$/;
        if (name.value === "") {
            setErrorStyle('user_name');
        } else if (!namePattern.test(name.value)) {
            setErrorStyle('user_name');
        } else {
            resetErrorStyle('user_name');
        }
    }

    function checkEmail() {
        let emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        if (email.value === "") {
            setErrorStyle('user_email');
        } else if (!emailPattern.test(email.value)) {
            setErrorStyle('user_email');
        } else {
            resetErrorStyle('user_email');
        }
    }

    function checkTel() {
        let telPattern = /^\d{3}-\d{3,4}-\d{4}$/;
        if (tel.value === "") {
            setErrorStyle('user_tel');
        } else if (!telPattern.test(tel.value)) {
            setErrorStyle('user_tel');
        } else {
            resetErrorStyle('user_tel');
        }
    }

    function checkJumin() {
        let juminPattern = /^\d{6}-\d{7}$/;
        if (jumin.value === "") {
            setErrorStyle('user_jumin');
        } else if (!juminPattern.test(jumin.value)) {
            setErrorStyle('user_jumin');
        } else {
            resetErrorStyle('user_jumin');
        }
    }

    function showMessage(message) {
        const messageDiv = document.createElement('div');
        messageDiv.className = 'message';
        messageDiv.textContent = message;
        // 메세지를 표시할 위치의 id를 설정해주세요.
        const messageContainer = document.getElementById('messageContainer'); // 수정 필요
        messageContainer.appendChild(messageDiv);
    }

    function setErrorStyle(elementId) {
        const element = document.getElementById(elementId);
        element.classList.add('error-input');

        const messageElement = document.getElementById(elementId + 'Message');
        messageElement.style.color = 'red';

        messageElement.textContent = '입력한 ' + element.getAttribute('placeholder') + '이(가) 올바르지 않습니다.';
    }

    function resetErrorStyle(elementId) {
        const element = document.getElementById(elementId);
        element.classList.remove('error-input'); // 'error-input' 클래스를 제거하여 초기 스타일로 복원

        // 해당 메시지 요소 초기화
        const messageElement = document.getElementById(elementId + 'Message');
        messageElement.style.color = 'black'; // 원하는 색상으로 변경 가능
        messageElement.textContent = ''; // 에러 메시지 초기화
    }
}

function user_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            var roadAddr = data.roadAddress;
            var extraRoadAddr = '';

            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                extraRoadAddr += data.bname;
            }

            if (data.buildingName !== '' && data.apartment === 'Y') {
                extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }

            if (extraRoadAddr !== '') {
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            document.getElementById('user_postcode').value = data.zonecode; // 우편번호 필드 설정 주석 처리
            document.getElementById("user_roadAddress").value = roadAddr;
            document.getElementById("user_jibunAddress").value = data.jibunAddress;

            if (roadAddr !== '') {
                document.getElementById("user_extraAddress").value = extraRoadAddr;
            } else {
                document.getElementById("user_extraAddress").value = '';
            }

            var guideTextBox = document.getElementById("guide");

            if (data.autoRoadAddress) {
                var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                guideTextBox.style.display = 'block';
            } else if (data.autoJibunAddress) {
                var expJibunAddr = data.autoJibunAddress;
                guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                guideTextBox.style.display = 'block';
            } else {
                guideTextBox.innerHTML = '';
                guideTextBox.style.display = 'none';
            }

            // 상세주소를 가져온다.
            var detailAddress = document.getElementById("user_detailAddress").value;

            // 주소를 합쳐서 출력 필드에 설정
            var combinedAddress = roadAddr + ' ' + data.jibunAddress + ' ' + detailAddress;
            document.getElementById("user_combinedAddress").value = combinedAddress;
        }
    }).open();

    // 상세주소 필드 값이 변경될 때마다 주소를 재결합하여 출력 필드에 설정
    document.getElementById("user_detailAddress").addEventListener("input", function () {
        var roadAddr = document.getElementById("user_roadAddress").value || ''; // 도로명 주소
        var jibunAddr = document.getElementById("user_jibunAddress").value || ''; // 지번 주소
        //var extraRoadAddr = document.getElementById("user_extraAddress").value || ''; // 참고항목
        var detailAddress = this.value || ''; // 상세주소

        // 주소를 합쳐서 출력 필드에 설정
        var combinedAddress = '';


        if (roadAddr) {
            combinedAddress += roadAddr + ' ';
        }

        if (jibunAddr) {
            combinedAddress += jibunAddr + ' ';
        }


        if (detailAddress) {
            combinedAddress += detailAddress;
        }

        // 앞뒤 공백 제거
        document.getElementById("user_combinedAddress").value = combinedAddress.trim();
    });
}





