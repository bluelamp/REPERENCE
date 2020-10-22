let myBoard = document.body.querySelector("#myboard");

let headRow = myBoard.querySelector("thead > tr");
let bodyRow = myBoard.querySelectorAll("tbody > tr");


headRow.style = "cursor: default";

bodyRow.forEach(function(el) {
    el.style = "cursor: pointer";
    let backupColor = el.style.backgroundColor;
    el.addEventListener('mouseover', function() {
        if(!el.classList.contains("selected")) {
            el.style.backgroundColor = "#aaa5";
        }
    })
    el.addEventListener('mouseleave', function() {
        if(!el.classList.contains("selected")) {
            el.style.backgroundColor = backupColor;
        }
    })

    el.addEventListener('click', function() {
        el.classList.toggle("selected");
        if(el.classList.contains("selected")) {
            el.style.backgroundColor = "#1115";
        } else {
            el.style.backgroundColor = backupColor;
        }
    })
    
})

if(window.sessionStorage.length != 0) {
    console.log(sessionStorage.getItem("UserVo"));
    let userName = sessionStorage.getItem("UserName");
    if(userName === "관리자") {
        let adminClass = myBoard.querySelectorAll(".adminClass");
        adminClass.forEach(function(el) {
            el.style.display = "table-cell";
        })
    } else {
        let adminClass = myBoard.querySelectorAll(".adminClass");
        adminClass.forEach(function(el) {
            el.style.display = "none";
        })
    }
} else {
    location.href= "/login";
}


let boardBody = myBoard.querySelector("tbody");
boardBody.innerHTML = "";

let hiddenForm = document.body.querySelector("#form-hidden");

hiddenForm.querySelectorAll("input").forEach(function(el) {
    el.onclick = function() {
        hiddenForm.elements["page"].value = el.value;
        hiddenForm.submit();
    }
});


if(jsonArray != null) {
    jsonArray.forEach(function(el, index) {
        let tr = document.createElement("tr");
        let elIndex = document.createElement("td");
        let elKategorie = document.createElement("td");
        let elTitle = document.createElement("td");
        let elNickname = document.createElement("td");
        let elCretateAt = document.createElement("td");
        let elViews = document.createElement("td");

        elIndex.innerText = jsonArray[index]["index"];
        elKategorie.innerText = jsonArray[index]["kategorie"];
        elTitle.innerText = jsonArray[index]["title"];
        elNickname.innerText = jsonArray[index]["nickName"];
        elCretateAt.innerText = jsonArray[index]["createAt"];
        elViews.innerText = jsonArray[index]["views"];

        tr.append(elIndex);
        tr.append(elKategorie);
        tr.append(elTitle);
        tr.append(elNickname);
        tr.append(elCretateAt);
        tr.append(elViews);

        boardBody.append(tr);
    });
}