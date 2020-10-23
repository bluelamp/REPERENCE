//HTML, CSS, JS, Java, Spring Boot, Spring, DBMS, (*React 추천 == Vue.js, Angluar.js)

window.addEventListener("load", function() {
    function attachEvents() {
       function attachLoginEvents() {
        let btnLogin = window.document.body.querySelector("#button-login");
        if(btnLogin !== null) {
            btnLogin.addEventListener('click', function() {
                let inputLoginEmail = document.querySelector("#input-login-email");
                let inputLoginPassword = document.querySelector("#input-login-password");

                //regexlib.com
                let emailRegex = new RegExp("^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+(?:[a-zA-Z]{2}|aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel)$");
                let passwordRex = new RegExp("^([0-9a-zA-Z~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:'\",<.>/?]{4,100})$");
                // 자바 : [문자열].matches([정규식]) <boolean>
                // JS : [정규식].test([문자열]) <boolean>

                if(!emailRegex.test(inputLoginEmail.value)) {
        //            alert("올바른 이메일을 입력해주세요.");
                    inputLoginEmail.focus();
                    inputLoginEmail.classList.add("shake");
                } else if(!passwordRex.test(inputLoginPassword.value)) {
        //            alert("올바른 비밀번호를 입력해주세요.");
                    inputLoginPassword.focus();
                    inputLoginPassword.classList.add("shake");
                } else {
                    function callback(responseText) {
                        if (responseText === "NORMALIZATION_FAILURE" || responseText === "LOGIN_FAILURE") {
                            alert("올바른 이메일 혹은 비밀번호를 입력해주세요.");
                        } else if(responseText === "LIMIT_EXCEEDED") {
                            alert("로그인 시도 횟수가 초과 하였습니다.");
                        } else if(responseText === "IP_BLOCKED") {
                            alert("차단된 ip 입니다. 1분뒤에 다시 시도해주세요.")
                        }else if(responseText === "LOGIN_SUCCESS") {
                            alert("로그인 성공");
                            window.location.reload();
                        } else {
                            console.log(responseText);
                        }

                    }
                    function fallback() {
                        alert("로그인 도중 예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
                    }
                    let formData = new FormData();
                    formData.append("email", inputLoginEmail.value);
                    formData.append("password", inputLoginPassword.value);
                    xhr("POST", "/admin/apis/login", callback, fallback, formData);
                }
                setTimeout(function() {
                    inputLoginEmail.classList.remove("shake");
                    inputLoginPassword.classList.remove("shake");
                }, 500);
            });
        }
    //  로그아웃 처리
        let btnLogout = window.document.body.querySelector("#button-logout");
        if(btnLogout !== null) {
            console.log("로그아웃");
            btnLogout.addEventListener('click', function() {
                function callback() {
                    alert("로그아웃 성공");
                }
                function fallback() {
                    alert("로그아웃 실패");
                }
                xhr("POST", "/admin/apis/logout", callback, fallback);
                window.location.reload();
            });
        }

    //  메뉴버튼 처리
        let btnAside = document.body.querySelector("#aside_button");
        let bAsideView = true;
        if(btnAside !== null) {
            btnAside.addEventListener('click', function() {
                let asideShape = btnAside.querySelector("div.aside-button-item");
                let asideWrap = document.body.querySelector("#asideWrap");
                if(bAsideView) {
                    asideShape.classList.remove("aside-show");
                    asideWrap.style.left = "-"+asideWrap.offsetWidth+"px";
                    setTimeout(function() {asideWrap.style.display = "none"}, 500);
                    bAsideView = false;
                } else {
                    asideShape.classList.add("aside-show");
                    asideWrap.style.display = "block";
                    setTimeout(function() {asideWrap.style.left = "0"}, 1);
                    bAsideView = true;
                }

            });
        }
        }
       function attachUserListEvents() {
        let maxContactNumber = 11;
        getUsers(getCurrentPage);
        deleteUserEvent();
        addUserEvent();
        modifyUserEvent();

            // deleteUserEvent();
            function deleteUserEvent() {
                let userList = document.querySelector("#userlist");
                if(userList !== null) {
                    let deleteButton = userList.querySelector("div.del.object-item.button");
                    deleteButton.addEventListener('click', function() {
                        let userListTable = userList.querySelector("table.table");
                        let userListTableBody = userList.querySelector("tbody");
                        let rows = userListTableBody.querySelectorAll("tr");
                        let selectedCount = 0;
                        let toDelete = "";
                        let deniedAdmin = false;
                        let deniedAlreadyDelete = false;
                        for(let i =0; i< rows.length; i++) {
                            if(rows[i].classList.contains("selected")) {
                                selectedCount +=1;
                                toDelete += `${rows[i].querySelector("td:first-child").innerText},`;
                                if(rows[i].querySelector("td:nth-child(8)").innerText === "여") {
                                    deniedAdmin = true;
                                } else if(rows[i].querySelector("td:nth-child(9)").innerText === "삭제") {
                                    deniedAlreadyDelete = true;
                                }
                            }
                        }
                        if(selectedCount === 0) {
                            alert("삭제할 회원을 선택해주세요.");
                        } else {
                            if(confirm(`정말로 선택한 ${selectedCount}명의 회원을 삭제할까요?`)) {
                                if(deniedAdmin === true) {
                                    alert("관리자는 삭제할 수 없습니다.");
                                    return;
                                } else if(deniedAlreadyDelete === true) {
                                    alert("선택된 계정에서 이미 삭제된 계정이 있습니다.");
                                    return;
                                }
                                toDelete = toDelete.substring(0, toDelete.length - 1);

                                let formData = new FormData();
                                formData.append("index", toDelete);
                                function callback(response) {
                                    console.log(response);
                                    if(response === "REQUEST_FAILURE") {
                                        alert("삭제 실패");
                                    } else if(response === "REQUEST_SUCCESS") {
                                        alert("삭제 성공!");
                                        getUsers(getCurrentPage);
                                        loadCounterValues();
                                    }
                                }
                                function fallback(status) {
                                    alert("삭제 실패!!삭제 실패선택한 회원을 삭제하는 도중 예상치 못한 오류가 발생하였습니다.")
                                }
                                xhr("POST", "/admin/apis/delete-user", callback, fallback, formData);
                            }
                        }
                    })
                }
            }

            // addUserEvent();
            function addUserEvent() {
                let userList = document.querySelector("#userlist");
                let register = document.querySelector("#register");
                
                if(userList !== null) {
                    let inputContainer = register.querySelectorAll("div.input-container");
                    let addButton = userList.querySelector("div.add.object-item.button");
                    addButton.addEventListener('click', function() {    
                        if(!register.classList.contains("visible")) {
                            Cover.On();
                            register.classList.add("visible");
                            for(el of inputContainer) {
                                if(el != null) {
                                    let inputs = el.querySelector("input");
                                    if(inputs.value !== "") {
                                        inputs.value = "";
                                    }
                                }
                            }
                        }
                    })
                    let cancleButton = register.querySelector("div.register-item.buttons > div.cancle");
                    cancleButton.addEventListener('click', function() {    
                        if(register.classList.contains("visible")) {
                            Cover.Off();
                            register.classList.remove("visible");
                        }
                    })
                    let confirmButton = register.querySelector("div.register-item.buttons > div.confirm");

                    confirmButton.addEventListener('click', function() {
                        //회원가입 서비스 실행됨.
                        let emailRegex = new RegExp("^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+(?:[a-zA-Z]{2}|aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel)$");
                        let passwordRex = new RegExp("^([0-9a-zA-Z~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:'\",<.>/?]{4,100})$");
                        let form = register.querySelector("form");
                        let inputRegisterEmail = form.querySelector("#inputRegisterEmail");
                        let inputRegisterPassword = form.querySelector("#inputRegisterPassword");
                        let inputRegisterCheckPassword = form.querySelector("#inputRegisterCheckPassword");
                        let inputCheckboxAdmin = form.querySelector("#isAdmin");
                        let availableRegister = true;

                        if(inputCheckboxAdmin.checked) {
                            inputCheckboxAdmin.value = "TRUE";
                        } else {
                            inputCheckboxAdmin.value = "FALSE";
                        }

                        for(el of inputContainer) {
                            if(el != null) {
                                let inputs = el.querySelector("input");
                                if(inputs.value === "") {
                                    if(!inputs.classList.contains("shake")) {
                                        inputs.focus();
                                        inputs.classList.add("shake");
                                        inputs.style.color = "red";
                                        availableRegister = false;
                                    }
                                }
                            }
                        }
                        setTimeout(function() {
                            for(el of inputContainer) {
                                let inputs = el.querySelector("input");
                                if(inputs.classList.contains("shake")) {
                                    inputs.classList.remove("shake");
                                    inputs.style.color = "#000";
                                }
                            }
                        }, 1000);
                        
                        if(!availableRegister) {
                            //회원가입에 필요한 정보가 없다.
                            return;
                        }
                        if(!emailRegex.test(inputRegisterEmail.value)) {
                            inputRegisterEmail.focus();
                            inputRegisterEmail.classList.add("shake");
                            inputRegisterEmail.style.color = "red";
                        } else if(!passwordRex.test(inputRegisterPassword.value)) {
                            inputRegisterPassword.focus();
                            inputRegisterPassword.classList.add("shake");
                            inputRegisterPassword.style.color = "red";
                        } else if(inputRegisterPassword.value !== inputRegisterCheckPassword.value) {
                            inputRegisterPassword.focus();
                            inputRegisterPassword.classList.add("shake");
                            inputRegisterCheckPassword.classList.add("shake");
                            inputRegisterPassword.style.color = "red";
                            inputRegisterCheckPassword.style.color = "red";
                            inputRegisterPassword.value = "";
                            inputRegisterCheckPassword.value = "";
                            inputRegisterPassword.style.placeholder = "비밀번호가 서로 다릅니다"
                            inputRegisterCheckPassword.style.placeholder = "비밀번호가 서로 다릅니다"
                        } else if(inputContainer[5].querySelector("input").value.length > maxContactNumber) {
                            inputContainer[5].querySelector("input").value = inputContainer[5].querySelector("input").value.slice(0, maxContactNumber);
                            inputContainer[5].querySelector("input").classList.add("shake");
                            inputContainer[5].querySelector("input").style.color = "red";
                        }else {
                            function callback(response) {
                                if(response === "USER_EMAIL_DUPLICATE") {
                                    alert("이메일이 중복 되었습니다.");
                                    inputRegisterEmail.focus();
                                } else if(response === "USER_NICKNAME_DUPLICATE") {
                                    alert("별명이 중복 되었습니다.");
                                    inputContainer[4].querySelector("input").focus();
                                } else if(response === "USER_CONTACT_DUPLICATE") {
                                    alert("연락처가 중복 되었습니다.");
                                    inputContainer[5].querySelector("input").focus();
                                } else if(response === "USER_REGISTER_FAILURE") {
                                    alert("회원등록에 실패하였습니다. 나중에 시도하여주십시오.");
                                } else if(response === "USER_REGISTER_SUCCESS") {
                                    alert("회원가입을 축하드립니다!");
                                    getUsers(getCurrentPage);
                                    loadCounterValues();
                                    if(register.classList.contains("visible")) {
                                        Cover.Off();
                                        register.classList.remove("visible");
                                    }
                                }
                            }

                            function fallback(status) {
                                alert("회원추가 실패");
                                if(register.classList.contains("visible")) {
                                    Cover.Off();
                                    register.classList.remove("visible");
                                }
                            }

                            let formData = new FormData(form);
                            xhr("POST", "/admin/apis/register", callback, fallback, formData);
                        }
                    });
                }
            }

            //modifyUserEvent();
            function modifyUserEvent() {
                let userList = document.querySelector("#userlist");
                let modify = document.querySelector("#modify");
                
                if(userList !== null) {                    
                    let modifyButton = userList.querySelector("div.modify.object-item.button");
                    modifyButton.addEventListener('click', function() {    
                        if(UserList.getSelectedUserCount() !== 1) {
                            alert("한 명의 사용자만 수정할 수 있습니다.");
                        } else {
                            Cover.On();
                            Modify.show(UserList.getSeletedUsers()[0]);
                        }
                    });
                    Modify.attachEvents()
                    getUsers(getCurrentPage);
                    loadCounterValues();
                }
            }
        }
       attachLoginEvents();
       attachUserListEvents();
    }

    attachEvents();
    loadCounterValues();

})

// getCurrentPage
let getCurrentPage = 1;    

// Users Count is Loaded from database
function loadCounterValues() {
    let counterWrapper = document.body.querySelector("#counter-wrapper");
    if(counterWrapper !== null) {
        let divcounter = counterWrapper.querySelectorAll("div.counter");

//      전체 회원
//        let total = counterWrapper.querySelector("div.counter.total");
//        function totalCallback(response) {
//            total.querySelector("div.value").innerHTML = response;
//        }
//        function totalFallback(status) {
//            total.querySelector("div.value").innerHTML = "#";
//        }
//        xhr("POST", "/admin/apis/counter/get_total_member_count", totalCallback, totalFallback);
//      오늘 가입한 회원
//        let todayRegister = counterWrapper.querySelector("div.counter.register");
//        function todayRegisterCallback(response) {
//            todayRegister.querySelector("div.value").innerHTML = response;
//        }
//        function todayRegisterFallback(status) {
//            todayRegister.querySelector("div.value").innerHTML = "#";
//        }
//        xhr("POST", "/admin/apis/counter/get_today_register_count", todayRegisterCallback, todayRegisterFallback);

//      오늘 탈퇴한 회원
//        let todayWithdraw = counterWrapper.querySelector("div.counter.withdraw");
//        function todayWithdrawCallback(response) {
//            todayWithdraw.querySelector("div.value").innerHTML = response;
//        }
//        function todayWithdrawFallback(status) {
//            todayWithdraw.querySelector("div.value").innerHTML = "#";
//        }
//        xhr("POST", "/admin/apis/counter/get_today_withdraw_count", todayWithdrawCallback, todayWithdrawFallback);

//      최근 30일간 회원 동향
//        let totalThirtyDaysCount = counterWrapper.querySelector("div.counter.thirtydays");
//        function totalThirtyDaysCallback(response) {
//            if(response > 0)
//                totalThirtyDaysCount.querySelector("div.value").innerHTML = "+ "+response;
//            else if (response < 0)
//                totalThirtyDaysCount.querySelector("div.value").innerHTML = "- "+response;
//            else
//                totalThirtyDaysCount.querySelector("div.value").innerHTML = 0;
//        }
//        function totalThirtyDaysFallback(status) {
//            totalThirtyDaysCount.querySelector("div.value").innerHTML = "#";
//        }
//        xhr("POST", "/admin/apis/counter/get_total_thirtydays_member_count", totalThirtyDaysCallback, totalThirtyDaysFallback);

        function Callback(response) {
            const memberCount = response.split(",");
            for(let index =0; index < divcounter.length; index++) {
                if(index < 3)
                    divcounter[index].querySelector("div.value").innerHTML = memberCount[index];
                else {
                    if(memberCount[index] > 0)
                        divcounter[index].querySelector("div.value").innerHTML = "+ "+memberCount[index];
                    else if(memberCount[index] < 0)
                        divcounter[index].querySelector("div.value").innerHTML = "- "+Math.abs(parseInt(memberCount[index]));
                    else
                        divcounter[index].querySelector("div.value").innerHTML = index;
                }
            }
        }
        function Fallback(status) {
            const memberCount = response.split(",");
                for(el of counter) {
                    el.querySelector("div.value").innerHTML = "#";
                }
        }
        xhr("POST", "/admin/apis/counter/get_member_count", Callback, Fallback);
    }
}

// getUsers();
function getUsers(pageNumber) {
    if( typeof(pageNumber) !== "number") {
        pageNumber=1;
    }
    function callback(response) {
        let userList = document.body.querySelector("#userlist");
        if(userList === null) return;

        let userListTable = userList.querySelector("table.table");
        let userListPage = userList.querySelector("div.button-wrapper-item.center");
        let userListTableBody = userListTable.querySelector("tbody");

        userListTableBody.innerHTML = "";

        let jsonUsers = JSON.parse(response);
//        for(let i =0; i < jsonUsers["users"].length; i++) {
//        userListTableBody.innerHTML += `
//            <tr>
//                <td>${jsonUsers["users"][i]["index"]}</td>
//                <td>${jsonUsers["users"][i]["email"]}</td>
//                <td>${jsonUsers["users"][i]["name"]}</td>
//                <td>${jsonUsers["users"][i]["nickname"]}</td>
//                <td>${jsonUsers["users"][i]["contact"]}</td>
//                <td>${jsonUsers["users"][i]["address"]}</td>
//                <td>${jsonUsers["users"][i]["birth"]}</td>
//                <td>${jsonUsers["users"][i]["isadmin"] === "true" ? "여" : "부"}</td>
//                <td>${jsonUsers["users"][i]["status"]}</td>
//            </tr>
//        `;
//        }

        for(let i =0; i < jsonUsers["users"].length; i++) {
                let tr = document.createElement("tr");
                let tdIndex = document.createElement("td");
                let tdEmail = document.createElement("td");
                let tdName = document.createElement("td");
                let tdNickname = document.createElement("td");
                let tdContact = document.createElement("td");
                let tdAddress = document.createElement("td");
                let tdBirth = document.createElement("td");
                let tdIsAdmin = document.createElement("td");
                let tdStatus = document.createElement("td");

                tdIndex.innerText = jsonUsers["users"][i]["index"];
                tdEmail.innerText = jsonUsers["users"][i]["email"];
                tdName.innerText = jsonUsers["users"][i]["name"];
                tdNickname.innerText = jsonUsers["users"][i]["nickname"];
                tdContact.innerText = jsonUsers["users"][i]["contact"];
                tdAddress.innerText = jsonUsers["users"][i]["address"];
                tdBirth.innerText = jsonUsers["users"][i]["birth"];
                tdIsAdmin.innerText = jsonUsers["users"][i]["isadmin"] === "true" ? "여" : "부";
                tdStatus.innerText = jsonUsers["users"][i]["status"];

                tr.append(tdIndex);
                tr.append(tdEmail);
                tr.append(tdName);
                tr.append(tdNickname);
                tr.append(tdContact);
                tr.append(tdAddress);
                tr.append(tdBirth);
                tr.append(tdIsAdmin);
                tr.append(tdStatus);

                tr.addEventListener('click', function() {
                    if(tr.classList.contains("selected")) {
                        tr.classList.remove("selected");
                    } else {
                        tr.classList.add("selected");
                    }
                })

                userListTableBody.append(tr);
        }

        userListPage.innerHTML = "";

        for(let i = Number(jsonUsers["startPage"]); i <= Number(jsonUsers["endPage"]); i++) {
            let divNumberButton = document.createElement("div");
            if(Number(jsonUsers["requestPage"]) === i) {
                divNumberButton.innerHTML += `
                    <div class="object-item page-number-button current">${i}</div>
                `;
            } else {
                divNumberButton.innerHTML += `
                    <div class="object-item page-number-button">${i}</div>
                `;
            }
            userListPage.append(divNumberButton);
        }

        let pages = userListPage.querySelectorAll("div.object-item.page-number-button");
        let current = 0;
        if(pages !== null) {
            for(let index in pages) {
                if(index < pages.length) {
                    if(!pages[index].classList.contains("current")) {
                        pages[index].addEventListener('click', function() {
                                getCurrentPage = parseInt(pages[index].innerHTML);
                                getUsers(getCurrentPage);
                                loadCounterValues();
                            }
                        );
                    } else {
                        current = index;
                        for(let i =0; i < pages.length ; i++) {
                            if(current !== i) {
                                let color = parseInt(255/Math.abs(current - i));
                                pages[i].style.color = "rgb("+color+","+color+","+color+")";
                            } else {
                                pages[i].style.color = "rgb(255,255,255)";
                            }
                        }
                    }
                }
            }
        }
    }
    function fallback(status) {
        console.log(status);
    }

    let formData = new FormData();
    formData.append("page", pageNumber);
    xhr("POST", "/admin/apis/get-user", callback,fallback, formData);
}

function xhr(method, url, callback, fallback, formData) {
    var xhr = new XMLHttpRequest();
    xhr.open(method, url);
    xhr.onreadystatechange = function() {
        if(xhr.readyState === XMLHttpRequest.DONE) {
            if(xhr.status >= 200 && xhr.status < 300){
                callback(xhr.responseText);
            }else{
                fallback();
            }
        }
    }
    if(typeof(formData) === "undefined") {
        xhr.send();
    } else {
        xhr.send(formData);
    }
}