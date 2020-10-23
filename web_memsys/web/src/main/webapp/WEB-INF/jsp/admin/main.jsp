<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import= "com.memsys.web.vos.UserVo"%>
<%
    Object Obj = request.getSession().getAttribute("UserVo");
    UserVo userVo = Obj instanceof UserVo ? (UserVo) Obj : null;
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="robots" content ="noindex, nofollow">
        <link rel="stylesheet" href="/admin/resources/stylesheets/dev.css">
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
        <script defer src="/admin/resources/scripts/dev.user-list-handler.js"></script>
        <script defer src="/admin/resources/scripts/dev.user-cover-handler.js"></script>
        <script defer src="/admin/resources/scripts/dev.user-modify-handler.js"></script>
        <script defer src="/admin/resources/scripts/dev.js"></script>
    <title>관리자 페이지 - Memsys</title>
</head>
<body>
    <div class="topWrap">
        <div class="titleWrap">
            <div class="title-item logo">Logo</div>
            <div class="title-item title">ios-null</div>
            <div class="title-item aside-button" id="aside_button">
                <div class="aside-button-item aside-show"></div>
            </div>
        </div>
        <nav class="navWrap">   
            <div class="nav-item search-bar">
                <input type="text" class="search-bar-item input" id="search-bar-input-item" placeholder="Search">
                <div class="search-bar-item button">Go</div>
            </div>
            <div class="nav-item spaces"></div>
            <div class="nav-item sidebuttons">
                <div class="sidebutton-item alarm">알림</div>
                <div class="sidebutton-item chat">채팅</div>
                <div class="sidebutton-item entire-menus">전체 메뉴</div>
            </div>
        </nav>
    </div>
    <div class="middleWrap">
        <aside class="asideWrap" id="asideWrap">
            <div class="aside-item profile">
                <div class="profile-item photo">
                    <div class="profile-photo-item outline"></div>
                    <div class="profile-photo-item photo"><img src="/admin/resources/medias/profile.jpg" alt="프로필사진"></div>
                </div>
                <div class="profile-item name"><%= userVo.getName()%></div>
                <div class="profile-item content">$ 10,000</div>
            </div>
            <div class="aside-item menus main">
                main
                <div class="aside-menu-item main-menu-item dashboard">dashboard</div>
                <div class="aside-menu-item main-menu-item sample-pages">sample pages</div>
                <div class="aside-menu-item main-menu-item ui-elements">ui elements</div>
                <div class="aside-menu-item main-menu-item forms">forms</div>
                <div class="aside-menu-item main-menu-item advanced-tables">advanced tables</div>
                <div class="aside-menu-item main-menu-item charts">charts</div>
                <div class="aside-menu-item main-menu-item icons">icons</div>
            </div>
            <div class="aside-item splitter"></div>
            <div class="aside-item menus app">
                app
                <div class="aside-menu-item app-menu-item email">email</div>
                <div class="aside-menu-item app-menu-item kaban-board">kaban-board</div>
                <div class="aside-menu-item app-menu-item calendar">calendar</div>
            </div>
            <div class="aside-item splitter"></div>
            <div class="aside-item menus docs">
                docs
                <div class="aside-menu-item docs-menu-item documentation">documentation</div>
            </div>
        </aside>
        <div class="mainWrap">
            <div class="main-item">
                <div id="counter-wrapper" class="content-item counter-wrapper">
                    <div class="counter-wrapper-item total counter">
                        <!-- 전체 회원수 -->
                        <div class="counter-item title">전체회원수</div>
                        <div class="counter-item value">0</div>
                    </div>
                    <div class="counter-wrapper-item register counter">
                        <!-- 오늘 가입한 회원수 -->
                        <div class="counter-item title">오늘 가입한 회원수</div>
                        <div class="counter-item value">0</div>
                    </div>
                    <div class="counter-wrapper-item withdraw counter">
                        <!-- 오늘 탈퇴한 회원수 -->
                        <div class="counter-item title">오늘 탈퇴한 회원수</div>
                        <div class="counter-item value">0</div>
                    </div>
                    <div class="counter-wrapper-item thirtydays counter">
                        <!-- 지난 30일 누적 = 지난30일간 가입한 회원수 - 지난 30간 탈퇴한 회원 수 -->
                        <div class="counter-item title">30일 누적</div>
                        <div class="counter-item value">+ 0</div>
                    </div>
                </div>
                <div class="content-item user-list" id="userlist">
                    <div class="user-list-item list-wrapper object-item frame">
                        <div class="list-wrapper-item title">회원목록</div>
                        <table class="list-wrapper-item table">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>이메일</th>
                                    <th>이름</th>
                                    <th>닉네임</th>
                                    <th>연락처</th>
                                    <th>주소</th>
                                    <th>생년월일</th>
                                    <th>관리자</th>
                                    <th>상태</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>admin@sample.com</td>
                                    <td>관리자</td>
                                    <td>관리자</td>
                                    <td>01011112222</td>
                                    <td>대구광역시 중구</td>
                                    <td>900101</td>
                                    <td>여</td>  <!--여 / 부-->
                                    <td>정상</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    
                    <div class="user-list-item button-wrapper">
                        <div class="button-wrapper-item left object-item button skyblue modify">
                            수정
                        </div>
                        <div class="button-wrapper-item center">
                            <div class="object-item page-number-button current">1</div>
                            <div class="object-item page-number-button">2</div>
                            <div class="object-item page-number-button">3</div>
                            <div class="object-item page-number-button">4</div>
                            <div class="object-item page-number-button">5</div>
                            <div class="object-item page-number-button">6</div>
                            <div class="object-item page-number-button">7</div>
                            <div class="object-item page-number-button">8</div>
                            <div class="object-item page-number-button">9</div>
                            <div class="object-item page-number-button">10</div>
                            <div class="object-item page-number-button">11</div>
                        </div>
                        <div class="button-wrapper-item right">
                            <div class="right-item del object-item button lightred">삭제</div>
                            <div class="right-item add object-item button lightgreen">추가</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="object-item cover" id="cover"></div>
    <jsp:include page="main.register.jsp" />
    <jsp:include page="main.modify.jsp" />
</body>
</html>