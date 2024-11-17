/*******************************************************
 * Daum 주소 API
 * *******************************************************/
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

            document.getElementById('user_postcode').value = data.zonecode;
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

            var detailAddress = document.getElementById("user_detailAddress").value;

            var combinedAddress = roadAddr + ' ' + data.jibunAddress + ' ' + detailAddress;
            document.getElementById("userAddress").value = combinedAddress;
        }
    }).open();

    document.getElementById("user_detailAddress").addEventListener("input", function () {
        var roadAddr = document.getElementById("user_roadAddress").value || '';
        var jibunAddr = document.getElementById("user_jibunAddress").value || '';
        //var extraRoadAddr = document.getElementById("user_extraAddress").value || '';
        var detailAddress = this.value || '';

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

        document.getElementById("userAddress").value = combinedAddress.trim();
    });
}