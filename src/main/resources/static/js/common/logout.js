function adminLogout() {
    const csrfTokenElement = document.querySelector("input[name=_csrf]");
    const csrfToken = csrfTokenElement ? csrfTokenElement.value : null;

    fetch("/api/logout", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrfToken
        },
        credentials: "include"
    })
        .then(response => {
            if (response.ok) {
                alert("로그아웃되었습니다. 메인페이지로 이동합니다.");
                window.location.href = "/";
            } else {
                alert("로그아웃에 실패했습니다. 다시 시도하거나 문제가 지속되면 관리자에게 문의하세요.");
            }
        })
        .catch(error => alert(error.message));
}