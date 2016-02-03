function showMessage(message) {
    var messageNode = $("<div></div>").css({
        "position": "fixed",
        "bottom": "0",
        "padding": "10px",
        "border-radius": "4px",
        "left": "35%",
        "width": "30%",
        "text-align": "center",
        "color": "#fff",
        "font-size": "12px",
        "background-color": "RGBA(0,0,0,0.5)",
        "z-index": "999999"
    }).text(message).appendTo("body");
    messageNode.animate(
        {"bottom": "100px"},
        500,
        (function () {
            setTimeout(function () {
                messageNode.remove()
            }, 680);
        })
    );
}

function FullScreenCover(z) {
    var z_index = z;
    if (!z) {
        z_index = 999999;
    }
    this.coverNode = $("<div></div>").css({
        "position": "fixed",
        "top": "0",
        "bottom": "0",
        "right": "0",
        "left": "0",
        "background-color": "RGBA(0,0,0,0.8)",
        "display": "none",
        "z-index": z_index
    }).appendTo("body");
    this.show = function () {
        this.coverNode.css("display", "block");
    }
    this.hide = function () {
        this.coverNode.css("display", "none");
    }
    this.setZAxis = function (z) {
        this.coverNode.css("z-index", z);
    }
}
//p:[upper[, switchIn[, switchOut]]]
function CoverHelper(coverClass, p) {
    var z = 999999;
    if (p) {
        if (p.upper) {
            z = $(p.upper).css("z-index") - 1;
        } else if (p.z) {
            z = p.z;
        }
        if (p.switchIn) {
            $(p.switchIn).click(function () {
                cover.show();
            });
        }
        if (p.switchOut) {
            $(p.switchOut).click(function () {
                cover.hide();
            });
        }
    }
    var cover = new coverClass(z);
    this.show = function () {
        cover.show();
    }
    this.hide = function () {
        cover.hide();
    }
}

function PopupLayer(p) {
    var _this = this;
    this.node = $(p);
    this._initialize = function () {
        this.hide();
        if (this.node.data("lower")) {
            this.node.css("z-index", $(this.node.data("lower")).css("z-index") + 1);
        }
        $(this.node.data("switch-in")).click(function () {
            _this.show();
        });
        $(this.node.data("switch-out")).click(function () {
            _this.hide();
        });
    }
    this.show = function () {
        this.node.css("display", "block");
    }
    this.hide = function () {
        this.node.css("display", "none");
    }
    this._initialize();
}
function PopupService() {
    this.popupLayerMap = new Array();
    this.get = function(id){
        return this.popupLayerMap[id];
    }
    $(".popup").each((function(i, e){
        var p = new PopupLayer(e);
        var id = $(p).attr("id");
        this.popupLayerMap[id] = p;
    }).bind(this));
}

function checkLogging(data, status) {
    if (data.redirect) {
        var url = data.redirectURL;
        window.location.href = url;
    }
}

function go(url){
    window.location.href = url;
}
function initGoBack(){
    $(".nav-back").each(function(){
        var url = $(this).data("back-url");
        if(url){
        }
    });

}

$(function(){
    initGoBack();
});