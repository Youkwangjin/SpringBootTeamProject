document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll('.cancel-btn').forEach(function (button) {
        button.addEventListener('click', function () {
            let bookingIdToDelete = this.getAttribute('data-id');
            if (confirm("예약을 취소하시겠습니까?")) {
                fetch('/booking/bookDelete', {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({booking_id: bookingIdToDelete})
                })
                    .then(response => {
                        if (!response.ok) {
                            return response.json().then(data => {
                                throw new Error(data.error || 'Network response 에러');
                            });
                        }
                        return response.text().then(text => text ? JSON.parse(text) : {});
                    })
                    .then(data => {
                        alert(data.message || "취소되었습니다.");
                        window.location.reload();
                    })
                    .catch((error) => {
                        alert(error.message || '예약을 취소할 수 없습니다.');
                        console.error('Error:', error);
                    });
            }
        });
    });
});
