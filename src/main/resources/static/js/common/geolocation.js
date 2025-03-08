function getCoordinatesFromAddress() {
    let geocoder = new google.maps.Geocoder();
    let roadAddress = document.getElementById("user_roadAddress").value.trim();
    let jibunAddress = document.getElementById("user_jibunAddress").value.trim();
    let detailAddress = document.getElementById("user_detailAddress").value.trim();
    let address = roadAddress ? roadAddress + " " + detailAddress : jibunAddress + " " + detailAddress;

    if (!address.trim()) {
        alert("주소를 입력하세요.");
        return;
    }

    geocoder.geocode({ 'address': address }, function (results, status) {
        if (status === 'OK') {
            let containerLatitude = results[0].geometry.location.lat();
            let containerLongitude = results[0].geometry.location.lng();

            document.getElementById("containerLatitude").value = containerLatitude;
            document.getElementById("containerLongitude").value = containerLongitude;
        } else {
            alert('주소를 찾을 수 없습니다.');
        }
    });
}

function validateCoordinates(latitude, longitude) {
    latitude = parseFloat(latitude);
    longitude = parseFloat(longitude);

    if (isNaN(latitude) || isNaN(longitude)) {
        alert("위도와 경도는 숫자 형식이어야 합니다.");
        return false;
    }

    const koreanRegex = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
    if (koreanRegex.test(latitude) || koreanRegex.test(longitude)) {
        alert("위도와 경도에는 한글을 입력할 수 없습니다.");
        return false;
    }

    let latDecimals = latitude.toString().split(".")[1]?.length || 0;
    let lngDecimals = longitude.toString().split(".")[1]?.length || 0;

    if (latDecimals > 7 || lngDecimals > 7) {
        alert("위도와 경도는 소수점 7자리까지만 입력 가능합니다.");
        return false;
    }

    if (latitude < -90 || latitude > 90) {
        alert("위도 값이 잘못되었습니다.");
        return false;
    }

    if (longitude < -180 || longitude > 180) {
        alert("경도 값이 잘못되었습니다.");
        return false;
    }

    return true;
}