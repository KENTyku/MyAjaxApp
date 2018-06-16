/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */

var req;
var isIE;

function init() {
    completeField = document.getElementById("complete-field");//возвращает из 
    //документа ссылку на элемент, который имеет атрибут id с указанным 
    //значением. В нашем случае это поле ввода текса
}

function doCompletion() {
        //формируем строку для запроса к серверу
        var url = "autocomplete?action=complete&id=" + escape(completeField.value);
        //создаем объект запроса
        req = initRequest();//метод вызывающий метод (который возвращает объект
        // XMLHttpRequest или ActiveXObject)
        // 
        //конфигурируем объект запроса
        req.open("GET", url, true);//тип запроса, строка адреса, асинхронный запрос
        req.onreadystatechange = callback;
        req.send(null);
}

function initRequest() {
    if (window.XMLHttpRequest) {//если возможно вызвать метод XMLHttpRequest
        //проверка на используемый браузер (MSIE)
        if (navigator.userAgent.indexOf('MSIE') != -1) {
            isIE = true;
        }
        return new XMLHttpRequest();//то вызвваем его
        
    } else if (window.ActiveXObject) {//иначе пробуем вызвать метод для MSIE
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");//и делаем это если это возможно 
    }
}
