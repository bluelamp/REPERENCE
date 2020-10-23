class UserList {
    static getElement = function() {
        return document.body.querySelector("#userlist");
    }
    static getSelectedUserCount = function() {
        return UserList.getSeletedUsers().length;
    }
    static getSeletedUsers = function() {
        let table = UserList.getElement().querySelector("table");
        return table.querySelectorAll("tr.selected");
    }
}