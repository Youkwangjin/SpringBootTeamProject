document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll('.cancel-btn').forEach(function (button) {
        button.addEventListener('click', function () {
            let bookingIdToUpdate = this.getAttribute('data-id');
            if (confirm("예약을 취소하시겠습니까?")) {
                // 예약 취소 상태 변경 AJAX 요청
                fetch('/booking/updateStatus/' + bookingIdToUpdate + '/' + encodeURIComponent('예약취소'), {
                    method: 'PUT'
                })
                    .then(response => {
                        if (!response.ok) {
                            return response.json().then(data => {
                                throw new Error(data.error || 'Network response 에러');
                            });
                        }
                        return response.json();
                    })
                    .then(data => {
                        alert(data.message || "예약이 취소되었습니다.");
                        window.location.reload();
                    })
                    .catch((error) => {
                        alert(error.message || '예약 취소에 실패했습니다.');
                        console.error('Error:', error);
                    });
            }
        });
    });
});
