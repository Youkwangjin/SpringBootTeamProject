let nowUtc = Date.now();
let timeOff = new Date().getTimezoneOffset() * 60000;
let today = new Date(nowUtc - timeOff).toISOString().split("T")[0];

function setMinDate() {
    document.getElementById("start-day").setAttribute("min", today);
    document.getElementById("end-day").setAttribute("min", today);
}

const bookingPriceEl = document.getElementById("booking_price");
const stringSizeEl = document.getElementById("stringSize");

function checkPrice() {
    const startDay = document.getElementById("start-day").value;
    const endDay = document.getElementById("end-day").value;
    const ar1 = startDay.split("-");
    const ar2 = endDay.split("-");
    const da1 = new Date(ar1[0], ar1[1] - 1, ar1[2]);
    const da2 = new Date(ar2[0], ar2[1] - 1, ar2[2]);
    const dateDiff = Math.abs((da2 - da1) / (24 * 60 * 60 * 1000));

    if (startDay && endDay) {
        bookingPriceEl.value = ((dateDiff * 2) * stringSizeEl.value).toLocaleString() + "원";
    }
    if (startDay && endDay && dateDiff === 0) {
        bookingPriceEl.value = (stringSizeEl.value).toLocaleString() + "원";
    }
}

function insertOpt(event) {
    event.preventDefault();

    const contNoEl = document.getElementById("cont_no");
    const userIdEl = document.getElementById("userId");
    const bookNameEl = document.getElementById("book-name");
    const startDayEl = document.getElementById("start-day");
    const endDayEl = document.getElementById("end-day");
    const bookingPriceEl = document.getElementById("booking_price");
    const selectedSizeEl = stringSizeEl.options[stringSizeEl.selectedIndex];

    if (startDayEl.value === "" || endDayEl.value === "" || contNoEl.value === "" || selectedSizeEl.value === "0") {
        alert("위 항목들을 선택해 주세요.");
        return;
    }

    if (confirm("예약하시겠습니까?")) {
        let xhr = new XMLHttpRequest();
        xhr.open("POST", "/booking/reservation", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
            if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                alert("예약되었습니다. 예약 정보를 서버에 전송했습니다.");
                window.location.href = '/';
            }
        }
        let data = `cont_no=${contNoEl.value}&user_id=${userIdEl.value}&user_name=${bookNameEl.value}&booking_date_start=${startDayEl.value}&booking_date_end=${endDayEl.value}&cont_size=${selectedSizeEl.value}&booking_price=${bookingPriceEl.value}`;

        xhr.send(data);
    } else {
        alert('예약을 취소합니다.');
    }
}

document.addEventListener("DOMContentLoaded", function () {
    setMinDate();
    const nextBtn = document.getElementById("next-btn");
    nextBtn.addEventListener("click", insertOpt);
});
