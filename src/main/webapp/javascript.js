/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */

var req;
var isIE;
var completeField;
var completeTable;
var autoRow;

function init() {
    completeField = document.getElementById("complete-field");//возвращает из 
    //документа ссылку на элемент, который имеет атрибут id с указанным 
    //значением. В нашем случае это поле ввода текса
    completeTable = document.getElementById("complete-table");
    autoRow = document.getElementById("auto-row");
    completeTable.style.top = getElementY(autoRow) + "px";
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
        /*
         * Если взаимодействие определено как асинхронное, необходимо указать 
         * функцию обратного вызова. Функция обратного вызова для этого 
         * взаимодействия определяется при помощи следующего оператора:        
         */
        req.onreadystatechange = callback;       
        
        req.send(null);//отсылаем запрос
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

function callback() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            parseMessages(req.responseXML);
        }
    }
}

function appendComposer(firstName,lastName,composerId) {

    var row;
    var cell;
    var linkElement;

    if (isIE) {
        completeTable.style.display = 'block';
        row = completeTable.insertRow(completeTable.rows.length);
        cell = row.insertCell(0);
    } else {
        completeTable.style.display = 'table';
        row = document.createElement("tr");
        cell = document.createElement("td");
        row.appendChild(cell);
        completeTable.appendChild(row);
    }

    cell.className = "popupCell";

    linkElement = document.createElement("a");
    linkElement.className = "popupItem";
    linkElement.setAttribute("href", "autocomplete?action=lookup&id=" + composerId);
    linkElement.appendChild(document.createTextNode(firstName + " " + lastName));
    cell.appendChild(linkElement);
}

function getElementY(element){

    var targetTop = 0;

    if (element.offsetParent) {
        while (element.offsetParent) {
            targetTop += element.offsetTop;
            element = element.offsetParent;
        }
    } else if (element.y) {
        targetTop += element.y;
    }
    return targetTop;
}