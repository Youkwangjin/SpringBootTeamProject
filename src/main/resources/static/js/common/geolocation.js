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
            let latitude = results[0].geometry.location.lat();
            let longitude = results[0].geometry.location.lng();

            document.getElementById("latitude").value = latitude;
            document.getElementById("longitude").value = longitude;
        } else {
            alert('주소를 찾을 수 없습니다.');
        }
    });
}

window.getCoordinatesFromAddress = getCoordinatesFromAddress;
