function getCoordinatesFromAddress() {
    const roadAddress   = document.getElementById("user_roadAddress").value.trim();
    const detailAddress = document.getElementById("user_detailAddress").value.trim();
    const address       = `${roadAddress} ${detailAddress}`.trim();

    if (!address) {
        alert("주소를 입력하세요.");
        return;
    }

    fetch(`/api/geocode?address=${encodeURIComponent(address)}`)
        .then(async response => {
            const data = await response.json();

            if (response.status === 200) {
                if (data.addresses && data.addresses.length > 0) {
                    const { x, y } = data.addresses[0];
                    document.getElementById("containerLatitude").value  = parseFloat(y).toFixed(7);
                    document.getElementById("containerLongitude").value = parseFloat(x).toFixed(7);
                } else {
                    alert(data.ERROR);
                }
            } else {
                alert(data.errorMsg);
            }
        })
        .catch(() => {
            alert(MSG.ERROR);
        });
}