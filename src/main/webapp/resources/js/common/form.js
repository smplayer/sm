function isArray(obj) {
    return Object.prototype.toString.call(obj) === '[object Array]';
}
var StringUtils = {
    isBlank: function (s) {
        if (s && $.trim(s) != "") {
            return false;
        } else {
            return true;
        }
    }
}

var onValid = function(out) {
    if(out != "alert"){
        $(out).hide();
    }
}
var onInvalid = function (prompt, out) {
    if (out == "alert") {
        showMessage(prompt);
    } else {
        $(out).show().text(prompt);
    }
}
var onValidated = function(result, prompt, out){
    if(!result){
        onInvalid(prompt, out);
    } else {
        onValid(out);
    }
}
function validateText(t) {
    var result = true;
    //var val = JSON.parse($(t).data("val"));
    var validation = $(t).data("validation");//这里已经直接转为对象, 不用parse
    var validatorMap = {
        "required" : function(value, requirement, out){
            var result = !StringUtils.isBlank(value);
            onValidated(result, requirement.prompt, out);
            return result;
        },
        "reg" : function(value, requirement, out){
            var reg = new RegExp(requirement.constraint);
            var result = reg.test(value);
            onValidated(result, requirement.prompt, out);
            return result;

        }
    }
    if (validation) {
        var value = $(t).val();
        var requirements = validation.requirements;
        if (!isArray(requirements)) {
            requirements = [validation.requirements];
        }
        for (var i = 0; i < requirements.length; i++) {
            var requirement = requirements[i];
            var constraint = requirement.constraint;
            var validator = validatorMap[constraint];
            if(!validator){
                validator = validatorMap["reg"];
            }
            if(validator(value, requirement, validation.out) == false){
                result = false;
                break;
            }
        }
    }
    return result;
}
function validateSelect(s) {
    var result = true;
    var validation = $(s).data("validation");//这里已经直接转为对象, 不用parse
    if (validation) {
        var requirements = validation.requirements;
        var value = $(s).val();

        result = !StringUtils.isBlank(value);
        onValidated(result, requirements.prompt, validation.out);
    }
    return result;
}
function validateRadioGroup(r){
    var result = true;
    var validation = $(r).data("validation");
    if (validation) {
        var requirements = validation.requirements;
        var checked = $(r).find("input[type='radio']:checked").length > 0;
        if(checked){
            result = true;
        } else {
            result = false;
        }
        onValidated(result, requirements.prompt, validation.out);
    }
    return result;
}
function validateForm() {
    var result = true;
    $("input[type='text'], input[type='password']").each(function () {
        if (validateText(this) == false) {
            result = false;
            return false;
        }
    });
    if(result == false){
        return false;
    }
    $("select").each(function () {
        if (validateSelect(this) == false) {
            result = false;
            return false;
        }
    });
    if(result == false){
        return false;
    }
    $(".radio-group").each(function(){
        if(validateRadioGroup(this) == false){
            result = false;
            return false;
        }
    });
    return result;
}
$(function () {
    $("input[type='text'], input[type='password']").each(function () {
        var event = $(this).data("validation-event");
        $(this).bind(event, function () {
            validateText(this)
        });
    });
    $("select").each(function () {
        var event = $(this).data("validation-event");
        $(this).bind(event, function () {
            validateSelect(this)
        });
    });
    $("#submit").click(function () {
        if (validateForm() == true) {
            return true;
        }
        return false;
    });
});