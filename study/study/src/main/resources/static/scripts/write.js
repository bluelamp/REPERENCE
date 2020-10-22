let writeForm = window.document.body.querySelector("#write-form");
writeForm.onsubmit = function() {
    // TODO : 정규화
    if(writeForm.elements["title"].value === "") {
        alert("제목을 입력해주십시오.");
        writeForm.elements["title"].focus();
        return false;
    } else if (writeForm.elements["content"].value === "") {
        alert("내용을 입력해주십시오.");
        writeForm.elements["content"].focus();
        return false;
    } else {
        return true;
    }   
}
