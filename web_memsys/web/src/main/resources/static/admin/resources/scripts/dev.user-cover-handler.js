class Cover {
    static getElement = function() {
        return document.body.querySelector("#cover");
    }
    
    static On = function() {
        let cover = Cover.getElement();
        if(!cover.classList.contains("visible"))
            cover.classList.add("visible");
    }

    static Off = function() {
        let cover = Cover.getElement();
        if(cover.classList.contains("visible"))
            cover.classList.remove("visible");
    }
}