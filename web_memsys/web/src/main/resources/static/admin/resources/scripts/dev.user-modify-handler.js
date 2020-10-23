class Modify {
    static getElement = function() {
        return window.document.body.querySelector("#modify");
    }

    static show = function(tr) {
        let modify = Modify.getElement();
        let form = modify.querySelector("form");
        let tds = tr.querySelectorAll("td");
        form.elements["index"].value = tds[0].innerText;
        form.elements["email"].value = tds[1].innerText;
        form.elements["password"].value = "";
        form.elements["checkPassword"].value = "";
        form.elements["name"].value = tds[2].innerText;
        form.elements["nickname"].value = tds[3].innerText;
        form.elements["contact"].value = tds[4].innerText;
        form.elements["address"].value = tds[5].innerText;
        let temp = "19"+tds[6].innerText;
        let after = temp.substring(0,4) +"-"+
        ((Number(temp.substring(4,6))<=12 && Number(temp.substring(4,6))>=1)? temp.substring(4,6):"01") +"-"+
        ((Number(temp.substring(6,8))<=30 && Number(temp.substring(6,8))>=1)? temp.substring(6,8):"01");
        form.elements["birth"].valueAsDate = new Date(after);
        if(tds[7].innerText === "여")
            form.elements["is_admin"].checked = true;
        else
            form.elements["is_admin"].checked = false;
        
        if(tds[8].innerText === "정상")
            form.elements["status"].querySelectorAll("option")[0].selected = "selected";
        else if(tds[8].innerText === "정지")
            form.elements["status"].querySelectorAll("option")[1].selected = "selected";
        else if(tds[8].innerText === "삭제")
            form.elements["status"].querySelectorAll("option")[2].selected = "selected";
        else if(tds[8].innerText === "관리자")
            form.elements["status"].querySelectorAll("option")[0].selected = "selected";
        

        if(!modify.classList.contains("visible")) {
            modify.classList.add("visible");
        }
    }
    static hide = function() {
        let modify = Modify.getElement();
        if(modify.classList.contains("visible")) {
            modify.classList.remove("visible");
        }
    }

    static attachEvents = function() {
        let maxContactNumber = 11;
        let modify = Modify.getElement();
        let inputContainer = modify.querySelectorAll("div.input-container");
        let cancleButton = modify.querySelector("div.modify-item.buttons > div.cancle");
        cancleButton.addEventListener('click', function() {    
            Cover.Off();
            Modify.hide();
        })

        let confirmButton = modify.querySelector("div.modify-item.buttons > div.confirm");
        confirmButton.addEventListener('click', function() {
            //회원수정 서비스 실행됨.
            let emailRegex = new RegExp("^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+(?:[a-zA-Z]{2}|aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel)$");
            let passwordRex = new RegExp("^([0-9a-zA-Z~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:'\",<.>/?]{4,100})$");
            let form = modify.querySelector("form");
            let inputModifyEmail = form.querySelector("#inputModifyEmail");
            let inputModifyPassword = form.querySelector("#inputModifyPassword");
            let inputModifyCheckPassword = form.querySelector("#inputModifyCheckPassword");
            let inputCheckboxAdmin = form.querySelector("#isAdmin");
            let availableModify = true;

            if(inputCheckboxAdmin.checked) {
                inputCheckboxAdmin.value = "TRUE";
            } else {
                inputCheckboxAdmin.value = "FALSE";
            }

            for(const el of inputContainer) {
                if(el != null) {
                    let inputs = el.querySelector("input");
                    if(inputs.value === "") {
                        if(!inputs.classList.contains("shake")) {
                            inputs.focus();
                            inputs.classList.add("shake");
                            inputs.style.color = "red";
                            availableModify = false;
                        }
                    }
                }
            }
            setTimeout(function() {
                for(const el of inputContainer) {
                    if(el != null) {
                        let inputs = el.querySelector("input");
                        if(inputs.classList.contains("shake")) {
                            inputs.classList.remove("shake");
                            inputs.style.color = "#000";
                        }
                    }
                }
            }, 1000);
            
            if(!availableModify) {
                //회원가입에 필요한 정보가 없다.
                return;
            }
            if(!emailRegex.test(inputModifyEmail.value)) {
                inputModifyEmail.focus();
                inputModifyEmail.classList.add("shake");
                inputModifyEmail.style.color = "red";
            } else if(!passwordRex.test(inputModifyPassword.value)) {
                inputModifyPassword.focus();
                inputModifyPassword.classList.add("shake");
                inputModifyPassword.style.color = "red";
            } else if(inputModifyPassword.value !== inputModifyCheckPassword.value) {
                inputModifyPassword.focus();
                inputModifyPassword.classList.add("shake");
                inputModifyCheckPassword.classList.add("shake");
                inputModifyPassword.style.color = "red";
                inputModifyCheckPassword.style.color = "red";
                inputModifyPassword.value = "";
                inputModifyCheckPassword.value = "";
                inputModifyPassword.style.placeholder = "비밀번호가 서로 다릅니다"
                inputModifyCheckPassword.style.placeholder = "비밀번호가 서로 다릅니다"
            } else if(inputContainer[5].querySelector("input").value.length > maxContactNumber) {
                inputContainer[5].querySelector("input").value = inputContainer[5].querySelector("input").value.slice(0, maxContactNumber);
                inputContainer[5].querySelector("input").classList.add("shake");
                inputContainer[5].querySelector("input").style.color = "red";
            }else {
                function callback(response) {
                    if(response === "USER_MODIFY_FAILURE") {
                        alert("회원수정에 실패하였습니다. 나중에 시도하여주십시오.");
                    } else if(response === "USER_MODIFY_SUCCESS") {
                        alert("회원수정을 완료하였습니다.");
                        Cover.Off();
                        Modify.hide();
                        getUsers(getCurrentPage);
                        loadCounterValues();
                    }
                }

                function fallback(status) {
                    alert("회원수정 실패");
                    Cover.Off();
                    Modify.hide();
                }

                let formData = new FormData(form);
                xhr("POST", "/admin/apis/modify-user", callback, fallback, formData);
            }
        });
    }
}