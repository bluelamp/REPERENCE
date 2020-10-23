<%@ page language ="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<div id="modify" class="body-item modify object-item window">
    <div class="modify-item title">
        회원 수정
    </div>
    <div class="modify-item content">
        <form class="content-item form">
            <div class="form-item input-container">
                <input type="text" name="index" placeholder="No" maxlength="100">
            </div>
            <div class="form-item input-container">
                <input type="email" name="email" id="inputModifyEmail" placeholder="이메일" maxlength="100">
            </div>
            <div class="form-item input-container">
                <input type="password" name="password" id="inputModifyPassword" placeholder="비밀번호" maxlength="50">
            </div>
            <div class="form-item input-container">
                <input type="password" name="checkPassword" id="inputModifyCheckPassword" placeholder="비밀번호 확인" maxlength="50">
            </div>
            <div class="form-item input-container">
                <input type="text" name="name" placeholder="이름" maxlength="20">
            </div>
            <div class="form-item input-container">
                <input type="text" name="nickname" placeholder="별명" maxlength="50">
            </div>
            <div class="form-item input-container">
                <input type="number" name="contact" placeholder="연락처"  min="0" max="99999999999">
            </div>
            <div class="form-item input-container">
                <input type="text" name="address" placeholder="주소" maxlength="250">
            </div>
            <div class="form-item input-container">
                <input type="date" name="birth" placeholder="생년월일" maxlength="6">
            </div>
            <div class="form-item checkbox-container">
                <input type="checkbox" name="is_admin" class="object-slider" id="isAdmin">
                <label for="isAdmin">관리자</label>
            </div>
            <div class="form-item select-container">
                <select name="status">
                    <option value="OKY">정상</option>
                    <option value="SUS">정지</option>
                    <option value="DEL">삭제</option>
                </select>
            </div>
        </form>
    </div>
    <div class="modify-item buttons">
        <div class="object-item button lightred cancle">취소</div>
        <div class="object-item button lightgreen confirm">확인</div>
    </div>
</div>