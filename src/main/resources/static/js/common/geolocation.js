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

    geocoder.geocode({ "address": address }, function (results, status) {
        if (status === "OK") {
            let containerLatitude = results[0].geometry.location.lat();
            let containerLongitude = results[0].geometry.location.lng();

            containerLatitude = Number(containerLatitude.toFixed(7));
            containerLongitude = Number(containerLongitude.toFixed(7));

            document.getElementById("containerLatitude").value = containerLatitude;
            document.getElementById("containerLongitude").value = containerLongitude;
        } else {
            alert("주소를 찾을 수 없습니다.");
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

    latitude = Number(latitude.toFixed(7));
    longitude = Number(longitude.toFixed(7));

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