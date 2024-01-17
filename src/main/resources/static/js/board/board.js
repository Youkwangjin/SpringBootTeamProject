document.querySelectorAll('.edit-button').forEach((button) => {
    button.addEventListener('click', (event) => {
        const itemId = event.target.getAttribute('data-id');
    });
});

document.querySelectorAll('.delete-button').forEach((button) => {
    button.addEventListener('click', (event) => {
        const itemId = event.target.getAttribute('data-id');
        if (confirm('정말로 삭제하시겠습니까?')) {
            fetch(`/delete/${itemId}`, {
                method: 'DELETE'
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                    } else {
                        alert('삭제 중 오류가 발생했습니다.');
                    }
                })
                .catch(error => {
                    console.error('삭제 요청 중 오류 발생:', error);
                    alert('삭제 중 오류가 발생했습니다.');
                });
        }
    });
});
