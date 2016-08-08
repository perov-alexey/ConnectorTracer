function visualize(scale) {
    var divConnectors = document.getElementById("connectors");
    divConnectors.innerHTML = "";
    
    for (var i = 0; i < jsonContainer.connectors.length; i++) {
        
        // creating  connector Box
        var divSupercontainer = document.createElement("div");
        var divConnectorBox = document.createElement("div");
        var connectorWidth = jsonContainer.connectors[i].width*scale;
        var connectorHeight = jsonContainer.connectors[i].height*scale;
        var pins = jsonContainer.connectors[i].pins;
        divSupercontainer.className = "supercontainer";
        divSupercontainer.style.margin = (connectorWidth/2) + "px";
        divSupercontainer.appendChild(divConnectorBox);
        divConnectors.appendChild(divSupercontainer);
        
        divConnectorBox.className = "connectorBox";
        divConnectorBox.style.height = connectorHeight + connectorWidth + "px";
        divConnectorBox.style.width =  connectorWidth + "px";
        divConnectorBox.title = "Коннектор #" + i + ", габариты: " + jsonContainer.connectors[i].width + "x" + jsonContainer.connectors[i].height + ", контактов: " + pins.length;
        divConnectorBox.style.left = jsonContainer.connectors[i].x + "px";
        divConnectorBox.style.top = jsonContainer.connectors[i].y + "px";
        divConnectorBox.style.borderRadius = connectorWidth * 0.5 + "px";
        
        // creating channels

        createChannel("topChannel");
        createChannel("bottomChannel");
        
        function createChannel(channel) {
            var divChannel = document.createElement("div");
            var capacity;
            var maxCapasity;
            if (channel == "topChannel") {
                capacity = jsonContainer.connectors[i].topChannel.capacity;
                maxCapasity = jsonContainer.connectors[i].topChannel.maxCapacity;
            } else {
                capacity = jsonContainer.connectors[i].bottomChannel.capacity;
                maxCapasity = jsonContainer.connectors[i].bottomChannel.maxCapacity;
            }
            divSupercontainer.appendChild(divChannel);
            divChannel.className = channel;
            divChannel.title = "Нагруженность / Пропускная способность канала";
            divChannel.style.height = divConnectorBox.offsetHeight/10 + "px";
            divChannel.style.width = divConnectorBox.offsetWidth + "px";
            divChannel.style.marginLeft = "-" + divChannel.offsetWidth/2 + "px";
            if (channel == "topChannel") {
                divChannel.style.top = 4*scale+5 + "px";
            } else {
                divChannel.style.bottom = 4*scale+5 + "px";
            }
            divChannel.style.fontSize = 13*scale + "px";
            divChannel.style.borderBottom = scale + "px ridge #292522";
            divChannel.style.borderTop = scale + "px groove #292522";
            divChannel.textContent = capacity + "/" + maxCapasity;
            if (capacity/maxCapasity > 0.8) {
                divChannel.style.background = "-moz-linear-gradient(top, rgba(0,0,0,0) 0%, #FF003C 0%)";
                divChannel.style.background = "-webkit-linear-gradient(top, rgba(0,0,0,0) 0%,#FF003C 0%)";
                divChannel.style.filter = "progid:DXImageTransform.Microsoft.gradient( startColorstr='rgba(0,0,0,0)', endColorstr='#00c176',GradientType=0 )";
            } else if (capacity/maxCapasity > 0.6){
                divChannel.style.background = "-moz-linear-gradient(top, rgba(0,0,0,0) 10%, #FF8A00 40%)";
                divChannel.style.background = "-webkit-linear-gradient(top, rgba(0,0,0,0) 10%,#FF8A00 40%)";
                divChannel.style.filter = "progid:DXImageTransform.Microsoft.gradient( startColorstr='rgba(0,0,0,0)', endColorstr='#00c176',GradientType=0 )";
            } else if (capacity/maxCapasity > 0.4){
                divChannel.style.background = "-moz-linear-gradient(top, rgba(0,0,0,0) 30%, #FABE28 60%)";
                divChannel.style.background = "-webkit-linear-gradient(top, rgba(0,0,0,0) 30%,#FABE28 60%)";
                divChannel.style.filter = "progid:DXImageTransform.Microsoft.gradient( startColorstr='rgba(0,0,0,0)', endColorstr='#00c176',GradientType=0 )";
            } else if (capacity/maxCapasity > 0.2){
                divChannel.style.background = "-moz-linear-gradient(top, rgba(0,0,0,0) 50%, #88C100 80%)";
                divChannel.style.background = "-webkit-linear-gradient(top, rgba(0,0,0,0) 50%,#88C100 80%)";
                divChannel.style.filter = "progid:DXImageTransform.Microsoft.gradient( startColorstr='#463f3a', endColorstr='#00c176',GradientType=0 )";
            } else {
                divChannel.style.background = "-moz-linear-gradient(top, rgba(0,0,0,0) 85%, #00C176 100%)";
                divChannel.style.background = "-webkit-linear-gradient(top, rgba(0,0,0,0) 85%,#00C176 100%)";
                divChannel.style.filter = "progid:DXImageTransform.Microsoft.gradient( startColorstr='rgba(0,0,0,0)', endColorstr='#00C176',GradientType=0 )";
            } 
        }
        
        // creating connector's body
        var divConnector = document.createElement("div");
        divConnectorBox.appendChild(divConnector);
        divConnector.className = "connector";
        divConnector.style.width = connectorWidth + "px";
        divConnector.style.height = connectorHeight + "px";
        divConnector.style.top = connectorWidth/2 + "px";
            
        var drawPins = function(pins) {
            for ( var n = 0; n < pins.length; n++) {
                var divPin = document.createElement("div");
                divConnector.appendChild(divPin);
                divPin.className = "pin";
                divPin.id = "con"+i+"pin"+n;
                divPin.title = "Контакт #" + pins[n].number + ", координаты: [" + pins[n].x + ", " + pins[n].y + "]";
                divPin.style.width = 2*scale + "px";
                divPin.style.height = 2*scale + "px";
                divPin.style.borderRadius = 2*scale + "px";
                divPin.style.left = pins[n].x*scale + "px";
                divPin.style.top = pins[n].y*scale + "px";
            }
        };
        drawPins(pins);  
    } 
    
    var divContent = document.getElementById("content");
    var canvasLinks = document.getElementById("links");
    canvasLinks.setAttribute("width", divContent.offsetWidth);
    canvasLinks.setAttribute("height", divContent.offsetHeight);
    
    // drawing links
    for (var l = 0; l < jsonContainer.links.length; l++) {
        var startDot = jsonContainer.links[l].pins[0];
        var endDot = jsonContainer.links[l].pins[1];
        var startPin = document.getElementById("con"+startDot.connectorID+"pin"+startDot.pinID);
        var endPin = document.getElementById("con"+endDot.connectorID+"pin"+endDot.pinID);
        
        var coordinateStartPinLeft = startPin.getBoundingClientRect().left+startPin.offsetWidth/3-divContent.offsetLeft;
        var coordinateStartPinTop = startPin.getBoundingClientRect().top+startPin.offsetHeight/3-divContent.offsetTop;
        var coordinateEndtPinLeft = endPin.getBoundingClientRect().left+endPin.offsetWidth/3-divContent.offsetLeft;
        var coordinateEndPinTop = endPin.getBoundingClientRect().top+endPin.offsetHeight/3-divContent.offsetTop;
        
        var risovalka = canvasLinks.getContext("2d");
        risovalka.moveTo(coordinateStartPinLeft, coordinateStartPinTop);
        risovalka.lineTo(coordinateEndtPinLeft, coordinateEndPinTop);
        risovalka.stroke();
    }
}




