function checkCustomization() {
    var customizationNode = $("#customization");
    //检查所有单选是否已经选择过
    //var undefinedSingleSelectionNodeList = customizationNode.find(".optionGroup.singleSelection[data-option-number=undefined]");
    var singleSelectionNodeList = customizationNode.find(".optionGroup.singleSelection");
    var undefinedSingleSelectionNodeList = new Array();
    singleSelectionNodeList.each(function (i, n) {
        if ($(n).data("option-number") == undefined) {
            undefinedSingleSelectionNodeList.push(n);
        }
    });
    if (undefinedSingleSelectionNodeList.length == 0) {
        return true;
    } else {
        return undefinedSingleSelectionNodeList;
    }
}

//获取sku的input节点
function getSelectedSKUNode() {
    var serialNumber = "";
    $(".optionGroup").each(function () {
        serialNumber = serialNumber + $(this).find(".option.selected").data("number") + "@";
    });
    return $("#skuList .sku[data-number='" + serialNumber + "']");
}

//刷新所选sku的信息
function refreshSKUInfo() {
    var skuNode = getSelectedSKUNode();
    //$("#summary .price").text(skuNode.data("price"));
    $("#purchase .price").text(Number(skuNode.data("price")).toFixed(2));
    $("#customization .description").text(skuNode.data("description"));
}

//初始化单选项
$(".optionGroup.singleSelection .option").click(function () {
    $(this).siblings(".selected").removeClass("selected");
    $(this).addClass("selected");
    $(this).closest(".optionGroup").data("option-number", $(this).data("number"));
    if (checkCustomization() == true) {
        refreshSKUInfo();
    }
});

//加入购物车按钮
$("#addToCart").click(function () {
    if ($(this).hasClass("disable")) {
        return false;
    }

    var productSKUId = "";
    if ($("#customization").data("customizable") == true) {
        var checkedResult = checkCustomization();
        if (checkedResult == true) {
            productSKUId = getSelectedSKUNode().val();
        } else {
            var firstUndefined = $(checkedResult[0]);
            var offsetTop = firstUndefined.offset().top;
            $("body").scrollTop(offsetTop - $(window).height() + firstUndefined.height() + $("#bottomArea").outerHeight());
            showMessage("请选择" + firstUndefined.find(".name").data("name"));
            return false;
        }
    } else {
        productSKUId = $("#skuList .sku").val();
    }

    //var productId = $("#summary").data("product-id");
    var quantity = 1;
    var url = $(this).data("url");
    $(this).addClass("disable");
    $.ajax({
        type: "POST",
        url: url,
        dataType: "json",
        //contentType: "application/json",
        data: {
            "productSKUId": productSKUId,
            "quantity": quantity
        },
        success: function (data, status) {
            checkLogging(data, status);
            $("#bottomNav .shoppingCart .totalQuantity").slideToggle("fast", function () {
                $("#bottomNav .shoppingCart .totalQuantity").text(data.shoppingCart.totalQuantity);
                $("#bottomNav .shoppingCart .totalQuantity").slideToggle("fast", function () {
                    $("#addToCart").removeClass("disable");
                });
            });
        }
    });
});