const cancelBtn = document.getElementById("cancel-btn");
const mainBtn = document.getElementById("main-btn");

cancelBtn.addEventListener("click", () => {
    if (confirm("예약을 취소하시겠습니까?")) {
        alert("취소되었습니다.");
        return "/userMypageBack";
    } else {
        alert('예약을 취소할 수 없습니다.');
        return false;
    }
});

