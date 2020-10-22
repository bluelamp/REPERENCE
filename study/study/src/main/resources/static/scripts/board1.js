
let url = new URL(window.location.href);
let result = url.searchParams.get("result");
if(result !== null) {
    if(result === "no_matching_id") {
        alert("존재하지 않는 게시판입니다.");
        url.searchParams.delete("result");
        window.location.href = url;
    } else if(result === "not_allowed") {
        alert("게시글을 작성할 권한이 없습니다.");
        url.searchParams.delete("result");
        window.location.href = url;
    }
}

let searchForm = document.body.querySelector("#search-form");
searchForm.onsubmit = function() {
    if(searchForm.elements["keyword"].value === "") {
        alert("검색어를 입력해주세요.");
        searchForm.elements["keyword"].focus();
        return false;
    }  else {
        return true;
    }
}
