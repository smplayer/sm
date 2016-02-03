/**
 * Created by Newbody on 11/14/2015.
 */

//p:target[lower[, switchIn[, switchOut[, zAxis]]]]
function Popup(p) {
    var _this = this;
    this.targetNode = $(p.target);
    this._initialize = function () {
        this.targetNode.css("display", "none");
        if (p.lower) {
            this.targetNode.css("z-index", $(p.lower).css("z-index") + 1);
        } else if (p.zAxis) {
            this.targetNode.css("z-index", p.zAxis);
        }
        if (p.switchIn) {
            $(p.switchIn).click(function () {
                _this.show();
            });
        }
        if (p.switchOut) {
            $(p.switchOut).click(function () {
                _this.hide();
            });
        }
    }
    this.show = function () {
        this.targetNode.css("display", "block");
    }
    this.hide = function () {
        this.targetNode.css("display", "none");
    }
    this._initialize();
}

function GlobalSlider(target, position, switchIn, switchOut) {
    var targetNode = $(target);
    var offset = new Array();
    offset["top"] = offset["bottom"] = targetNode.outerHeight(true);
    offset["left"] = offset["right"] = targetNode.outerWidth(true);

    var paramsIn = {};
    paramsIn[position] = 0;
    var paramsOut = {};
    paramsOut[position] = -offset[position];

    if (GlobalSlider.prototype.protoInitialized != true) {
        GlobalSlider.prototype.protoInitialized = true;
    }
    this.slideIn = function () {
        targetNode.css("display", "block");
        targetNode.animate(paramsIn);
    }
    this.slideOut = function () {
        targetNode.animate(paramsOut, function () {
            targetNode.css("display", "none");
        });
    }

    this._initialize = function () {
        if (switchIn) {
            $(switchIn).click(this.slideIn);
        }
        if (switchOut) {
            $(switchOut).click(this.slideOut);
        }
        var cssParams = {};
        cssParams[position] = -offset[position];
        cssParams["display"] = "none";
        targetNode.css(cssParams);
    }
    this._initialize();
}

function AreaSelector() {
    var Area = function (id, name) {
        this.id = id;
        this.name = name;
    }
    this.latestSelectedArea = null;
    this.popup = new Popup({
        target: "#areaSelector",
        lower: "#addingContact",
        switchIn: "#addingContact .areaList"
    });
    this.selectedAreaArray = new Array();
    this.locate = function (area) {
        //重新定位当前area和层次
        //1.使用gps定位功能时可以使用
        //2.返回上层区域列表重新选择时可以使用
        var id = area.id;
    }
    this.getTopLevel = function () {
        var url = $("#areaSelector").data("url-top");
        $.get(
            url,
            (function (data, status) {
                checkLogging(data, status);
                this.showAreaList(data);
            }).bind(this)
        );
    }
    this.getSubordinate = function (superiorId) {
        var url = $("#areaSelector").data("url-sub");
        $.get(
            url,
            {superiorId: superiorId},
            (function (data, status) {
                checkLogging(data, status);
                this.showAreaList(data);
            }).bind(this)
        );
    }
    this.showAreaList = function (data) {
        if ($(data).size() > 0) {
            var ul = $("#areaSelector .availableAreaList ul").empty();
            var liTemp = $("#areaSelector .template li");
            $(data).each(function (i, a) {
                var li = liTemp.clone(true);
                li.text(a.name);
                li.data("id", a.id);
                ul.append(li);
            });
        } else {
            this.popup.hide();
            this.showSelectedArea();
        }
    }
    this.showSelectedArea = function () {
        var target = $("#addingContact .areaList > .content").empty();
        if (this.selectedAreaArray.length > 0) {
            $.each(this.selectedAreaArray, function () {
                target.append(this.name);
            });
        } else {
            target.append("中国");
        }
    }
    this._initialize = function () {
        var _this = this;
        $("#areaSelector .template li").click(function () {
            _this.getSubordinate($(this).data("id"));
            var area = new Area($(this).data("id"), $(this).text());
            _this.selectedAreaArray.push(area);

            var ul = $("#areaSelector .selectedAreaList ul");
            ul.append($("<li class=\"float-left\"></li>").text($(this).text()));
        });
        $("#addingContact .areaList").click(function () {
            _this.getTopLevel();
            _this.selectedAreaArray = new Array();
            $("#areaSelector .selectedAreaList ul").empty();
        });
    }
    this._initialize();
}

