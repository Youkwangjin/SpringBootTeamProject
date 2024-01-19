let nowUtc = Date.now();
let timeOff = new Date().getTimezoneOffset() * 60000;
let today = new Date(nowUtc - timeOff).toISOString().split("T")[0];

function setMinDate() {
    document.getElementById("start-day").setAttribute("min", today);
    document.getElementById("end-day").setAttribute("min", today);
}

const intMoneyEl = document.getElementById("intMoney");
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
        intMoneyEl.value = ((dateDiff * 2) * stringSizeEl.value).toLocaleString() + "원";
    }
    if (startDay && endDay && dateDiff === 0) {
        intMoneyEl.value = (stringSizeEl.value).toLocaleString() + "원";
    }
}

function insertOpt(event) {
    event.preventDefault();

    const contNoEl = document.getElementById("cont_no");
    const userId = document.getElementById("userId").value;
    const bookName = document.getElementById("book-name").value;
    const startDay = document.getElementById("start-day").value;
    const endDay = document.getElementById("end-day").value;
    const selectedSize = stringSizeEl.options[stringSizeEl.selectedIndex].value;

    if (startDay === "" || endDay === "" || contNoEl.value === "" || selectedSize === "0") {
        alert("위 항목들을 선택해 주세요.");
    } else if (confirm("예약하시겠습니까?")) {
        alert("예약되었습니다. 예약 정보를 서버에 전송했습니다.");
        window.location.href = '/';
    } else {
        alert('예약을 취소합니다.');
    }
}

document.addEventListener("DOMContentLoaded", function () {
    setMinDate(); // 'min' 속성 설정을 적용합니다.
    const nextBtn = document.getElementById("next-btn");
    nextBtn.addEventListener("click", insertOpt);
});
