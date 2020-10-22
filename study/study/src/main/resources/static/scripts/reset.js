let resetForm = document.body.querySelector("#reset-form");
let url = new URL(window.location.href);
let result = url.searchParams.get("result");
if(result !== null) {
    if(result === "no_matching_user") {
        alert("일치하는 회원을 찾을 수 없습니다.");
        window.location.href= "/reset";
    }
    if(result === "code_nono") {
        alert("잘못된 코드 입니다.");
        window.location.href ="/reset?step=2";
    }
    if(result === "failure") {
        alert("비밀번호 변경에 실패하였습니다.");
        window.location.href ="/reset?step=3";
    }
}

if(resetForm !== null) {
    resetForm.onsubmit = function() {
        // TODO : 정규화
        if(resetForm.elements["email"].value === "") {
            alert("이메일을 입력해주세요.");
            resetForm.elements["email"].focus();
            return false;
        } else if(resetForm.elements["contact"].value === "") {
            alert("연락처를 입력해주세요.");
            resetForm.elements["contact"].focus();
            return false;
        } else {
            return true;
        }
    }
}

let resetCodeForm = window.document.body.querySelector("#reset-code-form");
if(resetCodeForm !== null) {
    resetCodeForm.onsubmit = function() {
        if(resetCodeForm.elements["code"].value === "") {
            alert("인증 번호를 입력해주세요.");
            resetCodeForm.elements["code"].focus();
            return false;
        } else {
            return true;
        }
    }
}

let resetpasswordFrom = document.body.querySelector("#reset-password-form");
resetpasswordFrom.onsubmit = function() {
    let resetPassword = resetpasswordFrom.elements["password"];
    let resetRepassword = resetpasswordFrom.elements["repassword"];

    if(resetPassword.value !== resetRepassword.value) {
        alert("비밀번호가 서로 매칭되지 않습니다.");
        return false;
    } else {
        return true;
    }
}