function ContactCreator() {
    this.popup = new Popup({
        target: "#addingContact",
        lower: "#contactList",
        switchIn: "#contactList .clickToAdd",
        switchOut: ".btn.cancel"
    });
    this.areaSelector = new AreaSelector();
    this.add = function () {
        var node = $("#addingContact");
        var receiver = node.find("input[name='receiver']").val();
        if (!(receiver && $.trim(receiver) != "")) {
            showMessage("请输入收件人");
            return false;
        }
        var mobile = node.find("input[name='mobile']").val();
        if (!(mobile && $.trim(mobile) != "")) {
            showMessage("请输入手机号");
            return false;
        }
        var addressDetails = node.find("input[name='addressDetails']").val();
        if (!(addressDetails && $.trim(addressDetails) != "")) {
            showMessage("请输入详细地址");
            return false;
        }
        if (this.areaSelector.selectedAreaArray.length == 0) {
            showMessage("请选择所在区域");
            return false;
        }

        var areaIdList = new Array();
        for (var i in this.areaSelector.selectedAreaArray) {
            areaIdList.push(this.areaSelector.selectedAreaArray[i].id);
        }
        var url = node.data("url-add");

        $.ajax({
            type: "POST",
            url: url,
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                receiver: receiver,
                mobile: mobile,
                addressDetails: addressDetails,
                areaIdList: areaIdList
            }),
            success: (function (data, status) {
                checkLogging(data, status);
                var newContact = $("#contactList .template .contact").clone(true);
                newContact.find(".content").data("id", data.contact.id);
                newContact.find(".content").data("test1", data.contact.id);
                newContact.find(".content").data("test2", "test2");
                newContact.find(".receiver").text(data.contact.name);
                newContact.find(".mobile").text(data.contact.mobileNumber);
                newContact.find(".address").text(data.contact.fullAddress);
                $("#contactList .existing").prepend(newContact);
                this.popup.hide();
                //重置表单
            }).bind(this)
        });
    }
    $("#addingContact .btn.confirm").click(this.add.bind(this));
}

$(function () {
    new GlobalSlider("#contactList", "left", "#selectedContact", "#contactList .contact");
    new ContactCreator();

    var contactListCover = new CoverHelper(FullScreenCover, {
        upper: "#contactList",
        switchIn: "#contactArea",
        switchOut: "#contactList .item"
    });

    $("#contactList .contact .content").click(function () {
        //jquery clone不复制js生成的data属性
        //alert($(this).data("id"));
        //alert($(this).clone().data("id"));
        $("#contactList .checked").removeClass("checked");
        var contactItem = $(this).closest(".contact");
        contactItem.addClass("checked");
        $(this).clone().data("id", $(this).data("id")).replaceAll($("#selectedContact .contact .content"));
        //$("#selectedContact .contact").empty().append($(this).clone().data("id", $(this).data("id")));
    });

    $("#createOrder").click(function () {
        var contactId = $("#selectedContact .contact .content").data("id");
        if (contactId == undefined) {
            return false;
        }
        var paymentMethod = $("#paymentMethodArea input[type='radio'][checked]").val();
        var cartItemIdList = new Array();
        $("#itemList .item").each(function () {
            cartItemIdList.push($(this).data("id"));
        });
        var url = $(this).data("url");
        $.ajax({
            type: "POST",
            url: url,
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                contactId: contactId,
                paymentMethod: paymentMethod,
                cartItemIdList: cartItemIdList
            }),
            success: (function (data, status) {
                checkLogging(data, status);
                window.location.href = $(this).data("success-url") + "?orderId=" + data.order.id;
            }).bind(this)
        });
    });
});
