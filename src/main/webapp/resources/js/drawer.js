//TODO Comments!!!
//TODO Combine drawConnector, drawPin and drawChannel
//TODO Enable use strict
//"use strict";

function Drawer() {

    this.CHANNEL_WIDTH = 10;
    this.CONNECTOR_PADDING = 5;
    this.TOP_TEXT_PADDING = 5;
    this.BOTTOM_TEXT_PADDING = 40;

    Drawer.prototype.drawConnector = function(x, y, width, height) {
        var context = $(".field")[0].getContext("2d");
        context.beginPath();
        context.rect(x, y, width, height);
        context.strokeStyle = 'black';
        context.stroke();
    };

    Drawer.prototype.drawLink = function(startX, startY, endX, endY) {
        var context = $(".field")[0].getContext("2d");
        context.beginPath();
        context.moveTo(startX, startY);
        context.lineTo(endX, endY);
        context.strokeStyle = 'grey';
        context.stroke();
    };

    Drawer.prototype.drawPin = function(centerX, centerY) {
        var context = $(".field")[0].getContext("2d");
        context.beginPath();
        context.arc(centerX, centerY, 4, 0, 2 * Math.PI);
        context.fillStyle = 'black';
        context.fill();
        context.strokeStyle = 'black';
        context.stroke();
    };

    Drawer.prototype.drawChannel = function(x, y, length, full, top, text) {
        var context = $(".field")[0].getContext("2d");
        context.beginPath();
        var textPositionY;
        if (top) {
            y -= this.CONNECTOR_PADDING;
            textPositionY = y - this.TOP_TEXT_PADDING;
        } else {
            y += this.CONNECTOR_PADDING;
            textPositionY = y + this.BOTTOM_TEXT_PADDING;
        }
        context.moveTo(x, y);
        context.lineTo(x + length, y);
        context.moveTo(x, y + this.CHANNEL_WIDTH);
        context.lineTo(x + length, y + this.CHANNEL_WIDTH);
        if (full) {
            context.strokeStyle = 'red';
        } else {
            context.strokeStyle = 'green';
        }
        context.stroke();
        context.font = "30px Arial";
        if (text) {
            context.fillText(text, x + length/2 - context.measureText(text).width/2, textPositionY);
        }
    };

    Drawer.prototype.drawTrace = function(points) {
        var context = $(".field")[0].getContext("2d");
        context.beginPath();
        if (points.length > 0) {
            var startPoint = points[0];
            context.moveTo(startPoint.x, startPoint.y);
            $.each(points, function(i, point) {
                context.lineTo(point.x, point.y);
            });
        }
        context.strokeStyle = 'orange';
        context.stroke();
    };

    Drawer.prototype.clearField = function() {
        var canvas = $(".field")[0];
        var context = canvas.getContext("2d");
        context.clearRect(0, 0, canvas.width, canvas.height);
    }

}
