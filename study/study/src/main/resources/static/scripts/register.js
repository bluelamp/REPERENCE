let registerForm = document.body.querySelector("#register-form");
registerForm.onsubmit = function() {
    let registerEmail = registerForm.elements["email"];
    let registerPassword = registerForm.elements["password"];
    let registerRepassword = registerForm.elements["repassword"];
    let registerName = registerForm.elements["name"];
    let registerNickname = registerForm.elements["nickname"];
    let registerContact = registerForm.elements["contact"];
    console.log(registerPassword.value);
    if(registerPassword.value !== registerRepassword.value) {
        alert("비밀번호가 서로 매칭되지 않습니다.");
        return false;
    } else {
        return true;
    }
}