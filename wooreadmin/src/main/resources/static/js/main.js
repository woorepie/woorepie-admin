// 승인 처리 함수
function approveItem(id, type) {
    if (confirm("정말 승인하시겠습니까?")) {
        const form = document.createElement("form")
        form.method = "POST"
        form.action = `/${type}/approve/${id}`

        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content")
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content")

        const csrfInput = document.createElement("input")
        csrfInput.type = "hidden"
        csrfInput.name = csrfHeader
        csrfInput.value = csrfToken

        form.appendChild(csrfInput)
        document.body.appendChild(form)
        form.submit()
    }
}

// 거부 처리 함수
function rejectItem(id, type) {
    if (confirm("정말 거부하시겠습니까?")) {
        const form = document.createElement("form")
        form.method = "POST"
        form.action = `/${type}/reject/${id}`

        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content")
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content")

        const csrfInput = document.createElement("input")
        csrfInput.type = "hidden"
        csrfInput.name = csrfHeader
        csrfInput.value = csrfToken

        form.appendChild(csrfInput)
        document.body.appendChild(form)
        form.submit()
    }
}
